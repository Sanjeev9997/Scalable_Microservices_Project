import { NavLink } from "react-router-dom";
import "./Sidebar.css";

function Sidebar() {
  const menuItems = [
    {
      name: "Dashboard",
      icon: "📊",
      path: "/admin"
    },
    {
      name: "Products",
      icon: "📦",
      path: "/admin/products"
    },
    {
      name: "Inventory",
      icon: "🏪",
      path: "/admin/inventory"
    },
    {
      name: "Orders",
      icon: "🛒",
      path: "/admin/orders"
    },
    {
      name: "Users",
      icon: "👥",
      path: "/admin/users"
    }
  ];

  const systemItems = [
    {
      name: "Services",
      icon: "⚙️",
      path: "/admin/services"
    },
    {
      name: "Kafka Events",
      icon: "⚡",
      path: "/admin/events"
    },
    {
      name: "Redis Cache",
      icon: "🗄️",
      path: "/admin/cache"
    },
    {
      name: "Resilience",
      icon: "🛡️",
      path: "/admin/resilience"
    }
  ];

  return (
    <aside className="sidebar">

      {/* Logo */}

      <div className="sidebar-logo">
        <div className="sidebar-logo-icon">
          S
        </div>

        <div>
          <h2>Shop<span>Easy</span></h2>
          <p>Admin Panel</p>
        </div>
      </div>


      {/* Main Menu */}

      <div className="sidebar-section">

        <p className="sidebar-title">
          MAIN MENU
        </p>

        <nav>

          {menuItems.map((item) => (

            <NavLink
              key={item.path}
              to={item.path}
              end={item.path === "/admin"}
              className={({ isActive }) =>
                isActive
                  ? "sidebar-link active"
                  : "sidebar-link"
              }
            >

              <span className="sidebar-icon">
                {item.icon}
              </span>

              <span>
                {item.name}
              </span>

            </NavLink>

          ))}

        </nav>

      </div>


      {/* System */}

      <div className="sidebar-section">

        <p className="sidebar-title">
          SYSTEM
        </p>

        <nav>

          {systemItems.map((item) => (

            <NavLink
              key={item.path}
              to={item.path}
              className={({ isActive }) =>
                isActive
                  ? "sidebar-link active"
                  : "sidebar-link"
              }
            >

              <span className="sidebar-icon">
                {item.icon}
              </span>

              <span>
                {item.name}
              </span>

            </NavLink>

          ))}

        </nav>

      </div>


      {/* Bottom */}

      <div className="sidebar-bottom">

        <div className="admin-profile">

          <div className="admin-avatar">
            A
          </div>

          <div>
            <strong>Admin</strong>
            <small>Administrator</small>
          </div>

        </div>

        <button className="logout-button">
          🚪 Logout
        </button>

      </div>

    </aside>
  );
}

export default Sidebar;