import {createApi, fetchBaseQuery} from "@reduxjs/toolkit/query/react"

import { IResponse } from "../models/IResponse";

import { EmailAddress, IUserRequest, IRegisterRequest, UpdateNewPassword, UpdatePassword } from "../models/ICredentials";
import { Http } from "../enum/http.method";
import { baseUrl, isJsonContentType, processError, processResponse } from "../utils/RequestUtils";
import { User } from "../models/IUser";


export const userAPI = createApi({
    reducerPath: 'userAPI',
    baseQuery: fetchBaseQuery({baseUrl, credentials: 'include', isJsonContentType}),
    tagTypes: ['User'],
    endpoints: (builder)=>({
        fetchUser: builder.query<IResponse<User>, void>({
            query: ()=>({
                url: 'user/profile',
                method: Http.GET
            }),
            keepUnusedDataFor:120,
            transformResponse: processResponse<User>,
            // transformErrorResponse: processError,
            providesTags: ()=> ['User']
        }),

        registerUser: builder.mutation<IResponse<void>, IRegisterRequest>({
            query: (registerRequest)=>({
                url: 'user/register',
                method: Http.POST,
                body: registerRequest
            }),
            transformResponse:processResponse<void>,
            transformErrorResponse: processError,
           
        }),



        verifyAccount: builder.mutation<IResponse<void>, string>({
            query: (key)=>({
                url: `user/verify/account?key=${key}`,
                method: Http.GET,
             
            }),
            transformResponse: processResponse<void>,
            transformErrorResponse: processError,
           
        }),

        verifyPassword: builder.mutation<IResponse<User>, string>({
            query: (key)=>({
                url: `user/verify/password?key=${key}`,
                method: Http.GET,
             
            }),
            transformResponse: processResponse<User>,
            transformErrorResponse: processError,
           
        }),

        loginUser: builder.mutation<IResponse<User>, IUserRequest>({
            query: (credentials)=>({
                url: '/user/login',
                method: Http.POST,
                body: credentials
            }),
            transformResponse: processResponse<User>,
            transformErrorResponse: processError,
           
        }),
        logoutUser: builder.mutation<IResponse<void>, void>({
            query: (credentials)=>({
                url: '/user/logout',
                method: Http.POST,
                body: credentials
            }),

        }),
        resetPassword: builder.mutation<IResponse<void>, EmailAddress>({
            query: (email)=>({
                url: `user/resetpassword`,
                method: Http.POST,
                body: email
             
            }),
            transformResponse:processResponse<void>,
            transformErrorResponse: processError,
            invalidatesTags: ( error) => error ? [] : ['User'] 
           
        }),

        doResetPassword: builder.mutation<IResponse<void>, UpdateNewPassword>({
            query: (passwordRequest)=>({
                url: `user/resetpassword/reset`,
                method: Http.POST,
                body: passwordRequest
             
            }),
            transformResponse: processResponse<void>,
            transformErrorResponse: processError,
            invalidatesTags: (error) => error ? [] : ['User']
           
        }),
        updatePhoto: builder.mutation<IResponse<string>, FormData>({
            query: (form)=>({
                url: `users/Users/photo`,
                method: Http.PATCH,
                body: form
             
            }),
            transformResponse: processResponse<string>,
            transformErrorResponse: processError,
            invalidatesTags: ( error) => error ? [] : ['User']
           
        }),
        
        updatePassword: builder.mutation<IResponse<void>, UpdatePassword>({
            query: (request)=>({
                url: `user/update_password`,
                method: Http.PATCH,
                body: request
             
            }),
            transformResponse: processResponse<void>,
            transformErrorResponse: processError,
            invalidatesTags: ( error) => error ? [] : ['User']
           
        }),


        

    

     
    })
    
})

