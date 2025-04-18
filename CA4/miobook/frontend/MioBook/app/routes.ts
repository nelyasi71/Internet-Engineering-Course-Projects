import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
    index("routes/Signup.jsx"),
    route("signup", "routes/signup.jsx"),
    route("signin", "routes/signin.jsx"),
] satisfies RouteConfig;
