import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

const AddBookModal = () => {
  const [page1, setPage1] = useState(false);
  const [page2, setPage2] = useState(false);

  const [form, setForm] = useState({
    name: '',
    author: '',
    publisher: '',
    genres: '',
    year: '',
    price: '',
    image: '',
    synopsis: '',
    content: ''
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleNext = (e) => {
    e.preventDefault();

    setPage1(false);
    setPage2(true); 
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    setPage1(false);
    setPage2(false); 
  };

  return (
    <>

      <button className="btn btn-post ms-5" onClick={() => setPage1(true)}>
          Add Book
      </button>

      <Modal
        show={page1}
        onHide={() => setPage1(false)}
        centered
        dialogClassName="custom-modal"
      >
        <Form>
          <Modal.Header closeButton>
            <Modal.Title className="w-100 text-center">Add Book</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            <Form.Group className="mb-3">
              <Form.Control
                name="name"
                placeholder="Name"
                value={form.name}
                onChange={handleChange}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Control
                name="author"
                placeholder="Author"
                value={form.author}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Control
                name="publisher"
                placeholder="Publisher"
                value={form.publisher}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Control
                name="genres"
                placeholder="Genres"
                value={form.genres}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Control
                name="year"
                placeholder="Publisher Year"
                value={form.year}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Control
                name="price"
                placeholder="Price"
                value={form.price}
                onChange={handleChange}
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Control
                name="image"
                placeholder="Image Link"
                value={form.image}
                onChange={handleChange}
              />
            </Form.Group>
          </Modal.Body>
          <div className="ps-3 pe-3">
            <button className="btn btn-post w-100" onClick={handleNext}>
              Next
            </button>
          </div>
          <div className="p-3">
            <button className="btn btn-light w-100" onClick={() => setPage1(false)}>
              Cancel
            </button>
          </div>
        </Form>
      </Modal>

      <Modal
        show={page2}
        onHide={() => setPage2(false)}
        centered
        dialogClassName="custom-modal"
      >
        <Modal.Header closeButton>
          <Modal.Title className="w-100 text-center">Add Book</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Form onSubmit={handleSubmit}>
            <Form.Group className="mb-3">
              <Form.Control
                as="textarea"
                rows={3}
                name="synopsis"
                placeholder="Synopsis"
                value={form.synopsis}
                onChange={handleChange}
                required
              />
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Control 
                as="textarea"
                rows={10}
                name="content"
                placeholder="Content"
                value={form.content}
                onChange={handleChange}
              />
            </Form.Group>
          </Form>
        </Modal.Body>
        <div className="ps-3 pe-3">
          <button className="btn btn-post w-100" onClick={handleSubmit}>
            Submit
          </button>
        </div>
        <div className="p-3">
          <button className="btn btn-light w-100" onClick={() => {setPage2(false); setPage1(true);}}>
            Back
          </button>
        </div>
      </Modal>
    </>
  );
};

export default AddBookModal;
