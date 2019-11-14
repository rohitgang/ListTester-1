import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

//import com.sun.org.apache.xml.internal.security.keys.content.RetrievalMethod;

/**
 * Double linked list which implements the methods of the IndexedUnsortedList interface.
 * 
 * 
 * @author rohit gangurde
 * 
 * @param <T> type to store
 */


public class IUDoubleLinkedList<T> implements IndexedUnsortedList<T> {
	private LinearNode<T> head;
	private LinearNode<T> tail;
	private int size;
	private int modCount;

	/**
	 * constructor to initialize the variables
	 */
	public IUDoubleLinkedList() {
		head = null;
		tail = null;
		size = 0;
		modCount = 0;
	}

	
	/**
	 * adds element to the front of the list
	 * @param element 
	 */
	public void addToFront(T element) {
		ListIterator<T> iter = listIterator(); 	
		iter.add(element);
	}

	
	/**
	 * adds element to the rear of the list
	 * @param element
	 */
	public void addToRear(T element) {
		
		LinearNode<T> nodeAdd = new LinearNode<T>();
		if (head == null) {
			head = new LinearNode<T>();
			head.setElement(element);
		} else {
			nodeAdd.setElement(element);
			tail.setNext(nodeAdd);
			nodeAdd.setPrevious(tail) ;
		}
		tail = nodeAdd;
		size++;
		modCount++;
		
	}

	
	/**
	 * adds element to the rear of the list
	 * @param element
	 */
	public void add(T element) {

		addToRear(element); 

	}

	
	/**
	 * adds element after the target element
	 * @param element
	 * @param target
	 */
	public void addAfter(T element, T target) {		
		LinearNode<T> nodeTarget = head;
		while (nodeTarget != null ) {
			if(nodeTarget.getElement().equals(target))
				break ;
			nodeTarget = nodeTarget.getNext();
		}
		if (nodeTarget == null) {
			throw new NoSuchElementException();
		}
		LinearNode<T> nodeAdd = new LinearNode<T>();
		nodeAdd.setElement(element);
		nodeAdd.setNext(nodeTarget.getNext());
		nodeAdd.setPrevious(nodeTarget);
		nodeTarget.setNext(nodeAdd);
		if (nodeTarget == tail) {
			tail = nodeAdd;
		} else {
			nodeAdd.getNext().setPrevious(nodeAdd);
		}
		size++;
		modCount++;
	}

	
	/**
	 * adds element at the specified index
	 * @param index
	 * @param element
	 */
	public void add(int index, T element) {

		if (index < 0 || index > size) 
			throw new IndexOutOfBoundsException();	
		ListIterator<T> iter = listIterator(index);
		iter.add(element);
		
		}
	

	
	/**
	 * removes the first element of the list
	 * @return retVal
	 */
	public T removeFirst() {

		if (head == null) {
			throw new NoSuchElementException();
		}
		T retVal = head.getElement();
		head = head.getNext();
		if (size == 1) {
			tail = null;
		}
		modCount++;
		size--;
		return retVal;

	}

	
	/**
	 * removes the last element of the list
	 * @return retVal
	 */
	public T removeLast() {
		
		if (head == null) {
			throw new NoSuchElementException();
		}
		T retVal = tail.getElement();
		if (size <= 1) {
			head = null;
			tail = null;
			size--;
			modCount++;
		} else {
		tail.getPrevious().setNext(null);		
		tail = tail.getPrevious();
		size--;
		modCount++;
		}
		return retVal;
	}

	
	/**
	 * removes the specifies element from the list
	 * @param element
	 * @return nodePointer.getElement()
	 */
	public T remove(T element) {
		
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		boolean elementFound = false ;
		LinearNode<T> nodePointer = head;

		while (nodePointer != null ) {
			if (element.equals(nodePointer.getElement())) {
				elementFound = true ;
				break;
			}
				nodePointer = nodePointer.getNext();
			}
		if(!elementFound)
			throw new NoSuchElementException () ;
		if (size() == 1) { 
			head = tail = null;
		} else if (nodePointer == head) { 
			head.getNext().setPrevious(null);
			head = nodePointer.getNext();
		} else if (nodePointer == tail) { 
			tail = tail.getPrevious();
			tail.setNext(null);
		} else { 
			nodePointer.getPrevious().setNext(nodePointer.getNext());
		}
		size--;
		modCount++;
		return nodePointer.getElement();

	}


	/**
	 * removes the element at the specified index
	 * @rparam index
	 * @return retVal
	 */
	public T remove(int index) {
		if (index < 0 || index >= size)  
			throw new IndexOutOfBoundsException();
		ListIterator<T> iter = listIterator(index); 
		T retVal = iter.next();
		iter.remove();
		return retVal;
	}

	/**
	 * replaces the element at the index with given element
	 * @param index
	 * @param element
	 */
	public void set(int index, T element) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> nodePointer = head;
		for (int i = 0; i < index; i++) {
			nodePointer = nodePointer.getNext();
		}
		nodePointer.setElement(element);
		modCount++;
	}

	/**
	 * returns the element at the index
	 * @param index
	 */
	public T get(int index) {		
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> nodePointer = head;
		for (int i = 0; i < index; i++) {
			nodePointer = nodePointer.getNext();
		}
		T retVal = nodePointer.getElement();
		return retVal;
	}

	
	/**
	 * returns the index of the element
	 * @param element
	 * @return index
	 */
	public int indexOf(T element) {		
//		ListIterator<T> it = listIterator();   Using Iterator, unable to reach the last element
//		T retVal = null;
//		int index = 0;
//		int NOT_FOUND = -1;
//		while (it.hasNext() ) {
//			if(element.equals(retVal)) {
//				NOT_FOUND= 0;
//				break ;
//			}
//			retVal = it.next();
//			index++;
//		}
//		if (NOT_FOUND == -1)
//			return NOT_FOUND;
		
		LinearNode<T> nodePointer = head;
		int index = 0;
		int NOT_FOUND = -1;
		while (nodePointer != null && NOT_FOUND == -1) {
			if (nodePointer.getElement().equals(element)) {
				NOT_FOUND = 0;
				break;
			}
			nodePointer = nodePointer.getNext();
			index++;
		}
		if (NOT_FOUND == -1)
			return NOT_FOUND;


		return (index);

		
	}

	
	/**
	 * returns the first element of the list
	 * @return head.getElement()
	 */
	public T first() {
		if (head == null) {
			throw new NoSuchElementException();
		}
		return head.getElement();
	}

	
	/**
	 * returns the last element of the list
	 * @return tail.getElement()
	 */
	public T last() {
		if (head == null) {
			throw new NoSuchElementException();
		}
		return tail.getElement();
	}

	
	/**
	 * checks if the list contains the target
	 * @param target
	 * @return elementFound
	 */
	public boolean contains(T target) {		
		LinearNode<T> nodePointer = head;
		boolean elementFound = false;
		while (nodePointer != null) {
			if (nodePointer.getElement().equals(target)) {
				elementFound = true;
				break;
			}
			nodePointer = nodePointer.getNext();
		}
		return elementFound;
	}

		/**
		 * converts the list into string
		 * @return str.toString()
		 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("[");
		// for(int i =0; i < rear; i++) {
		// str.append(array[i].toString());
		// str.append(",");
		// }
		Iterator<T> it = iterator();
		while (it.hasNext()) {
			str.append(it.next().toString());
			str.append(",");
		}
		if (!isEmpty()) {
			str.delete(str.length() - 1, str.length());
		}
		str.append("]");
		return str.toString();
	}


	/**
	 * checks if the list is empty
	 * @return true or false
	 */
	public boolean isEmpty() {
		return (head == null);
	}

	
	/**
	 * returns the size of the list
	 * @return size
	 */
	public int size() {
		return size;
	}

 
	/**
	 * returns the list iterator
	 */
	public Iterator<T> iterator() {

		return new DLLIterator();
	}

	 
	/**
	 * returns the list iterator
	 */
	public ListIterator<T> listIterator() {

		return new DLLIterator();
	}

	 
	/**
	 * returns the iterator with the starting index
	 * @param startingIndex
	 */
	public ListIterator<T> listIterator(int startingIndex) {

		return new DLLIterator(startingIndex);
	}

	/**
	 * Class for list iterator used in manipulating and navigating through double linked list\
	 * @author rohitgangurde
	 */
	private class DLLIterator implements ListIterator<T> {
		private LinearNode<T> nextNode,  nodePrevious, exPrevNode;
		private int index;
		private int iterModCount;
		private int prevCalled, nextCalled;
		
		/**
		 * constructor which redirects to the main constructor
		 */
		public DLLIterator() {
			this(0);
		}

		/**
		 * main constructor which initializes the variables and sets the starting index
		 * @param nextIndex
		 */
		public DLLIterator(int nextIndex) {

			if (index < 0 || index > size) {
				throw new IndexOutOfBoundsException();
			}
			index = nextIndex;
			iterModCount = modCount;
			nextNode = head;
			nodePrevious = null;
			for (int i = 0; i < index; i++) {
				nextNode = nextNode.getNext();
			}	
		}

		/**
		 * checks for concurrent modification error
		 */
		public void connModification() {
			if (iterModCount != modCount) {
				throw new ConcurrentModificationException();
			}

		}

		 
		/**
		 * checks if the list has an element going forwards
		 * @return true or false
		 */
		public boolean hasNext() {
			connModification();
		//	index++;
			nextCalled = 0;
			prevCalled = 0;
			if(nextNode != null)
			return true;
			return false ;
		}

		 
		/**
		 * returns the next element corresponding to the iterator
		 * @return retVal
		 */
		public T next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			T retVal = nextNode.getElement();
			nodePrevious = nextNode;
			nextNode = nextNode.getNext();
			index++;
			modCount++;
			iterModCount++;
			return retVal;
		}

		 
		/**
		 * checks if the list has element going backwards
		 * @return true or false
		 */
		public boolean hasPrevious() {
			connModification();
			if(nextNode == head)
				return false;
			return true;
		}

		 
		/**
		 * returns the previous element corresponding to the iterator
		 * @return retVal
		 */
		public T previous() {
			connModification();
			if (!hasPrevious()) {
				throw new NoSuchElementException();
			}
			if (nextNode == null) {
				nextNode = tail;
				nodePrevious = nextNode;
			} else {
				nextNode = nextNode.getPrevious();
				nodePrevious = nextNode;
			}
			 T retVal = nextNode.getElement();
			index--;
			
			return retVal;
		}

		 
		/**
		 * returns the index of the next element
		 * @return index
		 */
		public int nextIndex() {
			connModification();
			return index;
		}

		 
		/**
		 * returns the index of the previous element
		 * @return --index
		 */
		public int previousIndex() {
			connModification();
			return --index;
		}

		 
		/**
		 * removes the element returned by next or previous method
		 */
		public void remove() {
			connModification();
			if (nodePrevious == null) {
				throw new IllegalStateException();
			} if (nextNode == nodePrevious) { 
				nextNode = nextNode.getNext(); 
			} else {

				index--;
			}
			if (nodePrevious == head) {
				head = head.getNext();
				nextNode = head ;
			} else {
				nodePrevious.getPrevious().setNext(nextNode); 
			}
			if (nextNode == null) {
				tail = tail.getPrevious();
			} else { 
				nextNode.setPrevious(nodePrevious.getPrevious());
				nodePrevious = nodePrevious.getPrevious();
			}
			nodePrevious = null;
			size--;
			modCount++;
			iterModCount++;
		}

		 
		/**
		 * replaces the element last returned by next or previous method with the element supplied
		 * @param element
		 */
		public void set(T element) {

			connModification();

			if (nodePrevious == null) {
				throw new IllegalStateException();
			}

			nodePrevious.setElement(element);

			modCount++;
			iterModCount++;

		}

		 
		/**
		 * adds the element to the iterator position
		 * @param element
		 */
		public void add(T element) {
			connModification();
			LinearNode<T> nodeAdd = new LinearNode<T>();
			nodeAdd.setElement(element);
			if (nextNode == null && tail == null) 
				tail = nodeAdd ;
			else if(nextNode == null && tail != null) {
				nodeAdd.setPrevious(tail);
				tail.setNext(nodeAdd);
			}
			 else {
//				 nodeAdd.setElement(nextNode.getPrevious().getElement());
//				 nextNode.getPrevious().setElement(element);
//				 nodeAdd.setNext(nextNode.getPrevious());
//				 nodeAdd.setPrevious(nextNode.getPrevious().getPrevious());
//				 nextNode.getPrevious().setPrevious(nodeAdd);
				 
				 nodeAdd.setPrevious(nextNode.getPrevious());
				 nodeAdd.setNext(nextNode);
				 nextNode.setPrevious(nodeAdd);
			}
			if (nextNode==head) {
				head = nodeAdd;
			} else {
				nodeAdd.getPrevious().setNext(nodeAdd);
			}
			size++;
			modCount++;
			iterModCount++;
			index++;

		}

	}
}
