package components;

import Interface.AdminTableClickListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.User;

/**
 * Created by Alander on 2017/12/14.
 */
public class StudentBaseTable {
    private ObservableList<User> users = FXCollections.observableArrayList();
    private TableView<User> table = new TableView<>();
    private AdminTableClickListener clickListener;

    private int currentRowindex;


    public StudentBaseTable(AdminTableClickListener clickListener) {
        this.clickListener = clickListener;
        initTable();
    }

    public StudentBaseTable () {
        initTable();
    }

    private void initTable() {
        TableColumn idCol = new TableColumn("学号/工号");
        idCol.setMinWidth(120);
        idCol.setCellValueFactory(new PropertyValueFactory<User, String>("userId"));

        TableColumn nameCol = new TableColumn("姓名");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<User, String>("userName"));

        TableColumn sexCol = new TableColumn("性别");
        sexCol.setMinWidth(100);
        sexCol.setCellValueFactory(new PropertyValueFactory<User, String>("userSex"));

        TableColumn phoneCol = new TableColumn("联系方式");
        phoneCol.setMinWidth(120);
        phoneCol.setCellValueFactory(new PropertyValueFactory<User, String>("telephone"));

        TableColumn passwordCol = new TableColumn("密码");
        passwordCol.setMinWidth(120);
        passwordCol.setCellValueFactory(new PropertyValueFactory<User, String>("userPassword"));

        table.setItems(null);
        table.getColumns().addAll(idCol, nameCol, sexCol, phoneCol, passwordCol);

        table.setOnMouseClicked(event -> {
            // 得到用户选择的记录
            int selectedRow = table.getSelectionModel().getSelectedIndex();

            // 如果确实选取了某条记录
            if(selectedRow!=-1){
                // 获取选择的记录
                currentRowindex = selectedRow;
                User user = this.users.get(selectedRow);
                // 返回内容
                clickListener.onClick(selectedRow, user);
            }
        });
    }

    public TableView<User> getTableView() {
        return table;
    }

    public void setItems (ObservableList<User> users) {
        this.users.clear();
        this.users.addAll(users);
        table.setItems(this.users);
    }

    public void updateOneUser (User user) {
        users.set(currentRowindex, user);
    }
}
