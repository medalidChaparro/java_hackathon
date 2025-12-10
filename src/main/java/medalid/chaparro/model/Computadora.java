package medalid.chaparro.model;

import java.sql.Date;

/**
 * Clase modelo que representa una Computadora dentro del sistema.
 * Funciona como un POJO (contenedor de datos simple).
 */
public class Computadora {

    // Campos que representan las columnas de la tabla "computadoras"
    private int id;
    private String tipoEquipo;
    private String marca;
    private String modelo;
    private String sistemaOperativo;
    private int ram;
    private int almacenamiento;
    private Date fechaMantenimiento;
    private Date fechaRegistro;
    private String estado;

    // 🔥 Nuevo campo agregado para el checkbox de garantía
    private String garantia; // "sí" o "no"

    /**
     * Constructor vacío — útil para crear objetos desde ResultSet o frameworks.
     */
    public Computadora() {}

    // =========================
    //      GETTERS & SETTERS
    // =========================

    // ID
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // Tipo de equipo (Laptop, Desktop, etc.)
    public String getTipoEquipo() { return tipoEquipo; }
    public void setTipoEquipo(String tipoEquipo) { this.tipoEquipo = tipoEquipo; }

    // Marca
    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    // Modelo
    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    // Sistema operativo
    public String getSistemaOperativo() { return sistemaOperativo; }
    public void setSistemaOperativo(String sistemaOperativo) { this.sistemaOperativo = sistemaOperativo; }

    // RAM
    public int getRam() { return ram; }
    public void setRam(int ram) { this.ram = ram; }

    // Almacenamiento
    public int getAlmacenamiento() { return almacenamiento; }
    public void setAlmacenamiento(int almacenamiento) { this.almacenamiento = almacenamiento; }

    // Fecha de mantenimiento
    public Date getFechaMantenimiento() { return fechaMantenimiento; }
    public void setFechaMantenimiento(Date fechaMantenimiento) { this.fechaMantenimiento = fechaMantenimiento; }

    // Fecha de registro
    public Date getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(Date fechaRegistro) { this.fechaRegistro = fechaRegistro; }

    // Estado (activo / inactivo)
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    // 🔥 Nuevo: Garantía (sí / no)
    public String getGarantia() { return garantia; }
    public void setGarantia(String garantia) { this.garantia = garantia; }
}