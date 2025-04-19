import BookItem from "./BookItem";

export default function AllBooks({ items }) {
  return (
    <div className="container rounded-3 bg-white mt-4 p-4">
      <h2 className="p-2"><i className="bi bi-book"></i> Books</h2>
      <table className="table align-middle">
        <thead className="table-light">
          <tr>
            <th>Image</th>
            <th>Name</th>
            <th>Author</th>
            <th>Genre</th>
            <th>Publisher</th>
            <th>Published Year</th>
            <th>Price</th>
            <th>Total Buys</th>
          </tr>
        </thead>
        <tbody>
          {items.map((item, index) => (
            <BookItem key={index} item={item} />
          ))}
        </tbody>
      </table>
    </div>
  );
}
