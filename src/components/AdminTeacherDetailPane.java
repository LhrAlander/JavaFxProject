package components;

import Interface.EditUserListener;
import ModalDialogs.ChangeStNum;
import ModalDialogs.TipModal;
import dao.TeacherDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import scenes.AdminScene;

/**
 * Created by Alander on 2017/12/15.
 */
public class AdminTeacherDetailPane {
    // 布局所需
    private VBox root;
    private Button queryBtn;
    private GridPane head;
    private TextField userId;
    private TextField userName;
    private Stage primaryStage;
    private TextField studentNumber;
    private Button changeAllNumber;

    // 数据所需
    private TeacherDao teacherDao;
    private AdminTeacherDetailTable table;
    private String mode = AdminScene.CHECK_MODE;
    private int stNum;

    public AdminTeacherDetailPane (Stage primaryStage) {
        this.primaryStage = primaryStage;
        teacherDao = new TeacherDao();
        table = new AdminTeacherDetailTable();
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
        studentNumber = new TextField();
        studentNumber.setDisable(true);

        head.add(new Label("教师工号"), 1, 0);
        head.add(userId, 2, 0);
        head.add(new Label("教师姓名"), 3, 0);
        head.add(userName, 4, 0);
        head.add(queryBtn, 5, 0);
        head.add(new Label("可带数量"), 1, 1);
        head.add(studentNumber, 2, 1);
        head.add(changeAllNumber, 3, 1);



        root.getChildren().addAll(head, table.getTable());
        table.setItems(teacherDao.getAllTeachers());
    }


    private void initBtn () {
        queryBtn = new Button("查询");
        changeAllNumber = new Button("修改");
        changeAllNumber.setDisable(true);
        queryBtn.setOnAction(event -> {
            String userId = this.userId.getText();
            String userName = this.userName.getText();
            this.table.setItems(teacherDao.getTeachersByInfo(userId, userName));
        });
        changeAllNumber.setOnAction(event -> {
            String str = this.studentNumber.getText();
            if (str != null && !str.equals("")){
               try {
                   stNum = Integer.valueOf(str);
                   new ChangeStNum(this.primaryStage, "确定要修改所有导师可带学生数量吗？", new ChangeStNumListener());
               }
               catch (Exception e) {
                   new TipModal(this.primaryStage, "请输入正确的数字！");
               }
            }
        });
    }

    public VBox getRoot() {
        return root;
    }

    public void setMode(String mode) {
        this.mode = mode;
        if (mode.equals(AdminScene.EDIT_MODE)) {
            changeAllNumber.setDisable(false);
            studentNumber.setDisable(false);
        }
        else {
            changeAllNumber.setDisable(true);
            studentNumber.setDisable(true);
        }
    }

    private class ChangeStNumListener implements EditUserListener {

        @Override
        public void resetPassword(String id) {

        }

        @Override
        public void confirmEdit(String id, String name, String phone) {
            if (teacherDao.changeStudentNumber(stNum)) {
                new TipModal(primaryStage, "修改成功!");
                table.updateAllStNum(stNum);
                return;
            }
            new TipModal(primaryStage, "修改失败!");
        }
    }
}
