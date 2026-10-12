package facade;

import dao.MatriculaDAO;
import dao.NinoDAO;
import dao.PadreDAO;
import dao.SeccionDAO;
import dto.MatriculaDTO;
import dto.SeccionDTO;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import modelo.Nino;
import modelo.Padre;
import modelo.Seccion;

/**
 * Patrón Façade: los servlets solo hablan con esta clase.
 * Aquí se aplican las reglas de negocio y se coordinan los DAO.
 */
public class MatriculaFacade {

    // Año escolar para el que se matricula (la edad se calcula al 31 de marzo de ese año)
    public static final int ANIO_ESCOLAR = 2027;

    private final SeccionDAO seccionDAO = new SeccionDAO();
    private final PadreDAO padreDAO = new PadreDAO();
    private final NinoDAO ninoDAO = new NinoDAO();
    private final MatriculaDAO matriculaDAO = new MatriculaDAO();

    // ---------- Consultas ----------
    public List<SeccionDTO> listarSecciones() throws SQLException {
        return seccionDAO.listar(ANIO_ESCOLAR);
    }

    public List<MatriculaDTO> listarMatriculas() throws SQLException {
        return matriculaDAO.listar(ANIO_ESCOLAR);
    }

    public int calcularEdad(LocalDate nacimiento) {
        LocalDate corte = LocalDate.of(ANIO_ESCOLAR, 3, 31);
        if (nacimiento.isAfter(corte)) {
            return 0;
        }
        return Period.between(nacimiento, corte).getYears();
    }

    // RN01: la sección depende de la edad (devuelve null si no hay sección para esa edad)
    public SeccionDTO consultarSeccion(LocalDate nacimiento) throws SQLException {
        int edad = calcularEdad(nacimiento);
        return seccionDAO.buscarPorEdad(edad, ANIO_ESCOLAR);
    }

    // ---------- Registro del padre ----------
    public void registrarPadre(Padre p) throws SQLException, ReglaNegocioException {
        if (p.getNombres().isEmpty() || p.getApellidos().isEmpty()) {
            throw new ReglaNegocioException("Ingresa los nombres y apellidos del padre o apoderado.");
        }
        if (!p.getDni().matches("\\d{8}")) {
            throw new ReglaNegocioException("El DNI debe tener 8 números.");
        }
        if (!p.getTelefono().matches("\\d{7,9}")) {
            throw new ReglaNegocioException("El teléfono debe tener entre 7 y 9 números.");
        }
        if (!p.getCorreo().contains("@")) {
            throw new ReglaNegocioException("Ingresa un correo válido.");
        }
        if (padreDAO.buscarPorDni(p.getDni()) != null) {
            throw new ReglaNegocioException("Ya existe un padre registrado con ese DNI.");
        }
        if (padreDAO.existeCorreo(p.getCorreo())) {
            throw new ReglaNegocioException("Ya existe un padre registrado con ese correo.");
        }
        padreDAO.insertar(p);
    }

    // ---------- Matrícula ----------
    public SeccionDTO matricular(String dniPadre, String nombres, String apellidos, LocalDate nacimiento)
            throws SQLException, ReglaNegocioException {

        if (nombres.isEmpty() || apellidos.isEmpty()) {
            throw new ReglaNegocioException("Ingresa los nombres y apellidos del niño.");
        }
        if (nacimiento.isAfter(LocalDate.now())) {
            throw new ReglaNegocioException("La fecha de nacimiento no puede ser futura.");
        }

        Padre padre = padreDAO.buscarPorDni(dniPadre);
        if (padre == null) {
            throw new ReglaNegocioException("No hay un padre registrado con ese DNI. Regístralo primero.");
        }

        // RN01: solo se puede matricular en la sección que corresponde a su edad
        int edad = calcularEdad(nacimiento);
        SeccionDTO seccion = seccionDAO.buscarPorEdad(edad, ANIO_ESCOLAR);
        if (seccion == null) {
            throw new ReglaNegocioException("No hay sección para un niño de " + edad
                    + " años. El nivel inicial atiende niños de 3, 4 o 5 años (RN01).");
        }

        // RN02: no se matricula si la sección ya está llena
        if (seccion.isLlena()) {
            throw new ReglaNegocioException("El Aula " + seccion.getNombre() + " (" + seccion.getEdad()
                    + " años) está llena. No se puede registrar la matrícula (RN02).");
        }

        // Si el niño ya estaba registrado se reutiliza, si no se registra
        Nino nino = ninoDAO.buscar(padre.getId(), nombres, apellidos, nacimiento);
        int idNino;
        if (nino == null) {
            idNino = ninoDAO.insertar(new Nino(padre.getId(), nombres, apellidos, nacimiento));
        } else {
            idNino = nino.getId();
        }

        // RN03: un niño no puede tener dos matrículas activas en el mismo año
        if (matriculaDAO.existeActiva(idNino, ANIO_ESCOLAR)) {
            throw new ReglaNegocioException("El niño ya tiene una matrícula activa en el año "
                    + ANIO_ESCOLAR + " (RN03).");
        }

        matriculaDAO.insertar(idNino, seccion.getId(), ANIO_ESCOLAR);
        return seccion;
    }

    public void anularMatricula(int id) throws SQLException, ReglaNegocioException {
        if (matriculaDAO.anular(id) == 0) {
            throw new ReglaNegocioException("La matrícula no existe o ya estaba anulada.");
        }
    }

    // ---------- Gestión de secciones (RN06: la usa el personal de secretaría) ----------
    public void crearSeccion(String nombre, int edad, int capacidad) throws SQLException, ReglaNegocioException {
        if (nombre.isEmpty()) {
            throw new ReglaNegocioException("Ingresa el nombre de la sección.");
        }
        if (edad < 3 || edad > 5) {
            throw new ReglaNegocioException("La edad de la sección debe ser 3, 4 o 5 años (RN01).");
        }
        if (capacidad < 1) {
            throw new ReglaNegocioException("La capacidad debe ser mayor que cero.");
        }
        seccionDAO.insertar(new Seccion(nombre, edad, capacidad));
    }

    public void darBajaSeccion(int id) throws SQLException, ReglaNegocioException {
        SeccionDTO s = seccionDAO.buscarPorId(id, ANIO_ESCOLAR);
        if (s == null) {
            throw new ReglaNegocioException("La sección no existe o ya está de baja.");
        }
        if (s.getMatriculados() > 0) {
            throw new ReglaNegocioException("No se puede dar de baja el Aula " + s.getNombre()
                    + " porque tiene " + s.getMatriculados() + " matrículas activas.");
        }
        seccionDAO.darDeBaja(id);
    }
}