export default function CartTable({ items }) {
    return (
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
          {items.map((item, idx) => (
            <tr key={idx}>
              <td>
                <img src={item.image} alt="book" className="book-icon" />
              </td>
              <td>{item.name}</td>
              <td>{item.author}</td>
              <td>{item.genre}</td>
              <td>{item.publisher}</td>
              <td>{item.publishedYear}</td>
              <td>{item.status}</td>
              <td>
                <button className="btn btn-sm btn-primary">Action</button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    );
  }
  