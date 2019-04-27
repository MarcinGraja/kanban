package kanban.model;

import kanban.model.enumerations.ListModelName;
import kanban.model.enumerations.Priority;

import java.time.LocalDate;

public class Task {
    private String name;
    private LocalDate date;
    private Priority priority;
    private String description;
    private ListModelName location;
    public Task(String name, Priority priority, String description, LocalDate date, ListModelName location){
        this.name = name;
        this.date = date;
        this.priority = priority;
        this.description = description;
        this.location = location;
    }
    String allToString(){
        return "name: " + name + "\n priority: " + priority + "\ndeadline: " + date + "\ndescription: " + description + "\n in " + location;
    }
    public String toString(){
        return getName();
    }
    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getDescription() {
        return description;
    }
    public ListModelName getLocation(){
        return location;
    }

    public void setLocation(ListModelName location) {
        this.location = location;
    }

    public void move(ListModelName destination){
        location = destination;
    }
}
