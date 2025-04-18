import BookItem from "./BookItem";

export default function MyBooks({ items }) {
  return (
    <div className="section bg-white mt-4 p-4">
      <h2 className="p-2"><i className="bi bi-book"></i> My Books</h2>
      <table className="table align-middle">
        <thead className="table-light">
          <tr>
            <th>Image</th>
            <th>Name</th>
            <th>Author</th>
            <th>Genre</th>
            <th>Publisher</th>
            <th>Published Year</th>
            <th>Status</th>
            <th></th>
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
