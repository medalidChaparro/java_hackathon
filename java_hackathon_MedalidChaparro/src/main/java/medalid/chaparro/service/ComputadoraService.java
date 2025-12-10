package medalid.chaparro.service;

import medalid.chaparro.model.Computadora;
import medalid.chaparro.dao.ComputadoraDAO;

import java.util.List;

public class ComputadoraService {
    private ComputadoraDAO dao = new ComputadoraDAO();

    public boolean registrar(Computadora c) { return dao.insertar(c); }
    public boolean actualizar(Computadora c) { return dao.actualizar(c); }
    public List<Computadora> listar() { return dao.listarActivos(); }
    public Computadora buscarPorId(int id) { return dao.buscarPorId(id); }
    public boolean eliminar(int id) { return dao.eliminarLogico(id); }
}