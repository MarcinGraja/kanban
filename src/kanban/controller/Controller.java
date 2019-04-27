package kanban.controller;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;

import kanban.model.Task;
import kanban.model.ListModel;
import kanban.model.customCell;
import kanban.model.enumerations.ListModelName;


public class Controller {
    @FXML private ListView <Task> toDoView;
    @FXML private ListView <Task> inProgressView;
    @FXML private ListView <Task> doneView;
    @FXML MenuBar menuBar;
    @FXML private void initialize(){
        ListModel.setListModel(this);
        toDoView.setCellFactory(lv ->{
            Menu move = new Menu("move");
            ToggleGroup toggleGroup = new ToggleGroup();
            RadioMenuItem moveToToDo = new RadioMenuItem("to do");
            RadioMenuItem moveToInProgress = new RadioMenuItem("in progress");
            RadioMenuItem moveToDone = new RadioMenuItem("done");
            moveToToDo.setToggleGroup(toggleGroup);
            moveToInProgress.setToggleGroup(toggleGroup);
            moveToDone.setToggleGroup(toggleGroup);
            move.getItems().setAll(moveToToDo, moveToInProgress, moveToDone);
            ContextMenu contextMenu = new ContextMenu();
            MenuItem edit = new MenuItem();
            MenuItem delete = new MenuItem();
            contextMenu.getItems().setAll(move, edit, delete);

            ListCell<Task> cell = new customCell();
            edit.textProperty().bind(Bindings.format("Edit \"%s\"", cell.itemProperty().asString()));
            edit.setOnAction(event -> {
                Task task = cell.getItem();
                kanban.controller.AddTask.setTask(task, cell.getItem().getLocation());
            });
            delete.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty().asString()));
            delete.setOnAction(event -> ListModel.removeTask(cell.getItem()));

            moveToToDo.setOnAction(e -> {
                cell.getItem().move(ListModelName.TODO);
            });

            moveToDone.setOnAction(e -> {
                cell.getItem().move(ListModelName.DONE);
            });

            moveToInProgress.setOnAction(e -> {
                cell.getItem().move(ListModelName.INPROGRESS);
            });
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            switch (cell.getItem().getLocation()) {
                case TODO: {
                    moveToToDo.setSelected(true);
                    break;
                }
                case INPROGRESS: {
                    moveToInProgress.setSelected(true);
                    break;
                }
                case DONE: {
                    moveToDone.setSelected(true);
                    break;
                }
            }
            return cell;
        });
        inProgressView.setCellFactory(toDoView.getCellFactory());
        doneView.setCellFactory(toDoView.getCellFactory());
    }
    public void addTask(Task task){
        switch(task.getLocation()){
            case TODO:{
                toDoView.getItems().add(task);
                break;
            }
            case INPROGRESS:{
                inProgressView.getItems().add(task);
                break;
            }
            case DONE:{
                doneView.getItems().add(task);
                break;
            }
        }
    }
    public void removeTask(Task task){
        switch(task.getLocation()){
            case TODO:{
                toDoView.getItems().remove(task);
                break;
            }
            case INPROGRESS:{
                inProgressView.getItems().remove(task);
                break;
            }
            case DONE:{
                doneView.getItems().remove(task);
                break;
            }
        }
    }
    @FXML public void showAddTask() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/kanban/view/addTask.fxml"));

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
}
