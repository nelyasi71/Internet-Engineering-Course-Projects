import CartItem from "./CartItem";

export default function CartTable({ items }) {
    return (
      <>
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
      </>
    );
  }
  