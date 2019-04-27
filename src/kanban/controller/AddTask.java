package kanban.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import kanban.model.ListModel;
import kanban.model.enumerations.Priority;
import kanban.model.Task;
import kanban.model.enumerations.ListModelName;

import java.time.LocalDate;

public class AddTask {
    @FXML
    private ComboBox <Priority> priority;
    @FXML
    private TextArea taskDescription;
    @FXML
    private TextField taskName;
    @FXML
    private DatePicker datePicker;
    @FXML
    Button addButton;
    static private Task task = null;
    static private ListModelName location = null;
    @FXML private void initialize(){

        priority.getItems().setAll(Priority.values());
        datePicker.setValue(LocalDate.now());
        if (task != null){
            priority.getSelectionModel().select(task.getPriority());
            taskDescription.setText(task.getDescription());
            taskName.setText(task.getName());
            datePicker.setValue(task.getDate());
        }
    }
    @FXML void addTask(){
        if (priority.getValue() == null || datePicker.getValue() == null){
            System.out.println("priority: "+priority.getValue());
            System.out.println("date: "+datePicker.getValue());
            String message = "More data is required. You need to enter:\n";
            if (priority.getValue() == null) message += "-priority\n";
            if (datePicker.getValue() == null) message += "-date\n";
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("more data required");
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
            return;
        }
        Task task = new Task(taskName.getText(), priority.getValue(), taskDescription.getText(), datePicker.getValue(), location);
        if (location == null) location = ListModelName.TODO;
        ListModel.addTask(task);
        Stage stage = (Stage)addButton.getScene().getWindow();
        AddTask.task = null;
        AddTask.location = null;
        stage.close();
    }
    static void setTask(Task task, ListModelName location){
        AddTask.task = task;
        AddTask.location = location;
    }


}
