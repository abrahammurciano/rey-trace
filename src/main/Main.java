import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FilenameUtils;
import rendering.Renderer;

public class Main {
	public static void main(String[] args) {
		Options options = new Options();

		Option threadsOp = new Option("t", "threads", true, "Number of threads to use. Default is 8.");
		options.addOption(threadsOp);

		Option antiAliasingOp = new Option("a", "anti-aliasing", true,
			"The level of anti-aliasing to use. 1 means no anti-aliasing. 2 means moderate, 3 means extreme, and anything higher is simply overkill. Default is 2.");
		options.addOption(antiAliasingOp);

		CommandLineParser parser = new DefaultParser();
		HelpFormatter formatter = new HelpFormatter();
		CommandLine cmd;

		int threads;
		int antiAliasing;
		String fileIn;
		String fileOut;

		try {
			cmd = parser.parse(options, args);

			String threadsIn = cmd.getOptionValue("threads");
			threads = threadsIn == null ? 8 : Integer.parseInt(threadsIn);

			String antiAliasingIn = cmd.getOptionValue("anti-aliasing");
			antiAliasing = antiAliasingIn == null ? 2 : Integer.parseInt("anti-aliasing");

			String[] remaining = cmd.getArgs();

			fileIn = remaining[0];
			fileOut = remaining.length > 1 ? remaining[1] : FilenameUtils.removeExtension(fileIn) + ".png";

		} catch (ParseException | ArrayIndexOutOfBoundsException e) {
			System.out.println(e.getMessage());
			formatter.printHelp("reytrace [OPTIONS] <INFILE> [OUTFILE]", options);
			System.exit(1);
			return;
		}

		new Renderer(camera, rayTracer, filename);
	}
}
