package scenes;

import components.StudentIndexPane;
import components.StudentTeacherDetailPane;
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
public class StudentScene {
    private VBox root;
    private Scene scene;
    private Stage primaryStage;
    private User currentUser;

    private MenuBar menuBar;
    private Menu sysMenu;
    private MenuItem exit;
    private Menu checkMenu;
    private RadioMenuItem indexMenuItem;
    private RadioMenuItem myInfoItem;

    private StudentIndexPane stIndexPane;

    private StudentDao studentDao;
    private TeacherDao teacherDao;

    private static final String INDEX_PANE = "index";
    private static final String MY_INFO = "myInfo";

    private String paneName = INDEX_PANE;

    public StudentScene(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.currentUser = user;
        this.studentDao = new StudentDao();
        this.teacherDao = new TeacherDao();
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
        sysMenu.getItems().add(exit);
        // 初始化
        exit.setOnAction(event -> {
            this.primaryStage.setScene((new LoginScene(this.primaryStage)).getScene());
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
}
