import "bootstrap/dist/css/bootstrap.css";
import SignUp from "./signup.jsx";
export function meta({}) {
  return [
    { title: "New React Router App" },
    { name: "description", content: "Welcome to React Router!" },
  ];
}

export default function Home() {
  return (
    <>
      <SignUp />
    </>
  );
}
