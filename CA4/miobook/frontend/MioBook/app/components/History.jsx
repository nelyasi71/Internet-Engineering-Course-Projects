import CartTable from "./CartTable";

export default function History({ groupedItems }) {
  const [openIndex, setOpenIndex] = useState(null);

  const toggleAccordion = (index) => {
    setOpenIndex(openIndex === index ? null : index);
  };

  return (
    <div className="accordion">
      {groupedItems.map((group, index) => (
        <div className="accordion-item" key={index}>
          <h2 className="accordion-header">
            <button
              className={`accordion-button ${openIndex === index ? "" : "collapsed"}`}
              type="button"
              onClick={() => toggleAccordion(index)}
            >
              {group.title}
            </button>
          </h2>
          <div className={`accordion-collapse collapse ${openIndex === index ? "show" : ""}`}>
            <div className="accordion-body">
              <CartTable items={group.items} />
            </div>
          </div>
        </div>
      ))}
    </div>
  );
}
