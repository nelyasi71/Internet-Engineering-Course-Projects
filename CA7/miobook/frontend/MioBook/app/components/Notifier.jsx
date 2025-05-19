import React, { useEffect } from 'react';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';

const Notifier = ({ message, type = 'success' }) => {
  useEffect(() => {
    if (message) {
      toast[type](message);
    }
  }, [message]);

  return <ToastContainer />;
};

export default Notifier;
