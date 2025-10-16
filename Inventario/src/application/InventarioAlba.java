package application;

public class InventarioAlba {
	private int id;
    private String producto;
    private int cantidad;
    private float peso;
    private String estado;
    private float humedad;
    private String fechaIngreso;
    private String proveedor;
    
 // Constructor con parámetros
    public InventarioAlba(int id, String producto, int cantidad, float peso, String estado, float humedad, String fechaIngreso, String proveedor) {
        this.id = id;
        this.producto = producto;
        this.cantidad = cantidad;
        this.peso = peso;
        this.estado = estado;
        this.humedad = humedad;
        this.fechaIngreso = fechaIngreso;
        this.proveedor = proveedor;
    }

    public InventarioAlba(String string) {
		// TODO Auto-generated constructor stub
	}

	// Getters y setters (necesarios para TableView)
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProducto() {
		return producto;
	}
	public void setProducto(String producto) {
		this.producto = producto;
	}
	public int getCantidad() {
		return cantidad;
	}
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public float getPeso() {
		return peso;
	}
	public void setPeso(float peso) {
		this.peso = peso;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public float getHumedad() {
		return humedad;
	}
	public void setHumedad(float humedad) {
		this.humedad = humedad;
	}
	public String getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public String getProveedor() {
		return proveedor;
	}
	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}



}
