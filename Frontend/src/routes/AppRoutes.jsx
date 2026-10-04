
import {
  Routes,
  Route,
  Navigate,
  Outlet
} from "react-router-dom";

import { useAuth } from "../context/AuthContext";

import Navbar from "../components/Navbar/Navbar";
import Sidebar from "../components/Sidebar/Sidebar";

// Customer
import Home from "../pages/customer/Home/Home";
import Products from "../pages/customer/Products/Products";
import ProductDetails from "../pages/customer/ProductDetails/ProductDetails";
import Cart from "../pages/customer/Cart/Cart";
import Checkout from "../pages/customer/Checkout/Checkout";
import Orders from "../pages/customer/Orders/Orders";
import Profile from "../pages/profile/Profile";

// Auth
import Login from "../pages/auth/Login/Login";
import Register from "../pages/auth/Register/Register";

// Admin
import Dashboard from "../pages/admin/Dashboard/Dashboard";
import AdminProducts from "../pages/admin/Products/AdminProducts";
import Inventory from "../pages/admin/Inventory/Inventory";
import AdminOrders from "../pages/admin/Orders/AdminOrders";
import Users from "../pages/admin/Users/Users";
import Services from "../pages/admin/Services/Services";
import Events from "../pages/admin/Events/Events";
import Cache from "../pages/admin/Cache/Cache";
import AddAddress from "../pages/customer/Address/AddAddress";


function CustomerShell() {
  return (
    <>
      <Navbar />

      <main>
        <Outlet />
      </main>
    </>
  );
}


function AdminShell() {
  return (
    <div className="admin-layout">

      <Sidebar />

      <main className="admin-content">
        <Outlet />
      </main>

    </div>
  );
}


function ProtectedRoute({ children }) {
  const {
    isAuthenticated,
    loading
  } = useAuth();

  if (loading) {
    return (
      <div className="route-loading">
        Loading...
      </div>
    );
  }

  if (!isAuthenticated) {
    return (
      <Navigate
        to="/login"
        replace
      />
    );
  }

  return children;
}


function AdminRoute({ children }) {
  const {
    isAuthenticated,
    isAdmin,
    loading
  } = useAuth();

  if (loading) {
    return (
      <div className="route-loading">
        Loading...
      </div>
    );
  }

  if (!isAuthenticated) {
    return (
      <Navigate
        to="/login"
        replace
      />
    );
  }

  if (!isAdmin) {
    return (
      <Navigate
        to="/"
        replace
      />
    );
  }

  return children;
}


function AppRoutes() {
  return (
    <Routes>

      {/* ==========================
          AUTH
          ========================== */}

      <Route
        path="/login"
        element={<Login />}
      />

      <Route
        path="/register"
        element={<Register />}
      />


      {/* ==========================
          CUSTOMER
          ========================== */}

      <Route
        element={<CustomerShell />}
      >

        <Route
          path="/"
          element={<Home />}
        />

        <Route
          path="/products"
          element={<Products />}
        />

        <Route
          path="/products/:id"
          element={<ProductDetails />}
        />

        <Route
          path="/cart"
          element={
            <ProtectedRoute>
              <Cart />
            </ProtectedRoute>
          }
        />

        <Route
          path="/checkout"
          element={
            <ProtectedRoute>
              <Checkout />
            </ProtectedRoute>
          }
        />
        <Route
        path="addresses/add"
        element={
          <ProtectedRoute>
            <AddAddress />
          </ProtectedRoute>
        }
      />
        <Route
          path="/orders"
          element={
            <ProtectedRoute>
              <Orders />
            </ProtectedRoute>
          }
        />

        {/* ==========================
            PROFILE
            ========================== */}

        <Route
          path="/profile"
          element={
            <ProtectedRoute>
              <Profile />
            </ProtectedRoute>
          }
        />

      </Route>


      {/* ==========================
          ADMIN
          ========================== */}

      <Route
        path="/admin"
        element={
          <AdminRoute>
            <AdminShell />
          </AdminRoute>
        }
      >

        <Route
          index
          element={<Dashboard />}
        />

        <Route
          path="products"
          element={<AdminProducts />}
        />

        <Route
          path="inventory"
          element={<Inventory />}
        />

        <Route
          path="orders"
          element={<AdminOrders />}
        />

        <Route
          path="users"
          element={<Users />}
        />

        <Route
          path="services"
          element={<Services />}
        />

        <Route
          path="events"
          element={<Events />}
        />

        <Route
          path="cache"
          element={<Cache />}
        />

      </Route>


      {/* ==========================
          FALLBACK
          ========================== */}

      <Route
        path="*"
        element={
          <Navigate
            to="/"
            replace
          />
        }
      />

    </Routes>
  );
}


export default AppRoutes;