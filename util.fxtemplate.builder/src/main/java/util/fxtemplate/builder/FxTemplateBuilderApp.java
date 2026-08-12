package util.fxtemplate.builder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FxTemplateBuilderApp {
	private static final boolean enabled = false;
	
	private static final String filePrefix = "Test";
	
	private static final String packageName = "com.protoplant.mixer";
//	private static final String destFolder = "../../protoplant_java/";
	private static final String destFolder = "../../spike/";

//	private static final String packageName = "prj.test";
//	private static final String destFolder = "../../prj/";
	
	private static FxTemplateBuilderApp instance = null;

	private Path tmpPrjPath = null;
	private Path tmpJavPath = null;
	private Path tmpSrcPath = null;
	private Path tmpRscPath = null;

	private Path newPrjPath = null;
	private Path newJavPath = null;
	private Path newSrcPath = null;
	private Path newRscPath = null;
	
	public static void main(String[] args) {
		if (enabled) {
			instance = new FxTemplateBuilderApp();
			try {
				instance.setup();
				instance.build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Not enabled.");
		}
	}

	public void setup() throws Exception {
		tmpPrjPath = Paths.get("../util.fxtemplate");
		tmpJavPath = Paths.get(tmpPrjPath + "/src/main/java");
		tmpSrcPath = Paths.get(tmpJavPath + "/util/fxtemplate");
		tmpRscPath = Paths.get(tmpPrjPath + "/src/main/resources/util/fxtemplate");

		newPrjPath = Paths.get(destFolder+packageName);
		newJavPath = Paths.get(newPrjPath + "/src/main/java");
		newSrcPath = Paths.get(newJavPath + "/" + packageName.replace('.', '/'));
		newRscPath = Paths.get(newPrjPath + "/src/main/resources/" + packageName.replace('.', '/'));

		System.out.println("From:  " + tmpPrjPath + "    " + tmpJavPath + "    " + tmpSrcPath + "    " + tmpRscPath);
		System.out.println("  To:  " + newPrjPath + "    " + newJavPath + "    " + newSrcPath + "    " + newRscPath);
	}

	
	public void build() throws Exception {
		System.out.println("Creating directories...");
		
		Files.createDirectories(newSrcPath);
		Files.createDirectories(newRscPath);

		System.out.println("Copying files...");
		
		copyInPrj("build.gradle.kts");  //  TODO:  replace `project("` with `"java-util`
		copyInPrj("todo.txt");

		copyAndReplacePackageNameInJav("module-info.java");

		copyAndReplacePackageNameInSrc("Main.java");
		copyAndReplacePackageNameInSrc("RootController.java");
		copyAndReplacePackageNameInSrc("TemplateApp.java", filePrefix+"App.java");
		copyAndReplacePackageNameInSrc("TemplateGuice.java", filePrefix+"Guice.java");
		copyAndReplacePackageNameInSrc("TemplateConfig.java", filePrefix+"Config.java");

		copyAndReplacePackageNameInRsc("Root.fxml");
		copyInRsc("application.css");


		System.out.println("Done.");

	}

	
	
	private void copyInPrj(String fileName) throws Exception {
		Files.copy(tmpPrjPath.resolve(fileName), newPrjPath.resolve(fileName));
	}
	
	private void copyInSrc(String fileName) throws Exception {
		Files.copy(tmpSrcPath.resolve(fileName), newSrcPath.resolve(fileName));
	}

	private void copyInRsc(String fileName) throws Exception {
		Files.copy(tmpRscPath.resolve(fileName), newRscPath.resolve(fileName));
	}
	
	private void copyAndReplacePackageNameInJav(String fileName) throws Exception {
		copyAndReplacePackageName(tmpJavPath.resolve(fileName), newJavPath.resolve(fileName), false);
	}
	
	private void copyAndReplacePackageNameInSrc(String fileName) throws Exception {
		copyAndReplacePackageName(tmpSrcPath.resolve(fileName), newSrcPath.resolve(fileName), false);
	}
	
	private void copyAndReplacePackageNameInSrc(String oldFileName, String newFileName) throws Exception {
		copyAndReplacePackageName(tmpSrcPath.resolve(oldFileName), newSrcPath.resolve(newFileName), true);
	}

	private void copyAndReplacePackageNameInRsc(String fileName) throws Exception {
		copyAndReplacePackageName(tmpRscPath.resolve(fileName), newRscPath.resolve(fileName), false);
	}
	
	private void copyAndReplacePackageName(Path src, Path dst, boolean replaceFileName) throws Exception {
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(src), charset);
		content = content.replaceAll("util.fxtemplate", packageName);
		if (replaceFileName) content = content.replaceAll("Template", filePrefix);
		Files.write(dst, content.getBytes(charset));
	}
}
