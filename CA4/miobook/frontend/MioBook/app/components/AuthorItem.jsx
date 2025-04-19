import image from "../static/book-icon.jpg";


export default function AuthorItem({ item }) {
    return (
      <tr>
        <td><img src={item.image || image} className="book-icon" alt="image" /></td>
        <td>{item.name}</td>
        <td>{item.penName}</td>
        <td>{item.nationality ? item.nationality : "-"}</td>
        <td>{item.born}</td>
        <td>{item.death ? item.death : "-"}</td>
      </tr>
    );
  }
  