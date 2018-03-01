package util.fxtemplate;

import java.util.logging.Logger;

import com.cathive.fx.guice.FXMLController;
import com.google.inject.Inject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

@FXMLController
public class RootController {

	@Inject private Logger log;
	
	@FXML private Button btnTest;
	
	@FXML
	private void initialize() {
		log.info("Controller loaded");
	}
	
	@FXML
	public void onTest() {
		log.info("Hello world");
	}
	
}
