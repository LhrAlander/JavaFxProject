package components;

import dao.StudentDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

/**
 * Created by Alander on 2017/12/14.
 * 学生详细信息查看页面
 */
public class AdminStDetailPane {
    private VBox root;
    private ComboBox stStatusBox;
    private Button queryBtn;
    private FlowPane head;
    private StudentDao studentDao;
    private StudentTable studentTable;

    public AdminStDetailPane() {
        studentDao = new StudentDao();
        root = new VBox();
        root.setSpacing(15);
        // 绘制查询
        head = new FlowPane();
        head.setHgap(15);
        ObservableList<String> studentStatus = FXCollections.observableArrayList("未选", "待定", "选定");
        stStatusBox = new ComboBox(studentStatus);
        stStatusBox.setValue("未选");
        // 初始化查询按钮
        initBtn();
        head.getChildren().addAll(new Label("学生状态"), stStatusBox, queryBtn);

        // 设置信息表格
        studentTable = new StudentTable();
        root.getChildren().addAll(head, studentTable.getTableView());

    }

    private void initBtn () {
        queryBtn = new Button("查询");
        queryBtn.setOnAction(event -> {
            String state = stStatusBox.getValue().toString();
            studentTable.setItems(studentDao.findStudentsByState(state));
        });
    }

    public VBox getRoot() {
        return root;
    }
}
