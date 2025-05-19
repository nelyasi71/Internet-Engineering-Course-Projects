import image from "../static/book-icon.jpg";
import { useEffect, useState } from "react";
import Notifier from "../components/Notifier";

const token = typeof window !== "undefined" ? localStorage.getItem("accessToken") : null;

export default function CartItem({ item, onRemove, isHistory }) {
  async function removeItem(e) {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:9090/api/cart/remove", {
        method: "DELETE",
        headers: {
          "Authorization": `Bearer ${token}`, 
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ title: item.title }),
      });

      const data = await response.json();

      if (data.success) {
        onRemove(item.title);
      } else {
      }
    } catch (error) {
      console.error("Error removing item:", error);
    }
  }

  return (
    <>
      <tr>
        <td>
          <img src={item.image || image} className="book-icon" alt="book" />
        </td>
        <td>{item.title}</td>
        <td>{item.author}</td>
        <td>
          {!item.isBorrowed ? (
            `$${item.price}`
          ) : (
            <>
              <span
                style={{ textDecoration: "line-through", marginRight: "8px" }}
                >
                ${item.price}
              </span>
              <span>${(item.borrowDays * item.price) / 10}</span>
            </>
          )}
        </td>
        <td>{item.isBorrowed ? item.borrowDays : "Not Borrowed"}</td>
        {!isHistory && (
          <td>
            <button className="btn btn-light w-75" onClick={removeItem}>
              Remove
            </button>
          </td>
        )}
      </tr>
    </>
  );
}
