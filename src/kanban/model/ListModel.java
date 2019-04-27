package kanban.model;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import kanban.controller.Controller;
import kanban.model.enumerations.ListModelName;

import java.util.ArrayList;
import java.util.List;

public class ListModel{

    private static ObservableList <Task> observableList;
    private static Controller controller;
    public static void setListModel(Controller controller){
        ListModel.controller = controller;
        observableList = FXCollections.observableArrayList(new ArrayList<>());
        observableList.addListener((ListChangeListener<Task>) change -> {
            while (change.next()){
                for (Task task: change.getAddedSubList()) {
                    if (task.getLocation() == null) task.setLocation(ListModelName.TODO);
                    controller.addTask(task);
                }
                for (Task task: change.getRemoved()){
                    controller.removeTask(task);
                }
            }

        });
    }
    public static void addTask(Task task){
        observableList.add(task);
    }
    public static void removeTask(Task task){
        observableList.remove(task);
    }
}
