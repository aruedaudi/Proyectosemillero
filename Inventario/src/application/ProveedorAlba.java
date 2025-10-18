package application;

import java.math.BigDecimal;

public class ProveedorAlba {

	private BigDecimal idProveedor;
    private String nombre;
    private String direccion;
    private BigDecimal telefono;
    private String correo;
    private String ciudad;
    private String vereda;
    private String observaciones;
    private String activo;

    // Constructor con parámetros
    public ProveedorAlba(BigDecimal idProveedor, String nombre, String direccion, BigDecimal telefono, String correo,
                         String ciudad, String vereda, String observaciones, String activo) {
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.ciudad = ciudad;
        this.vereda = vereda;
        this.observaciones = observaciones;
        this.activo = activo;
    }

    // Constructor vacío (útil para JavaFX o frameworks)
    public ProveedorAlba() {
    }

    // Getters y Setters
    public BigDecimal getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(BigDecimal idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public BigDecimal getTelefono() {
        return telefono;
    }

    public void setTelefono(BigDecimal telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getVereda() {
        return vereda;
    }

    public void setVereda(String vereda) {
        this.vereda = vereda;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getActivo() {
        return activo;
    }

    public void setActivo(String activo) {
        this.activo = activo;
    }
}
