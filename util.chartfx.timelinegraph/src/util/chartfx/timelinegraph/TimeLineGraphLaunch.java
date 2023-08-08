package util.chartfx.timelinegraph;

import java.awt.SplashScreen;

import com.cathive.fx.guice.GuiceApplication;


public class TimeLineGraphLaunch {
	public static void main(String[] args) {
		GuiceApplication.launch(TimeLineGraphApp.class);
		if (SplashScreen.getSplashScreen()!=null) SplashScreen.getSplashScreen().close();
		System.out.println("END main()");
		System.exit(0);
	}
}
