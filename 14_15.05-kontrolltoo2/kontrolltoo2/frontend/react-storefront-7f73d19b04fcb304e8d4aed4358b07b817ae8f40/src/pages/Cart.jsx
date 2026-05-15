import React, { useEffect, useState } from 'react'
import { Button } from '@/components/ui/button'
import { X } from 'lucide-react';

const CART_STORAGE_KEY = "cart"
const PRODUCTS_STORAGE_KEY = "storefront-products"

const getStoredProducts = () => {
  const storedProducts = localStorage.getItem(PRODUCTS_STORAGE_KEY)
  if (!storedProducts) {
    return []
  }

  try {
    return JSON.parse(storedProducts)
  } catch {
    return []
  }
}

const getSyncedCart = () => {
  const cart = JSON.parse(localStorage.getItem(CART_STORAGE_KEY)) || []
  const products = getStoredProducts()

  if (products.length === 0) {
    return cart
  }

  const productIds = new Set(products.map((product) => product.id))
  return cart.filter((cartProduct) => productIds.has(cartProduct.id))
}

function Cart() {
  const [cart, setCart] = useState(getSyncedCart);

  useEffect(() => {
    localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(cart))
  }, [cart])

  const deleteProduct = (index) => {
    cart.splice(index,1);
    setCart(cart.slice());
  }

  const Sum = () => {
    let sum = 0
    cart.forEach((product) => {
      sum = sum + Number(product.price)
    })
    return sum.toFixed(2)
  }


  return (
    <div className="flex flex-col gap-6 pt-4">
      <h1 className="text-xl font-semibold">Cart</h1>
      {cart.length === 0 && <div>No products have been added to the cart.</div>}

      {cart.length > 0 &&
        <div>
          <div>Items in cart: {cart.length} pcs</div>
          <div>Total amount to be paid: {Sum()}€</div>
        </div>
      }
      
      {cart.length > 0 &&
      <div>
        {cart.map((product, index) => 
          <div key={index} className="grid w-full grid-cols-[2rem_100px_minmax(0,1fr)_auto] items-center gap-4 py-8">
            <div className="text-right">{index + 1}.</div>
            <img className="w-[100px] h-[100px] object-cover" src={product.image} alt={product.description} />
            <div className="min-w-0"> 
              <div>{product.title}</div>
              <div>{product.price}€</div>
            </div>
            <div className="justify-self-end">
              <Button onClick={() => deleteProduct(index)} size="icon" variant="outline" aria-label="Submit">
                <X />
              </Button>
            </div>
          </div>
        )}
        <br />
      </div>
      }
    </div>
  )
}

export default Cart