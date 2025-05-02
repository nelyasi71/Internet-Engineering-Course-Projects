import React from "react";
import Rating from "./Rating"; 
import "./styles.css"; 

const Comment = ({ name,  message, date, rating }) => {
  return (
    <div className="w-100 mb-3">
      <div className="list-group-item border-0 py-3">
        <div className="d-flex">
          <div className="me-3">
            <div className="bg-secondary-subtle d-flex justify-content-center align-items-center rounded-circle p-2">
              <span>{name[0]}</span>
            </div>
          </div>
          <div className="flex-grow-1">
            <p className="mb-1 fw-bold">{name}</p>
            <p className="mb-1 text-muted">{message}</p>
          </div>
          <div className="flex-row">
            <Rating rating={rating} />
            <p className="text-muted small mb-0">{date}</p>
          </div>
        </div>
      </div>
      </div>
  );
};

export default Comment;
