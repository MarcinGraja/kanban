package kanban.controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import kanban.model.ListModel;
import kanban.model.Task;

import java.time.LocalDate;

public class AddTask {
    @FXML
    private ComboBox <Task.Priority> priority;
    @FXML
    private TextArea taskDescription;
    @FXML
    private TextField taskName;
    @FXML
    private DatePicker datePicker;
    @FXML
    Button addButton;
    static private Task task = null;
    static private ListModel.listModelName location = null;
    @FXML private void initialize(){

        priority.getItems().setAll(Task.Priority.values());
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
        Task task = new Task(taskName.getText(), priority.getValue(), taskDescription.getText(), datePicker.getValue());
        if (location == null) location = ListModel.listModelName.TODO;
        switch (location){
            case TODO:{
                Controller.toDo.addTask(task);
                break;
            }
            case DONE:{
                Controller.done.addTask(task);
                break;
            }
            case INPROGRESS:{
                Controller.inProgress.addTask(task);
                break;
            }
        }
        Stage stage = (Stage)addButton.getScene().getWindow();
        AddTask.task = null;
        AddTask.location = null;
        stage.close();
    }
    static void setTask(Task task, ListModel.listModelName location){
        AddTask.task = task;
        AddTask.location = location;
    }


}
