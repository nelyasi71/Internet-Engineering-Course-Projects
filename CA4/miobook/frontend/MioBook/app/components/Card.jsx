import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import Rating from "./Rating"; 
import "./styles.css";

const BookCard = ({ title, author, price, image, rating }) => {
  return (
      <div className="book-card card shadow-lg rounded-5 overflow-hidden">
        <img
          src={image}
          className="rounded-top book-cover"
        />
        <div className="card-body card-body text-center">
          <h5 className="card-title fw-bold">{title}</h5>
          <p className="card-text">{author}</p>
          <div className="d-flex justify-content-center align-items-center gap-2">
            <Rating rating={rating} />
            <p className="fw-bold price mb-0 ms-2">${price}</p>
          </div>
          <button className="btn btn-success w-100 mt-2">Add to Cart</button>
        </div>
      </div>
  );
};

export default BookCard;
