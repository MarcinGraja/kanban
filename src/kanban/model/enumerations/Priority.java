package kanban.model.enumerations;

public enum Priority {
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
