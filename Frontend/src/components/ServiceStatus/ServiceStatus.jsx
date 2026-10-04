
import StatusBadge from "../StatusBadge/StatusBadge";
import "./ServiceStatus.css";

function ServiceStatus({ service }) {

  const {
    name,
    status,
    responseTime,
    description
  } = service;

  return (
    <div className="service-status">

      {/* Service Icon */}

      <div className="service-icon">
        ⚙️
      </div>


      {/* Service Information */}

      <div className="service-info">

        <h3>
          {name}
        </h3>

        <p>
          {description}
        </p>

      </div>


      {/* Response Time */}

      <div className="service-response">

        <span>
          Response
        </span>

        <strong>
          {responseTime} ms
        </strong>

      </div>


      {/* Status */}

      <div className="service-status-badge">

        <StatusBadge
          status={status}
        />

      </div>

    </div>
  );
}

export default ServiceStatus;

