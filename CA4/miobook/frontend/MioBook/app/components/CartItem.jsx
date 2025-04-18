import image from "../static/book-icon.jpg";

export default function CartItem({ item }) {
    return (
      <tr>
        <td><img src={item.image || image} className="book-icon" alt="image" /></td>
        <td>{item.title}</td>
        <td>{item.author}</td>
        <td>${item.price}</td>
        <td>{item.isBorrowd ? item.borrowDays : "Owened"}</td>
        <td>
          <button className="btn btn-danger w-100">Remove</button>
        </td>
      </tr>
    );
  }
  