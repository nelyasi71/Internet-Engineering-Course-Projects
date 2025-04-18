export default function CartItem({ item }) {
    return (
      <tr>
        <td><img src={item.image || "../assets/book-icon.jpg"} className="book-icon" alt="image" /></td>
        <td>{item.name}</td>
        <td>{item.author}</td>
        <td>${item.price}</td>
        <td>
          <button className="btn btn-light">-</button>
          <span className="mx-2">{item.amount}</span>
          <button className="btn btn-green">+</button>
        </td>
      </tr>
    );
  }
  