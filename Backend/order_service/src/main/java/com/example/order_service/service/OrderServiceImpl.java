package com.example.order_service.service;

import com.example.order_service.client.CartClient;
import com.example.order_service.client.UserClient;
import com.example.order_service.dto.*;
import com.example.order_service.entity.Order;
import com.example.order_service.entity.OrderItem;
import com.example.order_service.enums.OrderStatus;
import com.example.order_service.enums.PaymentMethod;
import com.example.order_service.enums.PaymentStatus;
import com.example.order_service.enums.ShipmentStatus;
import com.example.order_service.redis.IdempotencyService;
import com.example.order_service.redis.RedisLockService;
import com.example.order_service.redis.RedisOrderRepository;
import com.example.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final CartServiceClient cartServiceClient;
    private final OrderRepository orderRepository;
    private final UserAddressServiceClient userAddressServiceClient;
    private final OrderEventProducer orderEventProducer;
    private final ShippingEventProducer shippingEventProducer;
    private final NotificationEventProducer notificationEventProducer;
    private final RedisOrderRepository redisOrderRepository;
    private final IdempotencyService idempotencyService;
    private final RedisLockService redisLockService;
    private static final Logger log= LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public OrderResponse placeOrder(CreateOrderRequest request, String idempotencyKey) {
        log.info("Create Order Request");
        log.info("Checking idempotency");
        Boolean acquired=idempotencyService.tryAcquire(idempotencyKey);
        if(!acquired){
            log.info("Idempotency not acquired");
            Object value=idempotencyService.get(idempotencyKey);
            if(value==null){
                log.error("Idempotency key not found");
                throw new RuntimeException("Unable to process idempotent request");
            }
            if ("PROCESSING".equals(value.toString())) {
                log.error("Idempotency key already processed");
                throw new RuntimeException(
                        "Order request is already being processed"
                );
            }

            Long existingOrderId =
                    Long.valueOf(value.toString());
            log.error("Idempotency already found and return order request");
            return getOrderById(existingOrderId);
        }
        log.info("Idempotency checked");
        log.info("getting cart");
        CartResponse  cartResponse = cartServiceClient.getCart(request.getUserId());
        log.info("cart fetched");
        log.info("getting address");
        AddressResponse addressResponse=userAddressServiceClient.getAddress(request.getUserId(),  request.getAddressId());
        log.info("address fetched");
        List<OrderItem> orderItems=new ArrayList<>();
        for(CartItem cartItem:cartResponse.getCartItems()){
            OrderItem orderItem1=OrderItem.builder()
                    .productId(cartItem.getProductId())
                    .quantity(cartItem.getQuantity())
                    .totalPrice(cartItem.getPrice()*cartItem.getQuantity())
                    .productName(cartItem.getProductName())
                    .productPrice(cartItem.getPrice())
                    .build();
            orderItems.add(orderItem1);
        }

        Order order= Order.builder()
                .userId(request.getUserId())
                .orderDate(LocalDateTime.now())
                .orderItems(orderItems)
                .orderStatus(OrderStatus.SHIPPED)
                .totalAmount(cartResponse.getTotalPrice())
                .address(addressResponse.toString())
                .paymentStatus(PaymentStatus.PENDING)
                .build();
        log.info("order saving to db");
        Order savedOrder=orderRepository.save(order);
        log.info("order saved to db");
        log.info("acquiring lock to products inventory");
        for(OrderItem orderItem:orderItems){
            redisLockService.acquireLock(orderItem.getProductId());
        }
        log.info("lock acquired");
        if(request.getPaymentMethod()!= PaymentMethod.COD) {
            log.info("publishing order created event for payment service");
            orderEventProducer.publishOrderCreatedEvent(new OrderCreatedEvent(savedOrder.getOrderId(), savedOrder.getUserId(), savedOrder.getTotalAmount(), request.getPaymentMethod()));
            log.info("published order updated event for payment service");
        }
        log.info("sending notification event");
        notificationEventProducer.sendNotificationEvent(new NotificationRequest(savedOrder.getOrderId(),OrderStatus.SHIPPED));
        log.info("Notification event produced");
        log.info("Releasing lock on items");
        for(OrderItem orderItem:orderItems){
            redisLockService.releaseLock(orderItem.getProductId());
        }
        log.info("Lock released");


        OrderResponse response=mapToResponse(savedOrder);
        log.info("Saving order to redis");
        redisOrderRepository.save(response);
        log.info("Order saved to redis");
        log.info("Marking idempotency to completed");
        idempotencyService.markCompleted(idempotencyKey, savedOrder.getOrderId());
        log.info("Marked idempotency to completed");
        log.info("Order placed");
        return response;
    }


    @Override
    public List<OrderResponse> getOrdersByUser(Long userId) {
        log.info("getOrderByUser called");
        log.info("Getting orders");
        List<OrderResponse> orders=orderRepository.findByUserId(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
        log.info("Orders fetched");
        log.info("getOrdersByUser completed");
        return orders;
    }


    @Override
    public OrderResponse getOrderById(Long orderId) {
        log.info("getOrderById called");
        log.info("Fetching order via redis");
        OrderResponse response=redisOrderRepository.get(orderId);
        if(response!=null){
            log.info("Order fetched via redis");
            return (OrderResponse) response;
        }
        log.info("order not is redis");
        log.info("Fetching order via db");
        Order order=orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order Not Found"));
        log.info("Order fetched via db");
        OrderResponse response1=mapToResponse(order);
        log.info("Order saving in redis");
        redisOrderRepository.save(response1);
        log.info("Order saved in redis");
        log.info("getOrderById completed");
        return response1;
    }

    @Override
    public void cancelOrder(Long orderId) {
        log.info("CancelOrder called");
        log.info("Checking order in db");
        Order order=orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order Not Found"));
        log.info("Order found in db");
        log.info("Deleting order");
        orderRepository.delete(order);
        log.info("Order deleted");
        redisOrderRepository.delete(orderId);
        log.info("CancelOrder completed");
    }

    @Override
    public OrderResponse changeOrderStatus(Long orderId, OrderStatus orderStatus) {
        log.info("changeOrderStatus called");
        log.info("Checking order in db");
        Order order=orderRepository.findById(orderId).orElseThrow(()->new RuntimeException("Order Not Found"));
        log.info("Order found in db");
        order.setOrderStatus(orderStatus);

        log.info("Saving order in db");
        Order savedOrder=orderRepository.save(order);
        log.info("Order saved in db");
        OrderResponse orderResponse=mapToResponse(savedOrder);
        log.info("Saving order in redis");
        redisOrderRepository.save(orderResponse);
        log.info("Order saved in redis");
        log.info("getOrderById completed");
        return orderResponse;
    }

    @Override
    public OrderResponse shipOrder(PaymentCompletedEvent paymentCompletedEvent) {
        log.info("shipOrder called");

        log.info("Checking order in db");
        Order order=orderRepository.findById(paymentCompletedEvent.getOrderId()).orElseThrow(()->new RuntimeException("Order Not Found"));
        log.info("Order found in db");
        if(paymentCompletedEvent.getPaymentStatus().equals(PaymentStatus.FAILED)){
            throw new RuntimeException("Payment Failed please try again");
        }

        order.setOrderStatus(OrderStatus.SHIPPED);
        order.setPaymentStatus(PaymentStatus.SUCCESS);
        Order savedOrder=orderRepository.save(order);

        ShippingCreatedEvent shippingCreatedEvent=new ShippingCreatedEvent();
        shippingCreatedEvent.setOrderId(order.getOrderId());
        shippingCreatedEvent.setShippingAddress(order.getAddress());
        shippingCreatedEvent.setStatus(ShipmentStatus.CREATED);
        shippingCreatedEvent.setCourierPartner("XYZ Courier Service");
        shippingCreatedEvent.setCustomerName("Username");
        log.info("Shipping created");
        shippingEventProducer.sendShippingCreatedEvent(shippingCreatedEvent);
        log.info("Shipping created");
        OrderResponse orderResponse=mapToResponse(savedOrder);
        log.info("Saving order in redis");
        redisOrderRepository.save(orderResponse);
        log.info("Order saved in redis");
        log.info("getOrderById completed");
        return orderResponse;

    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .totalAmount(order.getTotalAmount())
                .orderStatus(order.getOrderStatus())
                .orderDate(order.getOrderDate())
                .orderItems(order.getOrderItems())
                .build();
    }
}
