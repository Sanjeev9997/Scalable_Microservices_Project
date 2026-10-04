import {
  createContext,
  useContext,
  useEffect,
  useState
} from "react";

import {
  getCart,
  addToCart as addToCartAPI,
  updateCartItem,
  removeFromCart as removeCartItem,
  clearCart as clearCartAPI
} from "../services/cartService";

const CartContext = createContext();

export function CartProvider({ children }) {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(false);

  // Get logged-in user
  const getUser = () => {
    try {
      const user = JSON.parse(localStorage.getItem("user"));
      return user;
    } catch (error) {
      console.error("Invalid user:", error);
      return null;
    }
  };

  // Fetch cart from backend
  const fetchCart = async () => {
    try {
      const user = getUser();

      if (!user) {
        setCartItems([]);
        return;
      }

      setLoading(true);

      const cart = await getCart(user.id);

      setCartItems(cart?.cartItems || []);

    } catch (error) {
      console.error("Error fetching cart:", error);
      setCartItems([]);
    } finally {
      setLoading(false);
    }
  };

  // Fetch cart when provider loads
  useEffect(() => {
    fetchCart();
  }, []);

  // Add product
  const addToCart = async (product) => {
    try {
      const user = getUser();

      if (!user) {
        console.error("User is not logged in");
        return;
      }

      await addToCartAPI(
        user.id,
        product.id,
        1
      );

      // Refresh cart from backend
      await fetchCart();

    } catch (error) {
      console.error("Error adding product to cart:", error);
    }
  };

  // Remove item
  const removeFromCart = async (cartItemId) => {
    try {
      await removeCartItem(cartItemId);

      // Refresh cart
      await fetchCart();

    } catch (error) {
      console.error("Error removing cart item:", error);
    }
  };

  // Increase quantity
  const increaseQuantity = async (cartItem) => {
    try {
      if (cartItem.quantity >= cartItem.stock) {
        return;
      }

      await updateCartItem(
        cartItem.itemId,
        cartItem.quantity + 1
      );

      await fetchCart();

    } catch (error) {
      console.error("Error increasing quantity:", error);
    }
  };

  // Decrease quantity
  const decreaseQuantity = async (cartItem) => {
    try {
      const newQuantity = cartItem.quantity - 1;

      if (newQuantity <= 0) {
        await removeCartItem(cartItem.itemId);
      } else {
        await updateCartItem(
          cartItem.itemId,
          newQuantity
        );
      }

      await fetchCart();

    } catch (error) {
      console.error("Error decreasing quantity:", error);
    }
  };

  // Clear cart
  const clearCart = async () => {
    try {
      const user = getUser();

      if (!user) {
        return;
      }

      await clearCartAPI(user.id);

      await fetchCart();

    } catch (error) {
      console.error("Error clearing cart:", error);
    }
  };

  // Get quantity of particular product
  const getItemQuantity = (productId) => {
    const item = cartItems.find(
      (item) => item.productId === productId
    );

    return item ? item.quantity : 0;
  };

  // Total number of items/units in cart
  const cartCount = cartItems.reduce(
    (total, item) => total + item.quantity,
    0
  );

  // Subtotal
  const subtotal = cartItems.reduce(
    (total, item) =>
      total +
      (item.price || 0) * item.quantity,
    0
  );

  // Delivery fee
  const deliveryFee =
    subtotal === 0 || subtotal >= 500
      ? 0
      : 49;

  // Final total
  const total = subtotal + deliveryFee;

  const value = {
    cartItems,
    cartCount,
    subtotal,
    deliveryFee,
    total,
    loading,

    fetchCart,
    addToCart,
    removeFromCart,
    increaseQuantity,
    decreaseQuantity,
    clearCart,
    getItemQuantity
  };

  return (
    <CartContext.Provider value={value}>
      {children}
    </CartContext.Provider>
  );
}

export function useCart() {
  const context = useContext(CartContext);

  if (!context) {
    throw new Error(
      "useCart must be used inside CartProvider"
    );
  }

  return context;
}