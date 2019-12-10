package handler;

import java.util.function.BiFunction;
import java.util.function.Function;

import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class EditingListCellOperationVehicle<OperationVehicle> extends ListCell<OperationVehicle> {
	private TextField textField;
    private Function<OperationVehicle, String> propertyAccessor;

    public EditingListCellOperationVehicle(Function<OperationVehicle, String> propertyAccessor, BiFunction<String, OperationVehicle, OperationVehicle> updater) {
        this.propertyAccessor = propertyAccessor ;
        this.textField = new TextField();

        textField.setOnAction(e -> {
        	OperationVehicle newItem = updater.apply(textField.getText(), getItem());
            commitEdit(newItem);
        });
        textField.addEventFilter(KeyEvent.KEY_RELEASED, e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                cancelEdit();
            }
        });
    }

    @Override
    protected void updateItem(OperationVehicle item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else if (isEditing()) {
            textField.setText(propertyAccessor.apply(item));
            setText(null);
            setGraphic(textField);
        } else {
            setText(propertyAccessor.apply(item));
            setGraphic(null);
        }
    }

    @Override
    public void startEdit() {
        super.startEdit();
        textField.setText(propertyAccessor.apply(getItem()));
        setText(null);
        setGraphic(textField);       
        textField.selectAll();
        textField.requestFocus();
    }

    @Override
    public void cancelEdit() {
        super.cancelEdit();
        setText(propertyAccessor.apply(getItem()));
        setGraphic(null);
    }

    @Override
    public void commitEdit(OperationVehicle item) {
        super.commitEdit(item);
        getListView().getItems().set(getIndex(), item);
        setText(propertyAccessor.apply(getItem()));
        setGraphic(null);        
    }
}