import { useEffect, useState } from "react";
import Navbar from "../components/NavBar";
import CreditForm from "../components/CreditForm";
import UserInfo from "../components/Userinfo";
import Footer from "../components/footer";
import MyBooks from "../components/MyBooks";
import AccessDenied from "./AccessDenied";

export function meta({}) {
  return [
    { title: "Dashboard" },
    { name: "MioBook", content: "MioBook" },
  ];
}

export default function Dashboard() {
  const [user, setUser] = useState(null);
  const [bookItems, setBookItems] = useState([]);
  const [loading, setLoading] = useState(true);


  useEffect(() => {
    fetch("http://localhost:9090/api/auth/user", { credentials: "include" })
    .then(res => res.json())
    .then(res => {
      setUser(res.data);
      setLoading(false);
    })
    .catch(() => {
      setUser(null);
      setLoading(false);
    });

    fetch("http://localhost:9090/api/purchased-books", {credentials: "include"})
      .then(res => res.json())
      .then(res => setBookItems(res.data.items));
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }
  
  if (!user) {
    return <Navigate to="/login" replace />;
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
