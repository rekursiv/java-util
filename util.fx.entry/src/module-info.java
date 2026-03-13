module util.fx.entry {
	exports util.fx.entry;
	opens util.fx.entry;

	requires transitive javafx.base;
	requires transitive javafx.controls;
	requires transitive javafx.graphics;
}