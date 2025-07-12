import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import Notifier from './Notifier';

const token = typeof window !== "undefined" ? localStorage.getItem("jwt") : null;

const AddBookModal = () => {
  const bookFields = [
    "title",
    "author",
    "publisher",
    "genres",
    "year",
    "price",
    "image",
    "synopsis",
    "content",
  ];
  

  const initialForm = bookFields.reduce((acc, field) => {
    acc[field] = "";
    return acc;
  }, {});

  const initialErrors = bookFields.reduce((acc, field) => {
    acc[field] = { hasError: false, message: "" };
    return acc;
  }, {});

  const [showStep1, setShowStep1] = useState(false);
  const [showStep2, setShowStep2] = useState(false);
  const [form, setForm] = useState(initialForm);
  const [errors, setErrors] = useState(initialErrors);
  const [notif, setNotif] = useState({ message: "", status: "" });
  

  const formattedForm = {
    ...form,
    genres: form.genres.split(",").map((g) => g.trim()),
    year: parseInt(form.year, 10),
    price: parseFloat(form.price)
  };

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

  const validateStep1 = () => {
    const requiredFields = ["title", "author", "publisher", "genres", "year", "price", "image"];
    let isValid = true;
    const newErrors = { ...initialErrors };

    requiredFields.forEach((field) => {
      if (!form[field].trim()) {
        newErrors[field] = { hasError: true, message: `${field} is required` };
        isValid = false;
      }

      if (field === "price" && !/^\d+(\.\d{1,2})?$/.test(form[field])) {
        newErrors[field] = { hasError: true, message: "Enter a valid price" };
        isValid = false;
      }

      if (field === "year" && !/^\d+(\.\d{1,2})?$/.test(form[field])) {
        newErrors[field] = { hasError: true, message: "Enter a valid year" };
        isValid = false;
      }
    });

    setErrors(newErrors);
    return isValid;
  };

  const validateStep2 = () => {
    let isValid = true;
    const newErrors = { ...initialErrors };

    if (!form.synopsis.trim()) {
      newErrors.synopsis = { hasError: true, message: "Synopsis is required" };
      isValid = false;
    }

    setErrors(newErrors);
    return isValid;
  };

  const handleNext = (e) => {
    e.preventDefault();
    if (validateStep1()) {
      setShowStep1(false);
      setShowStep2(true);
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateStep2()) return;

    try {
      const res = await fetch("/api/book", {
        method: "POST",
        headers: { 
          "Authorization": `Bearer ${token}`, 
          "Content-Type": "application/json" 
        },
        body: JSON.stringify(formattedForm),
      });
      
      const response = await res.json();
      console.log(response.data);

      if (response.success) {
        setForm(initialForm);
        setShowStep2(false);
        setNotif({message: "Book added successfully!", status: "success"})

      } else {
        setShowStep2(false);
        setShowStep1(true); 
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
      <button className="btn btn-post ms-5" onClick={() => setShowStep1(true)}>
        Add Book
      </button>

      <Notifier message={notif.message} type={notif.status}></Notifier>

      <Modal show={showStep1} onHide={() => setShowStep1(false)} centered dialogClassName="custom-modal">
        <Form>
          <Modal.Header closeButton>
            <Modal.Title className="w-100 text-center">Add Book</Modal.Title>
          </Modal.Header>
          <Modal.Body>
            {[
              "title",
              "author",
              "publisher",
              "genres",
              "year",
              "price",
              "image",
            ].map((field) => (
              <Form.Group className="mb-3" key={field}>
                <Form.Control
                  name={field}
                  placeholder={field.charAt(0).toUpperCase() + field.slice(1)}
                  value={form[field]}
                  onChange={handleChange}
                  isInvalid={errors[field].hasError}
                  required
                />
                <Form.Control.Feedback type="invalid">
                  {errors[field].message}
                </Form.Control.Feedback>
              </Form.Group>
            ))}
          </Modal.Body>
          <div className="ps-3 pe-3">
            <button className="btn btn-post w-100" onClick={handleNext}>
              Next
            </button>
          </div>
          <div className="p-3">
            <button className="btn btn-light w-100" onClick={() => setShowStep1(false)}>
              Cancel
            </button>
          </div>
        </Form>
      </Modal>

      <Modal show={showStep2} onHide={() => setShowStep2(false)} centered dialogClassName="custom-modal">
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
                isInvalid={errors.synopsis.hasError}
              />
              <Form.Control.Feedback type="invalid">
                {errors.synopsis.message}
              </Form.Control.Feedback>
            </Form.Group>
            <Form.Group className="mb-3">
              <Form.Control
                as="textarea"
                rows={10}
                name="content"
                placeholder="Content"
                value={form.content}
                onChange={handleChange}
                required
              />
            </Form.Group>
            <div className="ps-3 pe-3">
              <button className="btn btn-post w-100" type="submit">
                Submit
              </button>
            </div>
            <div className="p-3">
              <button className="btn btn-light w-100" onClick={() => { setShowStep2(false); setShowStep1(true); }}>
                Back
              </button>
            </div>
          </Form>
        </Modal.Body>
      </Modal>
    </>
  );
};

export default AddBookModal;
