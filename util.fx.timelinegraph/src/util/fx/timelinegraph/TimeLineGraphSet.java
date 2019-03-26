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

public class TimeLineGraphSet {

	private static final int STARTING_TIME_VIEW_WIDTH = 300;	// 30 seconds at 10hz data rate
//	private static final int MAX_CHART_DATA = 600;				// 1 minute at 10hz data rate
	private static final int MAX_CHART_DATA = 1800;				// 3 minutes at 10hz data rate
	
	@Inject protected Logger log;
	
	protected VBox root = new VBox();
	protected RangeSlider rsTimeRange = new RangeSlider();
	
//	protected LineChart<Number, Number> chart;
//	protected NumberAxis timeAxis = new NumberAxis();
//	protected NumberAxis yAxis = new NumberAxis();
	
	protected int timeIndex = 0;
//	protected int curDataSize = 0;
	protected boolean followTimeHead = true;
	protected boolean followTimeTail = false;	
	
	protected TimeLineGraph rootGraph = new TimeLineGraph();

	public void init(float yLowerBound, float yUpperBound) {
		init(yLowerBound, yUpperBound, "Time");
	}
	
	public void init(float yLowerBound, float yUpperBound, String timeAxisTitle) {	

		rootGraph.init(yLowerBound, yUpperBound, STARTING_TIME_VIEW_WIDTH, timeAxisTitle);
		root.getChildren().add(rootGraph.getChart());

				
		rsTimeRange.setMin(0);
		rsTimeRange.setLowValue(0);
		rsTimeRange.setMax(STARTING_TIME_VIEW_WIDTH);
		rsTimeRange.setHighValue(STARTING_TIME_VIEW_WIDTH);
		
		rsTimeRange.lowValueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {
				rootGraph.getTimeAxis().setLowerBound(newValue.intValue());
				if (root.getChildren().size()>2) {
					for (int i=0; i<root.getChildren().size()-1; ++i) {
						getGraph(i).getTimeAxis().setLowerBound(newValue.intValue());
					}
				}
				if (newValue.intValue()==timeIndex-MAX_CHART_DATA) followTimeTail = true;
				else followTimeTail = false;
//				log.info(newValue.intValue()+"   "+(timeIndex-MAX_CHART_DATA));
			}
        });
		
		rsTimeRange.highValueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> obs, Number oldValue, Number newValue) {
				rootGraph.getTimeAxis().setUpperBound(newValue.intValue());
				if (root.getChildren().size()>2) {
					for (int i=0; i<root.getChildren().size()-1; ++i) {
						getGraph(i).getTimeAxis().setUpperBound(newValue.intValue());
					}
				}
				if (newValue.intValue()<timeIndex) followTimeHead = false;
				else followTimeHead = true;
//				log.info(newValue.intValue()+"   "+timeIndex);
			}
        });
		
		root.getChildren().add(rsTimeRange);
		
	}
	
	// adds graph on TOP
	public void addGraph(float yLowerBound, float yUpperBound) {
		TimeLineGraph graph = new TimeLineGraph();
		graph.init(yLowerBound, yUpperBound, STARTING_TIME_VIEW_WIDTH, null);
		root.getChildren().add(0, graph.getChart());
	}
	
	public TimeLineGraph getGraph(int index) {
		TimeLineGraph graph = null;
		Node node = root.getChildren().get(index);
		if (node!=null) graph = (TimeLineGraph) node.getUserData();
		return graph;
	}
	
	public Node getRoot() {
		return root;
	}
	
	public int getMaxChartData() {
		return MAX_CHART_DATA;
	}
	
	///////////////
	
	public LineChart<Number, Number> getChart() {
		return rootGraph.getChart();
	}
	
	public NumberAxis getTimeAxis() {
		return rootGraph.getTimeAxis();
	}
	
	public NumberAxis getYAxis() {
		return rootGraph.getYAxis();
	}
	
	/////////////////
	
	public void addSeries(String title) {
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName(title);
		rootGraph.getChart().getData().add(series);
	}
	
	public void addData(int seriesIndex, Number data) {
		addDataRaw(seriesIndex, data);
		int curDataSize = rootGraph.getChart().getData().get(seriesIndex).getData().size();
        if (curDataSize > MAX_CHART_DATA) {
        	rootGraph.getChart().getData().get(seriesIndex).getData().remove(0, curDataSize-MAX_CHART_DATA);
        	curDataSize =  MAX_CHART_DATA;
        }
	}
	
	public void addDataRaw(int seriesIndex, Number data) {
		rootGraph.getChart().getData().get(seriesIndex).getData().add(new Data<Number, Number>(timeIndex, data));
	}
	
	/////////////////
	
	public void addSeries(int graphIndex, String title) {
		XYChart.Series<Number, Number> series = new XYChart.Series<Number, Number>();
		series.setName(title);
		getGraph(graphIndex).getChart().getData().add(series);
	}
	
	public void addData(int graphIndex, int seriesIndex, Number data) {
		addDataRaw(graphIndex, seriesIndex, data);
		int curDataSize = getGraph(graphIndex).getChart().getData().get(seriesIndex).getData().size();
        if (curDataSize > MAX_CHART_DATA) {
        	getGraph(graphIndex).getChart().getData().get(seriesIndex).getData().remove(0, curDataSize-MAX_CHART_DATA);
        	curDataSize =  MAX_CHART_DATA;
        }
	}
	
	public void addDataRaw(int graphIndex, int seriesIndex, Number data) {
		getGraph(graphIndex).getChart().getData().get(seriesIndex).getData().add(new Data<Number, Number>(timeIndex, data));
	}
	
	
	public void zoomToTimeIndex(int start, int width) {

		rsTimeRange.setHighValue(start+width);
		rsTimeRange.setLowValue(start);
	}
	
	public int advanceFrame() {
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
		return timeIndex;
	}
	


	public void addDataAsync(int graphIndex, int seriesIndex, Number data) {
		Platform.runLater(()->addData(graphIndex, seriesIndex, data));
	}

	public void advanceFrameAsync() {
		Platform.runLater(()->advanceFrame());
	}



	public void test() {
//		chart.getData().get(0).getData().
	}
	
}
