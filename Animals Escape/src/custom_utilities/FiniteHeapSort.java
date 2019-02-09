package custom_utilities;


/**
 * @author dvdco
 *FiniteHeap has been adapted from the HeapPriorityQueue class presented in Ch 7 of "Data Structures and Algorithsm in Java" to perform heap sorting.
 *The original implementation had twice to six times the runtime of the merge sort algorithm implemented in Arrays.sort. I determined
 *that the frequent calls to ArrayList.get() and ArrayList.add() were slowing the algorithm since they both run at O(n). I replaced the arraylist
 *with an array and stripped out the keys. This implementation is comparable to merge sort for n < 100 and better than merge sort for n > 100. 
 *
 *The price for improving its performance is that the size of the collection must be known at initialization. Also, stripping out the keys limits 
 *its ability to implement priority queues. 
 *
 * @param <E>
 */
public class FiniteHeapSort {
	protected E[] heap;
	private Comparator<E> comp;
	private int num = 0; //number of elements stored.
	
	/////////////////// Constructors /////////////////////////////////
	
	public FiniteHeap(E[] elements) {
		comp = new DefaultComparator<E>();
		heap = elements;
		num = elements.length;
		
		heapify();
	}
	
	////////////////////// Methods /////////////////////////////
	
	protected void heapify() {
		int startIndex = parent(size() - 1);
		for(int j = startIndex; j >= 0; j--)
			downheap(j);
	}
	
	protected int parent(int j) {
		return (j -1) / 2;
	}
	
	protected int left(int j) {
		return 2 * j + 1;
	}
	
	protected int right(int j) {
		return 2 * j + 2;
	}
	
	protected boolean hasLeft(int j) {
		return left(j) < num;
	}
	
	protected boolean hasRight(int j) {
		return right(j) < num;
	}
	
	protected void swap(int i, int j) {
		E temp = heap[i];
		heap[i] = heap[j];
		heap[j] = temp;
	}
	
	protected void upheap(int j) {
		while(j > 0) {
			int p = parent(j);
			if(comp.compare(heap[j], heap[p]) >= 0)
				break;
			swap(j,p);
			j = p;
		}
	}
	
	protected void downheap(int j) {
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
	
	
	public int size() {
		return num;
	}
	
	public boolean isEmpty() {
		return num == 0;
	}
	
	public E insert(E element) throws IllegalArgumentException {
		//TODO
		return null;
	}

	public E min() {
		if(isEmpty())
			return null;
		else
			return heap[0];
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
	
	void display(E[] list) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < num; i++)
			sb.append(list[i].toString() + ", ");
		System.out.println(sb.toString());
	}
}
