package custom_utilities;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * This class is a wrapper for FiniteHeapSort that implements the Queue interface.
 * @author dvdco
 *
 * @param <E>Any array or collections of comparable objects.
 */
public class FiniteHeapQueue<E> implements Queue<E> {
	
	private FiniteHeapSort<E> sorted;
	
	///////////////////// Constructors /////////////////////
	
	public FiniteHeapQueue(E[] elements, Comparator<E> comp) {
		sorted = new FiniteHeapSort<>(elements, comp);
	}
	
	public FiniteHeapQueue(E[] elements) {
		sorted = new FiniteHeapSort<>(elements);
	}
	
	public FiniteHeapQueue(Collection<E> collection){
		sorted = new FiniteHeapSort<>(collection);
	}
	
	public FiniteHeapQueue(Collection<E> collection, Comparator<E> comp){
		sorted = new FiniteHeapSort<>(collection, comp);
	}
	
	@Override
	public int size() {
		return sorted.size();
	}

	@Override
	public boolean isEmpty() {
		return sorted.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		return sorted.iterator();
	}

	@Override
	public Object[] toArray() {
		return sorted.toArray();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T[] toArray(T[] a) throws ArrayStoreException, NullPointerException {
		
		if(a == null)
			throw new NullPointerException();
		
		int size = sorted.size();
		
		if(a.length < size) {
			//The unchecked cast here is permissible because the reflection library
			//assures reification.
			a = (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
		}
		
		//TODO
	}

	@Override
	public boolean remove(Object o) throws ClassCastException, NullPointerException{
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean add(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean offer(E e) {
		// TODO Auto-generated method stub
		return false;
	}

	
	/**
	 * Returns and removes head of queue.
	 * Adheres to the Queue interface contract by throwing an
	 * exception if empty.
	 */
	@Override
	public E remove() throws NoSuchElementException{
		if(sorted.isEmpty()) {
			throw new NoSuchElementException();
		}
		else {
			return sorted.removeMin();
		}	
	}
	
	/**
	 * Return and removes head of queue.
	 * Adheres to the Queue interface contract by returning null
	 * when empty.
	 */
	@Override
	public E poll() {
		return sorted.removeMin();
	}
	
	/**
	 * Returns, without removing, the head of the queue Throws an
	 * exception if empty.
	 */
	@Override
	public E element() throws NoSuchElementException{
		if(sorted.isEmpty())
			throw new NoSuchElementException();
		return sorted.min();
	}

	/**
	 * Returns, without removing, the head of the queue. Returns null
	 * if empty.
	 */
	@Override
	public E peek() {
		return sorted.min();
	}
	
}
