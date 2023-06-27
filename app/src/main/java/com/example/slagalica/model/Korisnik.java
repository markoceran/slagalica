package com.example.slagalica.model;

public class Korisnik {

    private String email;
    private String sifra;
    private String korisnickoIme;



    public Korisnik(String email, String sifra, String korisnickoIme) {

        this.email = email;
        this.sifra = sifra;
        this.korisnickoIme = korisnickoIme;

    }

    public Korisnik() {
        this.email = "";
        this.sifra = "";
        this.korisnickoIme = "";
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getsifra() {
        return sifra;
    }

    public void setsifra(String sifra) {
        this.sifra = sifra;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }


    @Override
    public String toString() {
        return "Korisnik{" +
                "email='" + email + '\'' +
                ", sifra='" + sifra + '\'' +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                '}';
    }
}
