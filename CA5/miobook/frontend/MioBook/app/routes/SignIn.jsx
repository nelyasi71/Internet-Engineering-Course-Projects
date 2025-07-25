import React, { useState, useEffect, useRef } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import InputField from "../components/InputField";
import PasswordField from "../components/PasswordField";
import Footer from "../components/footer";

export function meta({}) {
  return [
    { title: "Sign In" },
    { name: "MioBook", content: "MioBook" },
  ];
}

const loginFields = ["username", "password"];

const initialLoginData = loginFields.reduce((acc, field) => {
  acc[field] = "";
  return acc;
}, {});

const initialLoginErrors = loginFields.reduce((acc, field) => {
  acc[field] = { hasError: false, message: "" };
  return acc;
}, {});



const SignIn = () => {
  const [formData, setFormData] = useState(initialLoginData);
  const [errors, setErrors] = useState(initialLoginErrors);
  const [loading, setLoading] = useState(true);
  const [user, setUser] = useState(null);
  const usernameRef = useRef(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://localhost:9090/api/auth/user", { credentials: "include" })
    .then(res => res.json())
    .then(res => {
      setUser(res.data);
      setLoading(false);
    })
    .catch(() => {
      setUser(null);
      setLoading(false);
    });

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
    const newErrors = { ...initialLoginErrors };

    if (!formData.username) {
      newErrors.username = { hasError: true, message: "Username is required" };
      isValid = false;
    }

    if (!formData.password) {
      newErrors.password = { hasError: true, message: "Password is required" };
      isValid = false;
    }

    setErrors(newErrors);
    return isValid;
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!validateForm()) return;

    try {
      const postBody = {
        username: formData.username,
        password: formData.password,
      };

      const response = await axios.post("http://localhost:9090/api/auth/login", postBody, {
        withCredentials: true,
      });

      if (response.data.success) {
        const userRoleResp = await axios.get(
          `http://localhost:9090/api/users/${formData.username}`,
          { withCredentials: true }
        );

        const role = userRoleResp.data.data.role;
        navigate(role === "admin" ? "/panel" : "/dashboard");
      } else {
        const fieldErrors = response.data.data?.fieldErrors;
        const newErrors = { ...initialLoginErrors };

        if (fieldErrors) {
          Object.entries(fieldErrors).forEach(([field, message]) => {
            if (newErrors[field] !== undefined) {
              newErrors[field] = { hasError: true, message };
            }
          });
        } else {
          // fallback for generic error
          loginFields.forEach((field) => {
            newErrors[field] = {
              hasError: true,
              message: "Invalid username or password",
            };
          });
        }

        setErrors(newErrors);
      }
    } catch (error) {
      console.error("Login failed:", error);
    }
  };

  const isFormValid = loginFields.every((field) => formData[field].trim() !== "");

  if (loading) {
    return <div>Loading...</div>;
  }
  
  if (user) {
    return <Navigate to={user.role === "admin" ? "/panel" : "/"} replace />;
  }
  
  return (
    <div className="bg-light min-vh-100">
      
      <div className="container d-flex justify-content-center align-items-center vh-100">
        <div className="card p-5 border-0 w-75">
          <h2 className="text-center">Sign In </h2>
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
            <button
              type="submit"
              className="btn btn-post rounded-3 w-100 border-0"
              disabled={!isFormValid}
              >
              Sign in
            </button>
          </form>
          <p className="text-center text-muted pt-4">
            Not a member yet?{" "}
            <Link to="/signup" className="text-decoration-none link">
              Sign up
            </Link>
          </p>
        </div>
        <Footer />
      </div>
    </div>
  );
};

export default SignIn;
