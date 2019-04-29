package kanban.model.enumerations;

public enum ListModelName {
    TODO("TO DO"), INPROGRESS("IN PROGRESS"), DONE("DONE");
    private String listModelName;
    ListModelName(String listModelName){
        this.listModelName=listModelName;
    }
    public static ListModelName fromString(String name){
        for (ListModelName l : ListModelName.values()){
            if (l.listModelName.equals(name)){
                return l;
            }
        }
        return null;
    }
    @Override
    public String toString(){
        return listModelName;
    }
}
