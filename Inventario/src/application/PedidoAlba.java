package application;

public class PedidoAlba {

	private int idPedido;
	private String idProducto;
    private String idProveedor;
    private String fechaEntrega;
    private int cantidad;
    private String fechaCreacion;
    private String recibido;
    private String observaciones;

    // Constructor con parámetros
    public PedidoAlba(int idPedido, String idProducto, String idProveedor, String fechaEntrega, int cantidad,
    		String fechaCreacion, String recibido, String observaciones) {
        this.idPedido = idPedido;
        this.idProducto = idProducto;
        this.idProveedor = idProveedor;
        this.fechaEntrega = fechaEntrega;
        this.cantidad = cantidad;
        this.fechaCreacion = fechaCreacion;
        this.recibido = recibido;
        this.observaciones = observaciones;
    }
    
    public int getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(int idPedido) {
		this.idPedido = idPedido;
	}

	public String getIdProveedor() {
		return idProveedor;
	}
	
	public String getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(String idProducto) {
		this.idProducto = idProducto;
		
	}

	public void setIdProveedor(String idProveedor) {
		this.idProveedor = idProveedor;
	}

	public String getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(String fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getRecibido() {
		return recibido;
	}

	public void setRecibido(String recibido) {
		this.recibido = recibido;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}