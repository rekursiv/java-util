package util.chartfx.timelinegraph;

import java.util.Arrays;
import java.util.logging.Logger;

import com.cathive.fx.guice.FXMLController;
import com.google.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;


public class MultiSeriesTest {

	@Inject private Logger log;
	@Inject private TimeLineGraphSet graph;

	
	private int numSeries=0;
	private int seriesIndex=0;
	private double delay=100;
	


	public void init() {
		
		graph.init(0, 2);
		
		numSeries = 4;
		
		for (int i=0; i<numSeries; ++i) {
			graph.addSeries(""+i);
		}
		delay /= numSeries;
		
//		for (int i=0; i<2000; ++i) {                   //  1800
//			graph.addData(0, Math.random());
//		}
//		graph.advanceFrame();
		
		
		TimerThread tt = new TimerThread();
		tt.setDaemon(true);
		tt.start();
		
	}
	
	public TimeLineGraphSet getGraph() {
		return graph;
	}
	
	public void onTest() {
//		graph.addData(0, Math.random());
		Circle c = new Circle();
		c.setRadius(0.1f);
//		graph.getGraph(0).getChart().getData().get(0).getData().get(2).setNode(c);
		Data<Number, Number> d = new Data<Number, Number>(10, 1);
		graph.getGraph(0).getChart().getData().get(seriesIndex).getData().get(3).setYValue(2);
	}
	
	
	private class TimerThread extends Thread {
		private float min = 0;
		private float[] pos = new float[100];
		private float max = 2;
		private float slope = 0.1f;
		
		public TimerThread() {
			Arrays.fill(pos, 1);
		}
	
		@Override
		public void run() {
			while (!isInterrupted()) {
				graph.addDataAsync(0, seriesIndex, rnd());
				++seriesIndex;
				if (seriesIndex>=numSeries) {
					graph.advanceFrameAsync();
					seriesIndex=0;
				}
				try {
					Thread.sleep((int)delay);
				} catch (InterruptedException e) {
					
				}
			}
		}
		public float rnd() {
			pos[seriesIndex]+=(0.5-Math.random())*slope;
			if (pos[seriesIndex]>max) pos[seriesIndex]=max;
			if (pos[seriesIndex]<min) pos[seriesIndex]=min;
			return pos[seriesIndex];
		}
	}
	
}
