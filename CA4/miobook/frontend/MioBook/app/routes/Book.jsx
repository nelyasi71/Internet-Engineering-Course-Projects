import Navbar from "../components/NavBar";
import BookCover from "../static/BookCover.png";
import Rating from "../components/Rating";
import Pagination from "../components/Pagination";
import { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import Comment from "../components/Comment"; 
import Footer from "../components/footer";
import AddToCartModal from "../components/AddCartModal.jsx"; 
import "bootstrap/dist/css/bootstrap.min.css";
import { toast } from "react-toastify";
import 'react-toastify/dist/ReactToastify.css';
import AddReviewModal from "../components/AddReviewModal.jsx";
const Book = () => {
  const [book, setBook] = useState(null);
  const [showAddToCartModal, setShowAddToCartModal] = useState(false);
  const [showAddReviewModal, setShowAddReviewModal] = useState(false);

  const { bookTitle } = useParams(); 
  

  useEffect(() => {
    fetch(`http://localhost:9090/api/books/${bookTitle}`)
      .then((res) => res.json())
      .then((data) => setBook(data.data));
  }, [bookTitle]);


const handleAddToCart = async ({ borrow, days }) => {
  const username = localStorage.getItem("username"); 
  const endpoint = borrow
    ? "http://localhost:9090/api/cart/borrow"
    : "http://localhost:9090/api/cart/add";

  const body = borrow
    ? { username, title: book.title, days }
    : { username, title: book.title };

  try {
    const response = await fetch(endpoint, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      credentials: "include",
      body: JSON.stringify(body),
    });

     const result = await response.json();

    if (result.success) {
      toast.success(borrow ? "Book borrowed successfully!" : "Book added to cart!");
    } else {
      toast.error(`Error: ${result.message}`);
    }
  } catch (err) {
    console.error("Cart error:", err);
    toast.error("Something went wrong. Please try again.");
  }
};

if (!book) return <div>Loading...</div>;

const reviewCount = book.ReviewCount ? book.ReviewCount() : 0;


  return (
    <div className="bg-light min-vh-100">
      <Navbar />
      <body>
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
                <button
                  className="btn btn-green"
                  onClick={() => setShowAddToCartModal(true)}
                >
                  Add to Cart
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
        <AddToCartModal
  show={showAddToCartModal}
  onHide={() => setShowAddToCartModal(false)}
  onConfirm={handleAddToCart}
  price={book.price}
/>

        <div className="review-box container mt-4">
          <div className="custom-border mb-5 p-4 rounded-3">
            <div className="d-flex justify-content-between align-items-center mb-3">
              <h5>Reviews <span className="text-muted">{reviewCount}</span></h5>
              <button className="btn bg-custom-gray border-0" onClick={() => setShowAddReviewModal(true)}>Add Review</button>

              <AddReviewModal
  show={showAddReviewModal}
  onClose={() => setShowAddReviewModal(false)}
  bookTitle={book.title}
  bookImage={book.coverImage || BookCover}
/>

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
