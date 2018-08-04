package util.fx.entry;

import java.util.function.BiConsumer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;


public class IntSpinnerHelper  implements ChangeListener<Integer> {
	private Spinner<Integer> spinner;
	private BiConsumer<Integer, Boolean> onChange;
	private boolean send = true;
	
	public IntSpinnerHelper(Spinner<Integer> spinner, BiConsumer<Integer, Boolean> onChange) {
		this.spinner = spinner;
		this.onChange = onChange;
		SpinnerUtil.setup(spinner);
		spinner.getValueFactory().valueProperty().addListener(this);
	}
	
	@Override
	public void changed(ObservableValue<? extends Integer> observable, Integer oldValue, Integer newValue) {
		onChange.accept(newValue.intValue(), send);
	}

	public void setValue(int value) {
		setValue(value, true);
	}
	
	public void setValue(int value, boolean send) {
		this.send = send;
		spinner.getValueFactory().setValue(value);
		this.send = true;
	}

	
}