package components;

import Interface.EditUserListener;
import ModalDialogs.ChangeStNum;
import ModalDialogs.TipModal;
import dao.StudentDao;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Student;
import model.Teacher;
import model.User;
import scenes.StudentScene;

/**
 * Created by Alander on 2017/12/16.
 */
public class StudentTeacherDetailPane {
    private Teacher teacher;
    private User student;
    private VBox root;
    private GridPane head;
    private Button selectBtn;
    private Button backBtn;
    private Button cancelBtn;
    private Stage primaryStage;
    private StudentTable table;
    private StudentDao studentDao;

    private TextField nameTF;
    private TextField phoneTF;
    private TextField titleTF;
    private TextField majorTF;
    private HBox btnBox;

    public StudentTeacherDetailPane(Stage primaryStage, User student, Teacher teacher) {
        this.primaryStage = primaryStage;
        this.student = student;
        this.teacher = teacher;
        this.studentDao = new StudentDao();
        String instructor = studentDao.getInstructor(student);
        System.out.println(instructor);
        root = new VBox();
        root.setSpacing(15);
        initHead(instructor);
        initBtnEvent();


        table = new StudentTable();
        root.getChildren().addAll(head);
        if (teacher != null) {
            root.getChildren().addAll(new Label("选择该导师的学生有："), table.getTableView());
            table.setItems(studentDao.findStudentsByInstructor(teacher.getUserId(), teacher.getName()));
        }
        root.setPadding(new Insets(15));
    }

    private void initHead (String teacherId) {
        head = new GridPane();
        head.setHgap(10);
        head.setVgap(10);

        // 姓名
        Label nameLabel = new Label("姓名");
         nameTF = new TextField();
        head.add(nameLabel, 0, 2);
        head.add(nameTF, 1, 2);
        if (teacher != null)
            nameTF.setText(teacher.getName());
        nameTF.setDisable(true);

        // 电话
        Label phoneLabel = new Label("联系方式");
         phoneTF = new TextField();
        head.add(phoneLabel, 2, 2);
        head.add(phoneTF, 3, 2);
        if (teacher != null)
            phoneTF.setText(teacher.getTelephone());
        phoneTF.setDisable(true);

        // 职称
        Label titleLabel = new Label("职称");
         titleTF = new TextField();
        head.add(titleLabel, 0, 3);
        head.add(titleTF, 1, 3);
        if (teacher != null)
            titleTF.setText(teacher.getTeacherTitle());
        titleTF.setDisable(true);

        // 研究方向
        Label majorLabel = new Label("研究方向");
         majorTF = new TextField();
        head.add(majorLabel, 2, 3);
        head.add(majorTF, 3, 3);
        if (teacher != null)
            majorTF.setText(teacher.getTeacherMajor());
        majorTF.setDisable(true);

        // 按钮
        selectBtn = new Button("选择他为导师");
        backBtn = new Button("返回导师列表");
        btnBox = new HBox();
        btnBox.setSpacing(15);
        if (teacher != null)
            btnBox.getChildren().addAll(selectBtn, backBtn);
        else
            btnBox.getChildren().addAll(new Label("你还未选择任何导师"), backBtn);
        head.add(btnBox, 0, 1, 2, 1);

        // 可选与否
        if (teacher != null) {
            Label canBeSelect = new Label("该导师名额已满，不可选择");
            selectBtn.setDisable(true);
            if (teacher.getCanBeSelected().equals("是")) {
                canBeSelect.setText("该导师还有名额，可以选择");
                selectBtn.setDisable(false);
            }
            if (!studentDao.canSelectTeacher(student)) {
                canBeSelect.setText("你已选择过导师，不可选择");
                selectBtn.setDisable(true);
            }
            if (teacherId != null && teacherId.equals(teacher.getUserId())) {
                cancelBtn = new Button("取消选择该导师");
                cancelBtn.setDisable(true);
                if (studentDao.canCancelTeacher(student)) {
                    cancelBtn.setDisable(false);
                }

                head.add(cancelBtn,3,1);
            }
            else {
                head.add(canBeSelect,3,1);
            }
        }
        else {
            cancelBtn = new Button("取消选择该导师");
            cancelBtn.setDisable(true);
            if (studentDao.canCancelTeacher(student)) {
                cancelBtn.setDisable(false);
            }

            head.add(cancelBtn,3,1);
        }

    }

    private void initBtnEvent () {

        if (selectBtn != null) {
            selectBtn.setOnAction(event -> {
                new ChangeStNum(this.primaryStage, "确定选择" + teacher.getName() + "为导师吗？", new chooseTeacherListener());
            });
        }

        if (backBtn != null) {
            backBtn.setOnAction(event -> {
                StudentScene studentScene = new StudentScene(this.primaryStage, student);
                primaryStage.setScene(studentScene.getScene());
            });
        }

        if (cancelBtn != null) {
            cancelBtn.setOnAction(event -> {
                new ChangeStNum(this.primaryStage, "确定取消" + teacher.getName() + "为导师吗？", new cancelTeacherListener());
            });
        }



    }

    public VBox getRoot() {
        return root;
    }

    private class chooseTeacherListener implements EditUserListener {
        @Override
        public void resetPassword(String id) {

        }

        @Override
        public void confirmEdit(String id, String name, String phone) {
            System.out.println(student.toString());
            if (studentDao.changeInstructor(student.getUserId(), teacher.getUserId(), "待定")) {
                new TipModal(primaryStage, "成功选择导师，请等待导师确认");
                StudentTeacherDetailPane detailPane = new StudentTeacherDetailPane(primaryStage, student,  teacher);
                Scene scene = new Scene(detailPane.getRoot());
                primaryStage.setScene(scene);
            }
            else {
                new TipModal(primaryStage, "选择导师失败，请稍后再试");
            }
        }
    }

    private class cancelTeacherListener implements  EditUserListener {

        @Override
        public void resetPassword(String id) {

        }

        @Override
        public void confirmEdit(String id, String name, String phone) {
            System.out.println("yes");
            System.out.println(student.toString());
            if (studentDao.cancelTeacher(student)) {
                StudentTeacherDetailPane detailPane = new StudentTeacherDetailPane(primaryStage, student,  null);
                Scene scene = new Scene(detailPane.getRoot());
                primaryStage.setScene(scene);
            }
            else {
                new TipModal(primaryStage, "取消导师失败，请稍后再试");
            }
        }
    }
}
