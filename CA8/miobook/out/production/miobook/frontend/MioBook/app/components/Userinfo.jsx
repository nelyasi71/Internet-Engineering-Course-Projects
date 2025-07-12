import axios from "axios";
import { useNavigate } from "react-router-dom";


const token = typeof window !== "undefined" ? localStorage.getItem("jwt") : null;

export default function UserInfo({ name, email, wide }) {
  const navigate = useNavigate();
  const handleLogout = async () => {
    try {
      await fetch("/api/auth/logout", {
        method: "POST",
        headers: {
          "Authorization": `Bearer ${token}`, 
          "Content-Type": "application/json"
        },
        body: JSON.stringify({})
      });
      localStorage.removeItem("jwt");
      navigate("/signin"); 
    } catch (error) {
      console.error("Logout failed:", error);
    }
  };
    if(wide) {
      return (
        <div className="container rounded-3 d-flex justify-content-between align-items-center bg-white p-2">
          <div className="p-3">
            <p><i className="bi bi-person-circle me-2"></i><strong>{name}</strong></p>
            <p><i className="bi bi-envelope-at me-2"></i>{email}</p>
          </div>
          <div className="w-25">
            <button onClick={handleLogout} className="btn btn-light rounded-3 w-50">Logout</button>
          </div>
        </div>
      );
    } else {
      return (
        <div className="container rounded-3 flex-fill bg-white p-3">
          <p><i className="bi bi-person-circle me-2"></i><strong>{name}</strong></p>
          <p><i className="bi bi-envelope-at me-2"></i>{email}</p>
          <div className="pt-2">
            <button onClick={handleLogout} className="btn btn-light rounded-3 w-50">Logout</button>
          </div>
        </div>
      );
    }

  }
  