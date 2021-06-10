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
	private static final String SYNOPSIS = "reytrace [OPTIONS] <INFILES>";

	private static final int THREADS_DEFAULT = 3;
	private static final int RECURSION_DEFAULT = 4;
	private static final double MIN_COEFFICIENT_DEFAULT = 0.01;

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

		try {
			cmd = parser.parse(options, args);

			checkHelp(cmd, formatter, options);

			int threads = parseArg("threads", Integer::parseInt, THREADS_DEFAULT, cmd);

			int recursion = parseArg("recursion", Integer::parseInt, RECURSION_DEFAULT, cmd);

			double minCoefficient = parseArg("min-coefficient", Double::parseDouble, MIN_COEFFICIENT_DEFAULT, cmd);

			String[] infiles = cmd.getArgs();
			if (infiles.length == 0) {
				System.out.println("Required argument <INFILES> missing");
				formatter.printHelp(SYNOPSIS, options);
				System.exit(6);
			}
			int i = 0;
			for (String infile : infiles) {
				System.out.println("(" + ++i + '/' + infiles.length + ") " + infile);
				renderXml(infile, FilenameUtils.removeExtension(infile) + ".png", threads, recursion, minCoefficient);
			}

		} catch (ParseException e) {
			System.out.println(e.getMessage());
			formatter.printHelp(SYNOPSIS, options);
			System.exit(1);
		} catch (NumberFormatException e) {
			System.out.println("Error parsing a number: " + e.getLocalizedMessage());
			System.exit(2);
		} catch (XmlParserException e) {
			System.out.println("Error parsing XML or position parameter: " + e.getLocalizedMessage());
			System.exit(4);
		}
	}

	private static void renderXml(String infile, String outfile, int threads, int recursion, double minCoefficient) {
		Scene scene;
		try {
			scene = new XmlSceneParser().parse(infile);
		} catch (IOException e) {
			System.out.println("Error: Coutld not open file \"" + infile + "\".");
			System.exit(3);
			return;
		} catch (XmlParserException e) {
			System.out.println(e.toString());
			System.out.println("There is an error in the XML file. Please check it and try again.");
			System.exit(5);
			return;
		}
		scene.geometries.optimize();
		RayTracer rayTracer = new PhongRayTracer(scene, recursion, minCoefficient);
		Renderer renderer = new Renderer(scene.camera(), rayTracer, outfile, threads);
		renderer.register(new ProgressBar(renderer.totalJobs(), 80, '#', '-'));
		renderer.render();
	}

	private static Options createOptions() {
		Options options = new Options();

		options.addOption("h", "help", false, "Print this help message.");

		options.addOption("t", "threads", true, "Number of threads to use. Default is " + THREADS_DEFAULT + ".");
		options.addOption("r", "recursion", true,
			"Maximum recursion level for reflections and refractions. Default is " + RECURSION_DEFAULT + ".");
		options.addOption("c", "min-coefficient", true,
			"Minimum effect coefficient. This is the minimum coefficient to consider worthwhile to calculate effects such as reflections, refractions, and shadows. Default is "
				+ MIN_COEFFICIENT_DEFAULT + ".");

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
