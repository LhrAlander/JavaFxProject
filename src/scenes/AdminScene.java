package scenes;

import components.AdminStBaseInfoPane;
import components.AdminStDetailPane;
import components.AdminTeacherDetailPane;
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
    private RadioMenuItem checkStudentUser;
    private RadioMenuItem checkTeacherUser;
    private RadioMenuItem checkStudent;
    private RadioMenuItem checkTeacher;
    private Menu modeMenu;          // 编辑菜单
    private RadioMenuItem modeCheck;
    private RadioMenuItem modeEdit;
    private Menu sysMenu;
    private MenuItem exit;
    private MenuBar menuBar;

    private String infoType = STUDENT_BASE_INFO;
    private String mode = CHECK_MODE;

    private Stage primaryStage;

    private AdminStBaseInfoPane stBaseInfoPane;
    private AdminStDetailPane stDetailPane;
    private AdminTeacherDetailPane teacherDetailPane;


    public AdminScene(Stage primaryStage) {
        this.primaryStage = primaryStage;
        root = new VBox();
        initMenu();  // 初始化菜单
        menuBar = new MenuBar();
        menuBar.getMenus().addAll(sysMenu, checkMenu, modeMenu);
        root.getChildren().add(menuBar);
        root.setSpacing(10);

        // 设置学生/教师基本信息查看面板
        stBaseInfoPane = new AdminStBaseInfoPane(this.primaryStage);
        root.getChildren().add(stBaseInfoPane.getRoot());
        scene = new Scene(root);
        primaryStage.setTitle("你好，管理员");
    }

    private void initMenu () {
        // 初始化退出登录菜单
        sysMenu = new Menu("系统");
        exit = new MenuItem("登出");
        sysMenu.getItems().add(exit);
        // 初始化
        exit.setOnAction(event -> {
            this.primaryStage.setScene((new LoginScene(this.primaryStage)).getScene());
        });
        // 初始化查看菜单
        checkMenu = new Menu("查看");
        ToggleGroup checkTag = new ToggleGroup();
         checkStudentUser = new RadioMenuItem("学生基本信息");
         checkTeacherUser = new RadioMenuItem("导师基本信息");
         checkStudent = new RadioMenuItem("学生详细信息");
         checkTeacher = new RadioMenuItem("导师详细信息");
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
                stBaseInfoPane.setInfoType(this.infoType);
                stBaseInfoPane.setMode(CHECK_MODE);
                changePane();
            }
        });

        checkTeacherUser.setOnAction(event -> {
            // 教师基本信息
            // 用户基本信息
            if (!this.infoType.equals(TEACHER_BASE_INFO)) {
                this.infoType = TEACHER_BASE_INFO;
                stBaseInfoPane.setMode(CHECK_MODE);
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
         modeCheck = new RadioMenuItem("查看模式");
         modeEdit = new RadioMenuItem("编辑模式");
        modeCheck.setToggleGroup(modeTag);
        modeCheck.setSelected(true);
        modeEdit.setToggleGroup(modeTag);
        // 事件
        modeCheck.setOnAction(event -> {
            System.out.println("this" + " " + this.mode);
            // 查看模式
            if (!this.mode.equals(CHECK_MODE)) {
                this.mode = CHECK_MODE;

                changeMode();
            }
        });
        modeEdit.setOnAction(event -> {
            // 编辑模式
            if (!this.mode.equals(EDIT_MODE)) {
                this.mode = EDIT_MODE;

                changeMode();
            }
        });
        modeMenu.getItems().addAll(modeCheck, modeEdit);
    }

    private void changePane () {
        root = new VBox();
        root.setSpacing(20);
        modeCheck.setSelected(true);
        this.mode = CHECK_MODE;
        if (this.infoType.equals(STUDENT_DETAIL)) {
            modeMenu.setDisable(true);
            stDetailPane = new AdminStDetailPane();
            root.getChildren().addAll(menuBar, stDetailPane.getRoot());
        }
        else if (this.infoType.equals(STUDENT_BASE_INFO) || this.infoType.equals(TEACHER_BASE_INFO)) {
            modeMenu.setDisable(false);
            // 设置学生/教师基本信息查看面板
            stBaseInfoPane = new AdminStBaseInfoPane(this.primaryStage);
            stBaseInfoPane.setInfoType(this.infoType);
            stBaseInfoPane.setMode(CHECK_MODE);
            root.getChildren().addAll(menuBar, stBaseInfoPane.getRoot());
        }
        else {
            modeMenu.setDisable(false);
            // 设置学生/教师基本信息查看面板
            teacherDetailPane = new AdminTeacherDetailPane(this.primaryStage);
            root.getChildren().addAll(menuBar, teacherDetailPane.getRoot());
        }
        scene = new Scene(root);
        this.primaryStage.setScene(scene);

    }

    private  void changeMode () {
        System.out.println(this.mode);
        stBaseInfoPane.setMode(this.mode);
        if (teacherDetailPane != null) {
            teacherDetailPane.setMode(this.mode);
        }
    }



    public Scene getScene() {
        return scene;
    }
}
