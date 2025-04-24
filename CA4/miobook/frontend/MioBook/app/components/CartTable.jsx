import { useState, useEffect } from "react";
import CartItem from "./CartItem";

export default function CartTable({ items, onlyShow }) {
  const [cartItems, setCartItems] = useState([]);

  useEffect(() => {
    setCartItems(items); // Keep state in sync with props
  }, [items]);

  const handleRemove = (title) => {
    setCartItems(prevItems => prevItems.filter(item => item.title !== title));
  };

  return (
    <div className="rounded-4 overflow-hidden shadow-sm">
      <table className="table align-middle mb-0">
        <thead className="table-light">
          <tr>
            <th className="fw-normal text-muted">Image</th>
            <th className="fw-normal text-muted">Name</th>
            <th className="fw-normal text-muted">Author</th>
            <th className="fw-normal text-muted">Price</th>
            <th className="fw-normal text-muted">Borrow Days</th>
            {!onlyShow && <th></th>}
          </tr>
        </thead>
        <tbody>
          {cartItems.map((item, index) => (
            <CartItem key={index} item={item} onRemove={handleRemove} isHistory={onlyShow} />
          ))}
        </tbody>
      </table>
    </div>
  );
}
