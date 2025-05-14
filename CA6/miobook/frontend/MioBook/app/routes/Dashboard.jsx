import { useEffect, useState } from "react";
import Navbar from "../components/NavBar";
import CreditForm from "../components/CreditForm";
import UserInfo from "../components/Userinfo";
import Footer from "../components/footer";
import MyBooks from "../components/MyBooks";
import AccessDenied from "./AccessDenied";
import { Navigate } from "react-router";


export function meta({}) {
  return [
    { title: "Dashboard" },
    { name: "MioBook", content: "MioBook" },
  ];
}

const token = typeof window !== "undefined" ? localStorage.getItem("accessToken") : null;

export default function Dashboard() {
  const [user, setUser] = useState(null);
  const [bookItems, setBookItems] = useState([]);
  const [loading, setLoading] = useState(true);
  

  useEffect(() => {
    fetch("http://localhost:9090/api/auth/user", {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`,
        "Content-Type": "application/json"
      }
    })
    .then(res => res.json())
    .then(res => {
      setUser(res.data);
      setLoading(false);
    })
    .catch(() => {
      setUser(null);
      setLoading(false);
    });
    
    
    fetch("http://localhost:9090/api/purchased-books", {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`, // or just `token` if your API expects it differently
        "Content-Type": "application/json"
      }
    })
    .then(res => res.json())
    .then(res => {console.log(res); setBookItems(res.data.items)});
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }
  
  if (!user) {
    return <Navigate to="/signin" replace />;
  }
  
  var requiredRole = "customer";
  if (requiredRole && user.role !== requiredRole) {
    return <AccessDenied user={user} requiredRole={requiredRole} />;
  }


  return (
    <div className="bg-light min-vh-100">
      <Navbar />
      <div className="container mt-4 p-4">
        <div className="row">
          <div className="col-8 d-flex">
            <CreditForm user_credit={user.balance} />
          </div>
          <div className="col-4">
            <UserInfo name={user.username} email={user.email} wide={false} />
          </div>
        </div>
        <MyBooks items={bookItems} />
      </div>
      <Footer />
    </div>
  );
}
