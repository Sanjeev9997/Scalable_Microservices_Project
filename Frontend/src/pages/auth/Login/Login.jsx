
import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import "./Login.css";
import { login } from "../../../services/authService";
import {getMyProfile} from "../../../services/userService";

function Login() {

  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    email: "",
    password: ""
  });

  const [showPassword, setShowPassword] = useState(false);
  const [rememberMe, setRememberMe] = useState(false);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const handleChange = (event) => {
    
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value
    }));

    // Clear error while typing
    setError("");
  };

  const handleSubmit = async (event) => {

    event.preventDefault();

    setError("");
    setSuccess("");

    // Basic validation
    if (!formData.email || !formData.password) {
      setError("Please enter email and password.");
      return;
    }

    try {

      setLoading(true);

      // Call auth-service login API
      const response = await login({
        email: formData.email,
        password: formData.password
      });

      console.log("Login Response:", response);

      /*
       * Expected response example:
       *
       * {
       *   token: "your-jwt-token",
       *   refreshToken: "your-refresh-token"
       * }
       */

      // Extract JWT token
      const accessToken =
        response?.token ||
        response?.accessToken ||
        response?.data?.token ||
        response?.data?.accessToken;

      const refreshToken =
        response?.refreshToken ||
        response?.data?.refreshToken;

      if (!accessToken) {
        throw new Error(
          "Login successful, but no access token was received."
        );
      }
      
      // Save token
      localStorage.setItem("accessToken", accessToken);
      console.log("Access Token saved to localStorage:", accessToken);
      // Save refresh token if provided
      if (refreshToken) {
        localStorage.setItem("refreshToken", refreshToken);
      }
      
      const user = await getMyProfile(formData.email);
      // Save user email if needed
      localStorage.setItem("user", JSON.stringify(user));

      // Save remember-me preference
      if (rememberMe) {
        localStorage.setItem("rememberMe", "true");
      } else {
        localStorage.removeItem("rememberMe");
      }
      
      setSuccess("Login successful! Redirecting...");

      // Redirect to home page
      setTimeout(() => {
        navigate("/");
      }, 500);

    } catch (error) {

      console.error("Login Error:", error);

      const errorMessage =
        error.response?.data?.message ||
        error.response?.data?.error ||
        error.message ||
        "Login failed. Please check your credentials.";

      setError(errorMessage);

    } finally {

      setLoading(false);

    }
  };

  return (
    <div className="auth-page">

      <div className="auth-container">

        {/* Left Side */}

        <div className="auth-brand-panel">

          <Link to="/" className="auth-logo">
            Shop<span>Easy</span>
          </Link>

          <div className="brand-content">

            <span className="brand-label">
              WELCOME BACK
            </span>

            <h1>
              Your shopping
              <br />
              journey continues.
            </h1>

            <p>
              Sign in to access your orders, saved products,
              personalized recommendations and more.
            </p>

            <div className="brand-features">

              <div>
                <span>✓</span>
                Secure authentication
              </div>

              <div>
                <span>✓</span>
                Track your orders
              </div>

              <div>
                <span>✓</span>
                Fast and secure checkout
              </div>

            </div>

          </div>

          <div className="brand-footer">
            © 2026 ShopEasy
          </div>

        </div>

        {/* Right Side */}

        <div className="auth-form-panel">

          <div className="auth-form-wrapper">

            <div className="mobile-logo">

              <Link to="/" className="auth-logo">
                Shop<span>Easy</span>
              </Link>

            </div>

            <div className="auth-header">

              <h2>Welcome back</h2>

              <p>
                Sign in to your ShopEasy account.
              </p>

            </div>

            {/* Error Message */}

            {error && (
              <div
                className="auth-error"
                role="alert"
              >
                {error}
              </div>
            )}

            {/* Success Message */}

            {success && (
              <div
                className="auth-success"
                role="status"
              >
                {success}
              </div>
            )}

            <form
              className="auth-form"
              onSubmit={handleSubmit}
            >

              {/* Email */}

              <div className="form-group">

                <label htmlFor="email">
                  Email Address
                </label>

                <input
                  id="email"
                  type="email"
                  name="email"
                  value={formData.email}
                  onChange={handleChange}
                  placeholder="you@example.com"
                  autoComplete="email"
                  required
                />

              </div>

              {/* Password */}

              <div className="form-group">

                <div className="password-label-row">

                  <label htmlFor="password">
                    Password
                  </label>

                  <Link to="/forgot-password">
                    Forgot password?
                  </Link>

                </div>

                <div className="password-input">

                  <input
                    id="password"
                    type={
                      showPassword
                        ? "text"
                        : "password"
                    }
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Enter your password"
                    autoComplete="current-password"
                    required
                  />

                  <button
                    type="button"
                    onClick={() =>
                      setShowPassword(
                        (previous) => !previous
                      )
                    }
                    className="password-toggle"
                    aria-label={
                      showPassword
                        ? "Hide password"
                        : "Show password"
                    }
                  >
                    {showPassword ? "🙈" : "👁️"}
                  </button>

                </div>

              </div>

              {/* Remember Me */}

              <label className="remember-row">

                <input
                  type="checkbox"
                  checked={rememberMe}
                  onChange={(event) =>
                    setRememberMe(event.target.checked)
                  }
                />

                <span>
                  Remember me
                </span>

              </label>

              {/* Submit */}

              <button
                type="submit"
                className="auth-submit-btn"
                disabled={loading}
              >

                {loading
                  ? "Signing In..."
                  : "Sign In →"}

              </button>

            </form>

            <div className="auth-divider">

              <span>
                New to ShopEasy?
              </span>

            </div>

            <Link
              to="/register"
              className="secondary-auth-btn"
            >
              Create an Account
            </Link>

            <p className="auth-terms">

              By continuing, you agree to our{" "}

              <Link to="/terms">
                Terms of Service
              </Link>{" "}

              and{" "}

              <Link to="/privacy">
                Privacy Policy
              </Link>.

            </p>

          </div>

        </div>

      </div>

    </div>
  );
}

export default Login;