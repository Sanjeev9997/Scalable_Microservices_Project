import ServiceStatus from "../../../components/ServiceStatus/ServiceStatus";
import "./Services.css";
import "../admin-common.css";

function Services() {
  const services = [
    {
      name: "API Gateway",
      status: "Healthy",
      responseTime: 18,
      description: "Spring Cloud Gateway"
    },
    {
      name: "Auth Service",
      status: "Healthy",
      responseTime: 24,
      description: "JWT authentication"
    },
    {
      name: "User Service",
      status: "Healthy",
      responseTime: 28,
      description: "User management"
    },
    {
      name: "Product Service",
      status: "Healthy",
      responseTime: 31,
      description: "Product catalog"
    },
    {
      name: "Order Service",
      status: "Healthy",
      responseTime: 39,
      description: "Order processing"
    },
    {
      name: "Inventory Service",
      status: "Healthy",
      responseTime: 26,
      description: "Inventory management"
    },
    {
      name: "Notification Service",
      status: "Healthy",
      responseTime: 42,
      description: "Notifications"
    }
  ];

  return (
    <div className="services-page">

      <div className="admin-page-header">
        <div>
          <span className="admin-label">
            MICROSERVICE ARCHITECTURE
          </span>

          <h1>Services</h1>

          <p>
            Monitor the health of distributed services.
          </p>
        </div>
      </div>

      <div className="services-list">

        {services.map((service) => (
          <ServiceStatus
            key={service.name}
            service={service}
          />
        ))}

      </div>

    </div>
  );
}

export default Services;