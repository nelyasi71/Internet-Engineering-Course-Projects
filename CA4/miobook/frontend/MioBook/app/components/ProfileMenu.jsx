import React, { useEffect, useState } from 'react';
import { Dropdown, Spinner } from 'react-bootstrap';
import { FaUser, FaBook, FaShoppingCart, FaHistory, FaSignOutAlt } from 'react-icons/fa';

const ProfileMenu = () => {
  const [user, setUser] = useState({name: "sample"});

  useEffect(() => {
    const fetchUser = async () => {
      const res = await fetch('/user/sample'); // Replace with actual endpoint
      const data = await res.json();
      setUser(data);
    };
    fetchUser();
  }, []);

  if (!user) {
    // return <Spinner animation="border" size="sm" />;
  }

  return (
    <Dropdown align="end">
      <Dropdown.Toggle
        variant="light"
        className="rounded-circle text-white bg-dark border-0"
        style={{ width: '40px', height: '40px' }}
        id="profile-dropdown"
      >
        {user.name.charAt(0).toUpperCase()}
      </Dropdown.Toggle>

      <Dropdown.Menu className="shadow p-2 rounded">
        <div className="fw-bold px-3">{user ? user.name : "Sample Name"}</div>
        <Dropdown.Divider />
        <Dropdown.Item href="/profile">
          <FaUser className="me-2" /> Profile
        </Dropdown.Item>
        <Dropdown.Item href="/my-books">
          <FaBook className="me-2" /> My Books
        </Dropdown.Item>
        <Dropdown.Item href="/cart">
          <FaShoppingCart className="me-2" /> Buy Cart
        </Dropdown.Item>
        <Dropdown.Item href="/history">
          <FaHistory className="me-2" /> Purchase History
        </Dropdown.Item>
        <Dropdown.Divider />
        <Dropdown.Item onClick={() => alert('Logging out...')}>
          <FaSignOutAlt className="me-2" /> Logout
        </Dropdown.Item>
      </Dropdown.Menu>
    </Dropdown>
  );
};

export default ProfileMenu;
