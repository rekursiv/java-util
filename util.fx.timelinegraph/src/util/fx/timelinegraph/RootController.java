package util.fx.timelinegraph;

import java.util.Arrays;
import java.util.logging.Logger;

import com.cathive.fx.guice.FXMLController;
import com.google.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

@FXMLController
public class RootController {

	@Inject private Logger log;
	@Inject private TimeLineGraph graph;
	
	@FXML private StackPane spGraph;
	
	private int numSeries=0;
	private int seriesIndex=0;
	private double delay=100;
	

	@FXML
	private void initialize() {
		spGraph.getChildren().add(graph.getRoot());
		graph.init(0, 2);
		
//		for (int i=0; i<200; ++i) {                   //  1800
//			graph.addDataRaw(0, Math.random());
//		}
		
		numSeries = 8;
		for (int i=0; i<numSeries; ++i) {
			graph.addSeries(""+i);
		}
		delay /= numSeries;

		TimerThread tt = new TimerThread();
		tt.setDaemon(true);
		tt.start();
		
	}
	
	
	@FXML
	public void onTest() {
		graph.addData(0, Math.random());
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
				graph.addDataAsync(seriesIndex, rnd());
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
