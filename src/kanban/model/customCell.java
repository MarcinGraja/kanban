package kanban.model;

import javafx.scene.control.ListCell;
import javafx.scene.control.Tooltip;

public class customCell extends ListCell<Task> {
    @Override
    public void updateItem(Task task, boolean b) {
        super.updateItem(task, b);
        if (task != null) {
            this.setText(task.getName());
            Tooltip tooltip = new Tooltip();
            tooltip.setText(task.allToString());
            this.setTooltip(tooltip);
            if(task.getPriority()== Task.Priority.HIGH) {
                setStyle("-fx-control-inner-background:#ff0000;");
            }
            else if (task.getPriority() == Task.Priority.MEDIUM){
                setStyle("-fx-control-inner-background:#ffff00;");
            }
            else{
                setStyle("-fx-control-inner-background:#ffffff;");
            }
        }
        else{
            setText(null);
            setGraphic(null);
            setStyle("-fx-control-inner-background:#ffffff;");
        }

    }

}
