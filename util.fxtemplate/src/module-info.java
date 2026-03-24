module util.fxtemplate {
	
	exports util.fxtemplate;
	opens util.fxtemplate;

	requires transitive com.fasterxml.jackson.annotation;
	requires transitive com.google.common;
	requires transitive com.google.guice;
	requires transitive fx.guice;
	requires transitive jakarta.inject;
	requires transitive java.desktop;
	requires transitive java.logging;
	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive javafx.graphics;
	requires transitive util.config;
	requires transitive util.logging.console;
	requires junique;
	

}