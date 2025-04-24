import { useEffect, useState } from "react";
import Navbar from "../components/NavBar";
import CreditForm from "../components/CreditForm";
import UserInfo from "../components/Userinfo";
import Footer from "../components/footer";
import MyBooks from "../components/MyBooks";
import AllBooks from "../components/AllBooks";
import AllAuthors from "../components/AllAuthors";
import AddAuthorModal from "../components/AddAuthorModal";
import AddBookModal from "../components/AddBookModal";

export function meta({}) {
  return [
    { title: "Panel" },
    { name: "MioBook", content: "MioBook" },
  ];
}

export default function Panel() {
  const [user, setUser] = useState(null);
  const [books, setbooks] = useState([]);
  const [authors, setAuthors] = useState([]);

  useEffect(() => {
    fetch("http://localhost:9090/api/auth/user", {credentials: "include"})
      .then(res => res.json())
      .then(res => setUser(res.data));

    fetch("http://localhost:9090/api/get-books", {credentials: "include"})
      .then(res => res.json())
      .then(res => setbooks(res.data.books));

    fetch("http://localhost:9090/api/authors", {credentials: "include"})
      .then(res => res.json())
      .then(res => setAuthors(res.data.authors));
  }, []);

  if (!user) return <div>Loading...</div>;

  return (
    <div className="bg-light min-vh-100">

      <Navbar />

      <div className="container pt-5 pb-3">
          <UserInfo name={user.username} email={user.email} wide={true} />
      </div>

      <div className="d-flex justify-content-center gap-5 p-3">
        <AddAuthorModal />
        <AddBookModal />
      </div>


      <div className="container pb-3">
        <AllBooks items={books} />
      </div>

      <div className="container pb-5">
        <AllAuthors items={authors} />
      </div>

      <div className="pt-5">
        <Footer />
      </div>
    </div>
  );
}
