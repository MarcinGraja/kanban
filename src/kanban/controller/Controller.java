package kanban.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import kanban.model.ListModel;

public class Controller {
    private ListModel toDo;
    private ListModel inProgress;
    private ListModel done;
    @FXML ListView toDoView;
    @FXML ListView inProgressView;
    @FXML ListView doneView;
    @FXML private void initialize(){
        toDo = new ListModel(toDoView,"TO DO");
        toDo = new ListModel(inProgressView,"IN PROGRESS");
        toDo = new ListModel(doneView,"DONE");
    }
    @FXML private void addTask(ActionEvent event){
        System.out.println("gratulacje");
    }

}
