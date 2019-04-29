package kanban.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

import kanban.model.Task;
import kanban.model.ListModel;
import kanban.model.CustomCell;
import kanban.model.enumerations.ListModelName;


public class Controller {
    private static Controller mainController;
    private ListModel listModel;
    private AddTask addTaskController;
    @FXML private ListView <Task> toDoView;
    @FXML private ListView <Task> inProgressView;
    @FXML private ListView <Task> doneView;
    @FXML MenuBar menuBar;
    @FXML private void initialize(){
        mainController = this;
        listModel = new ListModel();

        addTaskController = new AddTask(listModel);
        addTaskController.setTask(null);
        CustomCell.setController(this);

        toDoView.setCellFactory(lv -> {
            lv.setOnMouseClicked(mouseEvent -> lv.getSelectionModel().select(-1));  //forbid selection
            return new CustomCell();
        });
        inProgressView.setCellFactory(toDoView.getCellFactory());
        doneView.setCellFactory(toDoView.getCellFactory());

        toDoView.setItems(listModel.getObservableList().filtered(task -> task.getLocation() == ListModelName.TODO));
        inProgressView.setItems(listModel.getObservableList().filtered(task -> task.getLocation()== ListModelName.INPROGRESS));
        doneView.setItems(listModel.getObservableList().filtered(task -> task.getLocation()== ListModelName.DONE));
        loadTasksOnStartup();
    }
    public static Controller getMainController(){
        return mainController;
    }
    public void moveTask(Task task, ListModelName destination){
        removeTask(task);
        task.setLocation(destination);
        listModel.addTask(task);

    }
    public void removeTask(Task task){
        listModel.removeTask(task);
    }
    public void editTask(Task task){
        addTaskController.setTask(task);
        showAddTask();
        forceListViewUpdate(containingListView(task));
    }
    @FXML public void showAddTask() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/kanban/view/addTask.fxml"));
        fxmlLoader.setController(addTaskController);

        try {
            Scene scene = new Scene(fxmlLoader.load(), 400, 335);
            Stage stage = new Stage();
            stage.setTitle("Add task");
            stage.setScene(scene);
            stage.showAndWait();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    void close(){
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.close();
    }

    @FXML
    void about(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText(null);
        alert.setContentText("Made my Marcin Graja over a period of about 40 hours. It was terrible. Hope you enjoy this application!");
        alert.showAndWait();
    }
    private ListView<Task> containingListView(Task task){
        return toDoView.getItems().contains(task) ? toDoView : doneView.getItems().contains(task) ?
                doneView : inProgressView.getItems().contains(task) ? inProgressView : null;
    }
    private void forceListViewUpdate(ListView<Task> listView){
        if (listView!=null) {
            //without it listView will update when you select item/add item/delete item
            for (int i=0; i< listView.getItems().size(); i++){
                listView.getSelectionModel().select(i);
            }
            listView.getSelectionModel().select(-1);
        }
        else System.out.println("smthing bad m8");
    }
    @SuppressWarnings({"Duplicates", "unchecked"})
    private void loadTasksOnStartup(){
        File file = new File(System.getProperty("user.dir")+"/tasks.obs");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            listModel.getObservableList().setAll((ArrayList<Task>)objectInputStream.readObject());
            objectInputStream.close();
            fileInputStream.close();
        }catch (java.io.IOException|java.lang.ClassNotFoundException e){
            System.out.println(e);
            file = new File(System.getProperty("user.dir")+"/tasks.csv");
            StringBuilder in = new StringBuilder();
            BufferedReader reader;
            try{
                reader = new BufferedReader(new FileReader(file));
                String tmp;
                while ((tmp = reader.readLine()) != null){
                    in.append(tmp).append("\n");
                }
            }catch (java.io.IOException e2){
                e2.printStackTrace();
            }
            listModel.getObservableList().setAll(Task.fromCSVArray(in.toString()));
        }
    }
    @FXML
    void saveTasks(){
        FileChooser fileChooser = defaultFileChooser(false);
        fileChooser.setTitle("Choose file you want to save to");
        File file =fileChooser.showSaveDialog(new Stage());
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(new ArrayList<>(listModel.getObservableList()));
            objectOutputStream.close();
            fileOutputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @SuppressWarnings({"Duplicates", "unchecked"})
    @FXML
    void loadTasks(){
        FileChooser fileChooser = defaultFileChooser(false);
        fileChooser.setTitle("Choose file you want to load from");
        File file =fileChooser.showOpenDialog(new Stage());
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            listModel.getObservableList().setAll((ArrayList<Task>)objectInputStream.readObject());
            objectInputStream.close();
            fileInputStream.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void exportTasks(){
        FileChooser fileChooser = defaultFileChooser(true);
        fileChooser.setTitle("Choose file you want to export to");
        File file =fileChooser.showSaveDialog(new Stage());
        StringBuilder out = new StringBuilder();
        for (Task task : listModel.getObservableList()) {
            out.append(task.toCSV()).append("\n");
        }
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(out.toString());
            writer.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    @SuppressWarnings("Duplicates")
    void importTasks(){
        FileChooser fileChooser = defaultFileChooser(true);
        fileChooser.setTitle("Choose file you want to import from");
        File file =fileChooser.showOpenDialog(new Stage());
        StringBuilder in = new StringBuilder();
        BufferedReader reader;
        try{
            reader = new BufferedReader(new FileReader(file));
            String tmp;
            while ((tmp = reader.readLine()) != null){
                in.append(tmp).append("\n");
            }
        }catch (java.io.IOException e){
            e.printStackTrace();
        }
        listModel.getObservableList().setAll(Task.fromCSVArray(in.toString()));

    }
    private FileChooser defaultFileChooser(boolean toCSV){
        FileChooser fileChooser = new FileChooser();
        if(toCSV){
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setInitialFileName("tasks.csv");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("comma separated value file", "*.csv"),
                    new FileChooser.ExtensionFilter("all","*.*"));
        }
        else{
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            fileChooser.setInitialFileName("tasks.obs");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("object stream file", "*.obs"),
                    new FileChooser.ExtensionFilter("all","*.*"));
        }
        return fileChooser;
    }
}
