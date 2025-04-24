import CartTable from "../components/CartTable";
import { useEffect, useState } from "react";
import Navbar from "../components/NavBar";
import Footer from "../components/footer";

export function meta({}) {
  return [
    { title: "History" },
    { name: "MioBook", content: "MioBook" },
  ];
}

export default function History() {
  const [openIndex, setOpenIndex] = useState({});
  const [purchasedHistory, setPurchasedHistory] = useState([]);

  useEffect(() => {
    fetch("http://localhost:9090/api/purchase-history", {credentials: "include"})
      .then(res => res.json())
      .then(res => setPurchasedHistory(res.data.items));
    }, []);


  const toggleAccordion = (index) => {
    setOpenIndex({ ...openIndex, [index]: openIndex[index] ? false : true});
  };

  return (
    <div className="bg-light vh-100">
      <Navbar />
      <div className="container bg-white mt-5 p-5">
        <h2 className="p-3"><i className="bi bi-clock-history"></i> History</h2>
        <div className="accordion shadow rounded-5">
          {purchasedHistory.map((group, index) => (
            <div className="accordion-item" key={index}>
              <h2 className="accordion-header">
                <button
                  className={`accordion-button p-4 border ${openIndex[index] ? "" : "collapsed"}`}
                  type="button"
                  onClick={() => toggleAccordion(index)}
                  > <div>{group.purchaseDate} │ ${group.totalCost}</div>
                </button>
              </h2>
              <div className={`accordion-collapse collapse ${openIndex[index] ? "show" : ""}`}>
                <div className="accordion-body">
                  <CartTable items={group.items} onlyShow={true}/>
                </div>
              </div>
            </div>
          ))}
        </div>
      </div>
      <Footer />
    </div>
  );
}
