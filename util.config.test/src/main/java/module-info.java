module util.config.test {
	exports util.config.test;
	opens util.config.test;

	requires com.google.common;
	requires com.google.guice;
	requires transitive jakarta.inject;
	requires transitive util.config;
}