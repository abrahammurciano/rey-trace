package cli;

/**
 * Classes which implement this interface are able to track the progress and process progress updates of
 * {@link JobTrackable} objects.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public interface JobTracker {
	/**
	 * React to the completion of one job.
	 */
	void completeOneJob();

	/**
	 * React to the completion of the entire task.
	 */
	void finished();
}
