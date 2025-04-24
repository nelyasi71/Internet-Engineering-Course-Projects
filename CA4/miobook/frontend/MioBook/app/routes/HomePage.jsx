import Footer from "../components/Footer";
import Navbar from "../components/NavBar";
import Picture from "../static/Picture.png"
import { useState, useEffect } from "react";
import Card from "../components/Card";

const HomePage = () => {
  const [newReleases, setNewReleases] = useState([]);
  const [topRatedBooks, setTopRatedBooks] = useState([]);
  const [loading, setLoading] = useState(true);

  const fetchNewReleases = async () => {
    try {
      const response = await fetch('http://localhost:9090/api/books?sortBy=year&order=asc&size=5', { method: 'GET' });
      const data = await response.json();
      setNewReleases(data.data.books); 
    } catch (error) {
      console.error("Error fetching new releases:", error);
    }
  };

  const fetchTopRatedBooks = async () => {
    try {
      const response = await fetch('http://localhost:9090/api/books?sortBy=rating&order=desc&size=5', { method: 'GET' });
      const data = await response.json();
      setTopRatedBooks(data.data.books);
    } catch (error) {
      console.error("Error fetching top-rated books:", error);
    }
  };

  useEffect(() => {
    fetchNewReleases();
    fetchTopRatedBooks();
    setLoading(false);
  }, []);

  if (loading) {
    return <div>Loading...</div>;
  }

    return(
<div>
    <Navbar />
    <section class="text-dark p-5 home-section">
  <div class="container">
    <div class="row align-items-center">
      <div class="col-md-6 fs-5">
        <p>
          Welcome to MioBook – the online bookstore where you can buy or borrow books with ease.
        </p>
        <p>
          Whether you're looking for the latest bestseller, a classic novel, or a niche title, MioBook has you covered.
        </p>
        <p>
          Here, you can quickly find books by title, author, and genre. And if you’re not sure about buying, try borrowing instead! Rent a book for just a fraction of the price and enjoy full access for a set period.
        </p>
        <p>
          Your next great read is just a click away. Visit MioBook today and let the perfect book find you! 📚✨
        </p>
      </div>
      <div class="col-md-6 text-md-end">
        <img class="img-fluid w-50 ms-auto d-block" src={Picture} alt ="Books"/>
      </div>
    </div>
  </div>
</section>
<div class="container mt-4">
  <div class="row mb-4">
    <div class="col-3">
      <h3 class="p-3 me-3 text-end thick-text">New Releases</h3>
    </div>
  </div>
  
  <div className="row justify-content-center mb-4">
          {newReleases.map((book) => (
            <div className="col-md-2" key={book.id}>
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

  <div className="row justify-content-center">
          {topRatedBooks.map((book) => (
            <div className="col-md-2" key={book.id}>
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
