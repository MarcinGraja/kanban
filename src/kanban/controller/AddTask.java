package kanban.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import kanban.model.ListModel;
import kanban.model.Task;
import kanban.model.enumerations.Priority;
import kanban.model.enumerations.ListModelName;

import java.time.LocalDate;

class AddTask {
    @FXML
    private ComboBox <Priority> priority;
    @FXML
    private TextArea taskDescription;
    @FXML
    private TextField taskName;
    @FXML
    private DatePicker datePicker;
    @FXML
    private Button addButton;
    private Task task = null;
    private ListModel listModel;
    AddTask(ListModel listModel){
     this.listModel = listModel;
    }
    @FXML private void initialize(){
        addButton.setOnAction(e -> addTask());
        priority.getItems().setAll(Priority.values());
        datePicker.setValue(LocalDate.now());
        if (task != null){
            priority.getSelectionModel().select(task.getPriority());
            taskDescription.setText(task.getDescription());
            taskName.setText(task.getName());
            datePicker.setValue(task.getDate());
        }
    }
    @FXML private void addTask(){
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
        if (this.task == null){
            Task task = new Task(taskName.getText(), priority.getValue(), taskDescription.getText(), datePicker.getValue(), ListModelName.TODO);
            listModel.addTask(task);
        }
        else{
            task.editTask(taskName.getText(), priority.getValue(), taskDescription.getText(), datePicker.getValue(), task.getLocation());
        }
        this.task = null;
        Stage stage = (Stage)addButton.getScene().getWindow();
        stage.close();
    }
    void setTask(Task task){
        this.task = task;
    }
}
