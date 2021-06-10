package integration;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.Assert;
import org.junit.Test;
import cli.Cli;

public class GenerateImagesTest {
	@Test
	public void generateImagesTest() {
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**.xml");
		try (Stream<Path> paths = Files.walk(Paths.get("images"))) {
			Cli.runCommand(paths.filter(matcher::matches).map(Path::toString).toArray(String[]::new));
		} catch (Exception e) {
			Assert.fail(e.toString());
		}
	}
}
