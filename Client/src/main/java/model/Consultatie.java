package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class Consultatie implements Serializable {
    private int id;
    private int id_programare;
    private String diagnostic;
    private List<String> simptome;
    private int cost;
    private LocalDate data_consultatiei;

    public Consultatie(int id_programare, String diagnostic, List<String> simptome, int cost, LocalDate data_consultatiei) {
        this.id_programare = id_programare;
        this.diagnostic = diagnostic;
        this.simptome = simptome;
        this.cost = cost;
        this.data_consultatiei = data_consultatiei;
        this.id=0;
    }
    public Consultatie(int id,int id_programare, String diagnostic, List<String> simptome, int cost, LocalDate data_consultatiei) {
        this(id_programare,diagnostic, simptome, cost, data_consultatiei);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_programare() {
        return id_programare;
    }

    public void setId_programare(int id_programare) {
        this.id_programare = id_programare;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public List<String> getSimptome() {
        return simptome;
    }

    public void setSimptome(List<String> simptome) {
        this.simptome = simptome;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public LocalDate getData_consultatiei() {
        return data_consultatiei;
    }

    public void setData_consultatiei(LocalDate data_consultatiei) {
        this.data_consultatiei = data_consultatiei;
    }
}
