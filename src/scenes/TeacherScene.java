package scenes;

import components.TeacherIndexpane;
import dao.StudentDao;
import dao.TeacherDao;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

    private StudentDao studentDao;
    private TeacherDao teacherDao;


    public TeacherScene(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.currentUser = user;
        this.studentDao = new StudentDao();
        this.teacherDao = new TeacherDao();

        root = new VBox();
        root.setSpacing(10);
        teacherIndexpane = new TeacherIndexpane(primaryStage, user);
        root.getChildren().add(teacherIndexpane.getRoot());
        scene = new Scene(root);

        primaryStage.setTitle("你好：" + user.getUserName() + " 老师");
    }

    public Scene getScene() {
        return scene;
    }
}
