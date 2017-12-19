package scenes;

import components.StudentTeacherDetailPane;
import components.TeacherIndexpane;
import dao.StudentDao;
import dao.TeacherDao;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Teacher;
import model.User;

/**
 * Created by Alander on 2017/12/16.
 */
public class TeacherScene {
    private VBox root;
    private Scene scene;
    private Stage primaryStage;
    private User currentUser;
    private TeacherIndexpane teacherIndexpane;

    private MenuBar menuBar;
    private Menu sysMenu;
    private MenuItem exit;

    private StudentDao studentDao;
    private TeacherDao teacherDao;


    public TeacherScene(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.currentUser = user;
        this.studentDao = new StudentDao();
        this.teacherDao = new TeacherDao();

        root = new VBox();
        root.setSpacing(10);

        initMenu();
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(sysMenu);
        root.getChildren().add(menuBar);

        teacherIndexpane = new TeacherIndexpane(primaryStage, user);
        root.getChildren().add(teacherIndexpane.getRoot());
        scene = new Scene(root);

        primaryStage.setTitle("你好：" + user.getUserName() + " 老师");
    }


    private void initMenu () {
        sysMenu = new Menu("系统");
        exit = new MenuItem("登出");
        sysMenu.getItems().add(exit);
        // 初始化
        exit.setOnAction(event -> {
            this.primaryStage.setScene((new LoginScene(this.primaryStage)).getScene());
        });
    }
    public Scene getScene() {
        return scene;
    }
}
