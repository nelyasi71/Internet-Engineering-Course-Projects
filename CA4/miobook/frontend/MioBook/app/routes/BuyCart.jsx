import { Button } from "react-bootstrap";
import CartItem from "../components/CartItem";
import CartTable from "../components/CartTable";
import Footer from "../components/footer";
import Navbar from "../components/NavBar";
import { useEffect, useState } from "react";

export function meta({}) {
  return [
    { title: "Cart" },
    { name: "MioBook", content: "MioBook" },
  ];
}


export default function BuyCart() {
  const [cartItems, setCartItems] = useState([]);

  useEffect(() => {
    fetch("http://localhost:9090/api/cart/list", {credentials: "include"})
      .then(res => res.json())
      .then(res => setCartItems(res.data.items));
  }, []);

  return (
    <div className="bg-light min-vh-100">
      <Navbar />
      <div className="container bg-white mt-5 p-4">
        <h2 className="p-2"><i className="bi bi-cart3"></i> Cart</h2>
        <CartTable items={cartItems} />
        <div className="p-3 text-center">
          <button className="btn btn-post w-25">
            Purchase
          </button>
        </div>
      </div>
      <Footer />
    </div>
  );
}
