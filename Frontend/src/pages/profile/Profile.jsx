
import { useEffect, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { getMyProfile } from "../../services/userService";
import "./Profile.css";

function Profile() {

  const navigate = useNavigate();

  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {

    const fetchProfile = async () => {
      
      const token = localStorage.getItem("accessToken");

      if (!token) {
        navigate("/login");
        return;
      }

      try {

        const data = localStorage.getItem("user")
          ? JSON.parse(localStorage.getItem("user"))
          : null;

        setUser(data);

      } catch (error) {

        console.error("Profile Error:", error);

        if (
          error.response?.status === 401 ||
          error.response?.status === 403
        ) {

          localStorage.removeItem("accessToken");

          navigate("/login");

          return;
        }

        setError(
          error.response?.data?.message ||
          "Unable to load profile."
        );

      } finally {

        setLoading(false);

      }

    };

    fetchProfile();

  }, [navigate]);

  const handleLogout = () => {

    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");

    navigate("/login");

  };

  if (loading) {

    return (
      <div className="profile-page">
        <h2>Loading profile...</h2>
      </div>
    );

  }

  if (error) {

    return (
      <div className="profile-page">
        <h2>{error}</h2>

        <Link to="/">
          Back to Home
        </Link>
      </div>
    );

  }

  if (!user) {

    return (
      <div className="profile-page">
        <h2>No profile data found.</h2>
      </div>
    );

  }

  return (

    <div className="profile-page">

      <div className="profile-card">

        <div className="profile-top">

          <div className="profile-avatar">

            {(
              user.firstName ||
              user.fullName ||
              "U"
            ).charAt(0).toUpperCase()}
            
          </div>

          <h1>
            {user.firstName
              ? `${user.firstName} ${user.lastName || ""}`
              : user.fullName || "My Profile"}
          </h1>

          <p>{user.email}</p>

        </div>

        <div className="profile-details">

          <div className="detail-row">
            <strong>First Name</strong>
            <span>{user.firstName || "Not available"}</span>
          </div>

          <div className="detail-row">
            <strong>Last Name</strong>
            <span>{user.lastName || "Not available"}</span>
          </div>

          <div className="detail-row">
            <strong>Email</strong>
            <span>{user.email || "Not available"}</span>
          </div>

          <div className="detail-row">
            <strong>Phone</strong>
            <span>{user.phone || "Not available"}</span>
          </div>

          <div className="detail-row">
            <strong>Gender</strong>
            <span>{user.gender || "Not available"}</span>
          </div>

        </div>

        <div className="profile-actions">

          <Link to="/" className="profile-btn">
            Continue Shopping
          </Link>
          
          <button
            onClick={handleLogout}
            className="logout-btn"
          >
            Logout
          </button>

        </div>

      </div>

    </div>

  );

}

export default Profile;