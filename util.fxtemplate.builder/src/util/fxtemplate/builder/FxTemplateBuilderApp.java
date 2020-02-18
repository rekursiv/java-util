package util.fxtemplate.builder;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FxTemplateBuilderApp {
	private static final boolean enabled = false;
	
	private static final String filePrefix = "AmazonAddress";
	
	private static final String packageName = "com.protoplant.amazon.address";
	private static final String destFolder = "../../protoplant_java/";

//	private static final String packageName = "prj.fuguelooper";
//	private static final String destFolder = "../../prj/";
	
	private static FxTemplateBuilderApp instance = null;

	private Path tmpPrjPath = null;
	private Path tmpSrcPath = null;
	private Path newPrjPath = null;
	private Path newSrcPath = null;
	
	public static void main(String[] args) {
		if (enabled) {
			instance = new FxTemplateBuilderApp();
			try {
				instance.build();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Not enabled.");
		}
	}

	
	public void build() throws Exception {
		tmpPrjPath = Paths.get("../util.fxtemplate/");
		tmpSrcPath = Paths.get("../util.fxtemplate/src/util/fxtemplate/");
		newPrjPath = Paths.get(destFolder+packageName);
		newSrcPath = newPrjPath.resolve("src/"+packageName.replace(".", "/"));


		System.out.println("Creating directories...");
		
		Files.createDirectory(newPrjPath);
		Files.createDirectory(newPrjPath.resolve("bin"));
		Files.createDirectories(newSrcPath);
		
		
		System.out.println("Copying files...");
		
		copyAndReplacePackageNameInPrj(".project");
		copyAndReplacePackageNameInPrj("build.fxbuild");
		
		copyInPrj(".classpath");
		copyInPrj("todo.txt");
		
		
		copyAndReplacePackageNameInSrc("Root.fxml");
		copyAndReplacePackageNameInSrc("RootController.java");
		copyAndReplacePackageNameInSrc("TemplateLaunch.java", filePrefix+"Launch.java");
		copyAndReplacePackageNameInSrc("TemplateApp.java", filePrefix+"App.java");
		copyAndReplacePackageNameInSrc("TemplateGuice.java", filePrefix+"Guice.java");
		copyAndReplacePackageNameInSrc("TemplateConfig.java", filePrefix+"Config.java");
		
		copyInSrc("application.css");
		System.out.println("Done.");
	}

	
	
	private void copyInPrj(String fileName) throws Exception {
		Files.copy(tmpPrjPath.resolve(fileName), newPrjPath.resolve(fileName));
	}
	
	private void copyInSrc(String fileName) throws Exception {
		Files.copy(tmpSrcPath.resolve(fileName), newSrcPath.resolve(fileName));
	}
	
	private void copyAndReplacePackageNameInPrj(String fileName) throws Exception {
		copyAndReplacePackageName(tmpPrjPath.resolve(fileName), newPrjPath.resolve(fileName), false);
	}
	
	private void copyAndReplacePackageNameInSrc(String fileName) throws Exception {
		copyAndReplacePackageName(tmpSrcPath.resolve(fileName), newSrcPath.resolve(fileName), false);
	}
	
	private void copyAndReplacePackageNameInSrc(String oldFileName, String newFileName) throws Exception {
		copyAndReplacePackageName(tmpSrcPath.resolve(oldFileName), newSrcPath.resolve(newFileName), true);
	}
	
	private void copyAndReplacePackageName(Path src, Path dst, boolean replaceFileName) throws Exception {
		Charset charset = StandardCharsets.UTF_8;
		String content = new String(Files.readAllBytes(src), charset);
		content = content.replaceAll("util.fxtemplate", packageName);
		if (replaceFileName) content = content.replaceAll("Template", filePrefix);
		Files.write(dst, content.getBytes(charset));
	}
}
