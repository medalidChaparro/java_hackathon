package medalid.chaparro.service;

import medalid.chaparro.model.Computadora;
import medalid.chaparro.dao.ComputadoraDAO;

import java.util.List;

/**
 * Capa de servicio (Service)
 *
 * Esta clase se encarga de recibir las solicitudes que vienen desde
 * el Controller y decidir qué operación llamar en el DAO.
 *
 * Su función principal es actuar como un "intermediario" donde podría
 * agregarse lógica de validación, reglas de negocio o filtros.
 */
public class ComputadoraService {

    // Instancia del DAO para acceder a la base de datos
    private ComputadoraDAO dao = new ComputadoraDAO();

    /**
     * Registrar una nueva computadora
     * - Recibe un objeto Computadora desde la vista
     * - Envía los datos al DAO para insertarlos en la base
     */
    public boolean registrar(Computadora c) {
        return dao.insertar(c);
    }

    /**
     * Actualizar una computadora existente
     * - El Controller envía la computadora modificada
     * - El DAO ejecuta el UPDATE
     */
    public boolean actualizar(Computadora c) {
        return dao.actualizar(c);
    }

    /**
     * Listar todas las computadoras activas
     * - Llama al DAO que trae solo las que tienen estado = 'activo'
     */
    public List<Computadora> listar() {
        return dao.listarActivos();
    }

    /**
     * Buscar una computadora por su ID
     * - Útil para cargar datos en la vista cuando se quiere editar
     */
    public Computadora buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    /**
     * Eliminar una computadora (eliminación lógica)
     * - No se borra de la base, solo se cambia su estado a 'inactivo'
     */
    public boolean eliminar(int id) {
        return dao.eliminarLogico(id);
    }
}
