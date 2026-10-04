import axios from "axios";

const API_BASE_URL = "http://localhost:8080/order-service/api";

const getAuthHeaders = () => {
  const token = localStorage.getItem("accessToken");

  return {
    Authorization: `Bearer ${token}`,
    "Content-Type": "application/json",
  };
};

export const createOrder = async (orderRequest) => {

  const idempotencyKey = crypto.randomUUID();

  const response = await axios.post(
    `${API_BASE_URL}/order`,
    orderRequest,
    {
      headers: {
        ...getAuthHeaders(),
        "Idempotency-Key": idempotencyKey,
      },
    }
  );

  return response.data;
};

export const getMyOrders = async (userId) => {

  const token = localStorage.getItem("accessToken");    

    if(!token) {    
    throw new Error("User is not authenticated.");
  }

    const response = await axios.get(   

    `${API_BASE_URL}/order-service/api/orders/user/${userId}`,
    {
      headers: {    

        Authorization: `Bearer ${token}`
      }
    }
  );
  return response.data;
};



export const getOrderDetails = async (orderId) => {

  const token = localStorage.getItem("accessToken");
    
    if(!token) {
    throw new Error("User is not authenticated.");
  }

    const response = await axios.get(
    `${API_BASE_URL}/order-service/api/orders/${orderId}`,
    {
      headers: {
        Authorization: `Bearer ${token}`
        }
    }
  );
  return response.data;
};




