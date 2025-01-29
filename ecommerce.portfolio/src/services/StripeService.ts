

import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react"
import { baseUrl, isJsonContentType, processError } from "../utils/RequestUtils"
import { Http } from "../enum/http.method"
import { IStripe } from "../models/IStripe"




export const stripeAPI = createApi({
    reducerPath: 'StripeAPI',
    baseQuery: fetchBaseQuery({baseUrl, credentials: 'include', isJsonContentType}),
    tagTypes: ['Stripe'],
    endpoints: (builder)=>({
        // eslint-disable-next-line @typescript-eslint/ban-ts-comment
        // @ts-expect-error

        ProceedCheckout: builder.mutation<IStripe>({
            query: ()=>({
                url: `/api/order/checkout`,
                method: Http.POST,
             
            }),
            transformErrorResponse: processError,
            providesTags: ()=> ['Stripe']
           
        }),
       

     
    })
    
})