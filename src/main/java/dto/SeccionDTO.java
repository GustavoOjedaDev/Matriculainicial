package dto;

/**
 * DTO que lleva los datos de una sección junto con sus matriculados y vacantes
 * (datos calculados que no existen como columnas de la tabla seccion).
 */
public class SeccionDTO {

    private int id;
    private String nombre;
    private int edad;
    private int capacidad;
    private int matriculados;

    public SeccionDTO(int id, String nombre, int edad, int capacidad, int matriculados) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.capacidad = capacidad;
        this.matriculados = matriculados;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public int getEdad() { return edad; }
    public int getCapacidad() { return capacidad; }
    public int getMatriculados() { return matriculados; }

    public int getVacantes() {
        return capacidad - matriculados;
    }

    // RN04: la sección está llena cuando llega al máximo de alumnos
    public boolean isLlena() {
        return matriculados >= capacidad;
    }
}