export interface Product {
    id: number;
    referenceId: string;
    image: string;
    name: string;
    price: number;
    availableQuantity:number;

    description: string;
    imageUrls: string[]
    brand: string;
    status?: string;
    rate?: string;
  }