package util.fx.entry;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public class DateColumnFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {

    private DateTimeFormatter format;

    public DateColumnFactory(DateTimeFormatter format) {
        super();
        this.format = format;
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>() {

            @Override
            protected void updateItem(T item, boolean empty) {
                if (!empty && item != null) {
                	setText(LocalDate.parse((CharSequence)item, DateTimeFormatter.ofPattern("yyyyMMdd")).format(format));
                } else {
                    setText("");
                }
            }
        };
    }
}
