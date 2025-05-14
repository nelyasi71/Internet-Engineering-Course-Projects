import React, { useEffect, useState } from 'react';
import { Dropdown, Spinner } from 'react-bootstrap';
import { FaUser, FaBook, FaShoppingCart, FaHistory, FaSignOutAlt } from 'react-icons/fa';
import { useNavigate } from "react-router-dom";

const token = typeof window !== "undefined" ? localStorage.getItem("accessToken") : null;

const ProfileMenu = () => {
  const [user, setUser] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetch("http://localhost:9090/api/auth/user", {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`, // or just `token` if your API expects it differently
        "Content-Type": "application/json"
      }
    })
    .then(res => res.json())
    .then(res => setUser(res.data));
  }, []);

  const handleLogout = async () => {
    try {
      await fetch("http://localhost:9090/api/auth/logout", {
        method: "POST",
        headers: {
          "Authorization": `Bearer ${token}`, 
          "Content-Type": "application/json"
        },
        body: JSON.stringify({})
      });
      navigate("/signin"); 
    } catch (error) {
      console.error("Logout failed:", error);
    }
  };

  if (!user) {
    return <Spinner animation="border" size="sm" />;
  }

  return (
    <Dropdown align="end">
      <Dropdown.Toggle
        variant="light"
        className="rounded-circle text-white bg-dark border-0"
        style={{ width: '40px', height: '40px' }}
        id="profile-dropdown"
      >
        {user.username.charAt(0).toUpperCase()}
      </Dropdown.Toggle>

      <Dropdown.Menu className="shadow p-2 rounded">
        <div className="fw-bold px-3">{user.username}</div>
        <Dropdown.Divider />
        <Dropdown.Item onClick={() => {
          user.role === "customer" ? navigate("/dashboard") : navigate("/panel");
        }}>
          <FaUser className="me-2" /> Profile
        </Dropdown.Item>
        {user.role === "customer" && (
          <>
            <Dropdown.Item onClick={() => navigate("/dashboard")}>
              <FaBook className="me-2" /> My Books
            </Dropdown.Item>
            <Dropdown.Item onClick={() => navigate("/cart")}>
              <FaShoppingCart className="me-2" /> Buy Cart
            </Dropdown.Item>
            <Dropdown.Item onClick={() => navigate("/history")}>
              <FaHistory className="me-2" /> Purchase History
            </Dropdown.Item>
          </>
        )}
        <Dropdown.Divider />
        <Dropdown.Item onClick={handleLogout}>
          <FaSignOutAlt className="me-2" /> Logout
        </Dropdown.Item>
      </Dropdown.Menu>
    </Dropdown>
  );
};

export default ProfileMenu;
