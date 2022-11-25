package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import util.Generator;

public class LFJDAnalyticsDatabaseFeederController {
    @FXML
    public Button btnGenerate;
    public Button btnClose;
    public DatePicker fromDatePicker;
    public DatePicker toDatePicker;
    public TextArea taResult;
    public ProgressBar pgbResult;

    public void generate() {
        if (fromDatePicker.getValue() != null && toDatePicker.getValue() != null && fromDatePicker.getValue().isBefore(toDatePicker.getValue())) {
            Generator gen = new Generator(fromDatePicker.getValue(), toDatePicker.getValue(), this);
            gen.start();
        } else {
            if (fromDatePicker.getValue() == null && toDatePicker.getValue() == null) {
                taResult.appendText("Please select a From and To Date\r\n");
            } else if (fromDatePicker.getValue() == null) {
                taResult.appendText("Please select a From Date\r\n");
            } else if (toDatePicker.getValue() == null) {
                taResult.appendText("Please select a To Date\r\n");
            } else if (!fromDatePicker.getValue().isBefore(toDatePicker.getValue())){
                taResult.appendText("Please Make sure To Date is after From Date\r\n");
            }
        }
    }

    public void closeStage() {
        Stage stage = (Stage) btnGenerate.getScene().getWindow();
        stage.close();
    }
}
