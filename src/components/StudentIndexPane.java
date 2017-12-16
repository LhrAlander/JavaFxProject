package components;

import Interface.StSelectTableClickListener;
import dao.StudentDao;
import dao.TeacherDao;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Teacher;
import model.User;

/**
 * Created by Alander on 2017/12/16.
 */
public class StudentIndexPane {
    // 布局所需
    private VBox root;
    private Button queryBtn;
    private GridPane head;
    private TextField userId;
    private TextField userName;
    private Stage primaryStage;
    private StSelectTeacherTable table;
    private StudentTeacherDetailPane detailPane;
    private User user;


    // 数据所需
    private TeacherDao teacherDao;
    private StudentDao studentDao;

    public StudentIndexPane(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.user = user;
        teacherDao = new TeacherDao();
        studentDao = new StudentDao();
        table = new StSelectTeacherTable(new TableClickListener());

        root = new VBox();
        root.setSpacing(15);
        // 绘制查询
        head = new GridPane();
        head.setAlignment(Pos.CENTER);
        head.setHgap(10);
        head.setVgap(10);

        // 初始化查询按钮
        initBtn();
        userId = new TextField();
        userName = new TextField();



        head.add(new Label("教师工号"), 1, 1);
        head.add(userId, 2, 1);
        head.add(new Label("教师姓名"), 3, 1);
        head.add(userName, 4, 1);
        head.add(queryBtn, 5, 1);

        root.getChildren().addAll(head, table.getTable());
        table.setItems(teacherDao.getAllTeachers());
    }

    public VBox getRoot() {
        return root;
    }

    private void initBtn () {
        queryBtn = new Button("查询");
        queryBtn.setOnAction(event -> {
            String userId = this.userId.getText();
            String userName = this.userName.getText();
            table.setItems(teacherDao.getTeachersByInfo(userId, userName));
        });
    }

    private class TableClickListener implements StSelectTableClickListener {

        @Override
        public void onClick(int rawIndex, Teacher teacher) {
            detailPane = new StudentTeacherDetailPane(primaryStage, user,  teacher);
            Scene scene = new Scene(detailPane.getRoot());
            primaryStage.setScene(scene);
        }
    }
}
