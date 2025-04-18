import { useEffect, useState } from "react";
import Navbar from "../components/NavBar";
import CreditForm from "../components/CreditForm";
import UserInfo from "../components/Userinfo";
import Footer from "../components/footer";
import MyBooks from "../components/MyBooks";

export function meta({}) {
  return [
    { title: "Dashboard" },
    { name: "MioBook", content: "MioBook" },
  ];
}

export default function Dashboard() {
  const [user, setUser] = useState(null);
  const [bookItems, setBookItems] = useState([]);

  useEffect(() => {
    fetch("http://localhost:9090/api/auth/user", {credentials: "include"})
      .then(res => res.json())
      .then(res => setUser(res.data));

    fetch("http://localhost:9090/api/purchased-books", {credentials: "include"})
      .then(res => res.json())
      .then(res => setBookItems(res.data.items));
  }, []);

  if (!user) return <div>Loading...</div>;

  return (
    <div className="bg-light min-vh-100">
      <Navbar />
      <div className="container mt-4 p-4">
        <div className="row">
          <div className="col-8 d-flex">
            <CreditForm credit={0} />
          </div>
          <div className="col-4">
            <UserInfo name={user.username} email={user.email} />
          </div>
        </div>
        <MyBooks items={bookItems} />
      </div>
      <Footer />
    </div>
  );
}
