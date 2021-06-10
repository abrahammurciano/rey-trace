package cli;

/**
 * Classes which implement this interface are able to track the progress and process progress updates of
 * {@link Task} objects.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public interface TaskTracker {
	/**
	 * React to the starting of the entire task.
	 */
	public void start();

	/**
	 * React to the completion of {@code number} jobs.
	 *
	 * @param number The number of jobs to mark as completed.
	 */
	public void completeJobs(int number);

	/**
	 * React to the completion of the entire task.
	 */
	public void finished();
}
