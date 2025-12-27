package model;

import java.io.Serializable;
import java.util.List;

public class Prescriptie implements Serializable {
    private int id;
    private int id_consultatie;
    private List<String> medicament;
    private int doza_zilnica;
    private int durata_tratament_in_zile;

    public Prescriptie(int id_consultatie, List<String> medicament, int doza_zilnica, int durata_tratament_in_zile) {
        this.id_consultatie = id_consultatie;
        this.medicament = medicament;
        this.doza_zilnica = doza_zilnica;
        this.durata_tratament_in_zile = durata_tratament_in_zile;
    }
    public Prescriptie(int id,int id_consultatie, List<String> medicament, int doza_zilnica, int durata_tratament_in_zile) {
        this(id_consultatie,medicament,doza_zilnica,durata_tratament_in_zile);
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

    public List<String> getMedicament() {
        return medicament;
    }

    public void setMedicament(List<String> medicament) {
        this.medicament = medicament;
    }

    public int getDoza_zilnica() {
        return doza_zilnica;
    }

    public void setDoza_zilnica(int doza_zilnica) {
        this.doza_zilnica = doza_zilnica;
    }

    public int getDurata_tratament_in_zile() {
        return durata_tratament_in_zile;
    }

    public void setDurata_tratament_in_zile(int durata_tratament_in_zile) {
        this.durata_tratament_in_zile = durata_tratament_in_zile;
    }
}
