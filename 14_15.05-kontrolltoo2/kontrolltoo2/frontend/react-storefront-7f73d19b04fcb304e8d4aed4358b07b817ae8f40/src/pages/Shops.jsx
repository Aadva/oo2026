import { useEffect, useState } from "react"

function Shops() {
        const [books, setBooks] = useState([]);

        useEffect(() => {
        async function fetchBooks() {
            try {
                const data = await fetch("http://localhost:8080/api/shops");
                const fetchData = await data.json();
                setBooks(fetchData.slice());
            } catch (e) {
                console.error("Unable to fetch", e);
            }
            }
            fetchBooks();
    }, []);
  return (
        <div>
        {books.map(book => 
            <div>
                <div>{book.title}</div>
                <div>{book.id}</div>
                <br />
            </div>)}
    </div>
  )
}

export default Shops