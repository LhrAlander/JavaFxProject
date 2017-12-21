package scenes;

import Interface.EditUserListener;
import ModalDialogs.ChangePWD;
import ModalDialogs.TipModal;
import components.StudentTeacherDetailPane;
import components.TeacherIndexpane;
import dao.StudentDao;
import dao.TeacherDao;
import dao.UserDao;
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
    private MenuItem changePWD;

    private StudentDao studentDao;
    private TeacherDao teacherDao;
    private UserDao userDao;


    public TeacherScene(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.currentUser = user;
        this.studentDao = new StudentDao();
        this.teacherDao = new TeacherDao();
        this.userDao = new UserDao();

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
        changePWD = new MenuItem("修改密码");
        sysMenu.getItems().addAll(changePWD, exit);
        // 初始化
        exit.setOnAction(event -> {
            this.primaryStage.setScene((new LoginScene(this.primaryStage)).getScene());
        });
        changePWD.setOnAction(event -> {
            new ChangePWD(primaryStage, currentUser, new ChangePWDListener());
        });
    }
    public Scene getScene() {
        return scene;
    }

    private class ChangePWDListener implements EditUserListener {

        @Override
        public void resetPassword(String id) {

        }

        @Override
        public void confirmEdit(String prePwd, String secondPwd, String newPwd) {
            System.out.println(prePwd + secondPwd + newPwd);
            if (userDao.changeUserPWD(currentUser, prePwd,  newPwd)) {
                new TipModal(primaryStage, "修改密码成功！");
            }
            else {
                new TipModal(primaryStage, "修改密码请稍后再试！");
            }
        }
    }
}
