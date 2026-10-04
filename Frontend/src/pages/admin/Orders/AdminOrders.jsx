import StatusBadge from "../../../components/StatusBadge/StatusBadge";
import "./AdminOrders.css";
import "../admin-common.css";

function AdminOrders() {
  const orders = [
    {
      id: "ORD-10245",
      customer: "Rahul Sharma",
      amount: 3499,
      payment: "Paid",
      status: "Delivered",
      date: "16 Sep 2026"
    },
    {
      id: "ORD-10244",
      customer: "Aman Verma",
      amount: 5998,
      payment: "Paid",
      status: "Shipped",
      date: "15 Sep 2026"
    },
    {
      id: "ORD-10243",
      customer: "Neha Singh",
      amount: 4999,
      payment: "Pending",
      status: "Processing",
      date: "15 Sep 2026"
    }
  ];

  return (
    <div className="admin-orders">

      <div className="admin-page-header">
        <div>
          <span className="admin-label">ORDER SERVICE</span>
          <h1>Orders</h1>
          <p>Manage customer orders and statuses.</p>
        </div>
      </div>

      <div className="admin-table-card">

        <table className="admin-table">

          <thead>
            <tr>
              <th>ORDER</th>
              <th>CUSTOMER</th>
              <th>DATE</th>
              <th>PAYMENT</th>
              <th>AMOUNT</th>
              <th>STATUS</th>
              <th>ACTION</th>
            </tr>
          </thead>

          <tbody>
            {orders.map((order) => (
              <tr key={order.id}>

                <td>
                  <strong>{order.id}</strong>
                </td>

                <td>{order.customer}</td>

                <td>{order.date}</td>

                <td>
                  <StatusBadge status={order.payment} />
                </td>

                <td>
                  ₹{order.amount.toLocaleString("en-IN")}
                </td>

                <td>
                  <StatusBadge status={order.status} />
                </td>

                <td>
                  <button className="view-btn">
                    View
                  </button>
                </td>

              </tr>
            ))}
          </tbody>

        </table>

      </div>

    </div>
  );
}

export default AdminOrders;