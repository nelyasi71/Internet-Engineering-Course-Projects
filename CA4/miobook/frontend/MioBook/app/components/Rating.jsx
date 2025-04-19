import React from 'react';
import EmptyStar from './EmptyStar';
import FullStar from './FullStar';
import './styles.css';

const Rating = ({ rating = 4, total = 5 }) => {  
  return (
    <div className={"w-100"}>
      <div className="rating d-flex flex-row" >
        {[...Array(total)].map((_, index) =>
          index < rating ? <FullStar key={index} /> : <EmptyStar key={index} />
        )}
      </div>
    </div>
  );
};

export default Rating;
