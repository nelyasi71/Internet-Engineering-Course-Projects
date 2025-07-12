import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

// Layout and error handling

// Pages
import HomePage from "./routes/HomePage";
import SignUp from "./routes/SignUp";
import SignIn from "./routes/SignIn";
import Dashboard from "./routes/Dashboard";
import BuyCart from "./routes/BuyCart";
import History from "./routes/History";
import BookContent from "./routes/BookContent";
import AdminPanel from "./routes/AdminPanel";
import Author from "./routes/Author";
import Book from "./routes/Book";
import SearchResult from "./routes/SearchResult";
import OAuthCallback from "./routes/OAuthCallback";

// Styles
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import "./components/styles.css";
import "react-toastify/dist/ReactToastify.css";

export default function App() {
  return (
    <Routes>
      <Route path="/">
        <Route path="" element={<HomePage />} />
        <Route path="signup" element={<SignUp />} />
        <Route path="signin" element={<SignIn />} />
        <Route path="dashboard" element={<Dashboard />} />
        <Route path="cart" element={<BuyCart />} />
        <Route path="history" element={<History />} />
        <Route path="books/:title/content" element={<BookContent />} />
        <Route path="panel" element={<AdminPanel />} />
        <Route path="authors/:authorName" element={<Author />} />
        <Route path="books/:bookTitle" element={<Book />} />
        <Route path="books" element={<SearchResult />} />
        <Route path="oauth-callback" element={<OAuthCallback />} />
      </Route>
    </Routes>
  );
}
