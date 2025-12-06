package model;

import java.time.LocalDate;

public class Programare {
    private int id;
    private int id_doctor;
    private int id_pacient;
    private LocalDate data_programarii;
    private StatusProgramare status;

    public Programare(int id_doctor, int id_pacient, LocalDate data_programarii, StatusProgramare status) {
        this.id_doctor = id_doctor;
        this.id_pacient = id_pacient;
        this.data_programarii = data_programarii;
        this.status = status;
    }
    public Programare(int id,int id_doctor, int id_pacient, LocalDate data_programarii, StatusProgramare status) {
        this(id_doctor,id_pacient,data_programarii,status);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(int id_doctor) {
        this.id_doctor = id_doctor;
    }

    public int getId_pacient() {
        return id_pacient;
    }

    public void setId_pacient(int id_pacient) {
        this.id_pacient = id_pacient;
    }

    public LocalDate getData_programarii() {
        return data_programarii;
    }

    public void setData_programarii(LocalDate data_programarii) {
        this.data_programarii = data_programarii;
    }

    public StatusProgramare getStatus() {
        return status;
    }

    public void setStatus(StatusProgramare status) {
        this.status = status;
    }
}
