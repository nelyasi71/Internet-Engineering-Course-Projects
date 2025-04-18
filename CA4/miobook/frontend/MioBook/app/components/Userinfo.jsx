import axios from "axios";
import { useNavigate } from "react-router-dom";



export default function UserInfo({ name, email }) {
  const navigate = useNavigate();
  const logout = async () => {
  
    try {
      const response = await axios.post("http://localhost:9090/api/auth/logout", {}, {withCredentials: true});
      if(response.data.success) {
        navigate("/signin")
      }
    } catch (error) {
  
    }
  };
    return (
      <div className="section flex-fill bg-white p-3">
        <p><i className="bi bi-person-circle me-2"></i><strong>{name}</strong></p>
        <p><i className="bi bi-envelope-at me-2"></i>{email}</p>
        <div className="pt-2">
          <button onClick={logout} className="btn btn-light rounded-3 w-50">Logout</button>
        </div>
      </div>
    );
  }
  