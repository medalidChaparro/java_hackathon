package medalid.chaparro.controller;

import medalid.chaparro.model.Computadora;
import medalid.chaparro.service.ComputadoraService;

import java.util.List;

public class ComputadoraController {
    private ComputadoraService service = new ComputadoraService();

    public boolean registrar(Computadora c) { return service.registrar(c); }
    public boolean actualizar(Computadora c) { return service.actualizar(c); }
    public List<Computadora> listar() { return service.listar(); }
    public Computadora buscarPorId(int id) { return service.buscarPorId(id); }
    public boolean eliminar(int id) { return service.eliminar(id); }
}