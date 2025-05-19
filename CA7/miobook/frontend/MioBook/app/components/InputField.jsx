import React from "react";

const InputField = React.forwardRef(
  ({ type, placeholder, value, onChange, error, errorMessage }, ref) => (
    <div>
      <input
        ref={ref}
        type={type}
        className={`form-control ${error ? "border-danger" : ""}`}
        placeholder={placeholder}
        value={value}
        onChange={onChange}
      />
      {error && errorMessage && (
        <small className="text-danger d-block mt-1">{errorMessage}</small>
      )}
    </div>
  )
);

export default InputField;
