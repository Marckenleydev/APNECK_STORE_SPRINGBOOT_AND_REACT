


import { combineReducers, configureStore } from "@reduxjs/toolkit";
import logger from "redux-logger"
import { productAPI } from '../services/ProductService';
import { userAPI } from "../services/UserService";
import { stripeAPI } from "../services/StripeService";


const rootReducer = combineReducers({
    [productAPI.reducerPath]: productAPI.reducer,
    [userAPI.reducerPath]: userAPI.reducer,
    [stripeAPI.reducerPath]: stripeAPI.reducer,

});

export const setupStore = ()=>{
    return configureStore({
        reducer: rootReducer,
        middleware: (getDefaultMiddleware)=>
            getDefaultMiddleware({serializableCheck: false})
        .concat(productAPI.middleware)
        .concat(userAPI.middleware)
        .concat(stripeAPI.middleware)
        .concat(logger)

    })
}

export type RootState = ReturnType<typeof rootReducer>;
export type AppStore = ReturnType<typeof setupStore>;
export type AppDispatch = AppStore['dispatch'];