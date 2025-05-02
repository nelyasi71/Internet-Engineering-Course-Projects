import React, { useState } from "react";

const PasswordField = ({ value, onChange, error, errorMessage }) => {
  const [showPassword, setShowPassword] = useState(false);

  return (
    <div>
      <div className="input-group">
        <input
          type={showPassword ? "text" : "password"}
          className={`form-control ${error ? "border-danger" : ""}`}
          placeholder="Password"
          value={value}
          onChange={onChange}
        />
        <i
          className={`bi ${
            showPassword ? "bi-eye" : "bi-eye-slash"
          } input-group-text`}
          onClick={() => setShowPassword(!showPassword)}
          style={{ cursor: "pointer" }}
        />
      </div>
      {error && errorMessage && (
        <small className="text-danger d-block mt-1">{errorMessage}</small>
      )}
    </div>
  );
};

export default PasswordField;
