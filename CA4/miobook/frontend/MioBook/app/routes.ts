import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
    route("signup", "routes/signup.jsx"),
    route("signin", "routes/signin.jsx"),
    route("dashboard", "routes/Dashboard.jsx"),
    route("cart", "routes/BuyCart.jsx"),
    route("history", "routes/History.jsx"),
    route("books/:title/content", "routes/BookContent.jsx"),
] satisfies RouteConfig;
