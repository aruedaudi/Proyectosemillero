package application;

public class MovimientoAlba {
	
	private String producto;
    private String tipo_mov;
    private int cantidad;
    private String fecha;

    // Constructor con parámetros
    public MovimientoAlba(int id, String producto, String tipo_mov, int cantidad, String fecha) {
        this.id = id;
        this.producto = producto;
        this.tipo_mov = tipo_mov;
        this.cantidad = cantidad;
        this.fecha = fecha;
    }

	private int id;
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

	public String getTipo_mov() {
		return tipo_mov;
	}

	public void setTipo_mov(String tipo_mov) {
		this.tipo_mov = tipo_mov;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

}
