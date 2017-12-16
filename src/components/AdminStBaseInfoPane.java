package components;

import Interface.AdminTableClickListener;
import Interface.EditUserListener;
import ModalDialogs.EditUserInfo;
import ModalDialogs.TipModal;
import dao.UserDao;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import scenes.AdminScene;

/**
 * Created by Alander on 2017/12/14.
 * 学生基本信息查看页面
 */
public class AdminStBaseInfoPane {
    // 布局所需
    private VBox root;
    private Button queryBtn;
    private FlowPane head;
    private StudentBaseTable studentTable;
    private TextField userId;
    private TextField userName;
    private UserDao userDao;
    private Stage primaryStage;

    // 数据所需
    private ObservableList<User> users;
    private User currentUser;
    private String infoType = AdminScene.STUDENT_BASE_INFO;

    private String mode = AdminScene.CHECK_MODE;


    public AdminStBaseInfoPane (Stage primaryStage) {
        this.primaryStage = primaryStage;
        userDao = new UserDao();
        root = new VBox();
        root.setSpacing(15);
        // 绘制查询
        head = new FlowPane();
        head.setHgap(15);
        userId = new TextField();
        userName = new TextField();
        // 初始化查询按钮
        initBtn();
        head.getChildren().addAll(new Label("学号/工号"), userId , new Label("姓名"), userName, queryBtn);

        // 设置信息表格
        studentTable = new StudentBaseTable(new AdminTableClickListener() {
            @Override
            public void onClick(int rowIndex, User user) {
                if (mode.equals(AdminScene.EDIT_MODE)) {
                    editUser(user);
                }
            }
        });
        users = userDao.getAllStudents();
        studentTable.setItems(users);
        root.getChildren().addAll(head, studentTable.getTableView());

    }

    public void setMode(String mode) {
        this.mode = mode;
    }
    public void setInfoType(String infoType) {
        this.infoType = infoType;
        if (infoType == AdminScene.TEACHER_BASE_INFO) {
            studentTable.setItems(userDao.getAllTeachers());
        }
        else {
            studentTable.setItems(userDao.getAllStudents());
        }
    }

    private void editUser(User user) {
        this.currentUser = user;
        new EditUserInfo(this.primaryStage, user, new EditUserListenerImpl());
    }


    private void initBtn () {
        queryBtn = new Button("查询");
        queryBtn.setOnAction(event -> {
            String userId = this.userId.getText();
            String userName = this.userName.getText();
            userId = userId.trim();
            userName = userName.trim();
            if (this.infoType.equals(AdminScene.STUDENT_BASE_INFO))
                users = userDao.getStudentsByInfo("学生", userId, userName);
            else
                users = userDao.getStudentsByInfo("教师", userId, userName);
            studentTable.setItems(users);
        });
    }

    public VBox getRoot() {
        return root;
    }


    private class EditUserListenerImpl implements EditUserListener {

        @Override
        public void resetPassword(String id) {
            System.out.println("重置密码：" + id);
            if (userDao.resetUserPWD(id)) {
                System.out.println("重置密码成功");
                new TipModal(primaryStage, "重置密码成功！");
                currentUser.setUserPassword("1234567");
                studentTable.updateOneUser(currentUser);
            }
            else {
                System.out.println("重置密码失败");
                new TipModal(primaryStage, "重置密码失败！");
            }
        }

        @Override
        public void confirmEdit(String userId, String userName, String telephone) {
            if (userName.equals(currentUser.getUserName()) && telephone.equals(currentUser.getTelephone())) {
                return;
            }
            if (userDao.changeUserInfo(userId, userName, telephone)) {
                currentUser.setUserName(userName);
                currentUser.setTelephone(telephone);
                studentTable.updateOneUser(currentUser);
                new TipModal(primaryStage, "修改信息成功!");
            }
            else {
                new TipModal(primaryStage, "修改信息失败!");
            }
        }
    }
}
