package util.fxtemplate;
	
import java.awt.SplashScreen;
import java.util.List;

import com.cathive.fx.guice.GuiceApplication;
import com.cathive.fx.guice.GuiceFXMLLoader;
import com.google.inject.Inject;
import com.google.inject.Module;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class TemplateApp extends GuiceApplication {

    @Inject private GuiceFXMLLoader fxmlLoader;
	
    @Override
    public void init(List<Module> modules) throws Exception {
        modules.add(new TemplateGuice());
    }
    
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = fxmlLoader.load(getClass().getResource("Root.fxml")).getRoot();
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Template");
		    primaryStage.addEventHandler(WindowEvent.WINDOW_SHOWN, (evt)->{if (SplashScreen.getSplashScreen()!=null) SplashScreen.getSplashScreen().close();});
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		GuiceApplication.launch(TemplateApp.class);
		System.out.println("END main()");
		System.exit(0);
	}
}
