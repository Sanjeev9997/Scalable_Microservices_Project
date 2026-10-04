import "./Events.css";

function Events() {
  const events = [
    {
      id: "EVT-001",
      type: "ORDER_CREATED",
      topic: "order-events",
      service: "Order Service",
      timestamp: "23:41:12",
      status: "Processed"
    },
    {
      id: "EVT-002",
      type: "INVENTORY_RESERVED",
      topic: "inventory-events",
      service: "Inventory Service",
      timestamp: "23:41:13",
      status: "Processed"
    },
    {
      id: "EVT-003",
      type: "PAYMENT_COMPLETED",
      topic: "payment-events",
      service: "Order Service",
      timestamp: "23:41:15",
      status: "Processed"
    },
    {
      id: "EVT-004",
      type: "NOTIFICATION_SENT",
      topic: "notification-events",
      service: "Notification Service",
      timestamp: "23:41:17",
      status: "Processed"
    }
  ];

  return (
    <div className="events-page">

      <div className="admin-page-header">
        <div>
          <span className="admin-label">KAFKA</span>

          <h1>Event Monitor</h1>

          <p>
            Monitor recent event-driven communication.
          </p>
        </div>

        <div className="live-indicator">
          <span></span>
          LIVE
        </div>
      </div>

      <div className="event-stats">

        <div>
          <span>Events Today</span>
          <strong>18,492</strong>
        </div>

        <div>
          <span>Processed</span>
          <strong>18,470</strong>
        </div>

        <div>
          <span>Failed</span>
          <strong>22</strong>
        </div>

        <div>
          <span>Topics</span>
          <strong>8</strong>
        </div>

      </div>

      <div className="admin-table-card">

        <table className="admin-table">

          <thead>
            <tr>
              <th>EVENT</th>
              <th>TOPIC</th>
              <th>SERVICE</th>
              <th>TIMESTAMP</th>
              <th>STATUS</th>
            </tr>
          </thead>

          <tbody>

            {events.map((event) => (
              <tr key={event.id}>

                <td>
                  <div>
                    <strong>{event.type}</strong>
                    <small>{event.id}</small>
                  </div>
                </td>

                <td>{event.topic}</td>

                <td>{event.service}</td>

                <td>{event.timestamp}</td>

                <td>
                  <span className="event-success">
                    ● {event.status}
                  </span>
                </td>

              </tr>
            ))}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default Events;