

import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react"
import { baseUrl, isJsonContentType, processError } from "../utils/RequestUtils"
import { Http } from "../enum/http.method"
import { Product } from "../models/IProduct"

import { Cart } from "../models/ICard"




export const productAPI = createApi({
    reducerPath: 'ProductAPI',
    baseQuery: fetchBaseQuery({baseUrl, credentials: 'include', isJsonContentType}),
    tagTypes: ['Products'],
    endpoints: (builder)=>({
        
        fetchProducts: builder.query<Product[], void>({
            query: ()=>({
                url: `${baseUrl}/api/product`,
                method: Http.GET
            }),
      
            transformErrorResponse: processError,
            
            providesTags: ()=> ['Products']
        }),

 

        fetchProduct: builder.query<Product,  { productId: string  }>({
            query: (productId)=>({
                url: `${baseUrl}/api/product/referenceId/${productId}`,
                method: Http.GET,
             
            }),
            transformErrorResponse: processError,
            providesTags: ()=> ['Products']
           
        }),
        fetchUserCart: builder.query<Cart, void>({
            query: ()=>({
                url: `${baseUrl}/api/cart/myCart`,
                method: Http.GET,
             
            }),
            transformErrorResponse: processError,
            providesTags: ()=> ['Products']
           
        }),
        addProductToCart: builder.mutation<Product, number>({
            query: (productId)=>({
                url: `/api/cart/addproduct/${productId}`,
                method: Http.POST,
             
            }),
            transformErrorResponse: processError,
       
           
        }),
        removeProductToCart: builder.mutation<Product, number>({
            query: (productId)=>({
                url: `/api/cart/removeproduct/${productId}`,
                method: Http.POST,
             
            }),
            transformErrorResponse: processError,
           
           
        }),

        clearToCart: builder.mutation<Product, void>({
            query: ()=>({
                url: `/api/cart/clear-cart`,
                method: Http.POST,
             
            }),
            transformErrorResponse: processError,
           
           
        }),

     
    })
    
})