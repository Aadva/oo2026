import React, { useEffect, useState } from 'react'
import { Star, X } from "lucide-react"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Button } from '@/components/ui/button'

const PRODUCTS_API_URL = import.meta.env.VITE_BACK_URL + "/products"
const CART_STORAGE_KEY = "cart"

function EditProducts() {
  
  const [products, setProducts] = useState([])

  useEffect(() => {
    fetch(PRODUCTS_API_URL)
      .then((res) => res.json())
      .then((json) => setProducts(json))
  }, [])

  const deleteProduct = (id, index) => {
    setProducts((previousProducts) =>
      previousProducts.filter((_, productIndex) => productIndex !== index)
    )

    const cart = JSON.parse(localStorage.getItem(CART_STORAGE_KEY)) || []
    const syncedCart = cart.filter((cartProduct) => String(cartProduct.id) !== String(id))
    localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(syncedCart))

    fetch(PRODUCTS_API_URL + "/" + id, {
      method: "DELETE",
    })
  }

  return (
    <div className="flex flex-col gap-6 pt-4">
      <h1 className="text-xl font-semibold">Edit products</h1>
      <div className="overflow-hidden rounded-md border">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead></TableHead>
              <TableHead>ID</TableHead>
              <TableHead>Title</TableHead>
              <TableHead>Price</TableHead>
              <TableHead>Category</TableHead>
              <TableHead>Rating</TableHead>
              <TableHead>Image</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {products.map((product, index) => (
              <TableRow key={product.id}>
                <TableCell className="whitespace-normal break-all">
                  <Button onClick={() => deleteProduct(product.id, index)} size="icon" variant="outline" aria-label="Submit">
                    <X />
                  </Button>
                </TableCell>
                <TableCell>{product.id}</TableCell>
                <TableCell className="whitespace-normal break-words">{product.title}</TableCell>
                <TableCell>{product.price}€</TableCell>
                <TableCell className="whitespace-normal break-words">{product.category}</TableCell>
                <TableCell>
                  <div className="inline-flex items-center gap-1 whitespace-nowrap">
                    {product.rating?.rate ?? product.rating}
                    <Star className="h-4 w-4 text-primary" strokeWidth={2.25} />
                    ({product.rating?.count ?? product.count ?? 0})
                  </div>
                </TableCell>
                <TableCell className="whitespace-normal break-all">{product.image}</TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </div>
    </div>
  )
}

export default EditProducts