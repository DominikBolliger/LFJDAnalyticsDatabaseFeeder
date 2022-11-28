package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LFJDAnalyticsDatabaseFeederApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LFJDAnalyticsDatabaseFeederApplication.class.getResource("/view/LFJDAnalyticsDatabaseFeeder-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setResizable(false);
        primaryStage.setTitle("LFJD Analytics");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
