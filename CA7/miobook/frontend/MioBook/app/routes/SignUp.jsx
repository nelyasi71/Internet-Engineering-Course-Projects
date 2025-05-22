import React, { useState, useEffect, useRef } from "react";
import { Link, useNavigate } from "react-router-dom";
import InputField from "../components/InputField";
import PasswordField from "../components/PasswordField";
import RoleSelector from "../components/RoleSelector";
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import Footer from "../components/footer";
import axiosInstance from '../api/axiosInstance';
import useAuthApi from "../hooks/useAuthApi";
import axios from "axios";
import { FaGoogle } from "react-icons/fa";

const clientId = "908082702744-0450j4cjil8u1mbmrvtsosdd6puc1825.apps.googleusercontent.com";
const redirectUri = "http://localhost:5173/oauth-callback";
const scope = "openid email profile";
const googleAuthUri = `https://accounts.google.com/o/oauth2/auth?client_id=${clientId}&response_type=code&scope=${encodeURIComponent(
  scope
)}&redirect_uri=${encodeURIComponent(redirectUri)}`;

export function meta({}) {
  return [
    { title: "Sign Up" },
    { name: "MioBook", content: "MioBook" },
  ];
}

const SignUp = () => {
  const formFields = ["username", "password", "email", "country", "city"];
  const initialFormData = {
    username: "",
    password: "",
    email: "",
    country: "",
    city: "",
    role: "customer",
  };
  const initialErrors = formFields.reduce((acc, field) => {
    acc[field] = { hasError: false, message: "" };
    return acc;
  }, {});
  
  const [formData, setFormData] = useState(initialFormData);
  const [errors, setErrors] = useState(initialErrors);
  const navigate = useNavigate();
  const usernameRef = useRef(null);
  const { login, getUser, getUserRole } = useAuthApi();

  useEffect(() => {
    usernameRef.current?.focus();
  }, []);
  
  const handleChange = (field) => (e) => {
    const value = e.target.value;
    setFormData((prev) => ({ ...prev, [field]: value }));
  
    if (errors[field]?.hasError) {
      setErrors((prev) => ({
        ...prev,
        [field]: { hasError: false, message: "" },
      }));
    }
  };
  
  const validateForm = () => {
    let isValid = true;
    const newErrors = { ...initialErrors };
  
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
  
    const postBody = {
      username: formData.username,
      password: formData.password,
      email: formData.email,
      address: {
        country: formData.country,
        city: formData.city,
      },
      role: formData.role,
    };
  
    try {
      const response = await axiosInstance.post("/user", postBody);

      if (response.success) {
        const loginResp = await axiosInstance.post("/auth/login", {
          username: postBody.username,
          password: postBody.password,
        });

        const token = loginResp.data.data.token ;
        localStorage.setItem("jwt", token);

        const userRoleResp = await axiosInstance.get(`/users/${postBody.username}`);
        navigate(userRoleResp.data.data.role === "admin" ? "/panel" : "/dashboard");
      } else {
        const fieldErrors = response.data.data?.fieldErrors || {};
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
  
  const isFormValid = formFields.every(
    (field) => formData[field].trim() !== ""
  );
  
  return (
    <div className="bg-light min-vh-100">
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
              className="btn btn-post rounded-3 w-100 border-0 mb-3"
              disabled={!isFormValid}f
            >
              Sign up
            </button>

            <button
              type="button"
              className="btn btn-post rounded-3 w-100 border-0"
              onClick={() => {window.location.href = googleAuthUri}}
            >
              <FaGoogle size={20} className="me-2 mb-1"/>
              Signup with Google
            </button>
          </form>
          <p className="text-center text-muted pt-4">
            Already have an account?{" "}
            <Link to="/signin" className="text-decoration-none link">
              Sign in
            </Link>
          </p>
        </div>
        <Footer />
      </div>
    </div>
  );
};

export default SignUp;
