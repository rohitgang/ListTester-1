import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Array-based implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported. 
 * 
 * @author rohit gangurde
 *
 * @param <T> type to store
 */
public class IUArrayList<T> implements IndexedUnsortedList<T> {
	private static final int DEFAULT_CAPACITY = 10;
	private static final int NOT_FOUND = -1;
	
	private T[] array;
	private int rear;
	private int modCount;
	
	
	/** Creates an empty list with default initial capacity */
	public IUArrayList() {
		this(DEFAULT_CAPACITY);
	
		
	}
	
	/** 
	 * Creates an empty list with the given initial capacity
	 * @param initialCapacity
	 */
	@SuppressWarnings("unchecked")
	public IUArrayList(int initialCapacity) {
		array = (T[])(new Object[initialCapacity]);
		rear = 0;
		modCount = 0;
		
		
	}
	
	/** Double the capacity of array */
	private void expandCapacity() {
		array = Arrays.copyOf(array, array.length*2);
	}
 
	private void expandIfNecessary() {
		if(rear >= size())
			expandCapacity() ;
	}
	
	
	@Override
	public void addToFront(T element) {
		// TODO 
		
		expandIfNecessary() ;
		rear++ ;
		for(int i = rear; i> 0 ; i--)
		{
			array[i] = array[i-1] ;
			
		}
		array[0] = element ;
		
		
	}

	@Override
	public void addToRear(T element) {
		// TODO 
		
		expandIfNecessary() ;
		rear++ ;
		array[rear-1] = element ;
		
		
	}

	@Override
	public void add(T element) {
		// TODO 
		
		addToRear(element) ;
		
	}

	@Override
	public void addAfter(T element, T target) {
		// TODO 
		int targetIndex = indexOf(target) ;
		if(targetIndex == -1) {
			modCount++;
			throw new NoSuchElementException () ;
		}
		else
		{
			expandIfNecessary() ;
			rear++ ;
			for(int i = rear-1; i>targetIndex+1; i--) {
				array[i] = array[i-1] ;
			}
			array[targetIndex+1] = element ;
		
		
	}
	}

	@Override
	public void add(int index, T element) {
		// TODO 
		
		if(index>rear || index<0) {
			modCount++;
			throw new IndexOutOfBoundsException() ;
		}
		else {
			expandIfNecessary() ;
			rear++;
		for(int i = rear-1; i<index; i++) {
			array[i] = array[i-1] ;
		}
		array[index] = element ;
		
		
		}
	}

	@Override
	public T removeFirst() {
		// TODO 
		modCount++;
		if(rear == 0 )
			throw new NoSuchElementException();
		else {
		T retVal = array[0];
		rear-- ;
		for (int i = 0; i < rear-1; i++) {
			array[i] = array[i+1];
		}
		
		array[rear] = null;
		
		
		
		return retVal;
		}
		}

	@Override
	public T removeLast() {
		// TODO 
		modCount++ ;
		if(isEmpty())
			throw new NoSuchElementException();
		else {		
			
		T retVal = array[rear-1] ;
		array[rear] = null ;
		rear-- ;
	
		return retVal;
	}
	}

	@Override
	public T remove(T element) {
		int index = indexOf(element);
		modCount++;
		if (index == NOT_FOUND) {
			throw new NoSuchElementException();
		}
		
		T retVal = element ;
		
		
		//shift elements
		for (int i = index; i < rear-1; i++) {
			array[i] = array[i+1];
		}
		
		array[rear] = null;
		
		rear--;
		return retVal;
	}

	
	public T remove(int index) {
		// TODO 
		modCount++;
		if(index>=rear || index<0 || array[index]==null)
			throw new IndexOutOfBoundsException() ;
		else {
		T retVal = array[index] ;
		rear--;
		for(int i = index; i<rear-1; i++) {
			array[i] = array[i+1] ;
		}
		
		array[rear] = null ;
	
		return retVal;
	}
	}

	@Override
	public void set(int index, T element) {
		// TODO 
		modCount++;
		if(index>= rear || index< 0)
			throw new IndexOutOfBoundsException() ;
		else
		array[index] = element ;
		

	}

	@Override
	public T get(int index) {
		// TODO 
		modCount++;
		if(index> rear-1 || index< 0 || array[index] == null)
			throw new IndexOutOfBoundsException() ;
		else {
		T retVal = array[index] ;
	

		return retVal;
	}
	}

	@Override
	public int indexOf(T element) {
		int index = NOT_FOUND;
		modCount++;
		if (!isEmpty()) {
			int i = 0;
			while (index == NOT_FOUND && i < rear) {
				if (element.equals(array[i])) {
					index = i;
				} else {
					i++;
				}
			}
		

		}
		
		return index;
	}
	

	@Override
	public T first() {
		// TODO 
	
		if(isEmpty())
			throw new NoSuchElementException();
		else
		{
		T retVal = array[0];
		

		return retVal;
	}
	}

	@Override
	public T last() {
		// TODO
		modCount++;
		if(isEmpty())
		throw new NoSuchElementException();
		else {
	
		T retVal = array[rear-1] ;
	

		return retVal;
	}}

	@Override
	public boolean contains(T target) {
		//modCount++;

		return (indexOf(target) != NOT_FOUND);
	}

	@Override
	public boolean isEmpty() {
		// TODO 
		modCount++;

		return (rear==0);
	}

	@Override
	public int size() {
		modCount++;

	return rear ;
	}

	@Override
	public Iterator<T> iterator() {
		return new ALIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUArrayList */
	private class ALIterator implements Iterator<T> {
		private int nextIndex;
		private int iterModCount;
		private boolean nextCalled ;

		public ALIterator() {
			nextIndex = 0;
			iterModCount = modCount;
			nextCalled = false ;
		}

		@Override
		public boolean hasNext() {
			// TODO 
		//	Iterator<T> t = iterator() ;
			nextCalled = false ;
			if(iterModCount != modCount)
				throw new ConcurrentModificationException();
			try {
			
				next() ;
				nextIndex-- ;
				//modCount-- ;
				
			}
			catch(NoSuchElementException e)
			{
				return false ;
			}
			return true;
			//if(nextIndex <=rear-1)
				//return (nextIndex < rear);
				
			
			//return false;
			
		}

		@Override
		public T next() {
			// TODO 
		//	Iterator<T> t = iterator() ;
			if(iterModCount != modCount)
				throw new ConcurrentModificationException();
			if ( isEmpty() || nextIndex >rear-1)
				throw new NoSuchElementException() ;
			else {
				nextIndex++ ;
			T temp = array[nextIndex-1] ;
			iterModCount++;
			
			nextCalled = true ;
			
			
				return temp;
		}	
		}
	
		public void remove() {	
			if(iterModCount != modCount)
				throw new ConcurrentModificationException();
			
			if(!nextCalled)
				throw new IllegalStateException() ;
//			if(nextIndex == 0)
//					throw new ConcurrentModificationException() ;
			
				for(int i = nextIndex-1; i<rear-1; i++) {
					array[i] = array[i+1] ;
				}
				
				iterModCount ++ ;
				modCount++ ;
				array[rear-1] = null;
				nextIndex-- ;
				rear--;
				nextCalled = false;
				
			   
		}
			
		
		
	}
	
	@Override
	public String toString()
	{
		StringBuilder str = new StringBuilder() ;
		str.append("[");
//		for(int i =0; i < rear; i++) {
//			str.append(array[i].toString());
//			str.append(",");
//		}
		Iterator<T> it = iterator() ;
		while(it.hasNext()) {
			str.append(it.next().toString());
			str.append(",");
		}
		if(!isEmpty()) {
			str.delete(str.length()-1,  str.length());
		}
		str.append("]");
		return str.toString();
		
	}
}


			
		
	

