import React, { useState, useEffect, useRef } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import axios from "axios";
import InputField from "../components/InputField";
import PasswordField from "../components/PasswordField";
import useAuthApi from "../hooks/useAuthApi";
import { FaGoogle } from "react-icons/fa";
import Footer from "../components/Footer";

const loginFields = ["username", "password"];

const initialLoginData = loginFields.reduce((acc, field) => {
  acc[field] = "";
  return acc;
}, {});

const initialLoginErrors = loginFields.reduce((acc, field) => {
  acc[field] = { hasError: false, message: "" };
  return acc;
}, {});

const clientId = "908082702744-0450j4cjil8u1mbmrvtsosdd6puc1825.apps.googleusercontent.com";
const redirectUri = "http://localhost:5173/oauth-callback";
const scope = "openid email profile";
const googleAuthUri = `https://accounts.google.com/o/oauth2/auth?client_id=${clientId}&response_type=code&scope=${encodeURIComponent(
  scope
)}&redirect_uri=${encodeURIComponent(redirectUri)}`;


const SignIn = () => {
  const [formData, setFormData] = useState(initialLoginData);
  const [errors, setErrors] = useState(initialLoginErrors);
  const [loading, setLoading] = useState(true);
  const [user, setUser] = useState(null);
  const usernameRef = useRef(null);
  const navigate = useNavigate();
  const { login, getUser, getUserRole } = useAuthApi();

  useEffect(() => {
    getUser()
        .then((userData) => {
          setUser(userData);
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

      const result = await login(formData.username, formData.password);

      
      if (result.success) {

        const token = typeof window !== "undefined" ? localStorage.getItem("jwt") : null;
        const userRoleResp = await axios.get(
          `/api/users/${formData.username}`,
          {
            headers: {
              Authorization: `Bearer ${token}`
            }
          }
        );

        const role = userRoleResp.data.data.role;
        navigate(role === "admin" ? "/panel" : "/dashboard");
        
      } else {
        const fieldErrors = result.data?.fieldErrors;
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
              className="btn btn-post rounded-3 w-100 border-0 mb-3"
              disabled={!isFormValid}
              >
              Sign in
            </button>
            <button
              type="button"
              className="btn btn-post rounded-3 w-100 border-0"
              onClick={() => {window.location.href = googleAuthUri}}
            >
              <FaGoogle size={20} className="me-2 mb-1"/>
              Signin with Google
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
