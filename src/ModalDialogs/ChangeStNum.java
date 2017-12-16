package ModalDialogs;

import Interface.EditUserListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Alander on 2017/12/15.
 */
public class ChangeStNum {
    private Button okBtn;
    private Button cancelBtn;
    private Label label;

    public ChangeStNum (Stage stg, String content, EditUserListener listener) {
        //***********设计标签部分
        label = new Label(content);

        //***********设计按钮部分
        HBox btnPane = new HBox(50);
        btnPane.setAlignment(Pos.CENTER);
        okBtn = new Button("确认");
        cancelBtn = new Button("取消");
        btnPane.getChildren().addAll(okBtn);

        //******把标签部分和按钮部分放入面板
        VBox mainPane = new VBox();
        mainPane.setAlignment(Pos.CENTER);
        VBox.setMargin(btnPane, new Insets(10,0,0,0));
        VBox.setMargin(label, new Insets(20,0,20,0));
        mainPane.getChildren().addAll(label, btnPane);


        //******非常关键部分
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // 设定为模态窗体
        stage.initOwner(stg); // 设置主窗体
        stage.setTitle("操作结果提示");

        Scene scene = new Scene(mainPane, 300, 130);
        stage.setScene(scene);
        stage.show();

        // 注册点击“确定”按钮事件
        okBtn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                stage.hide();  // 关闭对话框
                listener.confirmEdit(null, null, null);
            }
        });

        cancelBtn.setOnAction(event -> {
            stage.hide();
        });
    }
}
