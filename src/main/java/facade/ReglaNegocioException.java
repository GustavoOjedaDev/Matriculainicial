package facade;

/** Se lanza cuando no se cumple una regla de negocio (RN01 a RN06). */
public class ReglaNegocioException extends Exception {

    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}