import React, { useEffect, useState } from 'react'
import { Button } from '@/components/ui/button'
import { toast } from "sonner"
import { Toaster } from "@/components/ui/sonner"
import { ArrowDown, ArrowUp, Check, ShoppingBag } from "lucide-react"
import { Link } from 'react-router-dom'

function Home() {
  const [, setAllProducts] = useState([]);
  const [categories, setCategories] = useState([])
  const [products, setProducts] = useState([]);
  const [, setSelectedCategory] = useState("all")
  const [sort, setSort] = useState("id,asc")
  const [size, setSize] = useState(2)

  const [page, setPage] = useState(0);
  const [totalPages, setTotalPages] = useState(0);

  useEffect(() => {
      fetch(import.meta.env.VITE_BACK_URL + "/categories")
        .then((response) => response.json())
        .then((json) => setCategories(json))
    }, [])

  useEffect(() => {
     fetch(import.meta.env.VITE_BACK_URL + `/products?page=${page}&size=${size}&sort=${sort}`)
      .then(res => res.json())
      .then(json => {
        setAllProducts(json.content)
        setProducts(json.content)
        setTotalPages(json.totalPages);
      })
  }, [page,size , sort]);

  const sortAZ = () => {
    setSort("title,asc")
  }

  const sortZA = () => {
    setSort("title,desc")
  }

  const sortPriceIncreasing = () => {
     setSort("price,asc")
  }

  const sortPriceDecreasing = () => {
    setSort("price,desc")
  }

  const filterByCategory = (category) => {
    setSelectedCategory(category)
  }

  const addToCart = (product) => {
    const cartLS = JSON.parse(localStorage.getItem("cart")) || [];
    cartLS.push(product);
    localStorage.setItem("cart", JSON.stringify(cartLS));
  }

  return (
    <div className="flex flex-col gap-6 pt-4">
      <h1 className="text-xl font-semibold">React Storefront</h1>

      <div className="flex flex-wrap gap-2">
        <Button onClick={sortAZ} variant="outline">A-Z</Button>
        <Button onClick={sortZA} variant="outline">Z-A</Button>
        <Button onClick={sortPriceIncreasing} variant="outline">Price <ArrowUp /></Button>
        <Button onClick={sortPriceDecreasing} variant="outline">Price <ArrowDown> </ArrowDown></Button>
      </div>

      <div className="flex items-center gap-2">
        <label htmlFor="category-filter">Choose category</label>
        <select onChange={(e) => filterByCategory(e.target.value)}>
          {categories.map(category => 
            <option>{category.name}</option>
          )}
        </select>
      </div>

      <div className="flex items-center gap-2">
        <label htmlFor="category-filter">Choose size</label>
        <select onChange={(e) => setSize(e.target.value)}>
          <option>2</option>
          <option>3</option>
        </select>
      </div>


      <div>{products.length} items currently in stock.</div>
      {products.map((product, index) => 
        <div key={product.id} className="grid w-full grid-cols-[2rem_100px_minmax(0,1fr)_auto] items-center gap-4 py-8">
          <div className="text-right">{index + 1}.</div>
          <img className="w-[100px] h-[100px] object-cover" src={product.image} alt={product.description} />
          <div className="min-w-0">
            <div>{product.title}</div> 
            <div>{product.price}€</div> 
          </div>
          <div className="justify-self-end flex gap-2">
            <Button asChild variant="outline">
              <Link to={`/product/${product.id}`}>
                View product
              </Link>
            </Button>
            <Button size="icon"
              onClick={() => {
                addToCart(product)
                toast("Product has been added to the cart.", {
                  icon: <Check className="h-4 w-4" />,
                })
              }} 
            >
              <ShoppingBag />
            </Button>
          </div>
        </div>
      )}
      {/* <div className="mt-2">Total: {calculateTotal()} €</div> */}
      <Toaster position="top-center" />
            <button disabled={page === 0} onClick={() => setPage(page - 1)}>
            Eelmine
          </button>
          <span>
            {page + 1} / {totalPages}
          </span>
          <button
            disabled={page + 1 === totalPages}
            onClick={() => setPage(page + 1)}
          >
            Järgmine
          </button>
    </div>
  )
}

export default Home