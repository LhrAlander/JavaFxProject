package scenes;

import Interface.EditUserListener;
import ModalDialogs.ChangePWD;
import ModalDialogs.TipModal;
import components.StudentIndexPane;
import components.StudentTeacherDetailPane;
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
public class StudentScene {
    private VBox root;
    private Scene scene;
    private Stage primaryStage;
    private User currentUser;

    private MenuBar menuBar;
    private Menu sysMenu;
    private MenuItem exit;
    private MenuItem changePWD;
    private Menu checkMenu;
    private RadioMenuItem indexMenuItem;
    private RadioMenuItem myInfoItem;

    private StudentIndexPane stIndexPane;

    private StudentDao studentDao;
    private TeacherDao teacherDao;
    private UserDao userDao;

    private static final String INDEX_PANE = "index";
    private static final String MY_INFO = "myInfo";

    private String paneName = INDEX_PANE;

    public StudentScene(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.currentUser = user;
        this.studentDao = new StudentDao();
        this.teacherDao = new TeacherDao();
        this.userDao = new UserDao();
        root = new VBox();
        root.setSpacing(10);

        initMenu();
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(sysMenu, checkMenu);
        root.getChildren().add(menuBar);

        stIndexPane = new StudentIndexPane(this.primaryStage, user);
        root.getChildren().add(stIndexPane.getRoot());
        scene = new Scene(root);
        primaryStage.setTitle("你好：" + user.getUserName() + " 同学");
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

        checkMenu = new Menu("查看");
        ToggleGroup checkGroup = new ToggleGroup();
        indexMenuItem = new RadioMenuItem("导师列表");
        myInfoItem = new RadioMenuItem("我的信息");
        indexMenuItem.setToggleGroup(checkGroup);
        indexMenuItem.setSelected(true);
        myInfoItem.setToggleGroup(checkGroup);
        checkMenu.getItems().addAll(indexMenuItem, myInfoItem);
        myInfoItem.setOnAction(event -> {
            if (paneName.equals(INDEX_PANE)) {
                Teacher teacher;
                String teacherId = studentDao.getInstructor(currentUser);
                if (teacherId == null) {
                    teacher = null;
                }
                else {
                    teacher = teacherDao.getTeachersByInfo(teacherId, null).get(0);
                }
                StudentTeacherDetailPane detailPane = new StudentTeacherDetailPane(primaryStage, currentUser,  teacher);
                Scene scene = new Scene(detailPane.getRoot());
                primaryStage.setScene(scene);
            }
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
                new TipModal(primaryStage, "原密码输入错误请稍后再试！");
            }
        }
    }
}
