package components;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Student;


/**
 * Created by Alander on 2017/12/14.
 */
public class StudentTable {
    ObservableList<Student> students = FXCollections.observableArrayList();
    TableView<Student> table = new TableView<>();

    public StudentTable(ObservableList<Student> students) {
        initTable();  // 初始化学生表格
        setItems(students);
    }

    public StudentTable () {
        initTable();
    }

    private void initTable() {
        TableColumn idCol = new TableColumn("学号");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(new PropertyValueFactory<Student, String>("userId"));

        TableColumn nameCol = new TableColumn("姓名");
        nameCol.setMinWidth(100);
        nameCol.setCellValueFactory(new PropertyValueFactory<Student, String>("userName"));

        TableColumn stateCol = new TableColumn("状态");
        stateCol.setMinWidth(100);
        stateCol.setCellValueFactory(new PropertyValueFactory<Student, String>("state"));

        TableColumn instructorCol = new TableColumn("导师");
        instructorCol.setMinWidth(100);
        instructorCol.setCellValueFactory(new PropertyValueFactory<Student, String>("instructor"));

        table.setItems(null);
        table.getColumns().addAll(idCol, nameCol, stateCol, instructorCol);
    }

    public TableView<Student> getTableView() {
        return table;
    }

    public void setItems (ObservableList<Student> students) {
        System.out.println(students.size()+"");
        this.students.clear();
        this.students.addAll(students);
        table.setItems(this.students);
    }
}
