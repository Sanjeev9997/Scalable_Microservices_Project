import StatusBadge from "../../../components/StatusBadge/StatusBadge";
import "./Cache.css";
import "../admin-common.css";

function Cache() {
  return (
    <div className="cache-page">

      <div className="admin-page-header">

        <div>
          <span className="admin-label">REDIS</span>

          <h1>Cache Dashboard</h1>

          <p>
            Monitor Redis performance and cached objects.
          </p>
        </div>

        <StatusBadge status="Healthy" />

      </div>

      <div className="cache-stats">

        <div className="cache-stat-card">
          <span>Hit Rate</span>
          <strong>94.8%</strong>
          <small>+2.4% today</small>
        </div>

        <div className="cache-stat-card">
          <span>Hits</span>
          <strong>184,920</strong>
          <small>Last 24 hours</small>
        </div>

        <div className="cache-stat-card">
          <span>Misses</span>
          <strong>10,128</strong>
          <small>Last 24 hours</small>
        </div>

        <div className="cache-stat-card">
          <span>Cached Objects</span>
          <strong>8,492</strong>
          <small>Current</small>
        </div>

      </div>

      <div className="cache-grid">

        <div className="cache-panel">

          <h2>Redis Connection</h2>

          <div className="redis-status">
            <StatusBadge status="Healthy" />
            <strong>Redis is connected</strong>
          </div>

          <div className="redis-details">
            <div>
              <span>Host</span>
              <strong>redis-server</strong>
            </div>

            <div>
              <span>Port</span>
              <strong>6379</strong>
            </div>

            <div>
              <span>Memory</span>
              <strong>142 MB</strong>
            </div>
          </div>

        </div>

        <div className="cache-panel">

          <h2>Cached Objects</h2>

          <div className="cache-object">
            <span>Products</span>
            <strong>3,842</strong>
          </div>

          <div className="cache-object">
            <span>Inventory Records</span>
            <strong>1,280</strong>
          </div>

          <div className="cache-object">
            <span>User Sessions</span>
            <strong>2,914</strong>
          </div>

          <div className="cache-object">
            <span>Other</span>
            <strong>456</strong>
          </div>

        </div>

      </div>

    </div>
  );
}

export default Cache;