import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
    index("routes/Signup.jsx"),
    route("signup", "routes/signup.jsx"),
    route("signin", "routes/signin.jsx"),
    route("dashboard", "routes/Dashboard.jsx"),
    route("cart", "routes/BuyCart.jsx"),
    route("history", "routes/History.jsx"),
] satisfies RouteConfig;
