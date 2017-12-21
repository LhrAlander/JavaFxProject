package components;

import Interface.AdminTableClickListener;
import dao.StudentDao;
import dao.TeacherDao;
import dao.UserDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;

/**
 * Created by Alander on 2017/12/16.
 */
public class TeacherIndexpane {
    // 布局所需
    private VBox root;
    private Button queryBtn;
    private FlowPane head;
    private ComboBox stStatusBox;
    private Stage primaryStage;
    private User user;
    private TeacherChooseStTable table;


    // 数据所需
    private TeacherDao teacherDao;
    private StudentDao studentDao;
    private UserDao userDao;

    public TeacherIndexpane(Stage primaryStage, User user) {
        this.primaryStage = primaryStage;
        this.user = user;
        teacherDao = new TeacherDao();
        studentDao = new StudentDao();
        userDao = new UserDao();


        root = new VBox();
        root.setSpacing(15);
        // 绘制查询
        head = new FlowPane();
        head.setHgap(15);
        head.setPadding(new Insets(10, 0, 5, 15));
        ObservableList<String> studentStatus = FXCollections.observableArrayList( "全部", "待定", "选定");
        stStatusBox = new ComboBox(studentStatus);
        stStatusBox.setValue("全部");
        // 初始化查询按钮
        initBtn();
        head.getChildren().addAll(new Label("学生状态"), stStatusBox, queryBtn);

        Label titleLabel = new Label("选择您为导师的学生有：");
        titleLabel.setPadding(new Insets(0, 0, 10, 15));
        root.getChildren().addAll(head, titleLabel);

        table = new TeacherChooseStTable(new tableClickListener());
        root.getChildren().add(table.getTableView());
        table.setItems(userDao.getStudentByInstructor(user.getUserId()));

    }

    private void initBtn () {
        queryBtn = new Button("查询");
        queryBtn.setOnAction(event -> {
            String state = stStatusBox.getValue().toString();
            if (state.equals("全部")) {
                table.setItems(userDao.getStudentByInstructor(user.getUserId()));
                return;
            }
            table.setItems(userDao.getStudentByInstructorAndState(user.getUserId(), state));
        });
    }

    public VBox getRoot() {
        return root;
    }

    private class tableClickListener implements AdminTableClickListener {

        @Override
        public void onClick(int rawIndex, User student) {
                TeacherCheckStudentDetailPane detailPane = new TeacherCheckStudentDetailPane(primaryStage, student, user);
                Scene scene = new Scene(detailPane.getRoot());
                primaryStage.setScene(scene);
        }
    }
}
