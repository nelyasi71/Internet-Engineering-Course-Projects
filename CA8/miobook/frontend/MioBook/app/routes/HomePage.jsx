import Navbar from "../components/NavBar";
import Picture from "../static/Picture.png";
import { useState, useEffect } from "react";
import Card from "../components/Card";
import Footer from "../components/Footer";

const HomePage = () => {
  const [newReleases, setNewReleases] = useState([]);
  const [topRatedBooks, setTopRatedBooks] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchNewReleases = async () => {
    try {
      const res = await fetch(
        "/api/books?sortBy=year&order=asc",
        {
          method: "GET",
        }
      );
      const data = await res.json();
      setNewReleases(data.data.books.slice(0, 5));
    } catch (err) {
      console.error("Error fetching new releases:", err);
    }
  };

  const fetchTopRatedBooks = async () => {
    try {
      const res = await fetch(
        "/api/books?sortBy=rating&order=desc",
        {
          method: "GET",
        }
      );
      const data = await res.json();
      setTopRatedBooks(data.data.books.slice(0, 5));
    } catch (err) {
      console.error("Error fetching top-rated books:", err);
    }
  };

  useEffect(() => {
    const fetchAll = async () => {
      await Promise.all([fetchNewReleases(), fetchTopRatedBooks()]);
      setLoading(false);
    };
    fetchAll();
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <Navbar />
      <section class="text-dark p-5 home-section">
        <div class="container">
          <div class="row align-items-center">
            <div class="col-md-6 fs-5">
              <p>
                Welcome to MioBook – the online bookstore where you can buy or
                borrow books with ease.
              </p>
              <p>
                Whether you're looking for the latest bestseller, a classic
                novel, or a niche title, MioBook has you covered.
              </p>
              <p>
                Here, you can quickly find books by title, author, and genre.
                And if you’re not sure about buying, try borrowing instead! Rent
                a book for just a fraction of the price and enjoy full access
                for a set period.
              </p>
              <p>
                Your next great read is just a click away. Visit MioBook today
                and let the perfect book find you! 📚✨
              </p>
            </div>
            <div class="col-md-6 text-md-end">
              <img
                class="img-fluid w-50 ms-auto d-block"
                src={Picture}
                alt="Books"
              />
            </div>
          </div>
        </div>
      </section>
      <div class="container ">
        <div class="row mb-4">
          <div class="col-3">
            <h3 class="p-3 me-3 text-end thick-text">New Releases</h3>
          </div>
        </div>

        <div className="row row-cols-5 g-4 justify-content-center">
          {newReleases.map((book) => (
  <div className="col" key={book.title}>
    <Card
      title={book.title}
      author={book.author}
      price={book.price}
      image={book.image}
      rating={book.rating}
    />
  </div>
))}

        </div>

        <div class="row mb-4">
          <div class="col-3">
            <h3 class="p-4 me-5 text-end thick-text">Top Rated</h3>
          </div>
        </div>

        <div className="row row-cols-5 g-4 justify-content-center">
        {topRatedBooks.map((book) => (
  <div className="col" key={book.title}>
    <Card
      title={book.title}
      author={book.author}
      price={book.price}
      image={book.image}
      rating={book.rating}
    />
  </div>
))}

        </div>
      </div>

      <Footer />
    </div>
  );
};
export default HomePage;
