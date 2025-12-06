package model;

import java.time.LocalDate;

public class Factura {
    private int id;
    private int id_consultatie;
    private LocalDate data_emitere;
    private int suma;

    public Factura(int id_consultatie, LocalDate data_emitere, int suma) {
        this.id_consultatie = id_consultatie;
        this.data_emitere = data_emitere;
        this.suma = suma;
    }
    public Factura(int id,int id_consultatie, LocalDate data_emitere, int suma) {
        this(id_consultatie, data_emitere, suma);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_consultatie() {
        return id_consultatie;
    }

    public void setId_consultatie(int id_consultatie) {
        this.id_consultatie = id_consultatie;
    }

    public LocalDate getData_emitere() {
        return data_emitere;
    }

    public void setData_emitere(LocalDate data_emitere) {
        this.data_emitere = data_emitere;
    }

    public int getSuma() {
        return suma;
    }

    public void setSuma(int suma) {
        this.suma = suma;
    }
}
