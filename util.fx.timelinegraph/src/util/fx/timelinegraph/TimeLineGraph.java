package util.fx.timelinegraph;


import java.util.logging.Logger;

import org.controlsfx.control.PlusMinusSlider;
import org.controlsfx.control.RangeSlider;

import com.google.inject.Inject;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;

public class TimeLineGraph {

	private static final int STARTING_TIME_VIEW_WIDTH = 300;	// 30 seconds at 10hz data rate
	private static final int MAX_CHART_DATA = 1800;				// 3 minutes at 10hz data rate
	
	@Inject protected Logger log;
	
	protected VBox root = new VBox();
	protected RangeSlider rsTimeRange = new RangeSlider();
	
	protected LineChart<Number, Number> chart;
	protected NumberAxis timeAxis = new NumberAxis();
	protected NumberAxis yAxis = new NumberAxis();
	
	protected int timeIndex = 0;
//	protected int curDataSize = 0;
	protected boolean followTimeHead = true;
	protected boolean followTimeTail = false;	
	
	
	
	public void init(float yLowerBound, float yUpperBound) {	
		timeAxis.setLabel("Time");
		timeAxis.setAutoRanging(false);
		timeAxis.setTickLabelsVisible(false);
		timeAxis.setTickMarkVisible(false);
		timeAxis.setMinorTickVisible(false);
		

		timeAxis.setLowerBound(0);
		timeAxis.setUpperBound(STARTING_TIME_VIEW_WIDTH);
		
		yAxis.setAutoRanging(false);
		yAxis.setLowerBound(yLowerBound);
		yAxis.setUpperBound(yUpperBound);
		
		chart = new LineChart<Number, Number>(timeAxis, yAxis);
		chart.setCreateSymbols(false);
		chart.setAnimated(false);
		chart.setHorizontalZeroLineVisible(false);
//		chart.setHorizontalGridLinesVisible(false);
		chart.setVerticalGridLinesVisible(false);
		root.getChildren().add(chart);
		VBox.setVgrow(chart, Priority.ALWAYS);
				
		rsTimeRange.setMin(0);
		rsTimeRange.setLowValue(0);
		rsTimeRange.setMax(STARTING_TIME_VIEW_WIDTH);
		rsTimeRange.setHighValue(STARTING_TIME_VIEW_WIDTH);
		
		rsTimeRange.lowValueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {
				timeAxis.setLowerBound(newValue.intValue());
				if (newValue.intValue()==timeIndex-MAX_CHART_DATA) followTimeTail = true;
				else followTimeTail = false;
//				log.info(newValue.intValue()+"   "+(timeIndex-MAX_CHART_DATA));
			}
        });
		
		rsTimeRange.highValueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {
				timeAxis.setUpperBound(newValue.intValue());
				if (newValue.intValue()<timeIndex) followTimeHead = false;
				else followTimeHead = true;
//				log.info(newValue.intValue()+"   "+timeIndex);
			}
        });
		
		root.getChildren().add(rsTimeRange);
		
	}
	
	public Node getRoot() {
		return root;
	}
	
	public void addSeries(String title) {
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName(title);
		chart.getData().add(series);
	}
	
	public void addData(int seriesIndex, Number data) {
		addDataRaw(seriesIndex, data);
		int curDataSize = chart.getData().get(seriesIndex).getData().size();
        if (curDataSize > MAX_CHART_DATA) {
        	chart.getData().get(seriesIndex).getData().remove(0, curDataSize-MAX_CHART_DATA);
        	curDataSize =  MAX_CHART_DATA;
        }
	}
	
	public void addDataRaw(int seriesIndex, Number data) {
		chart.getData().get(seriesIndex).getData().add(new Data<Number, Number>(timeIndex, data));
	}
	
	public void advanceFrame() {
		++timeIndex;
		if (timeIndex>rsTimeRange.getMax()) {
			if (timeIndex-MAX_CHART_DATA>0) rsTimeRange.setMin(timeIndex-MAX_CHART_DATA);
			else rsTimeRange.setMin(0);
			rsTimeRange.setMax(timeIndex);
			if (followTimeHead) {
				int range = (int)(rsTimeRange.getHighValue()-rsTimeRange.getLowValue());
				rsTimeRange.setHighValue(timeIndex);
				rsTimeRange.setLowValue(timeIndex-range);				
			} else if (followTimeTail) {
				int range = (int)(rsTimeRange.getHighValue()-rsTimeRange.getLowValue());
				rsTimeRange.setHighValue((timeIndex-MAX_CHART_DATA)+range+1);
			}
//			log.info((timeIndex-defaultTimeViewWidth)+"  "+timeIndex);
		}
	}
	


	public void addDataAsync(int seriesIndex, Number data) {
		Platform.runLater(()->addData(seriesIndex, data));
	}

	public void advanceFrameAsync() {
		Platform.runLater(()->advanceFrame());
	}



	public void test() {
//		chart.getData().get(0).getData().
	}
	
}
