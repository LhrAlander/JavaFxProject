package scenes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

//登录界面
public class LoginScene {
    private GridPane grid;
    private Button loginBtn;     // 登录按钮
    private TextField account;   // 帐号输入框
    private TextField password;  // 密码输入框
    private Stage primaryStage;  // 原始面板
    private Scene scene;

    public LoginScene() {


        grid = new GridPane();
        account = new TextField();
        password = new TextField();
        loginBtn = new Button("登录");

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        // 文本框
        Label scenetitle = new Label("Welcome");
        scenetitle.setId("welcome-text");
        grid.add(scenetitle, 0, 0, 2, 1);
        scenetitle.getStyleClass().add("scene-title");

        // 标签
        Label accountLabel = new Label("用户名:");
        grid.add(accountLabel, 0, 1);
        Label passwordLabel = new Label("用户名:");
        grid.add(passwordLabel, 0, 2);

        // 输入框
        grid.add(account, 1, 1);
        grid.add(password, 1, 2);

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginBtn);
        grid.add(hbBtn, 1, 4);

        scene = new Scene(grid);
        scene.getStylesheets().add("style/login.css");
    }

    public Scene getScene() {
        return scene;
    }
}
