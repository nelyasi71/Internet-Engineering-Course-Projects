import "bootstrap/dist/css/bootstrap.css";
import SignUp from "./signup.jsx";
import SignIn from "./signin.jsx";
import Dashboard from "./Dashboard.jsx";
import Navbar from "../components/NavBar.jsx";
import BuyCart from "./BuyCart.jsx";


export function meta({}) {
  return [
    { title: "New React Router App" },
    { name: "description", content: "Welcome to React Router!" },
  ];
}
export default function Home() {
  return (
    <div className="bg-light min-vh-100">
      <BuyCart items={[]} />
    </div>
  );
}
