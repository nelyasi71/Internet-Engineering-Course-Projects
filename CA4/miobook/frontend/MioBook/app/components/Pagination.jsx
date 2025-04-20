import React from "react";
import { FaChevronLeft, FaChevronRight } from "react-icons/fa";
import "./styles.css";
import "@fontsource/inter"; 

const Pagination = ({ currentPage = 1, totalPages = 20, onChange }) => {
  const getPaginationRange = () => {
    const delta = 2; 
    const range = [];

    let start = Math.max(1, currentPage - delta);
    let end = Math.min(totalPages, currentPage + delta);

    if (end - start < 4) {
      if (start === 1) {
        end = Math.min(5, totalPages);
      } else if (end === totalPages) {
        start = Math.max(totalPages - 4, 1);
      }
    }

    for (let i = start; i <= end; i++) {
      range.push(i);
    }

    return range;
  };

  const pagesToShow = getPaginationRange();

  return (
    <div className="pagination-wrapper">
      <button
        className="pagination-button icon-button"
        onClick={() => onChange(Math.max(1, currentPage - 1))}
        disabled={currentPage === 1}
      >
        <FaChevronLeft className="arrow-icon" />
      </button>

      {pagesToShow.map((page) => (
        <button
          key={page}
          className={`pagination-button number-button ${
            page === currentPage ? "active" : ""
          }`}
          onClick={() => onChange(page)}
        >
          {page}
        </button>
      ))}

      <button
        className="pagination-button icon-button"
        onClick={() => onChange(Math.min(totalPages, currentPage + 1))}
        disabled={currentPage === totalPages}
      >
        <FaChevronRight className="arrow-icon" />
      </button>
    </div>
  );
};

export default Pagination;
