package components;

import Interface.StSelectTableClickListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Teacher;

/**
 * Created by Alander on 2017/12/16.
 */
public class StSelectTeacherTable {
    private ObservableList<Teacher> teachers = FXCollections.observableArrayList();
    private TableView table = new TableView<>();
    private StSelectTableClickListener listener;
    private int currentRowindex;

    public StSelectTeacherTable(StSelectTableClickListener listener) {
        this.listener = listener;
        initTable();
    }
    private void initTable () {
        TableColumn idCol = new TableColumn("教师工号");
        idCol.setMinWidth(120);
        idCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("userId"));

        TableColumn nameCol = new TableColumn("教师姓名");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("name"));

        TableColumn titleCol = new TableColumn("教师职称");
        titleCol.setMinWidth(100);
        titleCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("teacherTitle"));

        TableColumn majorCol = new TableColumn("教师专业");
        majorCol.setMinWidth(120);
        majorCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("teacherMajor"));

        TableColumn phoneCol = new TableColumn("联系电话");
        phoneCol.setMinWidth(120);
        phoneCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("telephone"));

        TableColumn stNumCol = new TableColumn("是否可选");
        stNumCol.setMinWidth(120);
        stNumCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("canBeSelected"));

        table.setItems(null);
        table.getColumns().addAll(idCol, nameCol, titleCol, majorCol, stNumCol);


        table.setOnMouseClicked(event -> {
            // 得到用户选择的记录
            int selectedRow = table.getSelectionModel().getSelectedIndex();

            // 如果确实选取了某条记录
            if(selectedRow!=-1){
                // 获取选择的记录
                currentRowindex = selectedRow;
                Teacher teacher = this.teachers.get(selectedRow);
                // 返回内容
                listener.onClick(selectedRow, teacher);
            }
        });
    }

    public void setItems (ObservableList<Teacher> teachers) {
        this.teachers.clear();
        this.teachers.addAll(teachers);
        table.setItems(this.teachers);
    }

    public TableView getTable() {
        return table;
    }
}
