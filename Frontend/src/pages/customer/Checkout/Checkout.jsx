
import { Link, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import "./Checkout.css";
import { createOrder } from "../../../services/orderService";
import { getCart } from "../../../services/cartService";
import { getUserAddresses } from "../../../services/userService";

function Checkout() {
  const navigate = useNavigate();

  // ========================================
  // STATE
  // ========================================

  const [paymentMethod, setPaymentMethod] = useState("cod");

  const [cartItems, setCartItems] = useState([]);
  const [backendTotalPrice, setBackendTotalPrice] = useState(0);

  // Address state
  const [addresses, setAddresses] = useState([]);
  const [selectedAddressId, setSelectedAddressId] = useState(null);
  const [addressLoading, setAddressLoading] = useState(true);

  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  // ========================================
  // FETCH CART + ADDRESSES
  // ========================================

  useEffect(() => {
    const fetchCheckoutData = async () => {
      try {
        setLoading(true);
        setAddressLoading(true);
        setError("");

        // ========================================
        // GET LOGGED-IN USER
        // ========================================

        const storedUser = localStorage.getItem("user");

        if (!storedUser) {
          setError("Please login before checkout.");
          return;
        }

        const user = JSON.parse(storedUser);

        if (!user?.id) {
          setError("User information is missing.");
          return;
        }

        console.log("Logged in user:", user);

        // ========================================
        // FETCH CART
        // ========================================

        const cartResponse = await getCart(user.id);

        console.log("Fetched cart data:", cartResponse);

        const items = Array.isArray(cartResponse?.cartItems)
          ? cartResponse.cartItems
          : [];

        setCartItems(items);

        console.log("Cart Items:", items);

        setBackendTotalPrice(
          Number(cartResponse?.totalPrice ?? 0)
        );

        // ========================================
        // FETCH USER ADDRESSES
        // ========================================

        const addressResponse = await getUserAddresses(user.id);

        console.log("Fetched addresses:", addressResponse);

        /*
         * Depending on your backend response,
         * addresses may directly be an array:
         *
         * [
         *   {...},
         *   {...}
         * ]
         *
         * OR:
         *
         * {
         *   addresses: [...]
         * }
         */

        const userAddresses = Array.isArray(addressResponse)
          ? addressResponse
          : Array.isArray(addressResponse?.addresses)
          ? addressResponse.addresses
          : [];

        console.log("User Addresses:", userAddresses);

        setAddresses(userAddresses);

        // ========================================
        // SELECT DEFAULT ADDRESS
        // ========================================

        if (userAddresses.length > 0) {
          const defaultAddress = userAddresses.find(
            (address) => address.isDefault === true
          );

          if (defaultAddress) {
            setSelectedAddressId(defaultAddress.id);
          } else {
            // If no default address exists,
            // select the first address
            setSelectedAddressId(userAddresses[0].id);
          }
        } else {
          setSelectedAddressId(null);
        }
      } catch (err) {
        console.error(
          "Checkout loading error:",
          err
        );

        setError(
          err?.response?.data?.message ||
            "Unable to load checkout information."
        );

        setCartItems([]);
        setAddresses([]);
      } finally {
        setLoading(false);
        setAddressLoading(false);
      }
    };

    fetchCheckoutData();
  }, []);

  // ========================================
  // HELPER FUNCTIONS
  // ========================================

  const getProductName = (item) => {
    return (
      item?.name ||
      item?.productName ||
      item?.product?.name ||
      "Product"
    );
  };

  const getProductPrice = (item) => {
    return Number(
      item?.price ??
        item?.productPrice ??
        item?.product?.price ??
        0
    );
  };

  const getProductQuantity = (item) => {
    return Number(item?.quantity ?? 1);
  };

  const getProductImage = (item) => {
    return (
      item?.image ||
      item?.productImage ||
      item?.product?.image ||
      "🛍️"
    );
  };

  // ========================================
  // PRICE CALCULATIONS
  // ========================================

  const calculatedSubtotal = cartItems.reduce(
    (total, item) => {
      const price = getProductPrice(item);
      const quantity = getProductQuantity(item);

      return total + price * quantity;
    },
    0
  );

  /*
   * If backend sends totalPrice,
   * use it.
   *
   * Otherwise calculate subtotal
   * from cart items.
   */

  const subtotal =
    backendTotalPrice > 0
      ? backendTotalPrice
      : calculatedSubtotal;

  // Free delivery above ₹500
  const deliveryFee =
    subtotal >= 500 ? 0 : 49;

  // Coupon/discount currently zero
  const discount = 0;

  // Final payable amount
  const total =
    subtotal + deliveryFee - discount;

  // ========================================
  // PLACE ORDER
  // ========================================

 const handlePlaceOrder = async (event) => {
  event.preventDefault();

  if (paymentMethod !== "cod") {
    // Online payment later
    return;
  }

  if (!selectedAddressId) {
    setError("Please select a delivery address.");
    return;
  }

  const user = JSON.parse(localStorage.getItem("user"));

  try {
    setLoading(true);
    setError("");
    const order = await createOrder({
      userId: user.id,
      addressId: selectedAddressId,
      paymentMethod: "COD",
    });

    console.log("Order created:", order);

    navigate("/orders");

  } catch (error) {

    console.error("Order creation failed:", error);

    setError(
      error?.response?.data?.message ||
      "Unable to place order. Please try again."
    );

  } finally {
    setLoading(false);
  }
};

  // ========================================
  // LOADING
  // ========================================

  if (loading) {
    return (
      <div className="checkout-page">

        <div className="checkout-header">

          <div>
            <h1>Checkout</h1>

            <p>
              Complete your order securely
            </p>
          </div>

          <Link
            to="/cart"
            className="back-to-cart"
          >
            ← Back to Cart
          </Link>

        </div>

        <div className="checkout-card">
          <p>
            Loading your checkout...
          </p>
        </div>

      </div>
    );
  }

  // ========================================
  // ERROR
  // ========================================

  if (error) {
    return (
      <div className="checkout-page">

        <div className="checkout-header">

          <div>
            <h1>Checkout</h1>

            <p>
              Complete your order securely
            </p>
          </div>

          <Link
            to="/cart"
            className="back-to-cart"
          >
            ← Back to Cart
          </Link>

        </div>

        <div className="checkout-card">

          <h2>
            Unable to load checkout
          </h2>

          <p>{error}</p>

          <Link to="/cart">
            Go back to cart
          </Link>

        </div>

      </div>
    );
  }

  // ========================================
  // EMPTY CART
  // ========================================

  if (cartItems.length === 0) {
    return (
      <div className="checkout-page">

        <div className="checkout-header">

          <div>
            <h1>Checkout</h1>

            <p>
              Complete your order securely
            </p>
          </div>

          <Link
            to="/cart"
            className="back-to-cart"
          >
            ← Back to Cart
          </Link>

        </div>

        <div className="checkout-card">

          <h2>
            Your cart is empty
          </h2>

          <p>
            Add some products to your cart
            before proceeding to checkout.
          </p>

          <Link to="/products">
            Continue Shopping
          </Link>

        </div>

      </div>
    );
  }

  // ========================================
  // CHECKOUT UI
  // ========================================

  return (
    <div className="checkout-page">

      {/* =================================
          HEADER
      ================================= */}

      <div className="checkout-header">

        <div>

          <h1>
            Checkout
          </h1>

          <p>
            Complete your order securely
          </p>

        </div>

        <Link
          to="/cart"
          className="back-to-cart"
        >
          ← Back to Cart
        </Link>

      </div>

      <form
        className="checkout-layout"
        onSubmit={handlePlaceOrder}
      >

        {/* =================================
            LEFT SIDE
        ================================= */}

        <div className="checkout-main">

          {/* =================================
              DELIVERY ADDRESS
          ================================= */}

          <section className="checkout-card">

            <div className="section-heading">

              <div className="section-number">
                1
              </div>

              <div>

                <h2>
                  Delivery Address
                </h2>

                <p>
                  Choose where you want
                  your order delivered
                </p>

              </div>

            </div>

            {/* ADDRESS LOADING */}

            {addressLoading ? (

              <div className="address-loading">

                <p>
                  Loading your addresses...
                </p>

              </div>

            ) : addresses.length === 0 ? (

              /* =================================
                 NO ADDRESS
              ================================= */

              <div className="no-address">

                <div className="no-address-icon">
                  📍
                </div>

                <h3>
                  No saved address
                </h3>

                <p>
                  You don't have any saved
                  delivery address.
                  Add an address to continue
                  with your order.
                </p>

                <button
                  type="button"
                  className="add-address-btn"
                  onClick={() =>
                    navigate(
                      "/addresses/add"
                    )
                  }
                >
                  + Add New Address
                </button>

              </div>

            ) : (

              /* =================================
                 SAVED ADDRESSES
              ================================= */

              <div className="saved-addresses">

                {addresses.map(
                  (address) => (

                    <label
                      key={address.id}
                      className={`address-card ${
                        selectedAddressId ===
                        address.id
                          ? "selected"
                          : ""
                      }`}
                    >

                      <input
                        type="radio"
                        name="selectedAddress"
                        value={address.id}
                        checked={
                          selectedAddressId ===
                          address.id
                        }
                        onChange={() =>
                          setSelectedAddressId(
                            address.id
                          )
                        }
                      />

                      <div className="address-card-content">

                        <div className="address-card-header">

                          <strong>
                            {address.fullName}
                          </strong>

                          {address.isDefault && (
                            <span className="default-address">
                              Default
                            </span>
                          )}

                        </div>

                        <p>
                          {address.address}
                        </p>

                        <p>
                          {address.city},{" "}
                          {address.state} -{" "}
                          {address.pincode}
                        </p>

                        <p>
                          📞{" "}
                          {address.phone}
                        </p>

                      </div>

                    </label>

                  )
                )}

                {/* ADD ANOTHER ADDRESS */}

                <button
                  type="button"
                  className="add-address-link"
                  onClick={() =>
                    navigate(
                      "/addresses/add"
                    )
                  }
                >
                  + Add another address
                </button>

              </div>

            )}

          </section>

          {/* =================================
              DELIVERY METHOD
          ================================= */}

          <section className="checkout-card">

            <div className="section-heading">

              <div className="section-number">
                2
              </div>

              <div>

                <h2>
                  Delivery Method
                </h2>

                <p>
                  Choose how you want
                  your order delivered
                </p>

              </div>

            </div>

            <div className="delivery-options">

              <label className="delivery-option selected">

                <input
                  type="radio"
                  name="delivery"
                  value="standard"
                  defaultChecked
                />

                <div className="delivery-icon">
                  🚚
                </div>

                <div className="delivery-details">

                  <strong>
                    Standard Delivery
                  </strong>

                  <span>
                    Delivery within
                    3–5 business days
                  </span>

                </div>

                <strong className="delivery-price">

                  {deliveryFee === 0
                    ? "FREE"
                    : `₹${deliveryFee.toLocaleString(
                        "en-IN"
                      )}`}

                </strong>

              </label>

            </div>

          </section>

          {/* =================================
              PAYMENT
          ================================= */}

          <section className="checkout-card">

            <div className="section-heading">

              <div className="section-number">
                3
              </div>

              <div>

                <h2>
                  Payment Method
                </h2>

                <p>
                  Select your preferred
                  payment option
                </p>

              </div>

            </div>

            <div className="payment-options">

              {/* COD */}

              <label
                className={`payment-option ${
                  paymentMethod === "cod"
                    ? "selected"
                    : ""
                }`}
              >

                <input
                  type="radio"
                  name="payment"
                  value="cod"
                  checked={
                    paymentMethod === "cod"
                  }
                  onChange={(event) =>
                    setPaymentMethod(
                      event.target.value
                    )
                  }
                />

                <span className="payment-icon">
                  💵
                </span>

                <span className="payment-content">

                  <strong>
                    Cash on Delivery
                  </strong>

                  <small>
                    Pay when your
                    order arrives
                  </small>

                </span>

              </label>

              {/* UPI */}

              <label
                className={`payment-option ${
                  paymentMethod === "upi"
                    ? "selected"
                    : ""
                }`}
              >

                <input
                  type="radio"
                  name="payment"
                  value="upi"
                  checked={
                    paymentMethod === "upi"
                  }
                  onChange={(event) =>
                    setPaymentMethod(
                      event.target.value
                    )
                  }
                />

                <span className="payment-icon">
                  📱
                </span>

                <span className="payment-content">

                  <strong>
                    UPI
                  </strong>

                  <small>
                    Google Pay,
                    PhonePe, Paytm
                  </small>

                </span>

              </label>

              {/* CARD */}

              <label
                className={`payment-option ${
                  paymentMethod === "card"
                    ? "selected"
                    : ""
                }`}
              >

                <input
                  type="radio"
                  name="payment"
                  value="card"
                  checked={
                    paymentMethod === "card"
                  }
                  onChange={(event) =>
                    setPaymentMethod(
                      event.target.value
                    )
                  }
                />

                <span className="payment-icon">
                  💳
                </span>

                <span className="payment-content">

                  <strong>
                    Credit / Debit Card
                  </strong>

                  <small>
                    Visa, Mastercard,
                    RuPay
                  </small>

                </span>

              </label>

            </div>

            {/* CARD PLACEHOLDER */}

            {paymentMethod === "card" && (

              <div className="card-placeholder">

                <p>
                  💳 Card details will
                  be collected securely here.
                </p>

              </div>

            )}

            {/* UPI PLACEHOLDER */}

            {paymentMethod === "upi" && (

              <div className="card-placeholder">

                <p>
                  📱 UPI payment screen
                  will appear here.
                </p>

              </div>

            )}

          </section>

        </div>

        {/* =================================
            RIGHT SIDE
        ================================= */}

        <aside className="checkout-sidebar">

          <div className="checkout-card order-summary">

            <h2>
              Order Summary
            </h2>

            {/* CART ITEMS */}

            <div className="summary-items">

              {cartItems.map((item) => {

                const price =
                  getProductPrice(item);

                const quantity =
                  getProductQuantity(item);

                const itemTotal =
                  price * quantity;

                return (

                  <div
                    className="summary-item"
                    key={item.id}
                  >

                    {/* IMAGE */}

                    <div className="summary-product-image">

                      {getProductImage(item)}

                    </div>

                    {/* PRODUCT INFO */}

                    <div className="summary-product-info">

                      <strong>
                        {getProductName(item)}
                      </strong>

                      <span>
                        Qty: {quantity}
                      </span>

                      <small>

                        ₹
                        {price.toLocaleString(
                          "en-IN"
                        )}{" "}
                        each

                      </small>

                    </div>

                    {/* ITEM TOTAL */}

                    <strong>

                      ₹
                      {itemTotal.toLocaleString(
                        "en-IN"
                      )}

                    </strong>

                  </div>

                );
              })}

            </div>

            <div className="summary-divider"></div>

            {/* SUBTOTAL */}

            <div className="summary-row">

              <span>
                Subtotal
              </span>

              <strong>

                ₹
                {subtotal.toLocaleString(
                  "en-IN"
                )}

              </strong>

            </div>

            {/* DELIVERY */}

            <div className="summary-row">

              <span>
                Delivery
              </span>

              <strong>

                {deliveryFee === 0
                  ? "FREE"
                  : `₹${deliveryFee.toLocaleString(
                      "en-IN"
                    )}`}

              </strong>

            </div>

            {/* DISCOUNT */}

            <div className="summary-row">

              <span>
                Discount
              </span>

              <strong className="discount-text">

                - ₹
                {discount.toLocaleString(
                  "en-IN"
                )}

              </strong>

            </div>

            <div className="summary-divider"></div>

            {/* TOTAL */}

            <div className="summary-total">

              <span>
                Total Amount
              </span>

              <strong>

                ₹
                {total.toLocaleString(
                  "en-IN"
                )}

              </strong>

            </div>

            {/* COUPON */}

            <div className="coupon-box">

              <input
                type="text"
                placeholder="Enter coupon code"
              />

              <button
                type="button"
              >
                Apply
              </button>

            </div>

            {/* PLACE ORDER */}

            <button
              type="submit"
              className="place-order-btn"
              disabled={
                addresses.length === 0 ||
                !selectedAddressId
              }
            >

              🔒 Place Order

            </button>

            {/* SECURITY */}

            <div className="secure-payment">

              🔒 Your payment information
              is secure

            </div>

          </div>

        </aside>

      </form>

    </div>
  );
}

export default Checkout;

