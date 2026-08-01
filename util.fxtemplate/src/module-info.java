module util.fxtemplate {
	
	exports util.fxtemplate;
	opens util.fxtemplate;

	requires java.desktop;
	requires java.logging;
	requires javafx.base;
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires junique;
	requires com.google.common;
	requires com.google.guice;
	requires com.cathive.fx.guice;
	requires jakarta.inject;
	requires java.inject;
	requires com.fasterxml.jackson.annotation;
	requires transitive util.config;
	requires transitive util.logging.console;

}
