import React, { useState } from 'react';
import axios from 'axios';

export default function CreditForm({ user_credit }) {
  const [amount, setAmount] = useState(0);
  const [userCredit, setUserCredit] = useState(user_credit); 
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      let postBody = {
        credit: Number(amount),
        username: "matin"
      };
    
      console.log(postBody);
    
      const response = await fetch("http://localhost:9090/api/credit", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        credentials: "include",
        body: JSON.stringify(postBody)
      });
    
      const data = await response.json();
    
      if (data.success) {
        setUserCredit(prev => prev + Number(amount)); 
        setAmount(0); 
      } else {

      }
    } catch (error) {
    }
  }

  return (
    <form onSubmit={handleSubmit} className="section flex-fill bg-white p-3">
      <h2><i className="bi bi-currency-dollar"></i>{userCredit}</h2>
      <div className="row">
        <div className="col-8">
          <input type="number" className="form-control mb-2" placeholder="$Amount" value={amount} onChange={e => setAmount(e.target.value)} />
        </div>
        <div className="col-4">
          <button type="submit" className="btn btn-post w-100 rounded-3" disabled={!amount}>
            Add more credit
          </button>
        </div>
      </div>
    </form>
  );
  
}