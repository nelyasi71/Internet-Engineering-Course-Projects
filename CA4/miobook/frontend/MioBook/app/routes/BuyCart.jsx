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

  async function purchase(e) {
    e.preventDefault();

    try {
    
      const response = await fetch("http://localhost:9090/api/cart/purchase", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify({})
      });
    
      const data = await response.json();
    
      if (data.success) {
        setCartItems([]);
      } else {

      }
    } catch (error) {
    }
  }

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
        <CartTable items={cartItems} onlyShow={false}/>
        {cartItems.length > 0 && (
          <div className="p-3 text-center">
            <button className="btn btn-post w-25" onClick={purchase}>
              Purchase
            </button>
          </div>
        )}
      </div>
      <Footer />
    </div>
  );
}
