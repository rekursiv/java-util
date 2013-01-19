package util.config.test;
import com.google.inject.Inject;

public class Test {
	
	@Inject
	public Test(Config config) {
		System.out.println(config.test);
	}

}
