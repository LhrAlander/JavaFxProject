package ModalDialogs;

import Interface.EditUserListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

/**
 * Created by Alander on 2017/12/14.
 */
public class EditUserInfo {
    private Button okBtn;
    private Button cancelBtn;
    private User user;
    private EditUserListener listener;

    public EditUserInfo(Stage stg, User user, EditUserListener listener) {
        this.listener = listener;
        this.user = user;

        //***********设计按钮部分
        HBox btnPane = new HBox(50);
        btnPane.setAlignment(Pos.CENTER);
        okBtn = new Button("确认修改");
        cancelBtn = new Button("取消修改");
        btnPane.getChildren().addAll(okBtn, cancelBtn);

        //******把标签部分和按钮部分放入面板
        GridPane mainPane = new GridPane();
        mainPane.setAlignment(Pos.CENTER);
        mainPane.setHgap(20);
        mainPane.setVgap(10);
        mainPane.setPadding(new Insets(25));

        // 学号
        Label idLabel = new Label("学号");
        TextField idTF = new TextField();
        idTF.setDisable(true);
        mainPane.add(idLabel, 0, 1);
        mainPane.add(idTF, 1, 1);
        idTF.setText(user.getUserId());

        // 姓名
        Label nameLabel = new Label("姓名");
        TextField nameTF = new TextField();
        mainPane.add(nameLabel, 2, 1);
        mainPane.add(nameTF, 3, 1);
        nameTF.setText(user.getUserName());

        // 电话
        Label phoneLabel = new Label("联系方式");
        TextField phoneTF = new TextField();
        mainPane.add(phoneLabel, 0, 2);
        mainPane.add(phoneTF, 1, 2);
        phoneTF.setText(user.getTelephone());

        // 密码
        Label pwdLabel = new Label("联系方式");
        Button pwdBtn = new Button("重置密码");
        mainPane.add(pwdLabel, 2, 2);
        mainPane.add(pwdBtn, 3, 2);

        // 按钮
        HBox btnBox = new HBox();
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(15);
        btnBox.getChildren().addAll(okBtn, cancelBtn);
        mainPane.add(btnBox, 0, 3, 4, 1);


        //******非常关键部分
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // 设定为模态窗体
        stage.initOwner(stg); // 设置主窗体
        stage.setTitle("修改信息");

        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.show();

        // 注册点击“确定”按钮事件
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.hide();  // 关闭对话框
                listener.confirmEdit(idTF.getText(), nameTF.getText(), phoneTF.getText());
            }
        });

        // 注册点击“取消”按钮事件
        cancelBtn.setOnAction(event -> {
            stage.hide();
        });

        // 注册重置密码按钮事件
        pwdBtn.setOnAction(event -> {
            new ResetPWD(stg, listener, idTF.getText());
        });

    }
}
