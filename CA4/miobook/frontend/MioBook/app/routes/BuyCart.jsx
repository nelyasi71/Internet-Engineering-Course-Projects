import CartItem from "../components/CartItem";
import Footer from "../components/footer";
import Navbar from "../components/NavBar";

export default function BuyCart({ items }) {
  return (
    <>
      <Navbar />
      <div className="container bg-white mt-5 p-5">
        <h2><i className="bi bi-cart3"></i> Cart</h2>
        <table className="table align-middle">
          <thead className="table-light">
            <tr>
              <th>Image</th>
              <th>Name</th>
              <th>Author</th>
              <th>Price</th>
              <th>Borrow Days</th>
              <th></th>
            </tr>
          </thead>
          <tbody>
            {items.map((item, index) => (
              <CartItem key={index} item={item} />
            ))}
          </tbody>
        </table>
        <div className="text-center mt-4">
          <button className="btn btn-post w-25">Purchase</button>
        </div>
      </div>
      <Footer />
    </>
  );
}
