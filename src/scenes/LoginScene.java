package scenes;

import dao.UserDao;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lecture15.ModalDialog;
import model.User;

import java.util.HashMap;

//登录界面
public class LoginScene {
    private GridPane grid;
    private Button loginBtn;     // 登录按钮
    private TextField account;   // 帐号输入框
    private PasswordField password;  // 密码输入框
    private Stage primaryStage;  // 原始面板
    private Scene scene;

    private UserDao userDao;

    public LoginScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        userDao = new UserDao();

        grid = new GridPane();
        account = new TextField();
        password = new PasswordField();
        loginBtn = new Button("登录");


        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25));

        // 文本框
        Label sceneTitle = new Label("Welcome");
        sceneTitle.setId("welcome-text");
        grid.add(sceneTitle, 0, 0, 2, 1);
        sceneTitle.getStyleClass().add("scene-title");

        // 标签
        Label accountLabel = new Label("用户名:");
        grid.add(accountLabel, 0, 1);
        Label passwordLabel = new Label("密   码:");
        grid.add(passwordLabel, 0, 2);

        // 输入框
        grid.add(account, 1, 1);
        grid.add(password, 1, 2);

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(loginBtn);
        grid.add(hbBtn, 1, 4);

        // 按钮点击事件
        loginBtn.setOnAction(event -> {
            handleLogin();
        });

        scene = new Scene(grid);
        scene.getStylesheets().add("style/login.css");
    }


    // 登录
    private void handleLogin () {
//        String userId = account.getText();
//        String pwd = password.getText();
        String userId = "00000001";
        String pwd = "hairui321";
        if (pwd != null && userId != null) {
            HashMap<String, Object> res = userDao.login(userId, pwd);
            String code = (String)res.get("code");
            if (code.equals("200")) {
                User user = (User)res.get("user");
                if (user != null) {
                    // 登录成功后
                    changeScene(user);
                }
            }
            else {
                new ModalDialog(primaryStage,"用户名或密码错误，请重新输入用户名密码。");
            }
        }
    }

    public Scene getScene() {
        return scene;
    }

    private void changeScene (User user) {
        switch (user.getUserIdentity()) {
            case "管理员":
                AdminScene adminScene = new AdminScene(this.primaryStage);
                primaryStage.setScene(adminScene.getScene());
                break;
        }
    }


    class ModalDialog{
        private Label label;
        private Button okBtn;

        public ModalDialog(Stage stg, String contents) {
            //***********设计标签部分
            label = new Label(contents);
//            label.setFont(Font.font(20));

            //***********设计按钮部分
            HBox btnPane = new HBox(50);
            btnPane.setAlignment(Pos.CENTER);
            okBtn = new Button("确定");
//            okBtn.setFont(Font.font(17));
            btnPane.getChildren().addAll(okBtn);

            //******把标签部分和按钮部分放入面板
            VBox mainPane = new VBox();
            mainPane.setAlignment(Pos.CENTER);
            VBox.setMargin(btnPane, new Insets(30,0,20,0));
            mainPane.getChildren().addAll(label, btnPane);

            //******非常关键部分
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL); // 设定为模态窗体
            stage.initOwner(stg); // 设置主窗体
            stage.setTitle("错误");

            Scene scene = new Scene(mainPane, 300, 100);
            stage.setScene(scene);
            stage.show();

            // 注册点击“确定”按钮事件
            okBtn.setOnAction(new EventHandler<ActionEvent>() {
                public void handle(ActionEvent event) {
                    stage.hide();  // 关闭对话框
                }
            });

        }
    }

}
