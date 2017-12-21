package components;

import Interface.EditUserListener;
import ModalDialogs.ChangeStNum;
import ModalDialogs.TipModal;
import dao.StudentDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import scenes.TeacherScene;

/**
 * Created by Alander on 2017/12/16.
 */
public class TeacherCheckStudentDetailPane {
    private User teacher;
    private User student;
    private VBox root;
    private GridPane head;
    private Button selectBtn;
    private Button backBtn;
    private Button delBtn;
    private Stage primaryStage;
    private StudentTable table;
    private StudentDao studentDao;

    private TextField nameTF;
    private TextField phoneTF;
    private TextField stateTF;
    private HBox btnBox;

    public TeacherCheckStudentDetailPane(Stage primaryStage, User student, User user) {
        this.primaryStage = primaryStage;
        this.student = student;
        this.teacher = user;
        this.studentDao = new StudentDao();
        root = new VBox();
        root.setSpacing(15);
        initHead();
        root.getChildren().add(head);
    }

    private void initHead () {
        head = new GridPane();
        head.setHgap(10);
        head.setVgap(10);
        head.setPadding(new Insets(10));

        // 姓名
        Label nameLabel = new Label("姓名");
        nameTF = new TextField();
        head.add(nameLabel, 0, 2);
        head.add(nameTF, 1, 2);
        nameTF.setText(student.getUserName());
        nameTF.setDisable(true);

        // 电话
        Label phoneLabel = new Label("联系方式");
        phoneTF = new TextField();
        head.add(phoneLabel, 2, 2);
        head.add(phoneTF, 3, 2);
        phoneTF.setText(student.getTelephone());
        phoneTF.setDisable(true);

        // 状态
        Label titleLabel = new Label("状态");
        stateTF = new TextField();
        head.add(titleLabel, 0, 3);
        head.add(stateTF, 1, 3);
        stateTF.setText(student.getState());
        stateTF.setDisable(true);

        // 按钮
        selectBtn = new Button("确定");
        backBtn = new Button("返回学生列表");
        delBtn = new Button("淘汰");
        if (student.getState().equals("选定")) {
            selectBtn.setDisable(true);
            delBtn.setDisable(true);
        }
        btnBox = new HBox();
        btnBox.setSpacing(15);
        btnBox.getChildren().addAll(selectBtn, delBtn);
        btnBox.setAlignment(Pos.CENTER_RIGHT);
        head.add(backBtn, 0, 1, 2, 1);
        head.add(btnBox, 2, 3, 2, 1);

        initEvent();

    }


    private void initEvent() {
        selectBtn.setOnAction(event -> {
            new ChangeStNum(primaryStage, "确定选择 " + student.getUserName() + " 为学生吗？", new confirmStudentListener());
        });

        delBtn.setOnAction(event -> {
            new ChangeStNum(primaryStage, "确定淘汰 " + student.getUserName() + " 吗？", new delStudentListener());
        });
        backBtn.setOnAction(event -> {
            TeacherScene teacherScene = new TeacherScene(this.primaryStage, teacher);
            primaryStage.setScene(teacherScene.getScene());
        });
    }

    public VBox getRoot() {
        return root;
    }



    private class confirmStudentListener implements EditUserListener {

        @Override
        public void resetPassword(String id) {

        }

        @Override
        public void confirmEdit(String id, String name, String phone) {
            if (studentDao.teacherConfirmSt(student, teacher)) {
                new TipModal(primaryStage, "恭喜！" + student.getUserName() + " 已经成为您的学生！");
                TeacherScene teacherScene = new TeacherScene(primaryStage, teacher);
                primaryStage.setScene(teacherScene.getScene());
            }
            else {
                new TipModal(primaryStage, "操作失败，请稍后再试！");
            }
        }
    }

    private class delStudentListener implements EditUserListener {

        @Override
        public void resetPassword(String id) {

        }

        @Override
        public void confirmEdit(String id, String name, String phone) {
            if (studentDao.teacherDelSt(student)) {
                new TipModal(primaryStage, "已淘汰学生 " + student.getUserName());
                TeacherScene teacherScene = new TeacherScene(primaryStage, teacher);
                primaryStage.setScene(teacherScene.getScene());
            }
            else {
                new TipModal(primaryStage, "操作失败，请稍后再试！");
            }
        }
    }
}
