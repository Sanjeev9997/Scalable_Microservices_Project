
import StatusBadge from "../../../components/StatusBadge/StatusBadge";
import ServiceStatus from "../../../components/ServiceStatus/ServiceStatus";
import RevenueChart from "../../../components/Charts/RevenueChart"
import OrdersChart from "../../../components/Charts/OrdersChart"
import "../admin-common.css";
import "./Dashboard.css";

function Dashboard() {
  const stats = [
    {
      title: "Total Orders",
      value: "12,845",
      change: "+12.5%",
      icon: "🛒"
    },
    {
      title: "Revenue",
      value: "₹24.8L",
      change: "+8.2%",
      icon: "₹"
    },
    {
      title: "Users",
      value: "8,492",
      change: "+6.4%",
      icon: "👥"
    },
    {
      title: "Products",
      value: "1,284",
      change: "+4.8%",
      icon: "📦"
    }
  ];

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
      name: "Product Service",
      status: "Healthy",
      responseTime: 31,
      description: "Product management"
    },
    {
      name: "Order Service",
      status: "Healthy",
      responseTime: 39,
      description: "Order processing"
    }
  ];

  return (
    <div className="admin-dashboard">

      <div className="admin-page-header">
        <div>
          <span className="admin-label">ADMIN PANEL</span>
          <h1>Dashboard</h1>
          <p>Overview of your ShopEasy platform.</p>
        </div>

        <StatusBadge status="Healthy" />
      </div>

      <div className="stats-grid">
        {stats.map((stat) => (
          <div className="stat-card" key={stat.title}>
            <div className="stat-top">
              <div className="stat-icon">{stat.icon}</div>
              <span className="stat-change">{stat.change}</span>
            </div>

            <span className="stat-title">{stat.title}</span>
            <strong className="stat-value">{stat.value}</strong>
          </div>
        ))}
      </div>

      <div className="dashboard-charts">
        <RevenueChart />
        <OrdersChart />
      </div>

      <div className="services-preview">
        <div className="admin-section-header">
          <div>
            <h2>System Overview</h2>
            <p>Current microservice health.</p>
          </div>
        </div>

        <div className="services-grid">
          {services.map((service) => (
            <ServiceStatus
              key={service.name}
              service={service}
            />
          ))}
        </div>
      </div>

    </div>
  );
}

export default Dashboard;