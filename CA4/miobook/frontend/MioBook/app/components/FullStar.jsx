import React from 'react';
import { Star } from 'lucide-react';
import './styles.css';

const FullStar = ({ onClick }) => {
  return (
    <div onClick={onClick} className="star">
      <Star className="text-[#EBA452]" />
    </div>
  );
};

export default FullStar;
