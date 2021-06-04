package cli;

import java.text.DecimalFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

/**
 * This class prints the progress of a task to standard output.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public class ProgressBar implements JobTracker {
	private int totalJobs;
	private int completedJobs;
	private int length;
	private int updateFrequency;
	private Instant start;

	/**
	 * Construct a progress bar.
	 *
	 * @param totalJobs       The total number of jobs to be completed.
	 * @param length          The number of characters to try to take up when printing progress updates.
	 * @param updateFrequency The number of jobs to complete before updating the progress bar.
	 */
	public ProgressBar(int totalJobs, int length, int updateFrequency) {
		this.totalJobs = totalJobs;
		this.length = length;
		this.updateFrequency = updateFrequency;
		this.completedJobs = 0;
		this.start = Instant.now();
	}

	/**
	 * Get the number of characters to try to take up when printing progress updates.
	 *
	 * @return The number of characters to try to take up when printing progress updates.
	 */
	public int length() {
		return length;
	}

	/**
	 * Set the number of characters to try to take when printing progress updates.
	 *
	 * @param length The number of characters to try to take up when printing progress updates.
	 */
	public void length(int length) {
		this.length = length;
	}

	/**
	 * Get a ratio of how much of the task has been completed.
	 *
	 * @return A number between 0 and 1 representing how much of the task has been completed.
	 */
	public double percent() {
		return (double) completedJobs / totalJobs;
	}

	@Override
	public void start() {
		this.start = Instant.now();
	}

	@Override
	public void completeOneJob() {
		boolean print;
		synchronized (this) {
			print = ++completedJobs % updateFrequency == 0;
		}
		if (print) {
			System.out.print(toString());
		}
	}

	@Override
	public void finished() {
		completedJobs = totalJobs;
		System.out.println(toString());
	}

	private String alignRight(String text, int length) {
		if (text.length() > length) {
			return text;
		}
		int spaces = length - text.length();
		return " ".repeat(spaces) + text;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String totalJobsStr = Integer.toString(totalJobs);
		sb.append(alignRight(Integer.toString(completedJobs), totalJobsStr.length()));
		sb.append('/');
		sb.append(totalJobsStr);
		sb.append("  ");
		sb.append(alignRight(new DecimalFormat("#0.0").format(percent() * 100), 5));
		sb.append("%  ");
		sb.append(alignRight(Long.toString(start.until(Instant.now(), ChronoUnit.SECONDS)), 3));
		sb.append("s  [");
		int totalBarLength = length - sb.length() - 1;
		int completedBarLength = (int) (percent() * totalBarLength);
		sb.append("#".repeat(completedBarLength));
		sb.append(" ".repeat(totalBarLength - completedBarLength));
		sb.append("]\r");
		return sb.toString();
	}
}
