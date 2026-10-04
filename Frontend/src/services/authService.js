import apiClient from "../utils/apiClient";

export const login = (credentials) =>
  apiClient.post(
    "/auth-service/auth/login",
    credentials
  );

export const register = (userData) =>
  apiClient.post(
    "/auth-service/auth/register",
    userData
  );

export const refreshToken = (refreshToken) =>
  apiClient.post(
    "/auth-service/auth/refresh",
    { refreshToken }
  );