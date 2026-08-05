package util.fx.entry;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;

import javafx.scene.control.Spinner;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public class SpinnerUtil {
	
	public static void setup(Spinner<Integer> spn) {
		NumberFormat format = NumberFormat.getIntegerInstance();
		spn.getEditor().setTextFormatter( new TextFormatter<Double>(c ->
		{
			if (c.getControlNewText().isEmpty()) return c;
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = format.parse(c.getControlNewText(), parsePosition);
			if (object == null||parsePosition.getIndex()<c.getControlNewText().length()) return null;
			else return c;
		}));
		spn.focusedProperty().addListener((observable, oldValue, newValue) -> {if (!newValue) {spn.increment(0);}});  // update on loose focus
	}

	public static void setup(Spinner<Double> spn, String pattern) {
		DecimalFormat fmt = new DecimalFormat(pattern);
		spn.getValueFactory().setConverter(new StringConverter<Double>() {
			@Override public String toString(Double value) {
				if (value == null) return "";
				else return fmt.format(value);
			}
			@Override public Double fromString(String value) {
				try {
					if (value == null) return null;
					value = value.trim();
					if (value.length() < 1) return null;
					return fmt.parse(value).doubleValue();
				} catch (ParseException ex) {return null;}
			}
		});
		spn.getEditor().setTextFormatter( new TextFormatter<Double>(c ->
		{
			if (c.getControlNewText().isEmpty()) return c;
			ParsePosition parsePosition = new ParsePosition(0);
			Object object = fmt.parse(c.getControlNewText(), parsePosition);
			if (object == null||parsePosition.getIndex()<c.getControlNewText().length()) return null;
			else return c;
		}));
		spn.focusedProperty().addListener((observable, oldValue, newValue) -> {if (!newValue) {spn.increment(0);}});  // update on loose focus
	}
	
}
