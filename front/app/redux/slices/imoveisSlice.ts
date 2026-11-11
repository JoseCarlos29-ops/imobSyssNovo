import { Imovel } from "@/app/types/imovel";
import { createSlice, PayloadAction } from "@reduxjs/toolkit";
import { logout } from "./authSlice";

interface ImoveisState {
    lista: Imovel[]
    filtroStatus: string
}

const initialState: ImoveisState = {
    lista: [],
    filtroStatus: "TODOS"
}

const imoveisSlice = createSlice({
    name: 'imoveis',
    initialState,
    reducers: {
        setImoveis: (state, action: PayloadAction<Imovel[]>) => {
            state.lista = action.payload;
        },
        setFiltroStatus: (state, action: PayloadAction<string>) => {
            state.filtroStatus = action.payload;
        }
    },
    extraReducers: (builder) => {
        // ao sair (botao Sair ou 401) limpa os imoveis da empresa anterior
        builder.addCase(logout, () => initialState);
    }
});

export const { setImoveis, setFiltroStatus } = imoveisSlice.actions;
export default imoveisSlice.reducer;
