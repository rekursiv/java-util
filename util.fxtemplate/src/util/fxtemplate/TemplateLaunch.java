package util.fxtemplate;

import java.awt.SplashScreen;
import com.cathive.fx.guice.GuiceApplication;


public class TemplateLaunch {
	public static void main(String[] args) {
		GuiceApplication.launch(TemplateApp.class);
		if (SplashScreen.getSplashScreen()!=null) SplashScreen.getSplashScreen().close();
		System.out.println("END main()");
		System.exit(0);
	}
}
