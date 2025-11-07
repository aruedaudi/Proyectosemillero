package application;

public class ReporteInventario {
    private String tipoProducto;
    private int totalPedidos;
    private int cantidad;
    private double peso;

    public ReporteInventario(String tipoProducto, int totalPedidos, int cantidad, double peso) {
        this.tipoProducto = tipoProducto;
        this.totalPedidos = totalPedidos;
        this.cantidad = cantidad;
        this.peso = peso;
    }

    public String getTipoProducto() { return tipoProducto; }
    public int getTotalPedidos() { return totalPedidos; }
    public int getCantidad() { return cantidad; }
    public double getPeso() { return peso; }
}
