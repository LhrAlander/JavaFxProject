package components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Teacher;


/**
 * Created by Alander on 2017/12/15.
 */
public class AdminTeacherDetailTable {
    private ObservableList<Teacher> teachers = FXCollections.observableArrayList();
    private TableView table = new TableView<>();
    private int currentRowindex;

    public AdminTeacherDetailTable() {
        initTable();
    }


    public TableView getTable() {
        return table;
    }


    public void setItems (ObservableList<Teacher> teachers) {
        this.teachers.clear();
        this.teachers.addAll(teachers);
        table.setItems(this.teachers);
    }

    public void updateOneUser (Teacher user) {
        teachers.set(currentRowindex, user);
    }

    public void updateAllStNum (int stNum) {
        for (int i = 0; i < teachers.size(); i++){
            Teacher teacher = teachers.get(i);
            teacher.setStudentNumber(stNum);
            teachers.set(i, teacher);
        }
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

        TableColumn stNumCol = new TableColumn("可带学生数量");
        stNumCol.setMinWidth(120);
        stNumCol.setCellValueFactory(new PropertyValueFactory<Teacher, String>("studentNumber"));

        table.setItems(null);
        table.getColumns().addAll(idCol, nameCol, titleCol, majorCol, stNumCol);
    }


}
