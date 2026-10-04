
import { useState } from "react";
import { Link } from "react-router-dom";
import OrderCard from "../../../components/OrderCard/OrderCard";
import "./Orders.css";

function Orders() {
  const [activeFilter, setActiveFilter] = useState("All");

  const orders = [
    {
      id: "ORD-10245",
      orderDate: "16 Sep 2026",
      status: "Delivered",
      totalAmount: 3499,
      items: [
        {
          name: "Wireless Headphones",
          price: 3499,
          quantity: 1,
          image: null
        }
      ]
    },
    {
      id: "ORD-10238",
      orderDate: "13 Sep 2026",
      status: "Shipped",
      totalAmount: 5998,
      items: [
        {
          name: "Mechanical Keyboard",
          price: 2999,
          quantity: 1,
          image: null
        },
        {
          name: "Wireless Mouse",
          price: 799,
          quantity: 2,
          image: null
        }
      ]
    },
    {
      id: "ORD-10221",
      orderDate: "09 Sep 2026",
      status: "Processing",
      totalAmount: 4999,
      items: [
        {
          name: "Smart Watch",
          price: 4999,
          quantity: 1,
          image: null
        }
      ]
    },
    {
      id: "ORD-10195",
      orderDate: "02 Sep 2026",
      status: "Cancelled",
      totalAmount: 2999,
      items: [
        {
          name: "Mechanical Keyboard",
          price: 2999,
          quantity: 1,
          image: null
        }
      ]
    }
  ];

  const filters = [
    "All",
    "Processing",
    "Shipped",
    "Delivered",
    "Cancelled"
  ];

  const filteredOrders =
    activeFilter === "All"
      ? orders
      : orders.filter(
          (order) =>
            order.status.toLowerCase() ===
            activeFilter.toLowerCase()
        );

  return (
    <div className="orders-page">

      {/* Header */}

      <div className="orders-header">
        <div>
          <h1>My Orders</h1>
          <p>
            Track and manage all your orders in one place.
          </p>
        </div>

        <Link to="/products" className="shop-more-btn">
          Continue Shopping →
        </Link>
      </div>

      {/* Order Stats */}

      <div className="order-stats">

        <div className="order-stat-card">
          <div className="order-stat-icon">📦</div>

          <div>
            <strong>{orders.length}</strong>
            <span>Total Orders</span>
          </div>
        </div>

        <div className="order-stat-card">
          <div className="order-stat-icon">🚚</div>

          <div>
            <strong>
              {
                orders.filter(
                  (order) =>
                    order.status === "Shipped" ||
                    order.status === "Processing"
                ).length
              }
            </strong>
            <span>Active Orders</span>
          </div>
        </div>

        <div className="order-stat-card">
          <div className="order-stat-icon">✓</div>

          <div>
            <strong>
              {
                orders.filter(
                  (order) => order.status === "Delivered"
                ).length
              }
            </strong>
            <span>Delivered</span>
          </div>
        </div>

        <div className="order-stat-card">
          <div className="order-stat-icon">₹</div>

          <div>
            <strong>
              ₹
              {orders
                .reduce(
                  (total, order) =>
                    total + order.totalAmount,
                  0
                )
                .toLocaleString("en-IN")}
            </strong>
            <span>Total Spent</span>
          </div>
        </div>

      </div>

      {/* Filters */}

      <div className="orders-toolbar">

        <div className="order-filters">
          {filters.map((filter) => (
            <button
              key={filter}
              className={
                activeFilter === filter
                  ? "filter-btn active"
                  : "filter-btn"
              }
              onClick={() => setActiveFilter(filter)}
            >
              {filter}

              {filter !== "All" && (
                <span>
                  {
                    orders.filter(
                      (order) =>
                        order.status.toLowerCase() ===
                        filter.toLowerCase()
                    ).length
                  }
                </span>
              )}
            </button>
          ))}
        </div>

        <select className="order-sort">
          <option>Recent First</option>
          <option>Oldest First</option>
          <option>Highest Amount</option>
          <option>Lowest Amount</option>
        </select>

      </div>

      {/* Orders */}

      {filteredOrders.length > 0 ? (
        <div className="orders-list">

          {filteredOrders.map((order) => (
            <OrderCard
              key={order.id}
              order={order}
            />
          ))}

        </div>
      ) : (
        <div className="no-orders">

          <div className="no-orders-icon">
            📦
          </div>

          <h2>No {activeFilter.toLowerCase()} orders</h2>

          <p>
            You don't have any orders in this category.
          </p>

          <Link to="/products" className="shop-now-btn">
            Start Shopping
          </Link>

        </div>
      )}

    </div>
  );
}

export default Orders;

