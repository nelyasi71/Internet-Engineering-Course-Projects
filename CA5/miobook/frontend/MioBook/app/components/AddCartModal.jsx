import React, { useState } from "react";
import { Modal, Button, Form } from "react-bootstrap";

const AddCartModal = ({ show, onHide, onConfirm, price }) => {
  const [borrow, setBorrow] = useState(false);
  const [days, setDays] = useState(2);

  const handleConfirm = () => {
    onConfirm({ borrow, days });
    setBorrow(false);
    setDays(2);
    onHide();
  };

  const borrowPrice = ((price * days) / 10).toFixed(2);

  return (
    <Modal show={show} onHide={onHide} centered contentClassName="border-0 rounded-3">
      <Modal.Body className="p-4 pb-3">
        <div className="text-center mb-3">
          <h5 className="fw-bold">Add to Cart</h5>
        </div>

        <Form>
          <Form.Check
            type="checkbox"
            label="Borrow this book"
            checked={borrow}
            onChange={(e) => setBorrow(e.target.checked)}
            className="mb-5"
          />

          {borrow && (
            <div className="number-boxes">
            {[...Array(9)].map((_, idx) => {
              const day = idx + 1;
              const isSelected = days === day;
          
              return (
                <div
                  key={day}
                  className={`number-box ${isSelected ? "selected" : ""}`}
                  onClick={() => setDays(day)}
                >
                  {day} Day{day > 1 ? "s" : ""}
                </div>
              );
            })}
          </div>
          
          )}
        </Form>

        <div className="d-flex justify-content-between align-items-center mt-4">
          <p className="fw-bold mb-0">
            Final Price: ${borrow ? borrowPrice : price.toFixed(2)}
          </p>
          <div>
            <Button variant="btn btn-green" onClick={handleConfirm}>
              Add
            </Button>
          </div>
        </div>
      </Modal.Body>
    </Modal>
  );
};

export default AddCartModal;
