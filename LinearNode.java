/**
 * LinearNode represents a node in a linked list.
 *
 * @author Java Foundations, mvail
 * @version 4.0
 */
public class LinearNode<T> {
	private LinearNode<T> next, previous ;
	private T element;

	/**
  	 * Creates an empty node.
  	 */
	public LinearNode() {
		next = previous= null;
		element = null;
	}

	/**
  	 * Creates a node storing the specified element.
 	 *
  	 * @param elem
  	 *            the element to be stored within the new node
  	 */
	public LinearNode(T elem) {
		next = null;
		element = elem;
	}

	/**
 	 * Returns the node that follows this one.
  	 *
  	 * @return the node that follows the current one
  	 */
	public LinearNode<T> getNext() {
		return next;
	}
	
	public LinearNode<T> getPrevious() {
		return previous ;
	}
	/**
 	 * Sets the node that follows this one.
 	 *
 	 * @param node
 	 *            the node to be set to follow the current one
 	 */
	public void setNext(LinearNode<T> node) {
		next = node;
	}
	
	public void setPrevious (LinearNode<T> node) {
		previous = node ;
	}
	/**
 	 * Returns the element stored in this node.
 	 *
 	 * @return the element stored in this node
 	 */
	public T getElement() {
		return element;
	}

	/**
 	 * Sets the element stored in this node.
  	 *
  	 * @param elem
  	 *            the element to be stored in this node
  	 */
	public void setElement(T elem) {
		element = elem;
	}

	@Override
	public String toString() {
		return "Element: " + element.toString() + " Has next: " + (next != null);
	}
}

