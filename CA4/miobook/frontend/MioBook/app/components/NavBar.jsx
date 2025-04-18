import { Dropdown } from "react-bootstrap";

import logo from '../static/Logo.png';

export default function Navbar() {
  return (
    <nav className="navbar navbar-expand-lg shadow">
      <div className="container d-flex align-items-center justify-content-between">
        <img src={logo} className="navbar-brand" alt="logo" />
        <div className="d-flex align-items-center flex-grow-1 justify-content-center mx-3">
          <div className="col-md-6 col-sm-6">
            <div className="input-group rounded p-1 bg-custom-gray">
              <Dropdown>
                <Dropdown.Toggle
                  className="btn border-0 text-secondary bg-transparent"
                  id="dropdown-basic"
                >
                  Author
                </Dropdown.Toggle>

                <Dropdown.Menu className="rounded w-auto border-0">
                  <Dropdown.Item className="text-secondary" href="#">
                    Author
                  </Dropdown.Item>
                  <Dropdown.Item className="text-secondary" href="#">
                    Book
                  </Dropdown.Item>
                  <Dropdown.Item className="text-secondary" href="#">
                    Genre
                  </Dropdown.Item>
                </Dropdown.Menu>
              </Dropdown>

              <div className="border-start border-2 mx-2"></div>
              <input
                type="text"
                className="form-control border-0 bg-custom-gray"
                placeholder="Search"
              />
            </div>
          </div>
        </div>
        <button className="btn btn-green rounded-3">Buy Now</button>
      </div>
    </nav>
  );
}
