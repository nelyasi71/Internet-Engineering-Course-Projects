import { Dropdown } from "react-bootstrap";

import logo from '../static/Logo.png';
import ProfileMenu from "./ProfileMenu";
import SearchDropdown from "./SearchDropDown";

export default function Navbar() {
  return (
    <nav className="navbar navbar-expand-lg shadow">
      <div className="container d-flex align-items-center justify-content-between">
        <img src={logo} className="navbar-brand" alt="logo" />
        <SearchDropdown />
        <ProfileMenu />
      </div>
    </nav>
  );
}
