import StatusBadge from "../../../components/StatusBadge/StatusBadge";
import "./Users.css";
import "../admin-common.css";

function Users() {
  const users = [
    {
      id: "U001",
      name: "Rahul Sharma",
      email: "rahul@example.com",
      orders: 14,
      joined: "Jan 2026",
      status: "Active"
    },
    {
      id: "U002",
      name: "Aman Verma",
      email: "aman@example.com",
      orders: 8,
      joined: "Feb 2026",
      status: "Active"
    },
    {
      id: "U003",
      name: "Neha Singh",
      email: "neha@example.com",
      orders: 21,
      joined: "Dec 2025",
      status: "Active"
    },
    {
      id: "U004",
      name: "Vikas Kumar",
      email: "vikas@example.com",
      orders: 2,
      joined: "Sep 2026",
      status: "Inactive"
    }
  ];

  return (
    <div className="users-page">

      <div className="admin-page-header">
        <div>
          <span className="admin-label">USER SERVICE</span>
          <h1>Users</h1>
          <p>Manage registered customer accounts.</p>
        </div>
      </div>

      <div className="admin-table-card">

        <table className="admin-table">

          <thead>
            <tr>
              <th>USER</th>
              <th>EMAIL</th>
              <th>ORDERS</th>
              <th>JOINED</th>
              <th>STATUS</th>
              <th>ACTION</th>
            </tr>
          </thead>

          <tbody>

            {users.map((user) => (
              <tr key={user.id}>

                <td>
                  <div className="user-cell">

                    <div className="user-avatar">
                      {user.name.charAt(0)}
                    </div>

                    <div>
                      <strong>{user.name}</strong>
                      <small>{user.id}</small>
                    </div>

                  </div>
                </td>

                <td>{user.email}</td>

                <td>{user.orders}</td>

                <td>{user.joined}</td>

                <td>
                  <StatusBadge status={user.status} />
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

export default Users;