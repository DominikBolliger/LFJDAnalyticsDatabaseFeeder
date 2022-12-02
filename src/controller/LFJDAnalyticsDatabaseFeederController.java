package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Generator;
import logic.DBConnection;

import java.time.LocalDate;

public class LFJDAnalyticsDatabaseFeederController {
    @FXML
    public Button btnGenerate;
    @FXML
    public Button btnClose;
    @FXML
    public Button btnTruncate;
    @FXML
    public DatePicker fromDatePicker;
    @FXML
    public DatePicker toDatePicker;
    @FXML
    public TextArea taResult;
    @FXML
    public ProgressBar pgbResult;
    @FXML
    public VBox vBoxLog;
    public DBConnection con;

    @FXML
    public void initialize(){
        LocalDate today = LocalDate.now();
        LocalDate nextYearDay = today.plusDays(365);

        fromDatePicker.setValue(today);
        toDatePicker.setValue(nextYearDay);

        con = new DBConnection("jdbc:mysql://localhost:3306/lfjd-analytics", "root", "", this);

    }

    public void generate() {
        if (fromDatePicker.getValue() != null && toDatePicker.getValue() != null && fromDatePicker.getValue().isBefore(toDatePicker.getValue())) {
            btnGenerate.setDisable(true);
            btnTruncate.setDisable(true);
            Generator gen = new Generator(fromDatePicker.getValue(), toDatePicker.getValue(), this, con);
            gen.start();
        } else {
            if (fromDatePicker.getValue() == null && toDatePicker.getValue() == null) {
                taResult.appendText("Please select a From and To Date\n");
            } else if (fromDatePicker.getValue() == null) {
                taResult.appendText("Please select a From Date\n");
            } else if (toDatePicker.getValue() == null) {
                taResult.appendText("Please select a To Date\n");
            } else if (!fromDatePicker.getValue().isBefore(toDatePicker.getValue())) {
                taResult.appendText("Please Make sure To Date is after From Date\n");
            }
        }
    }

    public void closeStage() {
        Stage stage = (Stage) btnGenerate.getScene().getWindow();
        stage.close();
    }

    public void btnTruncateClick() {
        con.connect();
        con.truncateTables();
        con.close();
    }

}
