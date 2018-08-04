package util.fx.entry;

import java.util.function.BiConsumer;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;

public class FloatSpinnerHelper implements ChangeListener<Double> {
	private Spinner<Double> spinner;
	private BiConsumer<Float, Boolean> onChange;
	private boolean send = true;
	
	public FloatSpinnerHelper(Spinner<Double> spinner, BiConsumer<Float, Boolean> onChange, String pattern) {
		this.spinner = spinner;
		this.onChange = onChange;

		SpinnerUtil.setup(spinner, pattern);
		spinner.getValueFactory().valueProperty().addListener(this);
	}
	
	@Override
	public void changed(ObservableValue<? extends Double> observable, Double oldValue, Double newValue) {
		onChange.accept(newValue.floatValue(), send);
	}

	public void setValue(double value) {
		setValue(value, true);
	}
	
	public void setValue(double value, boolean send) {
		this.send = send;
		spinner.getValueFactory().setValue(value);
		this.send = true;
	}
	
	public void setValue(float value) {
		setValue(value, true);
	}
	
	public void setValue(float value, boolean send) {
		setValue((double)value, send);
	}
		
	
}