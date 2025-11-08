package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class ReporteController {

    @FXML private ComboBox<String> comboSemana;
    @FXML private ComboBox<String> comboMes;
    @FXML private ComboBox<Integer> comboAnho;
    @FXML private TableView<ReporteAlba> tablaReporte;
    @FXML private TableColumn<ReporteAlba, Integer> colId;
    @FXML private TableColumn<ReporteAlba, String> colProducto;
    @FXML private TableColumn<ReporteAlba, Integer> colUnidades;
    @FXML private TableColumn<ReporteAlba, String> colProveedor;
    @FXML private TextField txtTotalPedidos;
    @FXML private TextField txtProveedorMasSolicitado;
    @FXML private Label lblEstado;
    @FXML private Button btnCargar;
    @FXML private Button btnSalir;
    @FXML private Button btnDescargar;

    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "ALBA_CACAO";
    private final String PASSWORD = "ALBA_CACAO";

    @FXML
    private void initialize() {
        comboSemana.getItems().addAll("Primera", "Segunda", "Tercera", "Cuarta");
        comboMes.getItems().addAll(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        );
        comboAnho.getItems().addAll(2024, 2025, 2026);

        colId.setCellValueFactory(c -> c.getValue().idProductoProperty().asObject());
        colProducto.setCellValueFactory(c -> c.getValue().productoProperty());
        colUnidades.setCellValueFactory(c -> c.getValue().unidadesProperty().asObject());
        colProveedor.setCellValueFactory(c -> c.getValue().proveedorProperty());

        lblEstado.setText("");
    }

    @FXML
    private void btnCargar() {
        String semanaSel = comboSemana.getValue();
        String mesSel = comboMes.getValue();
        Integer anhoSel = comboAnho.getValue();

        if (semanaSel == null || mesSel == null || anhoSel == null) {
            lblEstado.setText("Selecciona semana, mes y año.");
            return;
        }

        // Convertir mes a número
        int mesNum = switch (mesSel) {
            case "Enero" -> 1; case "Febrero" -> 2; case "Marzo" -> 3;
            case "Abril" -> 4; case "Mayo" -> 5; case "Junio" -> 6;
            case "Julio" -> 7; case "Agosto" -> 8; case "Septiembre" -> 9;
            case "Octubre" -> 10; case "Noviembre" -> 11; case "Diciembre" -> 12;
            default -> 1;
        };

        LocalDate primerDiaMes = LocalDate.of(anhoSel, mesNum, 1);
        LocalDate inicioSemana, finSemana;

        switch (semanaSel) {
            case "Primera" -> { inicioSemana = primerDiaMes; finSemana = inicioSemana.plusDays(6); }
            case "Segunda" -> { inicioSemana = primerDiaMes.plusDays(7); finSemana = inicioSemana.plusDays(6); }
            case "Tercera" -> { inicioSemana = primerDiaMes.plusDays(14); finSemana = inicioSemana.plusDays(6); }
            case "Cuarta" -> {
                inicioSemana = primerDiaMes.plusDays(21);
                finSemana = inicioSemana.plusDays(6);
                if (finSemana.getMonthValue() != mesNum)
                    finSemana = primerDiaMes.withDayOfMonth(primerDiaMes.lengthOfMonth());
            }
            default -> { lblEstado.setText("Semana no válida."); return; }
        }

        lblEstado.setText("Mostrando pedidos del " + inicioSemana + " al " + finSemana);

        ObservableList<ReporteAlba> lista = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {

            // TOTAL DE PEDIDOS
        	String sqlTotal = """
        		    SELECT SUM(TOTAL_PEDIDOS) AS TOTAL_PEDIDOS
        		    FROM V_TOTAL_PEDIDOS
        		    WHERE DIA BETWEEN ? AND ?
        		""";

        		try (PreparedStatement psTotal = conn.prepareStatement(sqlTotal)) {
        		    psTotal.setDate(1, java.sql.Date.valueOf(inicioSemana));
        		    psTotal.setDate(2, java.sql.Date.valueOf(finSemana));

        		    try (ResultSet rsTotal = psTotal.executeQuery()) {
        		        if (rsTotal.next()) {
        		            txtTotalPedidos.setText(String.valueOf(rsTotal.getInt("TOTAL_PEDIDOS")));
        		        } else {
        		            txtTotalPedidos.setText("0");
        		        }
        		    }
        		}

        		// PROVEEDOR MÁS SOLICITADO
        		String sqlProv = """
        			    SELECT PROVEEDOR, TOTAL_VECES
        			    FROM (
        			        SELECT PROVEEDOR, SUM(VECES) AS TOTAL_VECES
        			        FROM V_PROVEEDOR_SOLICITADO
        			        WHERE DIA BETWEEN ? AND ?
        			        GROUP BY PROVEEDOR
        			        ORDER BY SUM(VECES) DESC
        			    )
        			    WHERE ROWNUM = 1
        			""";

        		try (PreparedStatement psProv = conn.prepareStatement(sqlProv)) {
        		    psProv.setDate(1, java.sql.Date.valueOf(inicioSemana));
        		    psProv.setDate(2, java.sql.Date.valueOf(finSemana));

        		    try (ResultSet rsProv = psProv.executeQuery()) {
        		        if (rsProv.next()) {
        		            txtProveedorMasSolicitado.setText(rsProv.getString("PROVEEDOR"));
        		        } else {
        		            txtProveedorMasSolicitado.setText("N/A");
        		        }
        		    }
        		}

        		// DATOS PARA LA TABLA
        		String sqlTabla = """
        			    SELECT
        			        p.ID_PRODUCTO,
        			        p.DESCRIPCION AS PRODUCTO,
        			        SUM(pe.CANTIDAD) AS UNIDADES_PEDIDAS,
        			        pvf.NOMBRE AS PROVEEDOR_FRECUENTE
        			    FROM PRODUCTO p
        			    JOIN PEDIDO pe ON pe.ID_PRODUCTO = p.ID_PRODUCTO
        			    JOIN (
        			        SELECT ID_PRODUCTO, NOMBRE
        			        FROM (
        			            SELECT
        			                pe2.ID_PRODUCTO,
        			                pv.NOMBRE,
        			                COUNT(*) AS FRECUENCIA,
        			                ROW_NUMBER() OVER (
        			                    PARTITION BY pe2.ID_PRODUCTO
        			                    ORDER BY COUNT(*) DESC
        			                ) AS RN
        			            FROM PEDIDO pe2
        			            JOIN PROVEEDOR pv ON pv.ID_PROVEEDOR = pe2.ID_PROVEEDOR
        			            WHERE pe2.FECHA_CREACION BETWEEN ? AND ?
        			            GROUP BY pe2.ID_PRODUCTO, pv.NOMBRE
        			        )
        			        WHERE RN = 1
        			    ) pvf ON pvf.ID_PRODUCTO = p.ID_PRODUCTO
        			    WHERE pe.FECHA_CREACION BETWEEN ? AND ?
        			    GROUP BY p.ID_PRODUCTO, p.DESCRIPCION, pvf.NOMBRE
        			    ORDER BY p.ID_PRODUCTO
        			""";

        			PreparedStatement psTabla = conn.prepareStatement(sqlTabla);
        			psTabla.setDate(1, java.sql.Date.valueOf(inicioSemana));
        			psTabla.setDate(2, java.sql.Date.valueOf(finSemana));
        			psTabla.setDate(3, java.sql.Date.valueOf(inicioSemana));
        			psTabla.setDate(4, java.sql.Date.valueOf(finSemana));

        			ResultSet rsTabla = psTabla.executeQuery();

        			ObservableList<ReporteAlba> lista1 = FXCollections.observableArrayList();

        			while (rsTabla.next()) {
        			    lista1.add(new ReporteAlba(
        			        rsTabla.getInt("ID_PRODUCTO"),
        			        rsTabla.getString("PRODUCTO"),
        			        rsTabla.getInt("UNIDADES_PEDIDAS"),
        			        rsTabla.getString("PROVEEDOR_FRECUENTE")
        			    ));
        			}

        			tablaReporte.setItems(lista1);

        } catch (SQLException e) {
            lblEstado.setText("Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void btnSalir (ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuPrincipal.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Menú Principal");
        stage.show();
    }

    @FXML
    private void btnDescargar() {
    	if (tablaReporte.getItems().isEmpty()) {
            lblEstado.setText("No hay datos para exportar.");
            return;
        }

        // Buscar impresora PDF disponible (por ejemplo, "Microsoft Print to PDF")
        javafx.print.Printer pdfPrinter = javafx.print.Printer.getAllPrinters().stream()
                .filter(p -> p.getName().toLowerCase().contains("pdf"))
                .findFirst()
                .orElse(null);

        if (pdfPrinter == null) {
            lblEstado.setText("No se encontró una impresora PDF instalada.");
            return;
        }

        // Crear el trabajo de impresión
        javafx.print.PrinterJob job = javafx.print.PrinterJob.createPrinterJob(pdfPrinter);
        if (job == null) {
            lblEstado.setText("No se pudo crear el trabajo de impresión.");
            return;
        }

        // Crear un PageLayout en orientación HORIZONTAL
        javafx.print.PageLayout pageLayout = pdfPrinter.createPageLayout(
                javafx.print.Paper.A3,
                javafx.print.PageOrientation.LANDSCAPE, // ← modo apaisado
                javafx.print.Printer.MarginType.DEFAULT
        );

        // Intentar imprimir la tabla
        boolean success = job.printPage(pageLayout, tablaReporte);
        if (success) {
            job.endJob();
            lblEstado.setText("Reporte enviado a impresora PDF en formato horizontal.");
        } else {
            lblEstado.setText("Error al generar PDF.");
        }
    }
}
