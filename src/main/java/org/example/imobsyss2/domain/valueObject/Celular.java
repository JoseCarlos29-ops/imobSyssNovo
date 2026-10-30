package org.example.imobsyss2.domain.valueObject;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public class Celular {

    private String celular;

    public Celular(){
        this.celular = "";
    }

    public Celular(String celular){

        if(!isValidCelular(celular)){
            throw new IllegalArgumentException("Celular Invalido!");
        }

        this.celular = normalizar(celular);
    }

    // Deixa so os digitos e tira o 55 do pais, se vier
    private static String normalizar(String valor) {
        String digitos = valor.replaceAll("\\D", "");
        if ((digitos.length() == 12 || digitos.length() == 13) && digitos.startsWith("55")) {
            digitos = digitos.substring(2);
        }
        return digitos;
    }

    // DDD (2 digitos, sem zero) + 9 + 8 digitos
    public static boolean isValidCelular(String celular) {
        if (celular == null) {
            return false;
        }
        return normalizar(celular).matches("^[1-9][1-9]9\\d{8}$");
    }

    public String getNumero() {
        return celular;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Celular outro)) return false;
        return Objects.equals(celular, outro.celular);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(celular);
    }

    @Override
    public String toString(){
        String digitos = celular == null ? "" : celular.replaceAll("\\D", "");
        return digitos.replaceAll("^(\\d{2})(\\d{5})(\\d{4})$", "($1) $2-$3");
    }
}