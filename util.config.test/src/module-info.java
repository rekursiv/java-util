module util.config.test {
	exports util.config.test;
	opens util.config.test;

	requires transitive com.google.common;
	requires transitive com.google.guice;
	requires transitive javax.inject;
	requires transitive jakarta.inject;
	requires transitive util.config;
}