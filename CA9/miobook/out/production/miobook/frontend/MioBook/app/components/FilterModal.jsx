import React, { useState } from "react";
import { Form } from "react-bootstrap";
import { useNavigate } from "react-router-dom";

const FilterModal = ({ show, onClose, onApply }) => {
  const [selectedSort, setSelectedSort] = useState(null);
  const [selectedOrder, setSelectedOrder] = useState(null);
  const navigate = useNavigate();
  const [filters, setFilters] = useState({
    title: "",
    author: "",
    genre: "",
    from: "",
    to: ""
  });

  const handleInputChange = (field, value) => {
    setFilters((prev) => ({ ...prev, [field]: value }));
  };

  const handleApply = () => {
    const params = new URLSearchParams();
    Object.entries(filters).forEach(([key, value]) => {
      if (value) params.append(key, value);
    });
    if (selectedSort) params.append("sort", selectedSort);
    if (selectedOrder) params.append("order", selectedOrder);
  
    navigate(`/books?${params.toString()}`); 
    onClose();
    if (onApply) onApply();
  };

  if (!show) return null;

  return (
    <div
      className="position-fixed top-0 start-0 bg-white shadow p-4"
      style={{ width: "500px", height: "1100px", zIndex: 1050 }}
    >
      <div className="d-flex justify-content-between align-items-center mb-3">
        <h5 className="mb-0">Filters</h5>
        <button
          onClick={onClose}
          className="btn p-0"
          style={{
            fontSize: "2.5rem",
            fontWeight: "bold",
            lineHeight: "1",
            color: "black",
            background: "none",
            border: "none",
          }}
        >
          &times;
        </button>
      </div>

      <hr style={{ width: "420px" }} />

      <Form>
        <div className="mb-3 d-flex align-items-center">
          <label className="me-3" style={{ minWidth: "120px" }}>Book Name:</label>
          <Form.Control type="text" placeholder="Enter book name" value={filters.title} onChange={(e) => handleInputChange("title", e.target.value)} />
        </div>

        <div className="mb-3 d-flex align-items-center">
          <label className="me-3" style={{ minWidth: "120px" }}>Author Name:</label>
          <Form.Control type="text" placeholder="Enter author name" value={filters.author} onChange={(e) => handleInputChange("author", e.target.value)} />
        </div>

        <div className="mb-3 d-flex align-items-center">
          <label className="me-3" style={{ minWidth: "120px" }}>Genre:</label>
          <Form.Select value={filters.genre} onChange={(e) => handleInputChange("genre", e.target.value)}>
            <option value="">Select genre</option>
            <option value="Fiction">Fiction</option>
            <option value="Nonfiction">Nonfiction</option>
          </Form.Select>
        </div>

        <div className="mb-3 d-flex align-items-center">
          <label className="me-3" style={{ minWidth: "120px" }}>From:</label>
          <Form.Control type="number" placeholder="Year from" value={filters.from} onChange={(e) => handleInputChange("from", e.target.value)} />
        </div>

        <div className="mb-3 d-flex align-items-center">
          <label className="me-3" style={{ minWidth: "120px" }}>To:</label>
          <Form.Control type="number" placeholder="Year to" value={filters.to} onChange={(e) => handleInputChange("to", e.target.value)} />
        </div>

        <div className="mt-4">
        <div className="mb-4 d-flex align-items-center">
  <label className="me-3" style={{ minWidth: "120px" }}>Sort by:</label>
  <div className="d-flex gap-3 flex-grow-1">
    <div
      className={`number-box ${selectedSort === "rating" ? "selected" : ""}`}
      onClick={() => setSelectedSort("rating")}
    >
      Rating
    </div>
    <div
      className={`number-box ${selectedSort === "reviews" ? "selected" : ""}`}
      onClick={() => setSelectedSort("reviews")}
    >
      Reviews
    </div>
  </div>
</div>

<div className="mb-5 d-flex align-items-center">
  <label className="me-3" style={{ minWidth: "120px" }}>Order:</label>
  <div className="d-flex gap-3 flex-grow-1">
    <div
      className={`number-box ${selectedOrder === "asc" ? "selected" : ""}`}
      onClick={() => setSelectedOrder("asc")}
    >
      Ascending
    </div>
    <div
      className={`number-box ${selectedOrder === "desc" ? "selected" : ""}`}
      onClick={() => setSelectedOrder("desc")}
    >
      Descending
    </div>
  </div>
</div>


          <div className="text-center mt-5 pt-5">
            <button className="btn btn-green px-4 " onClick={handleApply}>Apply</button>
          </div>
        </div>
      </Form>
    </div>
  );
};

export default FilterModal;