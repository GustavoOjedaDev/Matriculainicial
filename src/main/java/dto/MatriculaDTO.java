package dto;

/**
 * DTO para mostrar una matrícula con los datos del niño, el padre y la sección
 * (viene de un JOIN de varias tablas).
 */
public class MatriculaDTO {

    private int id;
    private String nino;
    private String fechaNacimiento;
    private String padre;
    private String dni;
    private String seccion;
    private int edad;
    private String estado;
    private String fechaMatricula;

    public MatriculaDTO(int id, String nino, String fechaNacimiento, String padre, String dni,
            String seccion, int edad, String estado, String fechaMatricula) {
        this.id = id;
        this.nino = nino;
        this.fechaNacimiento = fechaNacimiento;
        this.padre = padre;
        this.dni = dni;
        this.seccion = seccion;
        this.edad = edad;
        this.estado = estado;
        this.fechaMatricula = fechaMatricula;
    }

    public int getId() { return id; }
    public String getNino() { return nino; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getPadre() { return padre; }
    public String getDni() { return dni; }
    public String getSeccion() { return seccion; }
    public int getEdad() { return edad; }
    public String getEstado() { return estado; }
    public String getFechaMatricula() { return fechaMatricula; }

    public boolean isActiva() {
        return "ACTIVA".equals(estado);
    }
}