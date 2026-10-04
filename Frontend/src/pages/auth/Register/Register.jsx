
import { useState } from "react";
import { Link } from "react-router-dom";
import "./Register.css";
import { register } from "../../../services/authService";

function Register() {
  const [formData, setFormData] = useState({
    firstName: "",
    lastName: "",
    gender: "",
    email: "",
    phone: "",
    password: "",
    confirmPassword: ""
  });

  const [showPassword, setShowPassword] = useState(false);
  const [showConfirmPassword, setShowConfirmPassword] =
    useState(false);

  const [acceptTerms, setAcceptTerms] = useState(false);

  const [isLoading, setIsLoading] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;

    setFormData((previous) => ({
      ...previous,
      [name]: value
    }));
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (formData.password !== formData.confirmPassword) {
      alert("Passwords do not match.");
      return;
    }

    if (!acceptTerms) {
      alert("Please accept the Terms of Service.");
      return;
    }

    const registerData = {
      firstName: formData.firstName,
      lastName: formData.lastName,
      gender: formData.gender,
      email: formData.email,
      phone: formData.phone,
      password: formData.password
    };

    try {
      setIsLoading(true);

      const response = await register(registerData);

      console.log("Registration successful:", response.data);

      alert("Registration successful!");

      // Optional: Navigate to login page
      // navigate("/login");

    } catch (error) {
      console.error("Registration failed:", error);

      const message =
        error.response?.data?.message ||
        "Registration failed. Please try again.";

      alert(message);

    } finally {
      setIsLoading(false);
    }
  };

  return (
    <div className="auth-page">

      <div className="auth-container">

        {/* LEFT PANEL */}

        <div className="auth-brand-panel">

          <Link to="/" className="auth-logo">
            Shop<span>Easy</span>
          </Link>

          <div className="brand-content">

            <span className="brand-label">
              JOIN SHOPEASY
            </span>

            <h1>
              Shopping made
              <br />
              simple.
            </h1>

            <p>
              Create your account and unlock a faster,
              smarter and more personalized shopping
              experience.
            </p>

            <div className="brand-features">

              <div>
                <span>✓</span>
                Personalized shopping
              </div>

              <div>
                <span>✓</span>
                Easy order tracking
              </div>

              <div>
                <span>✓</span>
                Faster checkout
              </div>

              <div>
                <span>✓</span>
                Exclusive offers
              </div>

            </div>

          </div>

          <div className="brand-footer">
            © 2026 ShopEasy
          </div>

        </div>

        {/* RIGHT PANEL */}

        <div className="auth-form-panel">

          <div className="auth-form-wrapper">

            <div className="mobile-logo">

              <Link to="/" className="auth-logo">
                Shop<span>Easy</span>
              </Link>

            </div>

            <div className="auth-header">

              <h2>Create your account</h2>

              <p>
                Join ShopEasy and start shopping today.
              </p>

            </div>

            <form
              className="auth-form"
              onSubmit={handleSubmit}
            >

              <div className="form-grid">

                {/* FIRST NAME */}

                <div className="form-group">

                  <label htmlFor="firstName">
                    First Name
                  </label>

                  <input
                    id="firstName"
                    type="text"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleChange}
                    placeholder="John"
                    autoComplete="given-name"
                    required
                  />

                </div>

                {/* LAST NAME */}

                <div className="form-group">

                  <label htmlFor="lastName">
                    Last Name
                  </label>

                  <input
                    id="lastName"
                    type="text"
                    name="lastName"
                    value={formData.lastName}
                    onChange={handleChange}
                    placeholder="Doe"
                    autoComplete="family-name"
                    required
                  />

                </div>

                {/* GENDER */}

                <div className="form-group">

                  <label htmlFor="gender">
                    Gender
                  </label>

                  <select
                    id="gender"
                    name="gender"
                    value={formData.gender}
                    onChange={handleChange}
                    required
                  >

                    <option value="">
                      Select Gender
                    </option>

                    <option value="MALE">
                      Male
                    </option>

                    <option value="FEMALE">
                      Female
                    </option>

                    <option value="OTHER">
                      Other
                    </option>

                    <option value="PREFER_NOT_TO_SAY">
                      Prefer not to say
                    </option>

                  </select>

                </div>

                {/* EMAIL */}

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

                {/* PHONE */}

                <div className="form-group">

                  <label htmlFor="phone">
                    Phone Number
                  </label>

                  <input
                    id="phone"
                    type="tel"
                    name="phone"
                    value={formData.phone}
                    onChange={handleChange}
                    placeholder="9876543210"
                    autoComplete="tel"
                    maxLength="10"
                    pattern="[0-9]{10}"
                    required
                  />

                </div>

                {/* PASSWORD */}

                <div className="form-group">

                  <label htmlFor="register-password">
                    Password
                  </label>

                  <div className="password-input">

                    <input
                      id="register-password"
                      type={
                        showPassword
                          ? "text"
                          : "password"
                      }
                      name="password"
                      value={formData.password}
                      onChange={handleChange}
                      placeholder="Create password"
                      autoComplete="new-password"
                      required
                    />

                    <button
                      type="button"
                      className="password-toggle"
                      onClick={() =>
                        setShowPassword(
                          (previous) => !previous
                        )
                      }
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

                {/* CONFIRM PASSWORD */}

                <div className="form-group">

                  <label htmlFor="confirmPassword">
                    Confirm Password
                  </label>

                  <div className="password-input">

                    <input
                      id="confirmPassword"
                      type={
                        showConfirmPassword
                          ? "text"
                          : "password"
                      }
                      name="confirmPassword"
                      value={formData.confirmPassword}
                      onChange={handleChange}
                      placeholder="Confirm password"
                      autoComplete="new-password"
                      required
                    />

                    <button
                      type="button"
                      className="password-toggle"
                      onClick={() =>
                        setShowConfirmPassword(
                          (previous) => !previous
                        )
                      }
                      aria-label={
                        showConfirmPassword
                          ? "Hide confirm password"
                          : "Show confirm password"
                      }
                    >

                      {showConfirmPassword
                        ? "🙈"
                        : "👁️"}

                    </button>

                  </div>

                </div>

              </div>

              {/* TERMS */}

              <label className="terms-checkbox">

                <input
                  type="checkbox"
                  checked={acceptTerms}
                  onChange={(event) =>
                    setAcceptTerms(event.target.checked)
                  }
                />

                <span>
                  I agree to the{" "}
                  <Link to="/terms">
                    Terms of Service
                  </Link>{" "}
                  and{" "}
                  <Link to="/privacy">
                    Privacy Policy
                  </Link>
                </span>

              </label>

              {/* SUBMIT */}

              <button
                type="submit"
                className="auth-submit-btn"
                disabled={isLoading}
              >

                {isLoading
                  ? "Creating Account..."
                  : "Create Account →"}

              </button>

            </form>

            {/* LOGIN */}

            <div className="auth-divider">

              <span>
                Already have an account?
              </span>

            </div>

            <Link
              to="/login"
              className="secondary-auth-btn"
            >
              Sign In
            </Link>

          </div>

        </div>

      </div>

    </div>
  );
}

export default Register;