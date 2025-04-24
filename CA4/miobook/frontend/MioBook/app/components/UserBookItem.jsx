import image from "../static/book-icon.jpg";
import { useNavigate } from "react-router-dom";


export default function UserBookItem({ item }) {

  console.log(item);
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
        <td>
          {item.isBorrowed ? (
            <>
              Borrowed
              <div className="text-muted small">until {item.until}</div>
            </>
          ) : (
            "Owned"
          )}
        </td>
        <td>
            <button className="btn btn-light w-100" onClick={handleCLick}>
                Read
            </button>
        </td>
      </tr>
    );
  }
  