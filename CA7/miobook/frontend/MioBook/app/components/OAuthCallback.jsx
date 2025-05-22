// src/pages/OAuthCallback.jsx
import { useEffect } from "react";

function OAuthCallback() {
  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get("code");

    if (code) {
      fetch("http://localhost:9090/auth/google/callback?code=" + code)
        .then((res) => res.json())
        .then((data) => {
          localStorage.setItem("jwt", data.data.token);
          window.location.href = "/";
        })
        .catch((err) => {
          console.error("OAuth failed", err);
          window.location.href = "/signin";
        });
    }
  }, []);

  return <div>Redirecting...</div>;
}

export default OAuthCallback;
