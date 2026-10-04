import { useState } from "react";
import StatusBadge from "../../../components/StatusBadge/StatusBadge";
import "../admin-common.css";
import "./AdminProducts.css";

function AdminProducts() {
  const [products] = useState([
    {
      id: "P001",
      name: "Wireless Headphones",
      category: "Electronics",
      price: 3499,
      stock: 7,
      status: "Active"
    },
    {
      id: "P002",
      name: "Mechanical Keyboard",
      category: "Electronics",
      price: 2999,
      stock: 18,
      status: "Active"
    },
    {
      id: "P003",
      name: "Wireless Mouse",
      category: "Accessories",
      price: 799,
      stock: 25,
      status: "Active"
    },
    {
      id: "P004",
      name: "Smart Watch",
      category: "Accessories",
      price: 4999,
      stock: 0,
      status: "Inactive"
    }
  ]);

  return (
    <div className="admin-products">

      <div className="admin-page-header">
        <div>
          <span className="admin-label">CATALOG</span>
          <h1>Products</h1>
          <p>Manage your product catalog.</p>
        </div>

        <button className="primary-admin-btn">
          + Add Product
        </button>
      </div>

      <div className="products-toolbar">
        <input
          type="text"
          placeholder="Search products..."
        />

        <select>
          <option>All Categories</option>
          <option>Electronics</option>
          <option>Accessories</option>
          <option>Fashion</option>
        </select>
      </div>

      <div className="admin-table-card">
        <table className="admin-table">

          <thead>
            <tr>
              <th>PRODUCT</th>
              <th>CATEGORY</th>
              <th>PRICE</th>
              <th>STOCK</th>
              <th>STATUS</th>
              <th>ACTIONS</th>
            </tr>
          </thead>

          <tbody>
            {products.map((product) => (
              <tr key={product.id}>
                <td>
                  <div className="product-name-cell">
                    <div className="admin-product-icon">
                      📦
                    </div>
                    <div>
                      <strong>{product.name}</strong>
                      <small>{product.id}</small>
                    </div>
                  </div>
                </td>

                <td>{product.category}</td>

                <td>
                  ₹{product.price.toLocaleString("en-IN")}
                </td>

                <td>
                  <span
                    className={
                      product.stock === 0
                        ? "stock-danger"
                        : product.stock <= 10
                        ? "stock-warning"
                        : "stock-good"
                    }
                  >
                    {product.stock}
                  </span>
                </td>

                <td>
                  <StatusBadge status={product.status} />
                </td>

                <td>
                  <div className="table-actions">
                    <button>✏️</button>
                    <button>🗑️</button>
                  </div>
                </td>
              </tr>
            ))}
          </tbody>

        </table>
      </div>

    </div>
  );
}

export default AdminProducts;