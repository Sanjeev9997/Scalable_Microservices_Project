
import { Link } from "react-router-dom";
import "./OrderCard.css";

function OrderCard({ order }) {

  const {
    id,
    orderDate,
    status,
    totalAmount,
    items = []
  } = order;

  const getStatusClass = () => {
    switch (status?.toLowerCase()) {
      case "delivered":
        return "status-delivered";

      case "shipped":
        return "status-shipped";

      case "processing":
        return "status-processing";

      case "cancelled":
        return "status-cancelled";

      case "pending":
        return "status-pending";

      default:
        return "status-processing";
    }
  };

  const getStatusIcon = () => {
    switch (status?.toLowerCase()) {
      case "delivered":
        return "✓";

      case "shipped":
        return "🚚";

      case "processing":
        return "⚙";

      case "cancelled":
        return "✕";

      case "pending":
        return "⏳";

      default:
        return "⚙";
    }
  };

  return (
    <div className="order-card">

      {/* Header */}

      <div className="order-header">

        <div>
          <span className="order-label">
            ORDER
          </span>

          <h3>
            #{id}
          </h3>
        </div>

        <span
          className={`order-status ${getStatusClass()}`}
        >
          <span className="status-icon">
            {getStatusIcon()}
          </span>
          <span className="status-text">
            {status}
          </span>
        </span>
      </div>

      {/* Items */}

      <div className="order-items">
        {items.map((item) => (
          <div key={item.id} className="order-item">
            <img src={item.image} alt={item.name} />
            <div className="item-details">
              <h4>{item.name}</h4>
              <p>Quantity: {item.quantity}</p>
              <p>Price: ${item.price.toFixed(2)}</p>
            </div>
          </div>
        ))}
      </div>

      {/* Footer */}

      <div className="order-footer">
        <p>Total Amount: ${totalAmount.toFixed(2)}</p>
        <p>Order Date: {new Date(orderDate).toLocaleDateString()}</p>
      </div>
    </div>
  );
}

export default OrderCard;
