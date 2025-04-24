import Navbar from "../components/NavBar";
import { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import Card from "../components/Card";
import Footer from "../components/footer";

const SearchResult = () => {
  const [authorBooks, setAuthorBooks] = useState([]);
  const [loading, setLoading] = useState(true);

  const location = useLocation();

  const searchParams = new URLSearchParams(location.search);
  const title = searchParams.get("title");
  const author = searchParams.get("author");
  const genre = searchParams.get("genre");
  const from = searchParams.get("from");
  const to = searchParams.get("to");

  useEffect(() => {
    const fetchBooks = async () => {
      try {
        const params = new URLSearchParams();
        if (title) params.append("title", title);
        if (author) params.append("author", author);
        if (genre) params.append("genre", genre);
        if (from) params.append("from", from);
        if (to) params.append("to", to);

        const response = await fetch(
          `http://localhost:9090/api/books?${params.toString()}`,
          {
            method: "GET",
            credentials: "include",
          }
        );

        const data = await response.json();
        console.log("API response:", data);

        if (data.data && data.data.books) {
          setAuthorBooks(data.data.books);
        } else {
          console.error("No books found in the response data.");
        }

        setLoading(false);
      } catch (error) {
        console.error("Error fetching books:", error);
        setLoading(false);
      }
    };

    fetchBooks();
  }, [title, author, genre, from, to]);

  return (
    <div>
      <Navbar />
      <div className="container mt-4">
        <div className="row mb-4">
          <div className="col-5 ms-3">
            <h3 className="p-3 text-end">
              Results for &lt;Search Parameters&gt;{" "}
            </h3>
          </div>
        </div>
        <div className="container w-100">
          <div className="row row-cols-5 mx-0 w-100">
            {authorBooks.length > 0 ? (
              authorBooks.map((book) => (
                <div className="col mb-5" key={book.id}>
                  <Card
                    title={book.title}
                    author={book.author}
                    price={book.price}
                    image={book.image}
                    rating={book.rating}
                  />
                </div>
              ))
            ) : (
              <div>Meow.No books found</div>
            )}
          </div>
        </div>
      </div>
      <Footer />
    </div>
  );
};

export default SearchResult;
