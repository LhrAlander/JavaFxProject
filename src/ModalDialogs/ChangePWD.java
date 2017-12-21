package ModalDialogs;

import Interface.EditUserListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

/**
 * Created by Alander on 2017/12/19.
 */
public class ChangePWD {
    private Button okBtn;
    private Button cancelBtn;
    private User user;
    private EditUserListener listener;

    public ChangePWD(Stage stg, User user, EditUserListener listener) {
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

        // 原密码
        Label preLabel = new Label("原始密码");
        PasswordField preTF = new PasswordField();
        mainPane.add(preLabel, 0, 1);
        mainPane.add(preTF, 1, 1);

        // 重复密码
        Label secondLabel = new Label("重复密码");
        PasswordField secondTF = new PasswordField();
        mainPane.add(secondLabel, 0, 2);
        mainPane.add(secondTF, 1, 2);

        // 新密码
        Label newLabel = new Label("联系方式");
        PasswordField newTF = new PasswordField();
        mainPane.add(newLabel, 0, 3);
        mainPane.add(newTF, 1, 3);

        // 按钮
        HBox btnBox = new HBox();
        btnBox.setAlignment(Pos.CENTER);
        btnBox.setSpacing(15);
        btnBox.getChildren().addAll(okBtn, cancelBtn);
        mainPane.add(btnBox, 0, 4, 4, 1);

        //******非常关键部分
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // 设定为模态窗体
        stage.initOwner(stg); // 设置主窗体
        stage.setTitle("修改密码");

        Scene scene = new Scene(mainPane);
        stage.setScene(scene);
        stage.show();


        // 注册点击“确定”按钮事件
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.hide();  // 关闭对话框
                String pre = preTF.getText();
                String second = secondTF.getText();
                String newPwd = newTF.getText();
                if (!pre.equals("") && pre.equals(second)) {
                    if (!newPwd.equals("")) {
                        listener.confirmEdit(pre, second, newPwd);
                    }
                }
                else {
                    new TipModal(stage, "请输入两次原密码，且两次输入需一致");
                }
            }
        });

        // 注册点击“取消”按钮事件
        cancelBtn.setOnAction(event -> {
            stage.hide();
        });
    }
}
