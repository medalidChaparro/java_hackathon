package medalid.chaparro.controller;

import medalid.chaparro.model.Computadora;
import medalid.chaparro.service.ComputadoraService;

import java.util.List;

public class ComputadoraController {

    // El Controller se comunica con el Service.
    // Aquí creamos una instancia del servicio que maneja la lógica del negocio.
    private ComputadoraService service = new ComputadoraService();

    // ---------------------- MÉTODOS DEL CONTROLLER ----------------------

    // Método para registrar una nueva computadora.
    // Recibe un objeto Computadora que proviene de la Vista (el formulario).
    public boolean registrar(Computadora c) {
        return service.registrar(c); // Llama al Service y devuelve true/false
    }

    // Método para actualizar los datos de una computadora existente.
    public boolean actualizar(Computadora c) {
        return service.actualizar(c);
    }

    // Método para obtener la lista completa desde la BD.
    public List<Computadora> listar() {
        return service.listar();
    }

    // Método que busca una computadora por su ID (clave primaria).
    public Computadora buscarPorId(int id) {
        return service.buscarPorId(id);
    }

    // Método que elimina una computadora de la base de datos según su ID.
    public boolean eliminar(int id) {
        return service.eliminar(id);
    }
}
