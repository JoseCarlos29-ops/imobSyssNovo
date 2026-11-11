import { configureStore } from "@reduxjs/toolkit";
import authReducer from "../redux/slices/authSlice"
import imoveisReducer from "../redux/slices/imoveisSlice"

export const store = configureStore({
    reducer:{
        auth : authReducer,
        imoveis : imoveisReducer
    }
});

export type RootState = ReturnType<typeof store.getState >;
export type AppDispath = typeof store.dispatch;