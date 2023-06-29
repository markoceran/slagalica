package com.example.slagalica.model;

public class Korisnik1 {
    private String email;
    private String sifra;
    private String korisnickoIme;
    private String profilePicture;



    public Korisnik1(String korisnickoIme, String email, String sifra, String profilePicture) {

        this.email = email;
        this.sifra = sifra;
        this.korisnickoIme = korisnickoIme;
        this.profilePicture = profilePicture;

    }

    public Korisnik1() {
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
    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
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