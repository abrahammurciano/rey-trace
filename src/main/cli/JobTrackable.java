package cli;

import java.util.List;

/**
 * Classes which inherit this interface provide a way for registered {@link JobTracker}s to track the progress of a
 * large task this object is doing which consists of many small jobs.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public interface JobTrackable {

	/**
	 * Get the total number of jobs to be performed.
	 *
	 * @return The total number of jobs to be performed.
	 */
	public int totalJobs();

	/**
	 * Register a {@link JobTracker} to track the progress of this object.
	 *
	 * @param jobTracker The job tracker to track the progress of this object.
	 */
	public default void register(JobTracker jobTracker) {
		jobTrackers().add(jobTracker);
	}

	/**
	 * Let all registered trackers know that the task has been started.
	 */
	public default void start() {
		jobTrackers().forEach(JobTracker::start);
	}

	/**
	 * Let all registered trackers know that the task has been completed.
	 */
	public default void finished() {
		jobTrackers().forEach(JobTracker::finished);
	}

	public default void performTask(Runnable task) {
		start();
		task.run();
		finished();
	}

	/**
	 * Let all registered trackers know that one more job has been completed.
	 */
	public default void completeOneJob() {
		jobTrackers().forEach(JobTracker::completeOneJob);
	}

	/**
	 * Get a list of all registered {@link JobTracker}s.
	 *
	 * @return A list of all registered {@link JobTracker}s.
	 */
	public List<JobTracker> jobTrackers();
}
