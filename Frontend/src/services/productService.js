import axios from "axios";

const API_BASE_URL = "http://localhost:8080";
export const getAllProducts = async () => {
  const response = await axios.get(`${API_BASE_URL}/product-service/products`);
  return response.data;
};

export const getProductById = async (productId) => {
  const response = await axios.get(
    `${API_BASE_URL}/product-service/products/${productId}`
  );
  return response.data;
};