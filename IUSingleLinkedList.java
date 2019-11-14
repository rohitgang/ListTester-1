import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Single-linked node implementation of IndexedUnsortedList.
 * An Iterator with working remove() method is implemented, but
 * ListIterator is unsupported.
 * 
 * @author rohit gangurde 
 * 
 * @param <T> type to store
 */
public class IUSingleLinkedList<T> implements IndexedUnsortedList<T> {
	private LinearNode<T> head, tail;
	private int size;
	private int modCount;

	/** Creates an empty list */
	public IUSingleLinkedList() {
		head = tail = null;
		size = 0;
		modCount = 0;
	}

	@Override
	public void addToFront(T element) {

		LinearNode<T> nodeAdd = new LinearNode<T>();
		nodeAdd.setElement(element);
		nodeAdd.setNext(head);
		head = nodeAdd;
		if (tail == null) {
			tail = head;
		}
		size++;
		modCount++;
	}

	@Override
	public void addToRear(T element) {

		LinearNode<T> nodeAdd = new LinearNode<T>();
		if (head == null) {
			head = new LinearNode<T>();
			head.setElement(element);
		} else {
			nodeAdd.setElement(element);
			tail.setNext(nodeAdd);
		}
		tail = nodeAdd;
		size++;
		modCount++;
	}

	@Override
	public void add(T element) {

		addToRear(element);

	}

	@Override
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
		nodeTarget.setNext(nodeAdd);
		if (nodeTarget == tail) {
			tail = nodeAdd;
		}
		size++;
		modCount++;

	}

	@Override
	public void add(int index, T element) {

		if (index < 0 || index > size) {
			throw new IndexOutOfBoundsException();
		}
		LinearNode<T> nodeAdd = new LinearNode<T>();
		if (index == 0 && head != null) {
			nodeAdd.setElement(head.getElement());
			head.setElement(element);
			head.setNext(nodeAdd);
			nodeAdd.setNext(head.getNext().getNext());
		} else if (index == 0 && head == null) {
			head = new LinearNode<T>();
			head.setElement(element);
			tail = head;
			head.setNext(tail);
		} else {
			LinearNode<T> nodePointer = head;
			LinearNode<T> nodePrevious = head ;
			for (int i = 0; i < index; i++) {
				nodePrevious = nodePointer ;
				nodePointer = nodePointer.getNext();
			}
			nodePrevious.setNext(nodeAdd);
			nodeAdd.setElement(element);
			nodeAdd.setNext(nodePointer);
		}
		if (index == size) {
			tail = nodeAdd;
		}
		size++;
		modCount++;
	}

	@Override
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

	@Override
	public T removeLast() {

		if (isEmpty()) {
			throw new NoSuchElementException();
		}
		T retVal = tail.getElement();
		if (size == 1) {
			head = null;
			tail = null;
		} else {
			LinearNode<T> nodeLast = head;
			while (nodeLast.getNext() != tail) { // reach to end (tail)
				nodeLast = nodeLast.getNext();
			}
			nodeLast.setNext(null);
			tail = nodeLast;
		}
		size--;
		modCount++;
		return retVal;
	}

	@Override
	public T remove(T element) {
		if (isEmpty()) {
			throw new NoSuchElementException();
		}

		boolean found = false;
		LinearNode<T> previous = null;
		LinearNode<T> current = head;

		while (current != null && !found) {
			if (element.equals(current.getElement())) {
				found = true;
			} else {
				previous = current;
				current = current.getNext();
			}
		}

		if (!found) {
			throw new NoSuchElementException();
		}

		if (size() == 1) { // only node
			head = tail = null;
		} else if (current == head) { // first node
			head = current.getNext();
		} else if (current == tail) { // last node
			tail = previous;
			tail.setNext(null);
		} else { // somewhere in the middle
			previous.setNext(current.getNext());
		}

		size--;
		modCount++;

		return current.getElement();
	}

	@Override
	public T remove(int index) {

		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException();
		}
		T retVal = null;
		if (index == 0) {
			return removeFirst();
		} else {
			LinearNode<T> nodePrevious = head;
			LinearNode<T> nodePointer = head;
			for (int i = 0; i < index; i++) {
				nodePrevious = nodePointer;
				nodePointer = nodePointer.getNext();
			}
			retVal = nodePointer.getElement();
			nodePrevious.setNext(nodePointer.getNext());
			if (nodePrevious.getNext() == null) {
				tail = nodePointer;
			}
		}
		modCount++;
		size--;
		return retVal;

	}

	@Override
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

	@Override
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

	@Override
	public int indexOf(T element) {
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

		return index;
	}

	@Override
	public T first() {

		if (head == null) {
			throw new NoSuchElementException();
		}
		return head.getElement();

	}

	@Override
	public T last() {

		if (head == null) {
			throw new NoSuchElementException();
		}
		return tail.getElement();

	}

	@Override
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

	@Override
	public boolean isEmpty() {

		return (head == null);
	}

	@Override
	public int size() {

		return size;
	}

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

	@Override
	public Iterator<T> iterator() {
		return new SLLIterator();
	}

	@Override
	public ListIterator<T> listIterator() {
		throw new UnsupportedOperationException();
	}

	@Override
	public ListIterator<T> listIterator(int startingIndex) {
		throw new UnsupportedOperationException();
	}

	/** Iterator for IUSingleLinkedList */
	private class SLLIterator implements Iterator<T> {
		private LinearNode<T> nextNode, prevNode;
		private int iterModCount;
		private boolean nextCalled;
		
		/** Creates a new iterator for the list */
		public SLLIterator() {
			nextNode = head;
			iterModCount = modCount;
			nextCalled = false;
		}

		@Override
		public boolean hasNext() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			if (nextNode == null)
				return false;
			return true;
		}

		@Override
		public T next() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			if (!hasNext())
				throw new NoSuchElementException();
			T retVal = nextNode.getElement();
			prevNode = nextNode;
			nextNode = nextNode.getNext();
			nextCalled = true;
			modCount++;
			iterModCount++;
			return retVal;
		}

		@Override
		public void remove() {
			if (iterModCount != modCount)
				throw new ConcurrentModificationException();
			if (!nextCalled) {
				throw new IllegalStateException();
			}
			LinearNode<T> nodePrevious = head;
			LinearNode<T> nodePointer = head;
			while (nodePointer != prevNode) {
				nodePrevious = nodePointer;
				nodePointer = nodePointer.getNext();
			}
			if (nodePointer == head) {
				head = nextNode;
			} else if (nodePointer == tail) {
				tail = nodePrevious;
				nodePrevious.setNext(null);
			} else if (nodePointer == head && nodePointer == tail) {
				head = tail = null;
			} else {
				nodePrevious.setNext(nodePointer.getNext());
			}
			size--;
			modCount++;
			iterModCount++;
			nextCalled = false;

		}
	}
}
