import React from "react";
import "./styles.css";

const RoleSelector = ({ role, setRole }) => (
  <div className="btn-group w-100 gap-4" role="group">
    <button
      type="button"
      className={`btn ${
        role === "Customer" ? "selected-btn" : "btn-light"
      } rounded-3`}
      onClick={() => setRole("Customer")}
    >
      <i className="bi bi-person pe-2" />
      Customer
    </button>
    <button
      type="button"
      className={`btn ${
        role === "Manager" ? "selected-btn" : "btn-light"
      } rounded-3`}
      onClick={() => setRole("Manager")}
    >
      <i className="bi bi-briefcase pe-2" />
      Manager
    </button>
  </div>
);

export default RoleSelector;
