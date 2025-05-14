import CartTable from "../components/CartTable";
import Footer from "../components/footer";
import Navbar from "../components/NavBar";
import { useEffect, useState } from "react";
import Notifier from "../components/Notifier";
import { FaShoppingCart } from "react-icons/fa";


const token = typeof window !== "undefined" ? localStorage.getItem("accessToken") : null;

export function meta({}) {
  return [
    { title: "Cart" },
    { name: "MioBook", content: "MioBook" },
  ];
}


export default function BuyCart() {
  const [cartItems, setCartItems] = useState([]);
  const [notif, setNotif] = useState({ message: "", status: "" });

  async function purchase(e) {
    e.preventDefault();

    try {
    
      const response = await fetch("http://localhost:9090/api/cart/purchase", {
        method: "POST",
        headers: {
          "Authorization": `Bearer ${token}`,
          "Content-Type": "application/json",
        },
        body: JSON.stringify({})
      });
    
      const data = await response.json();
    
      if (data.success) {
        setCartItems([]);
        setNotif({message: "Cart purchased successfully!", status: "success"})
      } else {
        setNotif({ message: data.data.logicError, status: "error" });
      }
    } catch (error) {
    }
  }

  useEffect(() => {
    fetch("http://localhost:9090/api/cart/list", {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`, // or just `token` if your API expects it differently
        "Content-Type": "application/json"
      }
    })
    .then(res => res.json())
    .then(res => setCartItems(res.data.items));
  }, []);

  return (
    <div className="bg-light min-vh-100">
      <Navbar />
      <div className="container bg-white mt-5 p-4">
        <h2 className="p-2 fw-semibold"><i className="bi bi-cart3"></i> Cart</h2>
        <CartTable items={cartItems} onlyShow={false}/>
        {cartItems.length > 0 && (
          <div className="p-3 text-center">
            <button className="btn btn-post w-25 mt-4" onClick={purchase}>
              Purchase
            </button>
            {notif.status === "error" && (
              <div className="text-danger mt-2 small">{notif.message}</div>
            )}
          </div>
        )}
      </div>
      <Notifier message={notif.message} type={notif.status} />
      <Footer />
    </div>
  );
}
