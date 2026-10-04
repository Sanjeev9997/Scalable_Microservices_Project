import StatusBadge from "../../../components/StatusBadge/StatusBadge";
import "./System.css";
import "../admin-common.css";

function System() {
  const services = [
    { name: "API Gateway", status: "Healthy", type: "gateway" },

    { name: "Auth Service", status: "Healthy", type: "service" },
    { name: "User Service", status: "Healthy", type: "service" },
    { name: "Product Service", status: "Healthy", type: "service" },
    { name: "Order Service", status: "Healthy", type: "service" },
    { name: "Inventory Service", status: "Healthy", type: "service" },

    { name: "Notification Service", status: "Healthy", type: "support" },
    { name: "Kafka", status: "Healthy", type: "support" },
    { name: "Redis", status: "Healthy", type: "support" }
  ];

  const gateway = services[0];
  const coreServices = services.slice(1, 6);
  const supportServices = services.slice(6);

  return (
    <div className="system-page">

      <div className="admin-page-header">

        <div>
          <span className="admin-label">
            DISTRIBUTED ARCHITECTURE
          </span>

          <h1>System Health</h1>

          <p>
            Live overview of the ShopEasy microservices platform.
          </p>
        </div>

        <StatusBadge status="Healthy" />

      </div>

      <div className="architecture">

        <div className="architecture-node gateway-node">
          <div className="node-icon">🌐</div>
          <h3>{gateway.name}</h3>
          <StatusBadge status={gateway.status} />
        </div>

        <div className="connection-line"></div>

        <div className="core-services-grid">

          {coreServices.map((service) => (
            <div
              className="architecture-node"
              key={service.name}
            >
              <div className="node-icon">⚙️</div>

              <h3>{service.name}</h3>

              <StatusBadge status={service.status} />
            </div>
          ))}

        </div>

        <div className="support-label">
          SUPPORTING INFRASTRUCTURE
        </div>

        <div className="support-services-grid">

          {supportServices.map((service) => (
            <div
              className="architecture-node support-node"
              key={service.name}
            >
              <div className="node-icon">
                {service.name === "Kafka" ? "⚡" : "🗄️"}
              </div>

              <h3>{service.name}</h3>

              <StatusBadge status={service.status} />
            </div>
          ))}

        </div>

      </div>

    </div>
  );
}

export default System;