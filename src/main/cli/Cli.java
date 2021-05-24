package cli;

import java.io.IOException;
import java.util.function.Function;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;
import rendering.Renderer;
import rendering.raytracing.PhongRayTracer;
import rendering.raytracing.RayTracer;
import scene.Scene;
import xml.XmlParserException;
import xml.XmlSceneParser;

/**
 * A command line interface for the ray tracer.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class Cli {
	private static final String SYNOPSIS = "reytrace [OPTIONS] <INFILE> [OUTFILE]";

	/**
	 * The main entry point for the program.
	 *
	 * @param args The command line arguments.
	 */
	public static void main(String[] args) {
		Options options = createOptions();

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		int antiAliasing;
		int threads;
		String fileIn;
		String fileOut;

		try {
			cmd = parser.parse(options, args);

			checkHelp(cmd, formatter, options);

			antiAliasing = parseArg("anti-aliasing", Integer::parseInt, 3, cmd);

			threads = parseArg("threads", Integer::parseInt, 3, cmd);

			String[] remaining = cmd.getArgs();
			if (remaining.length == 0) {
				System.out.println("Required argument <INFILE> missing");
				formatter.printHelp(SYNOPSIS, options);
				System.exit(6);
			}
			fileIn = remaining[0];
			fileOut = remaining.length > 1 ? remaining[1] : FilenameUtils.removeExtension(fileIn) + ".png";

		} catch (ParseException | ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			formatter.printHelp(SYNOPSIS, options);
			System.exit(1);
			return;
		} catch (NumberFormatException e) {
			System.out.println("Error parsing a number: " + e.getLocalizedMessage());
			System.exit(2);
			return;
		} catch (XmlParserException e) {
			System.out.println("Error parsing XML or position parameter: " + e.getLocalizedMessage());
			System.exit(4);
			return;
		}

		Scene scene;
		try {
			scene = new XmlSceneParser().parse(fileIn);
		} catch (IOException e) {
			System.out.println("Error: Coutld not open file \"" + fileIn + "\".");
			System.exit(3);
			return;
		} catch (XmlParserException e) {
			System.out.println(e.toString());
			System.out.println("There is an error in the XML file. Please check it and try again.");
			System.exit(5);
			return;
		}
		RayTracer rayTracer = new PhongRayTracer(scene);
		new Renderer(scene.camera(), rayTracer, fileOut).render(threads, antiAliasing);
	}

	private static Options createOptions() {
		Options options = new Options();

		options.addOption("h", "help", false, "Print this help message.");

		options.addOption("a", "anti-aliasing", true,
			"The level of anti-aliasing to use. 1 means no anti-aliasing. 2 means moderate, 3 means extreme, and anything higher is simply overkill. Default is 2.");

		options.addOption("t", "threads", true, "Number of threads to use. Default is 8.");

		return options;
	}

	private static void checkHelp(CommandLine cmd, HelpFormatter formatter, Options options) {
		if (cmd.hasOption("help")) {
			formatter.printHelp(SYNOPSIS, options);
			System.exit(0);
		}
	}

	/**
	 * Parse an input from the command line.
	 *
	 * @param <T>          The type the input string will be parsed into.
	 * @param name         The argument name.
	 * @param parser       A function that takes a string and returns an object of type T.
	 * @param defaultValue The value to return if the argument was absent.
	 * @param cmd          The {@link CommandLine} object.
	 * @return The parsed object of type T.
	 */
	private static <T> T parseArg(String name, Function<String, T> parser, T defaultValue, CommandLine cmd) {
		String input = cmd.getOptionValue(name);
		return input == null ? defaultValue : parser.apply(input);
	}
}
