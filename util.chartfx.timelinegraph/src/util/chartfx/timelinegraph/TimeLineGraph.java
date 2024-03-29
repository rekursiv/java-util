package util.chartfx.timelinegraph;


import java.util.logging.Logger;

import com.google.inject.Inject;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class TimeLineGraph {

	@Inject protected Logger log;

	protected LineChart<Number, Number> chart;
	protected NumberAxis timeAxis = new NumberAxis();
	protected NumberAxis yAxis = new NumberAxis();
	
	protected int timeIndex = 0;
	
	
	public void init(float yLowerBound, float yUpperBound, int startingTimeViewWidth, String timeAxisTitle) {	
		timeAxis.setLabel(timeAxisTitle);
		timeAxis.setAutoRanging(false);
		timeAxis.setTickLabelsVisible(false);
		timeAxis.setTickMarkVisible(false);
		timeAxis.setMinorTickVisible(false);
		
		timeAxis.setLowerBound(0);
		timeAxis.setUpperBound(startingTimeViewWidth);
		
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(yLowerBound);
		yAxis.setUpperBound(yUpperBound);
		yAxis.setTickMarkVisible(false);   /// ??
		yAxis.setMinorTickVisible(false);   /// ??
		
		chart = new LineChart<Number, Number>(timeAxis, yAxis);
		chart.setAnimated(false);
		chart.setCreateSymbols(false);
		chart.setAnimated(false);
		chart.setHorizontalZeroLineVisible(false);
		chart.setHorizontalGridLinesVisible(false);  /// ???
		chart.setVerticalGridLinesVisible(false);
		VBox.setVgrow(chart, Priority.ALWAYS);
	
		chart.setUserData(this);
	}
	
	public LineChart<Number, Number> getChart() {
		return chart;
	}
	
	public NumberAxis getTimeAxis() {
		return timeAxis;
	}
	
	public NumberAxis getYAxis() {
		return yAxis;
	}
	


}
