export default function UserInfo({ name, email }) {
    return (
      <div className="section flex-fill bg-white p-3">
        <p><i className="bi bi-person-circle me-2"></i><strong>{name}</strong></p>
        <p><i className="bi bi-envelope-at me-2"></i>{email}</p>
        <div className="pt-2">
          <button className="btn btn-light rounded-3 w-50">Logout</button>
        </div>
      </div>
    );
  }
  