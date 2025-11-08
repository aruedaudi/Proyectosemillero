package application;

import javafx.beans.property.*;

public class ReporteAlba {

    private final IntegerProperty idProducto;
    private final StringProperty producto;
    private final IntegerProperty unidades;
    private final StringProperty proveedor;

    public ReporteAlba(int id, String producto, int unidades, String proveedor) {
        this.idProducto = new SimpleIntegerProperty(id);
        this.producto = new SimpleStringProperty(producto);
        this.unidades = new SimpleIntegerProperty(unidades);
        this.proveedor = new SimpleStringProperty(proveedor);
    }

    public int getIdProducto() { return idProducto.get(); }
    public String getProducto() { return producto.get(); }
    public int getUnidades() { return unidades.get(); }
    public String getProveedor() { return proveedor.get(); }

    public IntegerProperty idProductoProperty() { return idProducto; }
    public StringProperty productoProperty() { return producto; }
    public IntegerProperty unidadesProperty() { return unidades; }
    public StringProperty proveedorProperty() { return proveedor; }
}
