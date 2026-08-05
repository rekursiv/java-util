module util.config.test {
	exports util.config.test;
	opens util.config.test;

	requires com.google.common;
	requires com.google.guice;
	requires java.inject;
	requires transitive jakarta.inject;
	requires transitive util.config;
}