import axios from "axios";

const apiClient = axios.create({
  baseURL:
    import.meta.env.VITE_API_BASE_URL ||
    "http://localhost:8080",

  headers: {
    "Content-Type": "application/json"
  },

  timeout: 10000
});

/*
|--------------------------------------------------------------------------
| Request Interceptor
|--------------------------------------------------------------------------
| Attach JWT automatically to protected API requests.
*/

apiClient.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");

    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }

    return config;
  },

  (error) => {
    return Promise.reject(error);
  }
);


/*
|--------------------------------------------------------------------------
| Response Interceptor
|--------------------------------------------------------------------------
| Handle authentication failures.
*/

apiClient.interceptors.response.use(
  (response) => {
    return response;
  },

  async (error) => {
    const originalRequest = error.config;

    /*
     * If access token expired, try refresh once.
     */
    if (
      error.response?.status === 401 &&
      !originalRequest._retry &&
      !originalRequest.url?.includes("/auth-service/auth/refresh")
    ) {
      originalRequest._retry = true;

      const refreshToken =
        localStorage.getItem("refreshToken");

      if (!refreshToken) {
        handleLogout();
        return Promise.reject(error);
      }

      try {
        const refreshResponse = await axios.post(
          `${
            import.meta.env.VITE_API_BASE_URL ||
            "http://localhost:8080"
          }/auth-service/auth/refresh`,
          {
            refreshToken
          }
        );

        const newToken =
          refreshResponse.data.accessToken;

        /*
         * Store new access token.
         */
        localStorage.setItem(
          "token",
          newToken
        );

        /*
         * Retry original request with new token.
         */
        originalRequest.headers.Authorization =
          `Bearer ${newToken}`;

        return apiClient(originalRequest);

      } catch (refreshError) {
        handleLogout();

        return Promise.reject(
          refreshError
        );
      }
    }

    return Promise.reject(error);
  }
);


/*
|--------------------------------------------------------------------------
| Logout Helper
|--------------------------------------------------------------------------
*/

function handleLogout() {
  localStorage.removeItem("token");
  localStorage.removeItem("refreshToken");
  localStorage.removeItem("user");

  /*
   * Redirect user to login page.
   */
  window.location.href = "/login";
}


export default apiClient;