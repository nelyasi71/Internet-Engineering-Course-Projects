import React from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import Rating from "./Rating"; 
import "./styles.css";
import BookImage from "../static/Image.png" 

const BookCard = ({ title, author, price, image, rating }) => {
  return (
      <div className="book-card card shadow-lg rounded-5 overflow-hidden">
        <img
          src={image ? image : BookImage}
          className="rounded-top book-cover"
        />
        <div className="d-flex flex-column card-body card-body text-center">
        <h5 className="card-title fw-bold text-truncate" style={{maxWidth: '100%'}}>{title}</h5>
        <p className="card-text mb-0">{author ? author : 'No author'}</p>
          <div className="d-flex flex-row justify-content-center align-items-center">
            <Rating rating={rating} />
            <p className="fw-bold price ms-4 my-0">${price ? price : 0}</p>
          </div>
              
            <button className="btn btn-green w-100 mt-2 ">Add to Cart</button>
            
            </div>
      </div>
  );
};

export default BookCard;
