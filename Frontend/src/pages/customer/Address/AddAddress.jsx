import { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./AddAddress.css";
import { addUserAddress } from "../../../services/userService";
import {clearCart} from "../../../services/cartService";

function AddAddress() {
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    fullName: "",
    phoneNumber: "",
    houseNo: "",
    street: "",
    landmark: "",
    city: "",
    state: "",
    country: "India",
    postalCode: "",
    isDefault: false,
  });

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  // ========================================
  // HANDLE INPUT CHANGE
  // ========================================

  const handleChange = (event) => {
    const { name, value, type, checked } = event.target;

    setFormData((prev) => ({
      ...prev,
      [name]: type === "checkbox" ? checked : value,
    }));
  };


  // ========================================
  // SUBMIT ADDRESS
  // ========================================

  const handleSubmit = async (event) => {
    event.preventDefault();

    setError("");

    // Get logged-in user
    const storedUser = localStorage.getItem("user");

    if (!storedUser) {
      setError("Please login before adding an address.");
      return;
    }

    const user = JSON.parse(storedUser);

    if (!user?.id) {
      setError("User information is missing.");
      return;
    }


    // ========================================
    // VALIDATION
    // ========================================

    if (
      !formData.fullName.trim() ||
      !formData.phoneNumber.trim() ||
      !formData.houseNo.trim() ||
      !formData.street.trim() ||
      !formData.city.trim() ||
      !formData.state.trim() ||
      !formData.country.trim() ||
      !formData.postalCode.trim()
    ) {
      setError("Please fill all required fields.");
      return;
    }

    if (!/^\d{10}$/.test(formData.phoneNumber)) {
      setError("Please enter a valid 10-digit phone number.");
      return;
    }

    if (!/^\d{6}$/.test(formData.postalCode)) {
      setError("Please enter a valid 6-digit postal code.");
      return;
    }


    // ========================================
    // API CALL
    // ========================================

    try {
      setLoading(true);

      /*
       * IMPORTANT:
       *
       * Replace this section with your actual
       * addAddress() service function once we
       * connect userService.js.
       */

      // console.log("User ID:", user.id);
      // console.log("Address Request:", formData);
      const response = await addUserAddress(user.id, formData);

      console.log("Add address response:", response);
      if(response){
        await clearCart(user.id);
      }
      /*
       * TEMPORARY
       *
       * Remove this after connecting backend.
       */
      
      alert("Address added successfully!");

      navigate("/checkout");

    } catch (err) {

      console.error("Add address error:", err);

      setError(
        err?.response?.data?.message ||
          "Unable to save address. Please try again."
      );

    } finally {
      setLoading(false);
    }
  };


  // ========================================
  // UI
  // ========================================

  return (
    <div className="add-address-page">

      {/* HEADER */}

      <div className="add-address-header">

        <div>

          <h1>Add New Address</h1>

          <p>
            Add a delivery address for your order
          </p>

        </div>


        <button
          type="button"
          className="back-btn"
          onClick={() => navigate("/checkout")}
        >
          ← Back to Checkout
        </button>

      </div>


      {/* FORM CARD */}

      <div className="address-form-card">

        <div className="form-heading">

          <div className="address-icon">
            📍
          </div>

          <div>

            <h2>Delivery Address</h2>

            <p>
              Enter the address where you want
              your order delivered.
            </p>

          </div>

        </div>


        {/* ERROR */}

        {error && (
          <div className="form-error">
            {error}
          </div>
        )}


        <form onSubmit={handleSubmit}>


          {/* ========================================
              FULL NAME + PHONE
              ======================================== */}

          <div className="form-row">

            <div className="form-group">

              <label>
                Full Name <span>*</span>
              </label>

              <input
                type="text"
                name="fullName"
                value={formData.fullName}
                onChange={handleChange}
                placeholder="Enter full name"
              />

            </div>


            <div className="form-group">

              <label>
                Phone Number <span>*</span>
              </label>

              <input
                type="tel"
                name="phoneNumber"
                value={formData.phoneNumber}
                onChange={handleChange}
                placeholder="Enter 10-digit mobile number"
                maxLength="10"
              />

            </div>

          </div>


          {/* ========================================
              HOUSE NO
              ======================================== */}

          <div className="form-group">

            <label>
              House / Flat / Building No. <span>*</span>
            </label>

            <input
              type="text"
              name="houseNo"
              value={formData.houseNo}
              onChange={handleChange}
              placeholder="e.g. Flat 204, Tower B"
            />

          </div>


          {/* ========================================
              STREET
              ======================================== */}

          <div className="form-group">

            <label>
              Street / Area <span>*</span>
            </label>

            <input
              type="text"
              name="street"
              value={formData.street}
              onChange={handleChange}
              placeholder="Street, colony, sector, area"
            />

          </div>


          {/* ========================================
              LANDMARK
              ======================================== */}

          <div className="form-group">

            <label>
              Landmark
            </label>

            <input
              type="text"
              name="landmark"
              value={formData.landmark}
              onChange={handleChange}
              placeholder="Nearby landmark (optional)"
            />

          </div>


          {/* ========================================
              CITY + STATE
              ======================================== */}

          <div className="form-row">

            <div className="form-group">

              <label>
                City <span>*</span>
              </label>

              <input
                type="text"
                name="city"
                value={formData.city}
                onChange={handleChange}
                placeholder="Enter city"
              />

            </div>


            <div className="form-group">

              <label>
                State <span>*</span>
              </label>

              <input
                type="text"
                name="state"
                value={formData.state}
                onChange={handleChange}
                placeholder="Enter state"
              />

            </div>

          </div>


          {/* ========================================
              COUNTRY + POSTAL CODE
              ======================================== */}

          <div className="form-row">

            <div className="form-group">

              <label>
                Country <span>*</span>
              </label>

              <input
                type="text"
                name="country"
                value={formData.country}
                onChange={handleChange}
                placeholder="Enter country"
              />

            </div>


            <div className="form-group">

              <label>
                Postal Code <span>*</span>
              </label>

              <input
                type="text"
                name="postalCode"
                value={formData.postalCode}
                onChange={handleChange}
                placeholder="Enter 6-digit postal code"
                maxLength="6"
              />

            </div>

          </div>


          {/* ========================================
              DEFAULT ADDRESS
              ======================================== */}

          <label className="default-checkbox">

            <input
              type="checkbox"
              name="isDefault"
              checked={formData.isDefault}
              onChange={handleChange}
            />

            <span>
              Make this my default delivery address
            </span>

          </label>


          {/* ========================================
              ACTION BUTTONS
              ======================================== */}

          <div className="form-actions">

            <button
              type="button"
              className="cancel-btn"
              onClick={() => navigate("/checkout")}
              disabled={loading}
            >
              Cancel
            </button>


            <button
              type="submit"
              className="save-address-btn"
              disabled={loading}
            >
              {loading
                ? "Saving..."
                : "Save Address"}
            </button>

          </div>

        </form>

      </div>

    </div>
  );
}

export default AddAddress;
