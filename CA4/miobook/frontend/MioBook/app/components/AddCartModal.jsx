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

  return (
    <Modal show={show} onHide={onHide} centered>
      <Modal.Body className="p-4">
        <div className="text-center mb-4">
          <h5>Add to Cart</h5>
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
            <Form.Group className="mb-4">
              <Form.Label>Number of days to borrow:</Form.Label>
              <Form.Control
                type="number"
                min={1}
                value={days}
                onChange={(e) => setDays(parseInt(e.target.value))}
              />
            </Form.Group>
          )}
        </Form>

        <div className="d-flex justify-content-between align-items-center pt-3">
          {!borrow && (
            <p className="fw-bold mb-0">Price: ${price}</p>
          )}
          <div className="d-flex gap-2">

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
