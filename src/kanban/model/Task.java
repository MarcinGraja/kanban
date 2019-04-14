package kanban.model;

import java.time.LocalDate;

public class Task {
    public enum Priority{
        LOW("low"), MEDIUM("medium"), HIGH("high");
        private String priority;
        Priority(String priority){
            this.priority=priority;
        }
        @Override
        public String toString(){
            return priority;
        }
    }
    private String name;
    private LocalDate date;
    private Priority priority;
    private String description;
    public Task(String name, Priority priority, String description, LocalDate date){
        this.name = name;
        this.date = date;
        this.priority = priority;
        this.description = description;
    }
    String allToString(){
        return "name: " + getName() + "\n priority: " + getPriority() + "\ndeadline: " + getDate() + "\ndescription: " + getDescription();
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
}
