package util.chartfx.timelinegraph;

import java.util.logging.Logger;

import com.google.inject.Inject;

import de.gsi.chart.XYChart;
import de.gsi.chart.axes.spi.DefaultNumericAxis;
import de.gsi.dataset.spi.DoubleDataSet;

public class Test1 {

	private static final int N_SAMPLES = 100;
	
	@Inject private Logger log;
	
	private XYChart chart;
	
	
	
	public void init() {
		chart = new XYChart(new DefaultNumericAxis(), new DefaultNumericAxis());
		
		
		
        final DoubleDataSet dataSet1 = new DoubleDataSet("data set #1");
        final DoubleDataSet dataSet2 = new DoubleDataSet("data set #2");
        // lineChartPlot.getDatasets().add(dataSet1); // for single data set
        chart.getDatasets().addAll(dataSet1, dataSet2); // two data sets

        final double[] xValues = new double[N_SAMPLES];
        final double[] yValues1 = new double[N_SAMPLES];
        final double[] yValues2 = new double[N_SAMPLES];
        for (int n = 0; n < N_SAMPLES; n++) {
            xValues[n] = n;
            yValues1[n] = Math.cos(Math.toRadians(10.0 * n));
            yValues2[n] = Math.sin(Math.toRadians(10.0 * n));
        }
        dataSet1.set(xValues, yValues1);
        dataSet2.set(xValues, yValues2);
	}
	
	
	
	
	public void onTest() {
		log.info("");
	}
	
	public XYChart getChart() {
		return chart;
	}



	
	
}
