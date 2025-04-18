export default function CreditForm({ credit }) {
    return (
      <form className="section flex-fill bg-white p-3">
        <h2><i className="bi bi-currency-dollar"></i>{credit}</h2>
        <div className="row">
          <div className="col-8">
            <input type="text" className="form-control mb-2" placeholder="$Amount" />
          </div>
          <div className="col-4">
            <button type="submit" className="btn btn-post w-100 rounded-3" disabled="True">
              Add more credit
            </button>
          </div>
        </div>
      </form>
    );
  }
  