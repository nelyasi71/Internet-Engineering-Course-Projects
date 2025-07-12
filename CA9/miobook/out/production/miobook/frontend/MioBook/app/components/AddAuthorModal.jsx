import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import Notifier from './Notifier';

const token = typeof window !== "undefined" ? localStorage.getItem("jwt") : null;

const AddAuthorModal = () => {
  const authorFields = ["name", "penName", "nationality", "born", "died", "image"];

  const initialForm = authorFields.reduce((acc, field) => {
    acc[field] = "";
    return acc;
  }, {});
  
  const initialErrors = authorFields.reduce((acc, field) => {
    acc[field] = { hasError: false, message: "" };
    return acc;
  }, {});
  
  const [show, setShow] = useState(false);
  const [form, setForm] = useState(initialForm);
  const [errors, setErrors] = useState(initialErrors);
  const [notif, setNotif] = useState({ message: "", status: "" });
  
  
  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  
    if (errors[name]?.hasError) {
      setErrors((prev) => ({
        ...prev,
        [name]: { hasError: false, message: "" },
      }));
    }
  };
  
  const validateForm = () => {
    let isValid = true;
    const newErrors = { ...initialErrors };
  
    authorFields.forEach((field) => {
      if (!form[field].trim()) {
        newErrors[field] = { hasError: true, message: `${field} is required` };
        isValid = false;
      }
    });
  
    setErrors(newErrors);
    return isValid;
  };
  
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;
  
    try {
      const res = await fetch("/api/author", {
        method: "POST",
        headers: { 
          "Authorization": `Bearer ${token}`, 
          "Content-Type": "application/json" 
        },
        body: JSON.stringify(form),
      });

      const response = await res.json();
  
      if (response.success) {
        setShow(false);
        setForm(initialForm);
        setNotif({message: "Author added successfully!", status: "success"})
      } else {
        const fieldErrors = response.data?.fieldErrors || {};
        const newErrors = { ...initialErrors };
        
        Object.entries(fieldErrors).forEach(([field, message]) => {
          if (newErrors[field] !== undefined) {
            newErrors[field] = { hasError: true, message };
          }
        });
        
        setErrors((prev) => ({ ...prev, ...newErrors }));
      }
    } catch (error) {
      console.error("Submit failed:", error);
    }
  };
  
  return (
    <>
      <button className="btn btn-post ms-5" onClick={() => setShow(true)}>
          Add Author
      </button>

      <Notifier message={notif.message} type={notif.status}></Notifier>

      <Modal show={show} onHide={() => setShow(false)} centered dialogClassName="custom-modal">
        <Modal.Header closeButton>
          <Modal.Title className="w-100 text-center">Add Author</Modal.Title>
        </Modal.Header>
        <Form onSubmit={handleSubmit}>
          <Modal.Body>
            <Form.Group className="p-3" controlId="formName">
              <Form.Control
                type="text"
                name="name"
                placeholder="Name"
                value={form.name}
                onChange={handleChange}
                required
                isInvalid={errors.name.hasError}
              />
              <Form.Control.Feedback type="invalid">
                {errors.name.message}
              </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="p-3">
              <Form.Control
                name="penName"
                placeholder="Pen Name"
                value={form.penName}
                onChange={handleChange}
                required
                isInvalid={errors.penName.hasError}
              />
              <Form.Control.Feedback type="invalid">
                {errors.penName.message}
              </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="p-3">
              <Form.Control
                name="nationality"
                placeholder="Nationality"
                value={form.nationality}
                onChange={handleChange}
                required
                isInvalid={errors.nationality.hasError}
              />
              <Form.Control.Feedback type="invalid">
                {errors.nationality.message}
              </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="p-3">
              <Form.Label>Born</Form.Label>
              <Form.Control
                type="date"
                name="born"
                value={form.born}
                onChange={handleChange}
                required
                isInvalid={errors.born.hasError}
              />
              <Form.Control.Feedback type="invalid">
                {errors.born.message}
              </Form.Control.Feedback>
            </Form.Group>

            <Form.Group className="p-3">
              <Form.Label>Died</Form.Label>
              <Form.Control
                type="date"
                name="died"
                value={form.died}
                onChange={handleChange}
                required
                isInvalid={errors.died.hasError}
              />
              <Form.Control.Feedback type="invalid">
                {errors.died.message}
              </Form.Control.Feedback>
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
              <button className="btn btn-post w-100" type="submit" disabled={false}>
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
