package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;

public class ReporteInventarioController {

    @FXML private ComboBox<String> comboSemana;
    @FXML private ComboBox<String> comboMes;
    @FXML private ComboBox<Integer> comboAnho;
    @FXML private TableView<ReporteInventario> tablaReporte;
    @FXML private TableColumn<ReporteInventario, String> colTipoProd;
    @FXML private TableColumn<ReporteInventario, Integer> colTotalPedidos;
    @FXML private TableColumn<ReporteInventario, Integer> colCantidad;
    @FXML private TableColumn<ReporteInventario, Double> colPesoTotal;
    @FXML private Label lblEstado;
    @FXML private Button btnCargar;
    @FXML private Button btnSalir;
    @FXML private Button btnDescargar;

    private final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
    private final String USER = "ALBA_CACAO";
    private final String PASSWORD = "ALBA_CACAO";

    @FXML
    private void initialize() {
        // Semanas
        comboSemana.getItems().addAll("Primera", "Segunda", "Tercera", "Cuarta");
        // Meses
        comboMes.getItems().addAll(
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
        );
        // Años
        comboAnho.getItems().addAll(2024, 2025, 2026);

        // Configurar columnas
        colTipoProd.setCellValueFactory(new PropertyValueFactory<>("tipoProducto"));
        colTotalPedidos.setCellValueFactory(new PropertyValueFactory<>("totalPedidos"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colPesoTotal.setCellValueFactory(new PropertyValueFactory<>("peso"));

        lblEstado.setText("");
    }

    @FXML
    private void btnCargar() {
        String semanaSel = comboSemana.getValue();
        String mesSel = comboMes.getValue();
        Integer anhoSel = comboAnho.getValue();

        if (semanaSel == null || mesSel == null || anhoSel == null) {
            lblEstado.setText("Te faltan datos: selecciona semana, mes y año.");
            return;
        }

        // Convertir mes textual a número
        int mesNum = switch (mesSel) {
            case "Enero" -> 1;
            case "Febrero" -> 2;
            case "Marzo" -> 3;
            case "Abril" -> 4;
            case "Mayo" -> 5;
            case "Junio" -> 6;
            case "Julio" -> 7;
            case "Agosto" -> 8;
            case "Septiembre" -> 9;
            case "Octubre" -> 10;
            case "Noviembre" -> 11;
            case "Diciembre" -> 12;
            default -> 1;
        };

        int anhoNum = anhoSel;
        LocalDate inicioSemana;
        LocalDate finSemana;

        // Calcular inicio y fin de semana
        LocalDate primerDiaMes = LocalDate.of(anhoNum, mesNum, 1);

        switch (semanaSel) {
            case "Primera" -> {
                inicioSemana = primerDiaMes;
                finSemana = inicioSemana.plusDays(6);
            }
            case "Segunda" -> {
                inicioSemana = primerDiaMes.plusDays(7);
                finSemana = inicioSemana.plusDays(6);
            }
            case "Tercera" -> {
                inicioSemana = primerDiaMes.plusDays(14);
                finSemana = inicioSemana.plusDays(6);
            }
            case "Cuarta" -> {
                inicioSemana = primerDiaMes.plusDays(21);
                finSemana = inicioSemana.plusDays(6);
                if (finSemana.getMonthValue() != mesNum)
                    finSemana = primerDiaMes.withDayOfMonth(primerDiaMes.lengthOfMonth());
            }
            default -> {
                lblEstado.setText("Semana no válida.");
                return;
            }
        }

        lblEstado.setText("Mostrando pedidos del " + inicioSemana + " al " + finSemana);

        // Consulta SQL
        String sql = """
            SELECT pr.DESCRIPCION AS TIPO_PRODUCTO,
                   COUNT(p.ID_PEDIDO) AS TOTAL_PEDIDOS,
                   SUM(p.CANTIDAD) AS TOTAL_CANTIDAD,
                   SUM(pr.PESO * p.CANTIDAD) AS PESO_TOTAL
            FROM PEDIDO p
            JOIN PRODUCTO pr ON p.ID_PRODUCTO = pr.ID_PRODUCTO
            WHERE p.FECHA_ENTREGA BETWEEN ? AND ?
            GROUP BY pr.DESCRIPCION
            ORDER BY pr.DESCRIPCION
        """;

        ObservableList<ReporteInventario> listaFiltrada = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, java.sql.Date.valueOf(inicioSemana));
            stmt.setDate(2, java.sql.Date.valueOf(finSemana));

            ResultSet rs = stmt.executeQuery();

            int totalPedidos = 0;
            int totalCantidad = 0;
            double totalPeso = 0;

            while (rs.next()) {
                String tipoProducto = rs.getString("TIPO_PRODUCTO");
                int pedidos = rs.getInt("TOTAL_PEDIDOS");
                int cantidad = rs.getInt("TOTAL_CANTIDAD");
                double peso = rs.getDouble("PESO_TOTAL");

                listaFiltrada.add(new ReporteInventario(tipoProducto, pedidos, cantidad, peso));

                totalPedidos += pedidos;
                totalCantidad += cantidad;
                totalPeso += peso;
            }

            // Fila TOTAL
            if (!listaFiltrada.isEmpty()) {
                listaFiltrada.add(new ReporteInventario("TOTAL", totalPedidos, totalCantidad, totalPeso));
            }

            tablaReporte.setItems(listaFiltrada);

        } catch (SQLException e) {
            lblEstado.setText("Error al cargar datos: " + e.getMessage());
            e.printStackTrace();
        }
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
                javafx.print.Paper.A4,
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
    
    @FXML
    private void btnSalir(ActionEvent event) throws IOException {
        // Obtener el Stage actual desde el botón
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // Cerrar la ventana
        stage.close();
    }
}
