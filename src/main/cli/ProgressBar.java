package cli;

import java.text.DecimalFormat;

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

	/**
	 * Construct a progress bar.
	 *
	 * @param totalJobs The total number of jobs to be completed.
	 * @param length    The number of characters to try to take up when printing progress updates.
	 */
	public ProgressBar(int totalJobs, int length) {
		this.totalJobs = totalJobs;
		this.length = length;
		this.completedJobs = 0;
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
	public void completeOneJob() {
		boolean print;
		synchronized (this) {
			print = ++completedJobs % 10000 == 0;
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

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String totalJobsStr = Integer.toString(totalJobs);
		String completedJobsStr = Integer.toString(completedJobs);
		int spaces = totalJobsStr.length() - completedJobsStr.length();
		sb.append(" ".repeat(spaces));
		sb.append(completedJobsStr);
		sb.append('/');
		sb.append(totalJobsStr);
		sb.append("  ");
		sb.append(new DecimalFormat("#0.0").format(percent() * 100));
		sb.append("%  [");
		int totalBarLength = length - sb.length() - 1;
		int completedBarLength = (int) (percent() * totalBarLength);
		sb.append("#".repeat(completedBarLength));
		sb.append(" ".repeat(totalBarLength - completedBarLength));
		sb.append("]\r");
		return sb.toString();
	}
}
