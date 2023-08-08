package util.chartfx.timelinegraph;

import java.util.Arrays;
import java.util.logging.Logger;

import com.cathive.fx.guice.FXMLController;
import com.google.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

@FXMLController
public class RootController {

	@Inject private Logger log;
	
//	@Inject private MultiSeriesTest test;
//	@Inject private MultiGraphTest test;
	
	@Inject private Test1 test;
	
	@FXML private StackPane spGraph;
	

	
	@FXML
	private void initialize() {
//		spGraph.getChildren().add(test.getGraph().getRoot());
//		test.init();

		test.init();
		spGraph.getChildren().add(test.getChart());

	}
	
	@FXML
	public void onTest() {
		test.onTest();
	}
	

}
