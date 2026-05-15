import React, { useEffect, useState } from "react"
import { Link, Navigate, useParams } from "react-router-dom"
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table"
import { Button } from "@/components/ui/button"
import { ArrowLeft } from "lucide-react"

const PRODUCTS_API_URL = import.meta.env.VITE_BACK_URL + "/products"

function Product() {
  const { id } = useParams()
  const [product, setProduct] = useState(null)
  const [isLoaded, setIsLoaded] = useState(false)

  useEffect(() => {
    fetch(`${PRODUCTS_API_URL}/${id}`)
      .then((res) => res.json())
      .then((data) => setProduct(data))
      .finally(() => setIsLoaded(true))
  }, [id])

  if (isLoaded && (!product || !product.id)) {
    return <Navigate to="/notfound" replace />
  }

  if (!product) {
    return null
  }

  return (
    <div className="flex flex-col gap-4 pt-4">
      <h1 className="text-xl font-semibold">{product.title}</h1>
      <Table>
        <TableHeader>
          <TableRow>
            <TableHead>ID</TableHead>
            <TableHead>Image</TableHead>
            <TableHead>Title</TableHead>
            <TableHead>Category</TableHead>
            <TableHead>Price</TableHead>
            <TableHead>Active</TableHead>
            <TableHead>Description</TableHead>
          </TableRow>
        </TableHeader>
        <TableBody>
          <TableRow>
            <TableCell className="whitespace-normal break-all">
              {product.id}
            </TableCell>
            <TableCell>
              <img
                className="w-[240px] h-[240px] object-cover"
                src={product.image}
                alt={product.description}
              />
            </TableCell>
            <TableCell className="whitespace-normal break-words">
              {product.title}
            </TableCell>
            <TableCell>
              {product.category}
            </TableCell>
            <TableCell className="whitespace-normal break-words">
              {product.price}€
            </TableCell>
            <TableCell>
              {String(product.active)}
            </TableCell>
            <TableCell className="whitespace-normal break-all">
              {product.description}
            </TableCell>
          </TableRow>
        </TableBody>
      </Table>
      <Button asChild className="w-fit">
        <Link to="/"><ArrowLeft />Back home</Link>
      </Button>
    </div>
  )
}

export default Product