package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Utilizator implements Serializable {

    private int id;
    private String nume;
    private String prenume;
    private String username;
    private String parola;
    private String email;
    private String telefon;
    private LocalDate dataNastere;
    private UtilizatorType tip;

    public Utilizator(String nume, String prenume, String username, String parola, String email, String telefon, LocalDate dataNastere, UtilizatorType tip) {
        this.nume = nume;
        this.prenume = prenume;
        this.username = username;
        this.parola = parola;
        this.email = email;
        this.telefon = telefon;
        this.dataNastere = dataNastere;
        this.tip = tip;
    }

    public Utilizator(int id, String nume, String prenume, String username, String parola, String email, String telefon, LocalDate dataNastere, UtilizatorType tip) {
        this.id = id;
        this.nume = nume;
        this.prenume = prenume;
        this.username = username;
        this.parola = parola;
        this.email = email;
        this.telefon = telefon;
        this.dataNastere = dataNastere;
        this.tip = tip;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public LocalDate getDataNastere() {
        return dataNastere;
    }

    public void setDataNastere(LocalDate dataNastere) {
        this.dataNastere = dataNastere;
    }

    public UtilizatorType getTip() {
        return tip;
    }

    public void setTip(UtilizatorType tip) {
        this.tip = tip;
    }
}