package kanban.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kanban.controller.Controller;
import kanban.model.enumerations.ListModelName;
import kanban.model.enumerations.Priority;

import java.time.LocalDate;

public class Task{

    private StringProperty name;
    private ObjectProperty<LocalDate> date;
    private ObjectProperty<Priority> priority;
    private StringProperty description;
    private ObjectProperty<ListModelName> location;



    public Task(String name, Priority priority, String description, LocalDate date, ListModelName location) {
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleObjectProperty<>(date);
        this.priority = new SimpleObjectProperty<>(priority);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleObjectProperty<>(location);
    }
    public void editTask(String name, Priority priority, String description, LocalDate date, ListModelName location){
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleObjectProperty<>(date);
        this.priority = new SimpleObjectProperty<>(priority);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleObjectProperty<>(location);
    }
    String allToString(){
        return "name: " + getName() + "\n priority: " + getPriority() + "\ndeadline: " + getDate() + "\ndescription: " + getDescription() + "\n in " + getLocation();
    }
    public String toString(){
        return getName();
    }
    public String getName() {
        return name.getValue();
    }

    public LocalDate getDate() {
        return date.getValue();
    }

    public Priority getPriority() {
        return priority.getValue();
    }

    public String getDescription() {
        return description.getValue();
    }
    public ListModelName getLocation(){
        return location.getValue();
    }

    void requestMove(ListModelName destination){
        Controller.getMainController().moveTask(this, destination);
    }
    public void setLocation(ListModelName location){
        System.out.println("moving " + toString() + " from " + this.getLocation() + " to " + location);
        this.location = new SimpleObjectProperty<>(location);
        System.out.println("result:" + this.getLocation());
    }
}
