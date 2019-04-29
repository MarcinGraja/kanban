package kanban.model.enumerations;

public enum Priority {
    LOW("low"), MEDIUM("medium"), HIGH("high");
    private String priority;

    Priority(String priority){
        this.priority=priority;
    }
    public static Priority fromString(String priority){
        for (Priority p : Priority.values()){
            if (p.priority.equals(priority)){
                return p;
            }
        }
        return null;
    }
    @Override
    public String toString(){
        return priority;
    }
}
