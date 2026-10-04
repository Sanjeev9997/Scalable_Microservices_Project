
import { Link } from "react-router-dom";

import "./Cart.css";
import { useCart } from "../../../context/CartContext";

function Cart() {

  const {
    cartItems,
    increaseQuantity,
    decreaseQuantity,
    removeFromCart,
    subtotal,
    deliveryFee,
    total,
    loading,
    fetchCart
  } = useCart();

  return (
    <div className="cart-page">

      {/* Header */}
      <div className="cart-header">

        <div>

          <h1>Shopping Cart</h1>

          <p>
            {cartItems.length} product
            {cartItems.length !== 1 ? "s" : ""}
            {" "}in your cart
          </p>

        </div>

        <Link
          to="/products"
          className="continue-shopping"
        >
          ← Continue Shopping
        </Link>

      </div>


      {/* Loading */}
      {loading ? (

        <div className="empty-cart">
          <h2>Loading cart...</h2>
        </div>

      ) : cartItems.length === 0 ? (

        /* Empty Cart */
        <div className="empty-cart">

          <div className="empty-cart-icon">
            🛒
          </div>

          <h2>Your cart is empty</h2>

          <p>
            Looks like you haven't added anything
            to your cart yet.
          </p>

          <Link
            to="/products"
            className="shop-now-btn"
          >
            Start Shopping
          </Link>

        </div>

      ) : (

        <div className="cart-layout">

          {/* Cart Items */}
          <div className="cart-items-section">

            <div className="cart-items-header">

              <span>PRODUCT</span>
              <span>PRICE</span>
              <span>QUANTITY</span>
              <span>TOTAL</span>
              <span></span>

            </div>


            {cartItems.map((item) => (

              <div
                className="cart-item"
                key={item.itemId ?? item.id}
              >

                {/* Product */}
                <div className="cart-product">

                  <div className="cart-product-image">
                    {item.image || "📦"}
                  </div>

                  <div className="cart-product-info">

                    {/* Category */}
                    <span>
                      {item.category || "Electronics"}
                    </span>

                    {/* Product Name */}
                    <Link
                      to={`/products/${item.productId ?? item.id}`}
                    >
                      {item.name || item.productName || "Product"}
                    </Link>

                    {/* Stock */}
                    <small>
                      {item.stock > 0
                        ? `In Stock • ${item.stock} available`
                        : "Out of Stock"}
                    </small>

                  </div>

                </div>


                {/* Price */}
                <div className="cart-price">

                  ₹
                  {Number(item.price).toLocaleString("en-IN")}

                </div>


                {/* Quantity */}
                <div className="quantity-control">

                  <button
                    onClick={() => decreaseQuantity(item)}
                    disabled={item.quantity <= 1}
                  >
                    −
                  </button>

                  <span>
                    {item.quantity}
                  </span>

                  <button
                    onClick={() => increaseQuantity(item)}
                    disabled={item.quantity >= item.stock}
                  >
                    +
                  </button>

                </div>


                {/* Total */}
                <div className="cart-total">

                  ₹
                  {(
                    Number(item.price) * item.quantity
                  ).toLocaleString("en-IN")}

                </div>


                {/* Remove */}
                <button
                  className="remove-item"
                  onClick={() =>
                    removeFromCart(item.itemId)
                  }
                  title="Remove item"
                >
                  🗑️
                </button>

              </div>

            ))}

          </div>


          {/* Order Summary */}
          <div className="order-summary">

            <h2>Order Summary</h2>

            <div className="summary-row">

              <span>Subtotal</span>

              <strong>
                ₹{subtotal.toLocaleString("en-IN")}
              </strong>

            </div>

            <div className="summary-row">

              <span>Delivery</span>

              <strong>
                {deliveryFee === 0
                  ? "FREE"
                  : `₹${deliveryFee}`}
              </strong>

            </div>

            {deliveryFee === 0 && subtotal > 0 && (

              <div className="free-delivery-message">
                🎉 You got free delivery!
              </div>

            )}

            <div className="summary-divider"></div>

            <div className="summary-total">

              <span>Total</span>

              <strong>
                ₹{total.toLocaleString("en-IN")}
              </strong>

            </div>

            <Link
              to="/checkout"
              className="checkout-btn"
            >
              Proceed to Checkout →
            </Link>

            <div className="secure-checkout">
              🔒 Secure Checkout
            </div>

          </div>

        </div>

      )}

    </div>
  );
}

export default Cart;

