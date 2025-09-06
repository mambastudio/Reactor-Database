package com.nupea.reactordatabase.util;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCellEditor;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

import java.util.ArrayList;
import java.util.List;

public class OptionCellType<T> extends SpreadsheetCellType<T> {

    private final List<T> options;

    public OptionCellType(List<T> options) {
        super(null); // StringConverter not needed, toString() handles it
        this.options = new ArrayList<>(options);
    }

    public SpreadsheetCell createCell(int row, int column, int rowSpan, int colSpan, T value) {
        SpreadsheetCellBase cell = new SpreadsheetCellBase(row, column, rowSpan, colSpan, this);
        cell.setItem(value);
        return cell;
    }

    @Override
    public SpreadsheetCellEditor createEditor(SpreadsheetView sv) {
        return new SpreadsheetCellEditor(sv) {
            private final ComboBox<T> combo = new ComboBox<>(FXCollections.observableArrayList(options));

            @Override
            public void startEdit(Object value, String format, Object... opts) {
                @SuppressWarnings("unchecked")
                T typed = (T) value;
                combo.setValue(typed);

                // Commit immediately when a new item is selected
                combo.setOnAction(e -> endEdit(true));

                // Cancel on ESC
                combo.setOnKeyPressed(event -> {
                    switch (event.getCode()) {
                        case ESCAPE -> {
                            endEdit(false);
                            event.consume();
                        }
                        case ENTER -> {
                            endEdit(true);
                            event.consume();
                        }
                    }
                });

                // Commit on focus lost
                combo.focusedProperty().addListener((obs, old, focused) -> {
                    if (!focused) endEdit(true);
                });
            }

            @Override
            public Control getEditor() {
                Platform.runLater(combo::requestFocus);
                return combo;
            }

            @Override
            public String getControlValue() {
                T selected = combo.getValue();
                return selected == null ? "" : selected.toString();
            }

            @Override
            public void end() {
                // optional cleanup
            }
        };
    }

    @Override
    public String toString(T value) {
        return value == null ? "" : value.toString();
    }

    @Override
    public boolean match(Object o, Object... os) {
        return o == null || options.contains((T)o);
    }

    @Override
    public T convertValue(Object o) {
        if (o == null) return null;
        @SuppressWarnings("unchecked")
        T typed = (T) o;
        return typed;
    }

    @Override
    public String toString() {
        return "OptionCellType";
    }
}
