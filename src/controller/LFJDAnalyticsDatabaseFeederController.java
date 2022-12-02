package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import logic.Generator;
import modell.DataBehaviour;
import logic.DBConnection;

import javax.xml.crypto.Data;
import java.util.List;

public class LFJDAnalyticsDatabaseFeederController {
    @FXML
    public Button btnGenerate;
    @FXML
    public Button btnClose;
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

    public void generate() {
        if (fromDatePicker.getValue() != null && toDatePicker.getValue() != null && fromDatePicker.getValue().isBefore(toDatePicker.getValue())) {
            con = new DBConnection("jdbc:mysql://localhost:3306/lfjd-analytics", "root", "", this);
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
        con.truncateTables(this);
        con.close();
    }
}
