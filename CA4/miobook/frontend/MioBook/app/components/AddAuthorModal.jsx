import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';

const AddAuthorModal = () => {
  const [show, setShow] = useState(false);
  const [form, setForm] = useState({
    name: '',
    penName: '',
    nationality: '',
    born: '',
    died: '',
    image: ''
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const res = await fetch('/api/authors', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(form),
    });

    if (res.ok) {
      setShow(false);
      setForm({ name: '', penName: '', nationality: '', born: '', died: '', image: '' });
    }
  };

  return (
    <>
      <button className="btn btn-post ms-5" onClick={() => setShow(true)}>
          Add Author
      </button>

      <Modal show={show} onHide={() => setShow(false)} centered dialogClassName="custom-modal">
        <Modal.Header closeButton>
          <Modal.Title className="w-100 text-center">Add Author</Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            <Form.Group className="p-3">
              <Form.Control
                name="name"
                placeholder="Name"
                value={form.name}
                onChange={handleChange}
                required
              />
            </Form.Group>

            <Form.Group className="p-3">
              <Form.Control
                name="penName"
                placeholder="Pen Name"
                value={form.penName}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group className="p-3">
              <Form.Control
                name="nationality"
                placeholder="Nationality"
                value={form.nationality}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group className="p-3">
              <Form.Label>Born</Form.Label>
              <Form.Control
                type="date"
                name="born"
                value={form.born}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group className="p-3">
              <Form.Label>Died</Form.Label>
              <Form.Control
                type="date"
                name="died"
                value={form.died}
                onChange={handleChange}
              />
            </Form.Group>

            <Form.Group className="p-3">
              <Form.Control
                name="image"
                placeholder="Image Link"
                value={form.image}
                onChange={handleChange}
              />
            </Form.Group>
            <div className="p-3">
              <button className="btn btn-light w-100" onClick={() => setShow(false)}>
                Cancel
              </button>
            </div>
            <div className="ps-3 pe-3">
              <button className="btn btn-post w-100" type="submit" disabled={!form.name}>
                Submit
              </button>
            </div>
          </Modal.Body>
        </Form>
      </Modal>
    </>
  );
};

export default AddAuthorModal;
