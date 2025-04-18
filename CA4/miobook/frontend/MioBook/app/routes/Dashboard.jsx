import { useEffect, useState } from "react";
import Navbar from "../components/NavBar";
import CreditForm from "../components/CreditForm";
import Cart from "./BuyCart";
import UserInfo from "../components/Userinfo";
import Footer from "../components/footer";
import MyBooks from "../components/MyBooks";

export default function Dashboard() {
  const [user, setUser] = useState(null);
  const [cartItems, setCartItems] = useState([]);

  // useEffect(() => {
  //   fetch("/user/username") // username from session
  //     .then(res => res.json())
  //     .then(data => setUser(data));

  //   fetch("/cart/list")
  //     .then(res => res.json())
  //     .then(data => setCartItems(data));
  // }, []);

  // if (!user) return <div>Loading...</div>;

  return (
    <>
      <Navbar />
      <div className="container mt-4 p-4">
        <div className="row">
          <div className="col-8 d-flex">
            <CreditForm credit={0} />
          </div>
          <div className="col-4">
            <UserInfo name={"ali"} email={"ali"} />
          </div>
        </div>
        <MyBooks items={[]} />
      </div>
      <Footer />
    </>
  );
}
