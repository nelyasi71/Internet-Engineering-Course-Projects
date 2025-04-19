import React, { useState } from 'react';
import { Dropdown } from 'react-bootstrap';

const SearchDropdown = () => {
  const [searchType, setSearchType] = useState('Author');
  const [query, setQuery] = useState('');

  const handleSelect = (type) => {
    setSearchType(type);
  };

  return (
    <div className="d-flex align-items-center flex-grow-1 justify-content-center mx-3">
      <div className="col-md-6 col-sm-6">
        <div className="input-group rounded p-1 bg-custom-gray">
          
          <Dropdown onSelect={handleSelect}>
            <Dropdown.Toggle
              variant="transparent"
              className="text-secondary border-0 bg-transparent"
              id="search-dropdown"
            >
              {searchType}
            </Dropdown.Toggle>

            <Dropdown.Menu className="rounded w-auto border-0">
              {['Author', 'Book', 'Genre'].map((type) => (
                <Dropdown.Item
                  key={type}
                  eventKey={type}
                  className="text-secondary"
                >
                  {type}
                </Dropdown.Item>
              ))}
            </Dropdown.Menu>
          </Dropdown>

          <div className="border-start border-2 mx-2" />

          <input
            type="text"
            className="form-control border-0 bg-custom-gray"
            placeholder={`Search by ${searchType}`}
            value={query}
            onChange={(e) => setQuery(e.target.value)}
          />
        </div>
      </div>
    </div>
  );
};

export default SearchDropdown;
