import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

export const getInventory = async (productId) => {
  const token = localStorage.getItem("accessToken");

  if (!token) {
    throw new Error("User is not authenticated.");
  }
  const response = await axios.get(`${API_BASE_URL}/inventory-service/api/inventory/${productId}`, {
    headers: {
      Authorization: `Bearer ${token}`
    }
  });

  return response.data;
};
