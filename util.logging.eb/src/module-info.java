module util.logging.eb {
	exports util.logging.eb;
	opens util.logging.eb;

	requires transitive com.google.common;
	requires transitive java.logging;
}