package application;

import java.sql.Date;

public class ReporteInventario {
    private int id;
    private String producto;
    private int cantidad;
    private float peso;
    private String estado;
    private float humedad;
    private String ultimoMov;
    private Date fechaMov;
    private String proveedor;

    public ReporteInventario(int id, String producto, int cantidad, float peso, String estado,
                             float humedad, String ultimoMov, Date fechaMov, String proveedor) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.peso = peso;
        this.estado = estado;
        this.humedad = humedad;
        this.ultimoMov = ultimoMov;
        this.fechaMov = fechaMov;
        this.proveedor = proveedor;
    }

    // 🔹 Getters (JavaFX usa reflexión, así que deben llamarse getX)
    public int getId() { return id; }
    public String getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
    public float getPeso() { return peso; }
    public String getEstado() { return estado; }
    public float getHumedad() { return humedad; }
    public String getUltimoMov() { return ultimoMov; }
    public Date getFechaMov() { return fechaMov; }
    public String getProveedor() { return proveedor; }
}
