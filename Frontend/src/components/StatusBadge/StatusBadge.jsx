
import "./StatusBadge.css";

function StatusBadge({ status }) {

  const normalizedStatus = status
    ?.toLowerCase()
    .replace(/\s+/g, "-");

  const statusConfig = {

    delivered: {
      label: "Delivered",
      icon: "✓",
      className: "success"
    },

    shipped: {
      label: "Shipped",
      icon: "🚚",
      className: "info"
    },

    processing: {
      label: "Processing",
      icon: "⚙",
      className: "warning"
    },

    pending: {
      label: "Pending",
      icon: "⏳",
      className: "pending"
    },

    cancelled: {
      label: "Cancelled",
      icon: "✕",
      className: "danger"
    },

    failed: {
      label: "Failed",
      icon: "⚠",
      className: "danger"
    },

    active: {
      label: "Active",
      icon: "●",
      className: "success"
    },

    inactive: {
      label: "Inactive",
      icon: "●",
      className: "danger"
    },

    low: {
      label: "Low Stock",
      icon: "⚠",
      className: "warning"
    },

    "out-of-stock": {
      label: "Out of Stock",
      icon: "✕",
      className: "danger"
    },

    healthy: {
      label: "Healthy",
      icon: "●",
      className: "success"
    },

    down: {
      label: "Down",
      icon: "●",
      className: "danger"
    }

  };

  const config = statusConfig[normalizedStatus] || {
    label: status || "Unknown",
    icon: "•",
    className: "default"
  };

  return (
    <span
      className={`status-badge ${config.className}`}
    >
      <span className="status-badge-icon">
        {config.icon}
      </span>

      {config.label}
    </span>
  );
}

export default StatusBadge;

