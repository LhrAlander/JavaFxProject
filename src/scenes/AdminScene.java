package scenes;

import components.AdminStBaseInfoPane;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Created by Alander on 2017/12/14.
 */
public class AdminScene {
    public static String STUDENT_BASE_INFO = "stBaseInfo";
    public static String TEACHER_BASE_INFO = "teacherBaseInfo";
    public static String STUDENT_DETAIL = "stDetail";
    public static String TEACHER_DETAIL = "teacherDetail";

    public static String CHECK_MODE = "checkMode";
    public static String EDIT_MODE = "editMode";

    private VBox root;
    private Scene scene;
    private Menu checkMenu;         // 查看菜单
    private Menu modeMenu;          // 编辑菜单

    private String infoType = STUDENT_BASE_INFO;
    private String mode = CHECK_MODE;

    private Stage primaryStage;

    private AdminStBaseInfoPane stBaseInfoPane;


    public AdminScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        root = new VBox();
        root.setSpacing(20);
        initMenu();  // 初始化菜单
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(checkMenu, modeMenu);
        root.getChildren().add(menuBar);
        root.setSpacing(10);

        // 设置学生基本信息查看面板
        stBaseInfoPane = new AdminStBaseInfoPane(this.primaryStage);
        root.getChildren().add(stBaseInfoPane.getRoot());
        scene = new Scene(root);
    }

    private void initMenu () {
        // 初始化查看菜单
        checkMenu = new Menu("查看");
        ToggleGroup checkTag = new ToggleGroup();
        RadioMenuItem checkStudentUser = new RadioMenuItem("学生基本信息");
        RadioMenuItem checkTeacherUser = new RadioMenuItem("导师基本信息");
        RadioMenuItem checkStudent = new RadioMenuItem("学生详细信息");
        RadioMenuItem checkTeacher = new RadioMenuItem("导师详细信息");
        checkStudent.setToggleGroup(checkTag);
        checkTeacher.setToggleGroup(checkTag);
        checkStudentUser.setToggleGroup(checkTag);
        checkTeacherUser.setToggleGroup(checkTag);
        checkStudentUser.setSelected(true);
        // 事件
        checkStudentUser.setOnAction(event -> {
            // 用户基本信息
            if (!this.infoType.equals(STUDENT_BASE_INFO)) {
                this.infoType = STUDENT_BASE_INFO;
                changePane();
            }
        });

        checkTeacherUser.setOnAction(event -> {
            // 教师基本信息
            // 用户基本信息
            if (!this.infoType.equals(TEACHER_BASE_INFO)) {
                this.infoType = TEACHER_BASE_INFO;
                changePane();
            }
        });

        checkStudent.setOnAction(event -> {
            // 学生详细信息
            if (!this.infoType.equals(STUDENT_DETAIL)) {
                this.infoType = STUDENT_DETAIL;
                changePane();
            }
        });

        checkTeacher.setOnAction(event -> {
            // 教师基本信息
            if (!this.infoType.equals(TEACHER_DETAIL)) {
                this.infoType = TEACHER_DETAIL;
                changePane();
            }
        });

        checkMenu.getItems().addAll(checkStudentUser, checkTeacherUser, checkStudent, checkTeacher);

        // 初始化编辑菜单
        modeMenu = new Menu("模式");
        ToggleGroup modeTag = new ToggleGroup();
        RadioMenuItem modeCheck = new RadioMenuItem("查看模式");
        RadioMenuItem modeEdit = new RadioMenuItem("编辑模式");
        modeCheck.setToggleGroup(modeTag);
        modeCheck.setSelected(true);
        modeEdit.setToggleGroup(modeTag);
        // 事件
        modeCheck.setOnAction(event -> {
            // 查看模式
            if (!this.mode.equals(CHECK_MODE)) {
                this.mode = CHECK_MODE;
                stBaseInfoPane.setMode(this.mode);
                changeMode();
            }
        });
        modeEdit.setOnAction(event -> {
            // 编辑模式
            if (!this.mode.equals(EDIT_MODE)) {
                this.mode = EDIT_MODE;
                stBaseInfoPane.setMode(this.mode);
                changeMode();
            }
        });
        modeMenu.getItems().addAll(modeCheck, modeEdit);
    }

    private void changePane () {
        System.out.println(this.infoType);
    }

    private  void changeMode () {
        System.out.println(this.mode);
    }


    public Scene getScene() {
        return scene;
    }
}
