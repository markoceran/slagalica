package com.example.slagalica.model;

public class Korisnik {

    private String email;
    private String sifra;
    private String korisnickoIme;
    private long zvezde;
    private long tokeni;
    private long bodoviKoZnaZna;
    private long bodoviKorakPoKorak;
    private long bodoviAsocijacije;
    private long bodoviMojBroj;
    private long bodoviSkocko;
    private long bodoviSpojnice;
    private long pobedjenePartije;
    private long izgubljenePartije;


    public Korisnik() {

        this.email = "";
        this.sifra = "";
        this.korisnickoIme = "";
        this.zvezde = 0;
        this.tokeni = 0;
        this.bodoviKoZnaZna = 0;
        this.bodoviKorakPoKorak = 0;
        this.bodoviAsocijacije = 0;
        this.bodoviMojBroj = 0;
        this.bodoviSkocko = 0;
        this.bodoviSpojnice = 0;
        this.pobedjenePartije = 0;
        this.izgubljenePartije = 0;
    }

    public Korisnik(String email, String sifra, String korisnickoIme, long zvezde, long tokeni, long bodoviKoZnaZna, long bodoviKorakPoKorak, long bodoviAsocijacije, long bodoviMojBroj, long bodoviSkocko, long bodoviSpojnice, long pobedjenePartije, long izgubljenePartije) {
        this.email = email;
        this.sifra = sifra;
        this.korisnickoIme = korisnickoIme;
        this.zvezde = zvezde;
        this.tokeni = tokeni;
        this.bodoviKoZnaZna = bodoviKoZnaZna;
        this.bodoviKorakPoKorak = bodoviKorakPoKorak;
        this.bodoviAsocijacije = bodoviAsocijacije;
        this.bodoviMojBroj = bodoviMojBroj;
        this.bodoviSkocko = bodoviSkocko;
        this.bodoviSpojnice = bodoviSpojnice;
        this.pobedjenePartije = pobedjenePartije;
        this.izgubljenePartije = izgubljenePartije;
    }

    public Korisnik(String email, String sifra, String korisnickoIme) {
        this.email = email;
        this.sifra = sifra;
        this.korisnickoIme = korisnickoIme;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    public String getKorisnickoIme() {
        return korisnickoIme;
    }

    public void setKorisnickoIme(String korisnickoIme) {
        this.korisnickoIme = korisnickoIme;
    }

    public long getZvezde() {
        return zvezde;
    }

    public void setZvezde(long zvezde) {
        this.zvezde = zvezde;
    }

    public long getTokeni() {
        return tokeni;
    }

    public void setTokeni(long tokeni) {
        this.tokeni = tokeni;
    }

    public long getBodoviKoZnaZna() {
        return bodoviKoZnaZna;
    }

    public void setBodoviKoZnaZna(long bodoviKoZnaZna) {
        this.bodoviKoZnaZna = bodoviKoZnaZna;
    }

    public long getBodoviKorakPoKorak() {
        return bodoviKorakPoKorak;
    }

    public void setBodoviKorakPoKorak(long bodoviKorakPoKorak) {
        this.bodoviKorakPoKorak = bodoviKorakPoKorak;
    }

    public long getBodoviAsocijacije() {
        return bodoviAsocijacije;
    }

    public void setBodoviAsocijacije(long bodoviAsocijacije) {
        this.bodoviAsocijacije = bodoviAsocijacije;
    }

    public long getBodoviMojBroj() {
        return bodoviMojBroj;
    }

    public void setBodoviMojBroj(long bodoviMojBroj) {
        this.bodoviMojBroj = bodoviMojBroj;
    }

    public long getBodoviSkocko() {
        return bodoviSkocko;
    }

    public void setBodoviSkocko(long bodoviSkocko) {
        this.bodoviSkocko = bodoviSkocko;
    }

    public long getBodoviSpojnice() {
        return bodoviSpojnice;
    }

    public void setBodoviSpojnice(long bodoviSpojnice) {
        this.bodoviSpojnice = bodoviSpojnice;
    }

    public long getPobedjenePartije() {
        return pobedjenePartije;
    }

    public void setPobedjenePartije(long pobedjenePartije) {
        this.pobedjenePartije = pobedjenePartije;
    }

    public long getIzgubljenePartije() {
        return izgubljenePartije;
    }

    public void setIzgubljenePartije(long izgubljenePartije) {
        this.izgubljenePartije = izgubljenePartije;
    }
}
