import javafx.application.Application;
import javafx.stage.Stage;
import scenes.LoginScene;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        LoginScene root = new LoginScene();
        primaryStage.setScene(root.getScene());
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
