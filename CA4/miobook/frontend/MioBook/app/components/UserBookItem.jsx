import image from "../static/book-icon.jpg";
import { useNavigate } from "react-router-dom";


export default function UserBookItem({ item }) {

  const navigate = useNavigate();

  async function handleCLick() {
    navigate("/books/" + item.title + "/content");
  }
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
            <button className="btn btn-light w-100" onClick={handleCLick}>
                Read
            </button>
        </td>
      </tr>
    );
  }
  