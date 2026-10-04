import axios from "axios";

const API_BASE_URL = "http://localhost:8080/cart-service/api";


// Get JWT token
const getAuthHeaders = () => {
  const token = localStorage.getItem("accessToken");
  
  return {
    Authorization: `Bearer ${token}`
  };
};


// Get Cart
export const getCart = async () => {

  const user = JSON.parse(localStorage.getItem("user"));
  try {
    
    const response = await axios.get(
      `${API_BASE_URL}/cart/${user.id}`,
      {
        headers: getAuthHeaders()
      }
    );
    console.log("Fetched cart data:", response.data);
    return response.data;

  } catch (error) {

    console.error("Error fetching cart:", error);
    throw error;

  }
};


// Add to Cart
export const addToCart = async (productId, quantity) => {

  const user = JSON.parse(localStorage.getItem("user"));

  try {
    const response = await axios.post(
      `${API_BASE_URL}/cart/${user.id}/add`,
      {
        productId,
        quantity
      },
      {
        headers: getAuthHeaders()
      }
    );

    return response.data;

  } catch (error) {

    console.error("Error adding to cart:", error);
    throw error;

  }
};


// Update Cart Item
export const updateCartItem = async (itemId, quantity) => {
  
  const user = JSON.parse(localStorage.getItem("user"));

  try {

    const response = await axios.put(
      `${API_BASE_URL}/cart/${user.id}/item/${itemId}`,
      {
        quantity
      },
      {
        headers: getAuthHeaders()
      }
    );

    return response.data;

  } catch (error) {

    console.error("Error updating cart item:", error);
    throw error;

  }
};


// Remove from Cart
export const removeFromCart = async (itemId) => {

  const user = JSON.parse(localStorage.getItem("user"));
  
  try {

    const response = await axios.delete(
      `${API_BASE_URL}/cart/${user.id}/item/${itemId}`,
      {
        headers: getAuthHeaders()
      }
    );

    return response.data;

  } catch (error) {

    console.error("Error removing from cart:", error);
    throw error;

  }
};


// Clear Cart
export const clearCart = async () => {

  const user = JSON.parse(localStorage.getItem("user"));

  try {

    const response = await axios.delete(
      `${API_BASE_URL}/cart/${user.id}/clear`,
      {
        headers: getAuthHeaders()
      }
    );

    return response.data;

  } catch (error) {

    console.error("Error clearing cart:", error);
    throw error;

  }
};