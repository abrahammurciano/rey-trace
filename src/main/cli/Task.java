package cli;

import java.util.List;

/**
 * Classes which inherit this interface provide a way for registered {@link TaskTracker}s to track the progress of a
 * large task this object is doing which consists of many small jobs.
 *
 * @author Abraham Murciano
 * @author Eli Levin
 */
public interface Task {

	/**
	 * Get the total number of jobs to be performed.
	 *
	 * @return The total number of jobs to be performed.
	 */
	public int totalJobs();

	/**
	 * Register a {@link TaskTracker} to track the progress of this object.
	 *
	 * @param taskTracker The task tracker to track the progress of this object.
	 */
	public default void register(TaskTracker taskTracker) {
		taskTrackers().add(taskTracker);
	}

	/**
	 * Let all registered trackers know that the task has been started.
	 */
	public default void start() {
		taskTrackers().forEach(TaskTracker::start);
	}

	/**
	 * Let all registered trackers know that the task has been completed.
	 */
	public default void finished() {
		taskTrackers().forEach(TaskTracker::finished);
	}

	/**
	 * The task to be performed. This function will be called by {@link #performTask()}.
	 */
	public void task();

	/**
	 * Trigger the task. This function notifies all registered task trackers that the task has commenced, then performs
	 * the task, then notifies registered trackers that it has completed.
	 */
	public default void performTask() {
		start();
		task();
		finished();
	}

	/**
	 * Let all registered trackers know that one more job has been completed.
	 *
	 * @param number The number of jobs to mark as completed.
	 */
	public default void completeJobs(int number) {
		taskTrackers().forEach(tracker -> tracker.completeJobs(number));
	}

	/**
	 * Get a list of all registered {@link TaskTracker}s.
	 *
	 * @return A list of all registered {@link TaskTracker}s.
	 */
	public List<TaskTracker> taskTrackers();
}
