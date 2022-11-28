package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class LFJDAnalyticsDatabaseFeederApplication extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(LFJDAnalyticsDatabaseFeederApplication.class.getResource("/view/LFJDAnalyticsDatabaseFeeder-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setResizable(false);
        primaryStage.setTitle("Analytics");
        primaryStage.getIcons().add(new Image(LFJDAnalyticsDatabaseFeederApplication.class.getResourceAsStream("/resources/img/LFJD-Analytics-window-icon.png")));
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
