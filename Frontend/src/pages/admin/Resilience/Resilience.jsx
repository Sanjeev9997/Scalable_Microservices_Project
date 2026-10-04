import StatusBadge from "../../../components/StatusBadge/StatusBadge";
import "./Resilience.css";
import "../admin-common.css";

function Resilience() {
  const services = [
    {
      name: "User Service",
      state: "CLOSED",
      requests: 18420,
      successRate: "98.7%",
      failures: 241,
      retries: 82,
      fallback: 14
    },
    {
      name: "Order Service",
      state: "CLOSED",
      requests: 12540,
      successRate: "99.1%",
      failures: 113,
      retries: 41,
      fallback: 5
    },
    {
      name: "Inventory Service",
      state: "HALF_OPEN",
      requests: 10980,
      successRate: "96.4%",
      failures: 395,
      retries: 120,
      fallback: 31
    }
  ];

  return (
    <div className="resilience-page">

      <div className="admin-page-header">

        <div>
          <span className="admin-label">
            RESILIENCE4J
          </span>

          <h1>Resilience Monitor</h1>

          <p>
            Monitor circuit breakers, retries and fallbacks.
          </p>
        </div>

      </div>

      <div className="resilience-overview">

        <div>
          <span>Total Requests</span>
          <strong>41,940</strong>
        </div>

        <div>
          <span>Success Rate</span>
          <strong>98.2%</strong>
        </div>

        <div>
          <span>Retries</span>
          <strong>243</strong>
        </div>

        <div>
          <span>Fallback Calls</span>
          <strong>50</strong>
        </div>

      </div>

      <div className="resilience-grid">

        {services.map((service) => (
          <div
            className="resilience-card"
            key={service.name}
          >

            <div className="resilience-card-header">

              <div>
                <h2>{service.name}</h2>
                <p>Circuit Breaker</p>
              </div>

              <span
                className={`circuit-state ${service.state.toLowerCase()}`}
              >
                ● {service.state}
              </span>

            </div>

            <div className="resilience-metrics">

              <div>
                <span>Requests</span>
                <strong>
                  {service.requests.toLocaleString()}
                </strong>
              </div>

              <div>
                <span>Success Rate</span>
                <strong>{service.successRate}</strong>
              </div>

              <div>
                <span>Failures</span>
                <strong>{service.failures}</strong>
              </div>

              <div>
                <span>Retries</span>
                <strong>{service.retries}</strong>
              </div>

              <div>
                <span>Fallback</span>
                <strong>{service.fallback}</strong>
              </div>

            </div>

          </div>
        ))}

      </div>

    </div>
  );
}

export default Resilience;