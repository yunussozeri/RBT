package node;

public class Node<T extends Comparable<T>> {
	public static final Node NIL = new Node<>();
	
	private Node<T> parent;
	private Node<T> rightChild;
	private Node<T> leftChild;
	private 	 T 	value;
	private Color 	color;
	
	/**
	 * Creates a node with all components.
	 * 
	 * @param parent
	 * @param rightChild
	 * @param leftChild
	 * @param value
	 * @param color
	 */
	public Node( Node<T> 	parent 
				,Node<T> 	rightChild 
				,Node<T> 	leftChild
				,T 			value
				,Color 		color) {
		this.parent = parent;
		this.leftChild = leftChild;
		this.rightChild = rightChild;
		this.value = value;
		this.color = color;
	}
	
	/**
	 * Creates a Node without children.
	 * 
	 * @param parent
	 * @param value
	 * @param color
	 */
	public Node(Node<T> parent, T value, Color color) {
		this(value, color);
		this.setParent(parent);
	}
	
	/**
	 * Creates a Node without parent and without children.
	 * 
	 * @param value is the value
	 * @param color is the color
	 */
	public Node(T value, Color color) {
		this(NIL,NIL,NIL,value,color);
		
	}
	
	/**
	 * Constructor for sentinel node
	 */
	public Node() {
		setParent(this);
		setBlack();
		
	}
	
	public Node<T> getParent() {
		return parent;
	}
	
	public Node<T> getLeftChild() {
		return leftChild;
	}
	
	public Node<T> getRightChild() {
		return rightChild;
	}
	
	public T getValue() {
		return value;
	}
	
	public Node<T> getGrandParent(){
		return this.getParent().getParent();
	}
	
	public void setParent(Node<T> parent) {
		this.parent = parent;
	}
	
	public void setLeftChild(Node<T> leftChild) {
		this.leftChild = leftChild;
	}
	
	public void setRightChild(Node<T> rightChild) {
		this.rightChild = rightChild;
	}
	
	public void setValue(T value) {
		this.value = value;
	}
	
	public boolean isBlack() {
		return color == Color.BLACK;
	}
	
	public boolean isRed() {
		return color == Color.RED;
	}
	
	public void setBlack() {
		this.color = Color.BLACK;
	}
	
	public void setRed() {
		this.color = Color.RED;
	}
}

