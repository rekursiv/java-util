package util.fx.timelinegraph;

import java.util.Arrays;
import java.util.logging.Logger;

import com.cathive.fx.guice.FXMLController;
import com.google.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;


public class MultiGraphTest {

	@Inject private Logger log;
	@Inject private TimeLineGraphSet graph;
	

	
	private int numGraphs=0;
	private int graphIndex=0;
	private double delay=100;
	


	public void init() {
		graph.init(0, 2);

		numGraphs = 4;
		graph.addSeries(""+(numGraphs-1));
		

		for (int i=1; i<numGraphs; ++i) {
			graph.addGraph(0, 2);
		}
		for (int i=0; i<numGraphs-1; ++i) {
			graph.addSeries(i, ""+i);
		}
		
		delay /= numGraphs;
		

		TimerThread tt = new TimerThread();
		tt.setDaemon(true);
		tt.start();
		
	}
	
	
	public TimeLineGraphSet getGraph() {
		return graph;
	}
	
	public void onTest() {
//		graph.addData(0, Math.random());
		graph.zoomToTimeIndex(10, 10);
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
				graph.addDataAsync(graphIndex, 0, rnd());
				++graphIndex;
				if (graphIndex>=numGraphs) {
					graph.advanceFrameAsync();
					graphIndex=0;
				}
				try {
					Thread.sleep((int)delay);
				} catch (InterruptedException e) {
					
				}
			}
		}
		public float rnd() {
			pos[graphIndex]+=(0.5-Math.random())*slope;
			if (pos[graphIndex]>max) pos[graphIndex]=max;
			if (pos[graphIndex]<min) pos[graphIndex]=min;
			return pos[graphIndex];
		}
	}
	
}
