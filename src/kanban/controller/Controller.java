package kanban.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

import kanban.model.Task;
import kanban.model.ListModel;
import kanban.model.CustomCell;
import kanban.model.enumerations.ListModelName;


public class Controller {
    private static Controller mainController;
    private ListModel listModel;
    private AddTask addTaskContoller;
    @FXML private ListView <Task> toDoView;
    @FXML private ListView <Task> inProgressView;
    @FXML private ListView <Task> doneView;
    @FXML MenuBar menuBar;
    @FXML private void initialize(){
        mainController = this;
        listModel = new ListModel();

        addTaskContoller = new AddTask(listModel);
        addTaskContoller.setTask(null);
        CustomCell.setController(this);

        toDoView.setCellFactory(lv -> {
            lv.setOnMouseClicked(mouseEvent -> lv.getSelectionModel().select(-1));  //forbid selection
            return new CustomCell();
        });
        inProgressView.setCellFactory(toDoView.getCellFactory());
        doneView.setCellFactory(toDoView.getCellFactory());


        toDoView.setItems(listModel.getObservableList().filtered(task -> task.getLocation() == ListModelName.TODO));
        inProgressView.setItems(listModel.getObservableList().filtered(task -> task.getLocation()== ListModelName.INPROGRESS));
        doneView.setItems(listModel.getObservableList().filtered(task -> task.getLocation()== ListModelName.DONE));
    }
    public static Controller getMainController(){
        return mainController;
    }
    public void moveTask(Task task, ListModelName destination){
        removeTask(task);
        task.setLocation(destination);
        listModel.addTask(task);

    }
    public void removeTask(Task task){
        listModel.removeTask(task);
    }
    public void editTask(Task task){
        addTaskContoller.setTask(task);
        showAddTask();
        ListView tmp = containingListView(task);
        if (tmp!=null) {
            //force update; without it listView will update when you select an item/add item/delete item
            tmp.getSelectionModel().select(0);
            tmp.getSelectionModel().select(-1);

        }
        else System.out.println("smthing bad m8");
    }
    @FXML public void showAddTask() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/kanban/view/addTask.fxml"));
        fxmlLoader.setController(addTaskContoller);

        try {
            Scene scene = new Scene(fxmlLoader.load(), 400, 335);
            Stage stage = new Stage();
            stage.setTitle("Add task");
            stage.setScene(scene);
            stage.showAndWait();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void close(){
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void about(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Made my Marcin Graja over a period of about 40 hours. It was terrible. Hope you enjoy this application!");
        alert.showAndWait();
    }
    private ListView<Task> containingListView(Task task){
        return toDoView.getItems().contains(task) ? toDoView : doneView.getItems().contains(task) ?
                doneView : inProgressView.getItems().contains(task) ? inProgressView : null;
    }
}
