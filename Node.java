/**
 * Represents a generic object used for the implementation of various data structures that need only point to one other object.
 */
public class Node {
	
  /**
   * Holds basic integer type.
   */
  public int data;
	
  /**
   * Object reference to null or another Node object.
   */
  public Node next;

  /**
   * Initializes a new node object.
   * 
   * @param data Integer that initializing node will hold
   */
  public Node(int data) {
    this.data = data;
    next = null;
  }
}
