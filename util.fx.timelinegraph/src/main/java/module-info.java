module util.fx.timelinegraph {
	exports util.fx.timelinegraph;
	opens util.fx.timelinegraph;

	requires com.google.common;
	requires com.google.guice;
	requires com.cathive.fx.guice;
	requires java.desktop;
	requires java.logging;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires org.controlsfx.controls;
	requires util.logging.console;
}