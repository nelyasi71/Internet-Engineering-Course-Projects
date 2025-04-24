import React, { useState } from 'react';
import EmptyStar from './EmptyStar.jsx';
import StarSelectionFilled from './FullStar.jsx';
import 'bootstrap/dist/css/bootstrap.min.css';

const AddReviewModal = ({ bookTitle, bookImage, show, onClose }) => {
  const [rating, setRating] = useState(0);
  const [hover, setHover] = useState(0);
  const [reviewText, setReviewText] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = () => {
    if (rating === 0 || reviewText.trim() === '') {
      setError('Please provide a rating and review.');
      return;
    }
    setError('');
    onClose();
  };

  if (!show) return null;

  return (
    <div className="modal show d-block" tabIndex="-1" role="dialog" style={{ backgroundColor: 'rgba(0,0,0,0.5)' }}>
      <div className="modal-dialog modal-sm modal-dialog-centered" role="document">

        <div className="modal-content">
          
          <div className="modal-header">
            <h6 className="modal-title text-canter">Add Review</h6>
            <button type="button" className="btn-close" onClick={onClose}></button>
          </div>

          <div className="modal-body text-center">
            <img src={bookImage} alt="Book" className="mb-3" style={{ width: '80px', borderRadius: '0.5rem' }} />
            <h5 className="mb-3">{bookTitle}</h5>

            <div className="d-flex justify-content-evenly w-100 my-3">
              {[1, 2, 3, 4, 5].map((index) => (
                <span
                  key={index}
                  onClick={() => setRating(index)}
                  onMouseEnter={() => setHover(index)}
                  onMouseLeave={() => setHover(0)}
                  style={{ cursor: 'pointer' }}
                >
                  {index <= (hover || rating) ? <StarSelectionFilled /> : <EmptyStar />}
                </span>
              ))}
            </div>

            <textarea
              className="form-control mb-2"
              style={{ backgroundColor: '#F1F5F7' }}
              rows="4"
              placeholder="Type your review..."
              value={reviewText}
              onChange={(e) => setReviewText(e.target.value)}
            ></textarea>

            {error && <div className="text-danger mb-2">{error}</div>}

            <button
              type="button"
              className="btn btn-green w-100 mb-2"
              onClick={handleSubmit}
            >
              Submit Reviews
            </button>

            <button type="button" className="btn btn-secondary w-100" onClick={onClose}>
              Cancel
            </button>
          </div>

        </div>
      </div>
    </div>
  );
};

export default AddReviewModal;
