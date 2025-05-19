import React from "react";
import "./styles.css";

const RoleSelector = ({ role, setRole }) => (
  <div className="btn-group w-100 gap-4" role="group">
    <button
      type="button"
      className={`btn ${
        role === "customer" ? "btn-selected" : "btn-light"
      } rounded-3`}
      onClick={() => setRole("customer")}
    >
      <i className="bi bi-person pe-2" />
      Customer
    </button>
    <button
      type="button"
      className={`btn ${
        role === "admin" ? "btn-selected" : "btn-light"
      } rounded-3`}
      onClick={() => setRole("admin")}
    >
      <i className="bi bi-briefcase pe-2" />
      Manager
    </button>
  </div>
);

export default RoleSelector;
