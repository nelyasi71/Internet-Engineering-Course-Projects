import CartTable from "../components/CartTable";
import { useEffect, useState } from "react";
import Navbar from "../components/NavBar";
import Footer from "../components/footer";
import { RiChatHistoryFill, RiHistoryFill } from "react-icons/ri";


const token = typeof window !== "undefined" ? localStorage.getItem("jwt") : null;

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
    fetch("http://localhost:9090/api/purchase-history", {
      method: "GET",
      headers: {
        "Authorization": `Bearer ${token}`, // or just `token` if your API expects it differently
        "Content-Type": "application/json"
      }
    })
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
        <h2 className="p-3"><i><RiHistoryFill /></i> History</h2>
        {purchasedHistory.length === 0 &&  (
          <div className="d-flex flex-column align-items-center justify-content-center py-5 text-muted">
            <RiHistoryFill size={100} />
            <div className="mt-3 fs-4">You Have not Bought Anything Yet :( </div>
          </div>
        )}
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
