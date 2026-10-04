import StatusBadge from "../../../components/StatusBadge/StatusBadge";
import "./Inventory.css";
import "../admin-common.css";

function Inventory() {
  const inventory = [
    {
      product: "Wireless Headphones",
      sku: "WH-001",
      available: 7,
      reserved: 3,
      total: 10,
      status: "Low"
    },
    {
      product: "Mechanical Keyboard",
      sku: "MK-002",
      available: 18,
      reserved: 5,
      total: 23,
      status: "Active"
    },
    {
      product: "Wireless Mouse",
      sku: "WM-003",
      available: 25,
      reserved: 4,
      total: 29,
      status: "Active"
    },
    {
      product: "Smart Watch",
      sku: "SW-004",
      available: 0,
      reserved: 0,
      total: 0,
      status: "Out of Stock"
    }
  ];

  return (
    <div className="inventory-page">

      <div className="admin-page-header">
        <div>
          <span className="admin-label">INVENTORY SERVICE</span>
          <h1>Inventory</h1>
          <p>Monitor available and reserved inventory.</p>
        </div>
      </div>

      <div className="inventory-summary">

        <div>
          <span>Available</span>
          <strong>50</strong>
        </div>

        <div>
          <span>Reserved</span>
          <strong>12</strong>
        </div>

        <div>
          <span>Low Stock</span>
          <strong>4</strong>
        </div>

        <div>
          <span>Out of Stock</span>
          <strong>2</strong>
        </div>

      </div>

      <div className="admin-table-card">

        <table className="admin-table">

          <thead>
            <tr>
              <th>PRODUCT</th>
              <th>SKU</th>
              <th>AVAILABLE</th>
              <th>RESERVED</th>
              <th>TOTAL</th>
              <th>STATUS</th>
            </tr>
          </thead>

          <tbody>
            {inventory.map((item) => (
              <tr key={item.sku}>
                <td>
                  <strong>{item.product}</strong>
                </td>

                <td>{item.sku}</td>

                <td>{item.available}</td>

                <td>{item.reserved}</td>

                <td>{item.total}</td>

                <td>
                  <StatusBadge status={item.status} />
                </td>
              </tr>
            ))}
          </tbody>

        </table>

      </div>

    </div>
  );
}

export default Inventory;