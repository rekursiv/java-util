module util.fx.timelinegraph {
	exports util.fx.timelinegraph;
	opens util.fx.timelinegraph;

	requires transitive com.google.common;
	requires transitive com.google.guice;
	requires transitive jakarta.inject;
	requires transitive fx.guice;
	requires transitive java.desktop;
	requires transitive java.logging;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive org.controlsfx.controls;
	requires transitive util.logging.console;
}