package modelo;

public class Seccion {

    private int id;
    private String nombre;
    private int edad;
    private int capacidad;

    public Seccion() {
    }

    public Seccion(String nombre, int edad, int capacidad) {
        this.nombre = nombre;
        this.edad = edad;
        this.capacidad = capacidad;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public int getCapacidad() { return capacidad; }
    public void setCapacidad(int capacidad) { this.capacidad = capacidad; }
}