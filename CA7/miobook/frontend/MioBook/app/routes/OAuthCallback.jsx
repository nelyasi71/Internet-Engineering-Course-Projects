// src/pages/OAuthCallback.jsx
import { useEffect } from "react";
import axios from "axios";

function OAuthCallback() {
  useEffect(() => {
    const urlParams = new URLSearchParams(window.location.search);
    const code = urlParams.get("code");

    if (code) {
      axios.get("http://localhost:9090/api/auth/google/callback", {
        params: { code },
        // withCredentials: true
      })
        .then((response) => {
          localStorage.setItem("jwt", response.data.data.token);
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