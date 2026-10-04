
import "./UserCard.css";

function UserCard({ user }) {

  if (!user) {
    return null;
  }

  return (
    <div className="user-card">

      <div className="user-avatar">
        {user.name?.charAt(0).toUpperCase()}
      </div>

      <div className="user-info">

        <h3>{user.name}</h3>

        <p>{user.email}</p>

        <span className="user-role">
          {user.role}
        </span>

      </div>

    </div>
  );
}

export default UserCard;

