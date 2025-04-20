import image from "../static/book-icon.jpg";

export default function CartItem({ item, onRemove, isHistory }) {
  async function removeItem(e) {
    e.preventDefault();

    try {
      const response = await fetch("http://localhost:9090/api/cart/remove", {
        method: "DELETE",
        headers: {
          "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify({ title: item.title })
      });

      const data = await response.json();

      if (data.success) {
        onRemove(item.title);
      } else {
        console.log("Failed to remove item.");
      }
    } catch (error) {
      console.error("Error removing item:", error);
    }
  }

  return (
    <tr>
      <td><img src={item.image || image} className="book-icon" alt="book" /></td>
      <td>{item.title}</td>
      <td>{item.author}</td>
      <td>${item.price}</td>
      <td>{item.isBorrowd ? item.borrowDays : "Owned"}</td>
      {!isHistory && 
        <td>
          <button className="btn btn-danger w-100" onClick={removeItem}>
            Remove
          </button>
        </td>
      }
    </tr>
  );
}
