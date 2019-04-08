package kanban.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;

public class ListModel {
    private ObservableList<Task> listTasks = FXCollections.observableArrayList();
    private ListView listView;
    private String listName;

    public ListModel(ListView listView, String listName){
        this.listName=listName;
        this.listView=listView;
    }
    public void addTask(Task task){
        listTasks.add(task);
    }

}
