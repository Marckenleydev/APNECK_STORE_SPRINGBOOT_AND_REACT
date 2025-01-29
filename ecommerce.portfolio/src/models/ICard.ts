export interface Cart {
    id: number
    reference: string
    amount: number
    totalProduct:number
    customerName: string
    products: CartLine[]
  }
  
  export interface CartLine {
    productId: number
    availableQuantity:number;
    productName: string
    description: string
    price: number
    imageUrl: string
    quantity: number
  }
  