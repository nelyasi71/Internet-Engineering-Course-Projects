import Navbar from "../components/NavBar";
import Footer from "../components/Footer.jsx";
import BookCover from "../static/BookCover.png";
import Rating from "../components/Rating";
import Pagination from "../components/Pagination";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import Comment from "../components/Comment"; 

const Book = () => {
  const [book, setBook] = useState(null);
  const { bookTitle } = useParams(); 

  useEffect(() => {
    fetch(`http://localhost:9090/api/books/${bookTitle}`)
        .then(res => res.json())
        .then(data => {
            setBook(data.data);  
        });
}, []);

if (!book) {
    return <div>Loading...</div>;
}

const reviewCount = book.ReviewCount ? book.ReviewCount() : 0;

  return (
    <div>
      <Navbar />

      <body className="page-background">
        <div className="container mt-5 pb-3">
          <div className="custom-border custom-border p-5 mb-5 rounded">
            <div className="row g-3 align-items-start">
              <div className="col-12 col-md-4">
                <img
                  src={book.coverImage || BookCover} 
                  className="img-fluid rounded"
                  alt="Book Cover"
                />
              </div>

              <div className="col-12 col-md-8 d-flex flex-column">
                <h4 className="fw-bold">{book.title}</h4>
                <div className="d-flex align-items-center">
                  <Rating rating={book.averageRating} />
                  <span className="ms-2">{book.averageRating}</span>
                </div>

                <div className="row text-start mt-3">
                  <div className="col-3">
                    <p className="text-muted mb-0">Author</p>
                    <p>{book.author}</p>
                  </div>
                  <div className="col-3">
                    <p className="text-muted mb-0">Publisher</p>
                    <p>{book.publisher}</p>
                  </div>
                  <div className="col-3">
                    <p className="text-muted mb-0">Year</p>
                    <p>{book.year}</p>
                  </div>
                  <div className="col-3">
                    <p className="text-muted mb-0">Genre</p>
                    <p>{book.genres ? book.genres.join(", ") : ""}</p>
                  </div>
                </div>

                <p className="mt-2">About</p>
                <p className="text-muted">{book.synopsis}</p>
                <h5 className="fw-bold">${book.price}</h5>
                <div className="mt-2">
                  <button className="btn btn-success">Add to Cart</button>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div className="review-box container mt-4">
          <div className="custom-border mb-5 p-4 rounded-3">
            <div className="d-flex justify-content-between align-items-center mb-3">
              <h5>Reviews <span className="text-muted">{reviewCount}</span></h5>
              <button className="btn bg-custom-gray border-0 ">
                Add Review
                <i className="bi bi-list-stars"></i>
              </button>
            </div>

            <div className="list-group">
            {book.reviews && reviewCount > 0 ? (
                book.reviews.map((review, index) => (
                    <Comment key={index}
                        name={review.customer.name}
                        message={review.comment}
                        rating={review.rate}
                        date={review.data}
                    />
                ))
            ) : (
                <p>No reviews yet.</p>
            )}
            </div>

            <Pagination />
          </div>
        </div>
      </body>

      <Footer />
    </div>
  );
};

export default Book;
