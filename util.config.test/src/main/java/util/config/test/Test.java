package util.config.test;

import javax.inject.Inject;

public class Test {
	
	@Inject
	public Test(Config config) {
		System.out.println(config.test);
	}

}
