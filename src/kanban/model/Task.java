package kanban.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import kanban.controller.Controller;
import kanban.model.enumerations.ListModelName;
import kanban.model.enumerations.Priority;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.List;

public class Task implements Serializable {

    private transient StringProperty name;
    private transient ObjectProperty<Priority> priority;
    private transient StringProperty description;
    private transient ObjectProperty<LocalDate> date;
    private transient ObjectProperty<ListModelName> location;
    static final long serialVersionUID = 7888371833708311411L;
    public Task(String name, Priority priority, String description, LocalDate date, ListModelName location) {
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleObjectProperty<>(date);
        this.priority = new SimpleObjectProperty<>(priority);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleObjectProperty<>(location);
    }

    public void editTask(String name, Priority priority, String description, LocalDate date, ListModelName location) {
        this.name = new SimpleStringProperty(name);
        this.date = new SimpleObjectProperty<>(date);
        this.priority = new SimpleObjectProperty<>(priority);
        this.description = new SimpleStringProperty(description);
        this.location = new SimpleObjectProperty<>(location);
    }

    String allToString() {
        return "name: " + getName() + "\n priority: " + getPriority() + "\ndeadline: " + getDate() + "\ndescription: " + getDescription() + "\n in " + getLocation();
    }

    public String toString() {
        return getName();
    }

    public String getName() {
        return name.getValue();
    }

    public Priority getPriority() {
        return priority.getValue();
    }

    public String getDescription() {
        return description.getValue();
    }

    public LocalDate getDate() {
        return date.getValue();
    }

    public ListModelName getLocation() {
        return location.getValue();
    }

    void requestMove(ListModelName destination) {
        Controller.getMainController().moveTask(this, destination);
    }

    public void setLocation(ListModelName location) {
        System.out.println("moving " + toString() + " from " + this.getLocation() + " to " + location);
        this.location = new SimpleObjectProperty<>(location);
        System.out.println("result:" + this.getLocation());
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeUTF(name.getValueSafe());
        s.writeObject(priority.getValue());
        s.writeUTF(description.getValueSafe());
        s.writeObject(date.getValue());
        s.writeObject(location.getValue());
    }
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        editTask(s.readUTF(), (Priority)s.readObject(), s.readUTF(),(LocalDate) s.readObject(), (ListModelName) s.readObject());
    }
    private static String CSVSeparator = ";";
    public String toCSV(){

        return toCSV(CSVSeparator);
    }
    public String toCSV(String separator){
        return getName()+separator+getPriority()+separator+getDescription()+separator+getDate()+separator+getLocation();
    }

    public static Task fromCSV(String data){
        return fromCSV(data, CSVSeparator);
    }
    public static Task  fromCSV(String data, String separator){
        List<String> arr = new ArrayList<>(Arrays.asList(data.split(separator)));
        return new Task(arr.get(0), Priority.fromString(arr.get(1)), arr.get(2), LocalDate.parse(arr.get(3)),
                ListModelName.fromString(arr.get(4)));
    }
    public static List<Task> fromCSVArray(String data){
        return fromCSVArray(data, CSVSeparator);
    }
    public static List<Task> fromCSVArray(String data, String memberSeparator){
        List<String> input = new ArrayList<>(Arrays.asList(data.split("\n")));
        List<Task> output = new ArrayList<>();
        for (String string : input){
            output.add(fromCSV(string, memberSeparator));
        }
        return output;
    }
}
