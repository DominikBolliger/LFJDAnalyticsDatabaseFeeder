package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modell.DataBehaviour;
import util.DBConnection;
import util.Generator;

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

    public void generate() {
        DataBehaviour.createBehaviourFromXml();
//        if (fromDatePicker.getValue() != null && toDatePicker.getValue() != null && fromDatePicker.getValue().isBefore(toDatePicker.getValue())) {
//            Generator gen = new Generator(fromDatePicker.getValue(), toDatePicker.getValue(), this);
//            gen.start();
//        } else {
//            if (fromDatePicker.getValue() == null && toDatePicker.getValue() == null) {
//                taResult.appendText("Please select a From and To Date\r\n");
//            } else if (fromDatePicker.getValue() == null) {
//                taResult.appendText("Please select a From Date\r\n");
//            } else if (toDatePicker.getValue() == null) {
//                taResult.appendText("Please select a To Date\r\n");
//            } else if (!fromDatePicker.getValue().isBefore(toDatePicker.getValue())){
//                taResult.appendText("Please Make sure To Date is after From Date\r\n");
//            }
//        }
    }

    public void closeStage() {
        Stage stage = (Stage) btnGenerate.getScene().getWindow();
        stage.close();
    }

    public void btnTruncateClick(ActionEvent actionEvent) {
        DBConnection con = new DBConnection();
        con.connect();
        con.truncateTables(this);
        con.close();
    }
}
