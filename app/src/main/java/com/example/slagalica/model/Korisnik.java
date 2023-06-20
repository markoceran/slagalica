package com.example.slagalica.model;

public class Korisnik {

    private String ime;
    private String prezime;
    private String email;
    private String lozinka;
    private String korisnickoIme;
    private String imageUrl;


    public Korisnik(String ime, String prezime, String email, String lozinka, String korisnickoIme, String imageUrl) {
        this.ime = ime;
        this.prezime = prezime;
        this.email = email;
        this.lozinka = lozinka;
        this.korisnickoIme = korisnickoIme;
        this.imageUrl = imageUrl;
    }

    public Korisnik() {
        this.ime = "";
        this.prezime = "";
        this.email = "";
        this.lozinka = "";
        this.korisnickoIme = "";
        this.imageUrl = "";
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLozinka() {
        return lozinka;
    }

    public void setLozinka(String lozinka) {
        this.lozinka = lozinka;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Korisnik{" +
                "ime='" + ime + '\'' +
                ", prezime='" + prezime + '\'' +
                ", email='" + email + '\'' +
                ", lozinka='" + lozinka + '\'' +
                ", korisnickoIme='" + korisnickoIme + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
