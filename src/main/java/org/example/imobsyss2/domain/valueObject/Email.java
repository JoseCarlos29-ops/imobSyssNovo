package org.example.imobsyss2.domain.valueObject;

import jakarta.persistence.Embeddable;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class Email {

    private static final Pattern PADRAO =
            Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*\\.[A-Za-z]{2,}$");

    private String email;

    public Email(){
        this.email = "";
    }

    public Email(String email){

        if(!isValidEmail(email)){
            throw new IllegalArgumentException("Email Invalido!");
        }

        this.email = email.trim().toLowerCase();
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return PADRAO.matcher(email.trim()).matches();
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Email outro)) return false;
        return Objects.equals(email, outro.email);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(email);
    }

    @Override
    public String toString(){
        return email;
    }
}