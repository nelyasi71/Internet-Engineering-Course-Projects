import image from "../static/book-icon.jpg";


export default function UserBookItem({ item }) {
    return (
      <tr>
        <td><img src={item.image || image} className="book-icon" alt="image" /></td>
        <td>{item.title}</td>
        <td>{item.author}</td>
        <td>{item.genres.join(', ')}</td>
        <td>{item.publisher}</td>
        <td>{item.year}</td>
        <td>{item.isBorrowed ? "borowed" : "Owend"}</td>
        <td>
            <button className="btn btn-light w-100">
                Read
            </button>
        </td>
      </tr>
    );
  }
  