package util;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides a framework to implement iterators without having to worry about executing hasNext() twice per iteration.
 *
 * @author Abraham Murciano
 */
public abstract class EfficientIterator<T> implements Iterator<T> {

	/**
	 * A flag indicating whether there are more elements to iterate over. This flag should be set to false by the
	 * {@link #setNext()} method once there are no more elements.
	 */
	protected boolean hasNext = true;

	/**
	 * The next element to iterate over. This element should be set by the {@link #setNext()} method and must also be
	 * initialised by constructors of derived classes.
	 */
	protected T next;

	@Override
	public boolean hasNext() {
		return hasNext;
	}

	@Override
	public T next() {
		if (hasNext()) {
			T temp = next;
			setNext();
			return temp;
		}
		throw new NoSuchElementException();
	}

	/**
	 * This method should set the values of the fields {@code next} and {@code hasNext}. Each subsequent call to this
	 * method should set next to the subsequent element of the iterable.
	 *
	 * If there are more elements, then {@code next} should be set to the next element that is to be returned by
	 * {@link #next()}. Otherwise, {@code hasNext} should be set to false (the
	 * value of {@code naxt} is irrelevant in this case).
	 *
	 * This method may be used by the derived constructors to set the values of {@code next} initially in preperation
	 * for the first iteration.
	 */
	protected abstract void setNext();

}
