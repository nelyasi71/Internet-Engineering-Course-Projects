import React, { useState, useEffect, useRef } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import InputField from "../components/InputField";
import PasswordField from "../components/PasswordField";
import RoleSelector from "../components/RoleSelector";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import Footer from "../components/footer";

const SignUp = () => {
  const [formData, setFormData] = useState({
    username: "",
    password: "",
    email: "",
    country: "",
    city: "",
    role: "customer",
  });
  const [errors, setErrors] = useState({
    username: { hasError: false, message: "" },
    password: { hasError: false, message: "" },
    email: { hasError: false, message: "" },
    country: { hasError: false, message: "" },
    city: { hasError: false, message: "" },
  });
  const navigate = useNavigate();
  const usernameRef = useRef(null);

  useEffect(() => {
    if (usernameRef.current) {
      usernameRef.current.focus();
    }
  }, []);

  const handleChange = (field) => (e) => {
    setFormData({ ...formData, [field]: e.target.value });
    if (errors[field].hasError) {
      setErrors({
        ...errors,
        [field]: { hasError: false, message: "" },
      });
    }
  };

  const validateForm = () => {
    const newErrors = {
      username: { hasError: false, message: "" },
      password: { hasError: false, message: "" },
      email: { hasError: false, message: "" },
      country: { hasError: false, message: "" },
      city: { hasError: false, message: "" },
    };
    let isValid = true;

    if (!formData.username) {
      newErrors.username = { hasError: true, message: "Username is required" };
      isValid = false;
    }
    if (!formData.password) {
      newErrors.password = { hasError: true, message: "Password is required" };
      isValid = false;
    }
    if (!formData.email || !/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = { hasError: true, message: "Valid email is required" };
      isValid = false;
    }
    if (!formData.country) {
      newErrors.country = { hasError: true, message: "Country is required" };
      isValid = false;
    }
    if (!formData.city) {
      newErrors.city = { hasError: true, message: "City is required" };
      isValid = false;
    }

    setErrors(newErrors);
    return isValid;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      let postBody = {
        username: formData.username,
        password: formData.password,
        email: formData.email,
        address: {
          country: formData.country,
          city: formData.city,
        },
        role: formData.role,
      };

      await axios.post("http://localhost:9090/api/user", postBody);
    } catch (error) {
      setErrors({
        ...errors,
        username: { hasError: true, message: "Invalid username or password" },
        password: { hasError: true, message: "Invalid username or password" },
      });
    }
  };

  const isFormValid = Object.values(formData).every(
    (value) => value.trim() !== "" || value === formData.role
  );

  return (
    <div className="container d-flex justify-content-center align-items-center vh-100">
      <div className="card p-5 border-0 w-75">
        <h2 className="text-center">Sign Up</h2>
        <p className="text-center text-muted">MioBook</p>
        <form className="mx-auto w-50" onSubmit={handleSubmit}>
          <div className="mb-4">
            <InputField
              type="text"
              placeholder="Username"
              value={formData.username}
              onChange={handleChange("username")}
              error={errors.username.hasError}
              errorMessage={errors.username.message}
              ref={usernameRef}
            />
          </div>
          <div className="mb-4">
            <PasswordField
              value={formData.password}
              onChange={handleChange("password")}
              error={errors.password.hasError}
              errorMessage={errors.password.message}
            />
          </div>
          <div className="mb-4">
            <InputField
              type="email"
              placeholder="Email"
              value={formData.email}
              onChange={handleChange("email")}
              error={errors.email.hasError}
              errorMessage={errors.email.message}
            />
          </div>
          <div className="row g-2 mb-4">
            <div className="col me-2">
              <InputField
                type="text"
                placeholder="Country"
                value={formData.country}
                onChange={handleChange("country")}
                error={errors.country.hasError}
                errorMessage={errors.country.message}
              />
            </div>
            <div className="col ms-2">
              <InputField
                type="text"
                placeholder="City"
                value={formData.city}
                onChange={handleChange("city")}
                error={errors.city.hasError}
                errorMessage={errors.city.message}
              />
            </div>
          </div>
          <p>I am</p>
          <div className="mb-4">
            <RoleSelector
              role={formData.role}
              setRole={(role) => setFormData({ ...formData, role })}
            />
          </div>
          <button
            type="submit"
            className="btn btn-post rounded-3 w-100 border-0"
            disabled={!isFormValid}
          >
            Sign up
          </button>
        </form>
        <p className="text-center text-muted pt-4">
          Already have an account?{" "}
          <Link to="/main/signin" className="text-decoration-none link">
            Sign in
          </Link>
        </p>
      </div>
      <Footer />
    </div>
  );
};

export default SignUp;
