import AuthorItem from "./AuthorItem";
import BookItem from "./UserBookItem";

export default function AllAuthors({ items }) {
  return (
    <div className="section rounded-3 bg-white mt-4 p-4">
      <h2 className="p-2"><i className="bi bi-pen"></i> Authors</h2>
      <table className="table align-middle">
        <thead className="table-light">
          <tr>
            <th>Image</th>
            <th>Name</th>
            <th>Pen Name</th>
            <th>Nationality</th>
            <th>Born</th>
            <th>Died</th>
          </tr>
        </thead>
        <tbody>
          {items.map((item, index) => (
            <AuthorItem key={index} item={item} />
          ))}
        </tbody>
      </table>
    </div>
  );
}
