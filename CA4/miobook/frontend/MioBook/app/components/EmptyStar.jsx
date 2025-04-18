import React from 'react';
import { Star } from 'lucide-react';
import './EmptyStar.css';

const EmptyStar = ({ onClick }) => {
  return (
    <div onClick={onClick} className="empty-star">
      <Star className="text-gray-300" />
    </div>
  );
};

export default EmptyStar;
