package kanban.model.enumerations;

public enum ListModelName {
    TODO("TO DO"), INPROGRESS("IN PROGRESS"), DONE("DONE");
    private String listModelName;
    ListModelName(String listModelName){
        this.listModelName=listModelName;
    }
    @Override
    public String toString(){
        return listModelName;
    }
}
