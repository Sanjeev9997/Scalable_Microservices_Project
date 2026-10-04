
import axios from "axios";

const API_BASE_URL = "http://localhost:8080";

export const getMyProfile = async (email) => {

  const token = localStorage.getItem("accessToken");

  if (!token) {
    throw new Error("User is not authenticated.");
  }
  
  const response = await axios.get(
    `${API_BASE_URL}/user-service/api/users/email/${email}`,
    {
      headers: {
        Authorization: `Bearer ${token}`
      }
    }
  );
  return response.data;

};

export const getUserAddresses = async (userId) => {

  const token = localStorage.getItem("accessToken");  
  if(!token) {
    throw new Error("User is not authenticated.");
  }
  const response = await axios.get(
    `${API_BASE_URL}/user-service/api/addresses/user/${userId}`,
    { 
      headers: {
        Authorization: `Bearer ${token}`
      }
    }
  );
  return response.data;

};

export const addUserAddress = async (userId, addressData) => {

  const token = localStorage.getItem("accessToken");  
  if(!token) {
    throw new Error("User is not authenticated.");
  }   
  const response = await axios.post(
    `${API_BASE_URL}/user-service/api/addresses/user/${userId}`,
    addressData,  
    {
      headers: {
        Authorization: `Bearer ${token}`  
      }
    }
  );
  return response.data;   

};

