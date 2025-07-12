import Navbar from "../components/NavBar";
import Background from "../static/Background.png" 
import Image from "../static/Vertical-Image.png"
import Card from "../components/Card";
import { useParams } from 'react-router-dom';  

const token = typeof window !== "undefined" ? localStorage.getItem("jwt") : null;

const Author = () => {
    const [authorBooks, setAuthorBooks] = useState([]);
    const [authorDetails, setAuthorDetails] = useState(null);
    const { authorName } = useParams(); 

    useEffect(() => {
        fetch(`/api/authors/${authorName}`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        })
        .then(res => res.json())
        .then(res => {
          setAuthorDetails(res.data);  
        })
         
        fetch(`/api/books?author=${authorName}`, {
          method: "GET",
          headers: {
            "Authorization": `Bearer ${token}`,
            "Content-Type": "application/json"
          }
        })
        .then(res => res.json())
        .then(res => {
          setAuthorBooks(res.data.books);  
        })
          
    }, [authorName]);  

    if (!authorDetails) {
        return <div className="text-center p-5">Still loading author details...</div>
      }
      
    return (
        <div>
            <Navbar />

            <img
              src={Background}
              className="hero-banner mx-auto w-100"
            />

            <div className="container">
                <div className="row">
                    <div className="col-5 offset-0 position-relative">
                        <img
                          src={Image}
                          className="img-fluid top-50 start-0 translate-middle-y rounded-3 z-index-2"
                        />
                    </div>
                </div>
            </div>

            <div className="container d-flex flex-row justify-content-between mb-0">
                <div className="">
                    <h1 className="text-center fs-1 fw-bold">
                        {authorDetails.name}
                    </h1>
                </div>

                <div className="">
                    <div className="text-muted small">Pen Name</div>
                    <div>{authorDetails.penName}</div>
                </div>

                <div className="">
                    <div className="text-muted small">Nationality</div>
                    <div>{authorDetails.nationality}</div>
                </div>

                <div className="">
                    <div className="text-muted small">Born</div>
                    <div>{authorDetails.born}</div>
                </div>

                <div className="">
                    <div className="text-muted small">Died</div>
                    <div>{authorDetails.death ? authorDetails.death : "-"}</div>
                </div>

                <div className="">
                    <div className="text-muted small">Books</div>
                    <div>{authorBooks.length}</div>
                </div>
            </div>
           
            <div className="container w-100 ">
            <div className="row row-cols-5 mx-0 w-100 ">
                    {authorBooks.map(book => (
                      <div className="col mb-5 " key={book.id}>
                        <Card
                          title={book.title}
                          author={book.author}
                          price={book.price}
                          image={book.image}
                          rating={book.rating}
                        />
                      </div>
                    ))}
                </div>
            </div>



            {/* <Footer /> */}
        </div>
    );
}

export default Author;