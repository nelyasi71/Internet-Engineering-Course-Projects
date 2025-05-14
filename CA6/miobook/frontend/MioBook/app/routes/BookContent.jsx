import Footer from "../components/footer";
import Navbar from "../components/NavBar";
import { useEffect, useState } from "react";
import { useParams } from "react-router-dom";


const token = typeof window !== "undefined" ? localStorage.getItem("accessToken") : null;

export function meta({}) {
  return [
    { title: "Book Content" },
    { name: "MioBook", content: "MioBook" },
  ];
}

export default function BookContent() {

  const { title } = useParams();
  const [book, setBook] = useState({});



  useEffect(() => {
    fetch("http://localhost:9090/api/books/" + title + "/content", {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`, // or just `token` if your API expects it differently
        "Content-Type": "application/json"
      }
    })
    .then(res => res.json())
    .then(res => setBook(res.data));
  }, []);

  return (
    <div className="bg-light min-vh-100">
      <Navbar />
      <div className="container rounded-2 shadow book-content mt-5 p-4">
        <div className="d-flex justify-content-between align-items-center">
          <h2 className="fw-semibold">
            <i className="bi bi-journal-text me-2"></i>
            {book.title}
          </h2>
          <span className="text-muted">By {book.author}</span>
        </div>
      </div>


      <div className="container rounded-2 book-content mt-5 p-4">
        <p className="p-2">
          {book.content}
        </p>
      </div>

      <Footer />
    </div>
  );
}
