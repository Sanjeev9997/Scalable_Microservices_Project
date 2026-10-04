import { useState, useRef, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";

import "./Navbar.css";

import { useAuth } from "../../context/AuthContext";
import { useCart } from "../../context/CartContext";

function Navbar() {

  const { user, isAuthenticated, logout } = useAuth();

  // Get cart count directly from CartContext
  const { cartCount } = useCart();

  const [isDropdownOpen, setIsDropdownOpen] = useState(false);

  const dropdownRef = useRef(null);

  const navigate = useNavigate();


  // Close dropdown when clicking outside
  useEffect(() => {

    function handleClickOutside(event) {

      if (
        dropdownRef.current &&
        !dropdownRef.current.contains(event.target)
      ) {
        setIsDropdownOpen(false);
      }

    }

    document.addEventListener(
      "mousedown",
      handleClickOutside
    );

    return () => {

      document.removeEventListener(
        "mousedown",
        handleClickOutside
      );

    };

  }, []);


  // Toggle dropdown
  const handleDropdownToggle = () => {

    setIsDropdownOpen(
      (previous) => !previous
    );

  };


  // Navigate and close dropdown
  const handleMenuClick = (path) => {

    setIsDropdownOpen(false);

    navigate(path);

  };


  // Logout
  const handleLogout = () => {

    setIsDropdownOpen(false);

    logout();

    navigate("/login");

  };


  return (
    <nav className="navbar">

      {/* Logo */}
      <Link
        to="/"
        className="logo"
      >
        Shop<span>Easy</span>
      </Link>


      {/* Search */}
      <div className="nav-search">

        <input
          type="text"
          placeholder="Search for products..."
        />

        <button>
          🔍
        </button>

      </div>


      {/* Navigation Links */}
      <div className="nav-links">

        <Link to="/">
          Home
        </Link>


        <Link to="/products">
          Products
        </Link>


        {/* Cart */}
        <Link
          to="/cart"
          className="cart-link"
        >

          🛒 Cart

          {cartCount > 0 && (
            <span className="cart-count">
              {cartCount}
            </span>
          )}

        </Link>


        {/* Authentication */}
        {!isAuthenticated ? (

          <Link to="/login">
            👤 Login
          </Link>

        ) : (

          // Profile Dropdown
          <div
            className="profile-dropdown"
            ref={dropdownRef}
          >

            {/* Profile Button */}
            <button
              className="profile-button"
              onClick={handleDropdownToggle}
            >

              👤 {user?.firstName || "User"}

              <span className="dropdown-arrow">
                {isDropdownOpen
                  ? "▲"
                  : "▼"}
              </span>

            </button>


            {/* Dropdown Menu */}
            {isDropdownOpen && (

              <div className="dropdown-menu">

                <div className="dropdown-user-info">

                  <strong>
                    {user?.firstName}{" "}
                    {user?.lastName || ""}
                  </strong>

                  <small>
                    {user?.email}
                  </small>

                </div>


                <div className="dropdown-divider"></div>


                {/* Profile */}
                <button
                  onClick={() =>
                    handleMenuClick("/profile")
                  }
                >
                  👤 My Profile
                </button>


                {/* Orders */}
                <button
                  onClick={() =>
                    handleMenuClick("/orders")
                  }
                >
                  📦 My Orders
                </button>


                {/* Wishlist */}
                <button
                  onClick={() =>
                    handleMenuClick("/wishlist")
                  }
                >
                  ❤️ Wishlist
                </button>


                {/* Settings */}
                <button
                  onClick={() =>
                    handleMenuClick("/settings")
                  }
                >
                  ⚙️ Settings
                </button>


                <div className="dropdown-divider"></div>


                {/* Logout */}
                <button
                  className="logout-button"
                  onClick={handleLogout}
                >
                  🚪 Logout
                </button>

              </div>

            )}

          </div>

        )}

      </div>

    </nav>
  );
}

export default Navbar;