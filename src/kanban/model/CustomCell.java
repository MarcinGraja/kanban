package kanban.model;

import javafx.beans.binding.Bindings;
import javafx.scene.control.*;
import kanban.controller.Controller;
import kanban.model.enumerations.Priority;

import static kanban.model.enumerations.ListModelName.*;

public class CustomCell extends ListCell<Task> {
    private static Controller controller;
    public static void setController(Controller controller) {
        CustomCell.controller = controller;
    }

    @Override
    public void updateItem(Task task, boolean b) {
        super.updateItem(task, b);
        if (task != null) {
            setContextMenu();
            if (task.getPriority() == Priority.HIGH) {
                setStyle("-fx-control-inner-background:#ff0000;");
            } else if (task.getPriority() == Priority.MEDIUM) {
                setStyle("-fx-control-inner-background:#ffff00;");
            } else {
                setStyle("-fx-control-inner-background:#ffffff;");
            }
        } else {
            setText(null);
            setGraphic(null);
            setStyle("-fx-control-inner-background:#ffffff;");
        }
    }

    private void setContextMenu() {
        Task task = this.getItem();
        this.setText(task.getName());
        Tooltip tooltip = new Tooltip();
        tooltip.setText(task.allToString());
        this.setTooltip(tooltip);
        Menu move = new Menu("move");
        ToggleGroup toggleGroup = new ToggleGroup();
        RadioMenuItem moveToToDo = new RadioMenuItem("to do");
        RadioMenuItem moveToInProgress = new RadioMenuItem("in progress");
        RadioMenuItem moveToDone = new RadioMenuItem("done");
        moveToToDo.setToggleGroup(toggleGroup);
        moveToInProgress.setToggleGroup(toggleGroup);
        moveToDone.setToggleGroup(toggleGroup);
        move.getItems().setAll(moveToToDo, moveToInProgress, moveToDone);
        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem();
        MenuItem delete = new MenuItem();
        contextMenu.getItems().setAll(move, edit, delete);
        edit.textProperty().bind(Bindings.format("Edit \"%s\"", this.itemProperty().asString()));
        edit.setOnAction(event -> controller.editTask(task));

        delete.textProperty().bind(Bindings.format("Delete \"%s\"", this.itemProperty().asString()));
        delete.setOnAction(event -> controller.removeTask(this.getItem()));

        moveToToDo.setOnAction(e -> task.requestMove(TODO));

        moveToDone.setOnAction(e -> task.requestMove(DONE));

        moveToInProgress.setOnAction(e -> task.requestMove(INPROGRESS));
        switch (task.getLocation()) {
            case TODO: {
                moveToToDo.setSelected(true);
                break;
            }
            case INPROGRESS: {
                moveToInProgress.setSelected(true);
                break;
            }
            case DONE: {
                moveToDone.setSelected(true);
                break;
            }
        }
        this.setContextMenu(contextMenu);
    }
}
