import UserBookItem from "./UserBookItem";
import { SiBookstack } from "react-icons/si";
export default function MyBooks({ items }) {

  return (
    <div className="section bg-white mt-4 p-4">
      <h2 className="p-2 fw-bold"><SiBookstack /> My Books</h2>
      <table className="table align-middle">
        <thead className="table-light no-border">
          <tr>
            <th className="fw-normal text-muted">Image</th>
            <th className="fw-normal text-muted">Name</th>
            <th className="fw-normal text-muted">Author</th>
            <th className="fw-normal text-muted">Genre</th>
            <th className="fw-normal text-muted">Publisher</th>
            <th className="fw-normal text-muted">Published Year</th>
            <th className="fw-normal text-muted">Status</th>
            <th></th>
          </tr>
        </thead>
        <tbody>
          {items.map((item, index) => (
            <UserBookItem key={index} item={item} />
          ))}
        </tbody>
      </table>
    </div>
  );
}
