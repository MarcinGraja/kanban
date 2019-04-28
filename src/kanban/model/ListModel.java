package kanban.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.util.ArrayList;

public class ListModel{

    private ObservableList <Task> observableList;
    public ListModel(){
        observableList = FXCollections.observableArrayList(new ArrayList<>());
    }
    public void addTask(Task task){
        observableList.add(task);
    }
    public void removeTask(Task task){
        observableList.remove(task);
    }

    public ObservableList<Task> getObservableList() {
        return observableList;
    }
}
