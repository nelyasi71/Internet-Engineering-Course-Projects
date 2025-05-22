import { type RouteConfig, index, route } from "@react-router/dev/routes";

export default [
    route("signup", "routes/SignUp.jsx"),
    route("signin", "routes/SignIn.jsx"),
    route("dashboard", "routes/Dashboard.jsx"),
    route("cart", "routes/BuyCart.jsx"),
    route("history", "routes/History.jsx"),
    route("books/:title/content", "routes/BookContent.jsx"),
    route("panel", "routes/AdminPanel.jsx"),
    route("authors/:authorName", "routes/Author.jsx"),
    route("books/:bookTitle", "routes/Book.jsx"),
    route("books", "routes/SearchResult.jsx"),
    route("oauth-callback", "routes/OAuthCallback.jsx"),
    route("/","routes/HomePage.jsx")
] satisfies RouteConfig;
