package modelo;

import java.time.LocalDate;

public class Nino {

    private int id;
    private int idPadre;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;

    public Nino() {
    }

    public Nino(int idPadre, String nombres, String apellidos, LocalDate fechaNacimiento) {
        this.idPadre = idPadre;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getIdPadre() { return idPadre; }
    public void setIdPadre(int idPadre) { this.idPadre = idPadre; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
}