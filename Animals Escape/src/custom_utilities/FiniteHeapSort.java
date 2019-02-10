package custom_utilities;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

/**
 * 
 *FiniteHeap has been adapted from the HeapPriorityQueue class presented in Ch 7 of "Data Structures and Algorithsm in Java" to perform heap sorting.
 *The original implementation had twice to six times the runtime of the merge sort algorithm implemented in Arrays.sort. I determined
 *that the frequent calls to ArrayList.get() and ArrayList.add() were slowing the algorithm since they both run at O(n). I replaced the arraylist
 *with an array and stripped out the keys. This implementation is comparable to merge sort for n < 100 and better than merge sort for n > 100. 
 *
 *The price for improving its performance is that the size of the collection must be known at initialization. Also, stripping out the keys limits 
 *its ability to implement priority queues. 
 *
 *@author David Cox
 * @param <E> the type of element sorted in the heap.
 */
public class FiniteHeapSort<E> {
	
	/**
	 * Array storing the heap. It's length is set exactly to the length
	 * of the specified collection or array. It also should not be exposed to the public
	 * because it relies on unchecked casts
	 * The "principle of indecent exposure" found in "Java Generics" requires that unchecked
	 * arrays are not exposed to the public. 
	 */
	private E[] heap;
	private Comparator<E> comp;
	private int num = 0; //number of elements stored.
	
	/////////////////// Constructors /////////////////////////////////
	
	public FiniteHeapSort(E[] elements, Comparator<E> comp) {
		this.comp = comp;
		heap = elements;
		num = elements.length;
		heapify();
	}
	
	public FiniteHeapSort(E[] elements) {
		this(elements, new DefaultComparator<E>());
	}
	
	public FiniteHeapSort(Collection<E> collection){
		this(collection, new DefaultComparator<E>());
		
	}
	
	@SuppressWarnings("unchecked")
	public FiniteHeapSort(Collection<E> collection, Comparator<E> comp){
		//This unchecked cast is permitted as long as the principle of indecent
		//exposure is not violated.
		this(collection.toArray((E[])new Object[collection.size()]), comp);
		
	}
	
	////////////////////// Helper  Methods /////////////////////////////
	
	/**
	 * Bottom up construction of heap. Runs at O(n).
	 */
	private void heapify() {
		int startIndex = parent(size() - 1);
		for(int j = startIndex; j >= 0; j--)
			downheap(j);
	}
	
	private int parent(int j) {
		return (j -1) / 2;
	}
	
	private int left(int j) {
		return 2 * j + 1;
	}
	
	private int right(int j) {
		return 2 * j + 2;
	}
	
	private boolean hasLeft(int j) {
		return left(j) < num;
	}
	
	private boolean hasRight(int j) {
		return right(j) < num;
	}
	
	private void swap(int i, int j) {
		E temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	
	private void upheap(int j) {
		while(j > 0) {
			int p = parent(j);
			if(comp.compare(heap[j], heap[p]) >= 0)
				break;
			swap(j,p);
			j = p;
		}
	}
	
	private void downheap(int j) {
		while(hasLeft(j)) {
			int leftIndex = left(j);
			int smallChildIndex = leftIndex;
			if(hasRight(j)) {
				int rightIndex = right(j);
				if(comp.compare(heap[leftIndex], heap[rightIndex]) > 0)
					smallChildIndex = rightIndex;
			}
			if(comp.compare(heap[smallChildIndex], heap[j]) >= 0)
				break;
			swap(j, smallChildIndex);
			j = smallChildIndex;
		}
	}
	
	
	
	//////////////////////// Acessor Methods //////////////////
	
	public int size() {
		return num;
	}
	
	public Iterator<E> iterator(){
		//TODO
		return null;
	}
	
	public E min() {
		if(isEmpty())
			return null;
		else
			return heap[0];
	}
	
	/**
	 * Returns a sorted array. Runs at O(n) time.
	 * @return Sorted array
	 */
	public Object[] toArray() {
		Object[] output = new Object[num];
		
		for(int i = 0; i < num; i++) {
			output[i] = heap[i];
		}
		return output;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T[] toArray(T[] a) throws ArrayStoreException, NullPointerException {
		
		if(a == null)
			throw new NullPointerException();
		
		
		if(a.length < heap.length) {
			//The unchecked cast here is permissible because the reflection library
			//assures reification.
			a = (T[])java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), heap.length);
		}
		
		//TODO
	}
	
	
	///////////////////// Checker Methods //////////////////////
	
	public boolean isEmpty() {
		return num == 0;
	}
	
	////////////////////// Mutator Methods /////////////////////
	
	public E insert(E element) throws IllegalArgumentException {
		//TODO
		return null;
	}

	public E removeMin() {
		if(isEmpty())
			return null;
		else {
			E answer = heap[0];
			swap(0, num - 1);
			heap[num - 1] = null;//remove last element.
			num--;//decrement number of elements.
			downheap(0);
			return answer;
		}
	}
	
	///////////////////// Debugging //////////////////////////
	
	void display(E[] list) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < num; i++)
			sb.append(list[i].toString() + ", ");
		System.out.println(sb.toString());
	}
	
	//////////////////////////// Inner Classes /////////////////////////
	
	private static class DefaultComparator<T> implements Comparator<T>{
		
		/**
		 * Any potential exceptions resulting from the unchecked cast are
		 * handled by the Comparator API's ClassCastException
		 */
		@SuppressWarnings("unchecked")
		public int compare(T a, T b) throws ClassCastException, NullPointerException{
			if(a == null || b == null)
				throw new NullPointerException();
			return ((Comparable<T>) a).compareTo(b);//unchecked cast
			
		}
	}
}
