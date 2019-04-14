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


public class Controller {
    static ListModel toDo;
    static ListModel inProgress;
    static ListModel done;
    @FXML private ListView <Task> toDoView;
    @FXML private ListView <Task> inProgressView;
    @FXML private ListView <Task> doneView;
    @FXML MenuBar menuBar;
    @FXML private void initialize(){
        toDo = initListModel(ListModel.listModelName.TODO);
        inProgress = initListModel( ListModel.listModelName.INPROGRESS);
        done = initListModel(ListModel.listModelName.DONE);
    }
    private ListModel initListModel(ListModel.listModelName name){
        ListModel listModel;
        switch(name){
            case TODO: {
                listModel = new ListModel(toDoView, name);
                break;
            }
            case INPROGRESS:{
                listModel = new ListModel(inProgressView, name);
                break;
            }
            case DONE:{
                listModel = new ListModel(doneView, name);
                break;
            }
            default:{
                throw new RuntimeException("you added another listModelName and you decided not to handle this, you dummy. If you're not me please disregard this message.");
            }
        }

        listModel.getListView().setCellFactory(lv -> {
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
                 listModel.getListView().getItems().remove(cell.getItem());
                 kanban.controller.AddTask.setTask(task, listModel.getListNameAsEnum());
                 showAddTask();
            });
            delete.textProperty().bind(Bindings.format("Delete \"%s\"", cell.itemProperty().asString()));
            delete.setOnAction(event -> listModel.getListView().getItems().remove(cell.getItem()));

            moveToToDo.setOnAction(e-> {
                Controller.toDo.addTask(cell.getItem());
                listModel.removeTask(cell.getItem());
                    });

            moveToInProgress.setOnAction(e-> {
                Controller.inProgress.addTask(cell.getItem());
                listModel.removeTask(cell.getItem());
            });

            moveToDone.setOnAction(e-> {
                Controller.done.addTask(cell.getItem());
                listModel.removeTask(cell.getItem());
            });
            cell.emptyProperty().addListener((obs, wasEmpty, isNowEmpty) -> {
                if (isNowEmpty) {
                    cell.setContextMenu(null);
                } else {
                    cell.setContextMenu(contextMenu);
                }
            });
            switch(listModel.getListNameAsEnum()){
                case TODO: {
                    moveToToDo.setSelected(true);
                    break;
                }
                case INPROGRESS:{
                    moveToInProgress.setSelected(true);
                    break;
                }
                case DONE:{
                    moveToDone.setSelected(true);
                    break;
                }

            }
            return cell ;
        });

        return listModel;
    }
    @FXML void showAddTask() {
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
