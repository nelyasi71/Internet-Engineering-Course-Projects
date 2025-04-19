import image from "../static/book-icon.jpg";


export default function BookItem({ item }) {
    return (
      <tr>
        <td><img src={item.image || image} className="book-icon" alt="image" /></td>
        <td>{item.title}</td>
        <td>{item.author}</td>
        <td>{item.genres.join(', ')}</td>
        <td>{item.publisher}</td>
        <td>{item.year}</td>
        <td>${item.price}</td>
        <td>{item.totalBuys}</td>
      </tr>
    );
  }
  