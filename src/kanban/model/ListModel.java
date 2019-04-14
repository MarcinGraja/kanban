package kanban.model;

import javafx.scene.control.ListView;

public class ListModel {
    public enum listModelName{
        TODO("TO DO"), INPROGRESS("IN PROGRESS"), DONE("DONE");
        private String listModelName;
        listModelName(String listModelName){
            this.listModelName=listModelName;
        }
        @Override
        public String toString(){
            return listModelName;
        }
    }
    private ListView <Task> listView;
    private listModelName listName;

    public ListModel(ListView <Task> listView, listModelName listName){
        this.listName=listName;
        this.listView=listView;
    }
    public void addTask(Task task){
        listView.getItems().add(task);
    }
    public void removeTask(Task task){
        listView.getItems().remove(task);
    }
    public ListView <Task> getListView(){
        return listView;
    }
    public listModelName getListNameAsEnum(){
        return listName;
    }


}
