import React, { useState } from 'react';
import { FaDollarSign } from "react-icons/fa";
import Notifier from './Notifier';
import InputField from './InputField';

const token = typeof window !== "undefined" ? localStorage.getItem("jwt") : null;

export default function CreditForm({ user_credit }) {
  const [amount, setAmount] = useState(0);
  const [userCredit, setUserCredit] = useState(user_credit); 
  const [notif, setNotif] = useState({ message: "", status: "" });
  const [error, setError] = useState({ hasError: false, message: "" });


  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      let postBody = {
        credit: Number(amount),
        username: "matin"
      };
    
      const response = await fetch("/api/credit", {
        method: "POST",
        headers: {
          "Authorization": `Bearer ${token}`, 
          "Content-Type": "application/json"
        },
        body: JSON.stringify(postBody)
      });
    
      const data = await response.json();
      
      if (data.success) {
        setUserCredit(prev => prev + Number(amount)); 
        setAmount(0); 
        setNotif({message: "credit added successfully!", status: "success"});
        setError({ hasError: false, message: "" });
      } else {
        setError({hasError: true, message: data.data?.fieldErrors.credit});
        console.log(data.data);
      }
    } catch (error) {
    }
  }

  let USDollar = new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',

  });

  return (
    <form onSubmit={handleSubmit} className="section flex-fill bg-white p-3">
      <h2 className="fw-bold">{USDollar.format(userCredit)}</h2>
      <div className="row">
        <div className="col-8">
          <InputField 
            type="number" 
            className="form-control mb-2" 
            placeholder="$Amount" 
            value={amount} 
            onChange={e => setAmount(e.target.value)} 
            error={error.hasError}
            errorMessage={error.message}
          />
        </div>
        <div className="col-4">
          <button type="submit" className="btn btn-post w-100 rounded-3" disabled={!amount}>
            Add more credit
          </button>
        </div>
      <Notifier message={notif.message} type={notif.status} />
      </div>
    </form>
  );
  
}