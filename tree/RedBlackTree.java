package tree;

import node.Node;
import node.Color;

/**
 * Implementation of a Red Black Tree This class should preserve the 4 following
 * properties: 1) All nodes are either red or black 2) The root node and the
 * leaves are black 3) If a node is red, then its children are black 4) All
 * paths from a node to its leaf nodes contains the same number of black nodes.
 * 
 * Additionally, leaf nodes are black nodes without an assigned value. These are
 * also called NIL nodes.
 * 
 * Red-Black trees hold data in a sorted manner. Left children are smaller than
 * the parents. Right children are larger than the parents.
 * 
 * @param <T> generic type of this class
 */
public class RedBlackTree<T extends Comparable<T>> {

	Node<T> root;
	private final Node<T> SENTINEL = Node.NIL;

	/**
	 * Initializes a Red-Black Tree with a given root node.
	 * 
	 * The root node has the parent NIL for sentinel purposes.
	 * 
	 * @param root is the root node. Must be black.
	 * @throws IllegalArgumentException when the root is not black
	 * @throws IllegalArgumentException when the root has a parent
	 */
	private RedBlackTree(Node<T> root) {
		assert root.isBlack() : new IllegalArgumentException("Invalid root color");
		assert root.getParent() == null : new IllegalArgumentException("Root cannot have parent");
		this.root = root;
		root.setParent(SENTINEL);
	}

	/**
	 * Creates a red black tree with a black root
	 * 
	 * @param value is the roots value
	 */
	public RedBlackTree(T value) {
		this(new Node<T>(value, Color.BLACK));
	}

	/**
	 * Creates an empty tree
	 * 
	 */
	public RedBlackTree() {

	}

	/**
	 * Insertion Method for inserting a new node the red black tree. if the tree is
	 * empty, then the new node becomes the root. Colors are fixed after each
	 * insertion. As a strategy, all new insertions are initially red.
	 * 
	 * @param value
	 */
	public void insert(final T value) {
		// create an empty node with red color
		Node<T> insertion = new Node<>(value, Color.RED);

		// if the tree is empty, make the new node the root and assign the sentinel as
		// parent
		// fix the colors afterwards.
		if (isEmpty()) {
			root = insertion;
			root.setParent(SENTINEL);
			fixColorsAfterInsertion(insertion);
			return;
		}

		Node<T> recent = root;
		Node<T> storage = SENTINEL;


		// iterate until the recent node is not NIL go left, if the recent nodes value is
		// larger than the insertion value else go right
		while (recent != Node.NIL) {

			storage = recent; // store recent

			T recentVal = recent.getValue();
			boolean recentNodeIsLarger = recentVal.compareTo(value) > 0;

			// go left if the recent is larger, go right if the recent is smaller
			if (recentNodeIsLarger) {
				recent = recent.getLeftChild();
			} else {
				recent = recent.getRightChild();
			}
		}
		// set the last stored node as the parent of new node
		insertion.setParent(storage);

		// compare stored value and the insertion value
		T storedValue = storage.getValue();
		boolean storedValueIsLarger = storedValue.compareTo(value) > 0;

		// if stored is larger, insert left, else insert right
		if (storedValueIsLarger) {
			storage.setLeftChild(insertion);
		} else {
			storage.setRightChild(insertion);
		}
		// fix colors
		fixColorsAfterInsertion(insertion);
	}

	/**
	 * This method is called after each insertion to correct the colors of nodes.
	 * @param node
	 */
	private void fixColorsAfterInsertion(Node<T> node) {
		// iterate while the parent is red
		while (node.getParent().isRed()) {
			// get the grandparent, predecessor of predecessor
			Node<T> grandparent = node.getGrandParent();
			// find out if the parent node is a left child
			boolean parentIsLeftChild = isLeftChild(node.getParent());
			if (parentIsLeftChild) {
				node = fixLeft(node, grandparent); // update node
			} else {  
				node = fixRight(node, grandparent);  // update node
			}  
		}  
		// set the root black to preserve property  
		root.setBlack();  
	}


	/**
	 * Fixes right half of the tree
	 * METHOD HANDLES ONLY RIGHT-RIGHT INSERTION CASE.
	 * 
	 * @param node is the current position
	 * @param grandparent is the predecessor of predecessor
	 * @return the grandparent, if the uncle is red and not NIL, otherwise return the parent
	 */
	private Node<T> fixRight(Node<T> node, Node<T> grandparent) {
		Node<T> uncle = grandparent.getLeftChild(); // the parent was the right child, so uncle must be left
		if (uncle != Node.NIL && uncle.isRed()) {  // if uncle is red and not NIL
			// set parent and uncle black, and grandparent red
			// set node as grandparent for next iteration
			node.getParent().setBlack();
			uncle.setBlack();  
			grandparent.setRed();  
			node = grandparent;  
		} else {  // if uncle is not red or NIL
			if (isLeftChild(node)) {  // if current node is left child
				// set the parent as the node, and right rotation around the node.
				node = node.getParent();  
				rightRotationAround(node);  
			}  
			// set the nodes parent black and grandfather red, left rotation around grandparent
			node.getParent().setBlack();  
			grandparent.setRed();  
			leftRotationAround(grandparent);  
		}  
		return node;  // return the node for the iteration
	}  


	private Node<T> fixLeft(Node<T> node, Node<T> grandparent) {
		Node<T> uncle = grandparent.getRightChild();

		if (uncle != Node.NIL && uncle.isRed()) {
			node.getParent().setBlack();
			uncle.setBlack();
			grandparent.setRed();
			node = grandparent;
		} else {
			if (isRightChild(node)) {
				node = node.getParent();
				leftRotationAround(node);
			}
			node.getParent().setBlack();
			grandparent.setRed();
			rightRotationAround(grandparent);
		}
		return node;  
	}

	private boolean isRightChild(Node<T> node) {
		return node == node.getParent().getRightChild();
	}

	private boolean isLeftChild(Node<T> node) {
		return node == node.getParent().getLeftChild();
	}

	//-- 
	private void leftRotationAround(Node<T> node) {
		Node<T> y = node.getRightChild();
		node.setRightChild(y.getLeftChild());

		if (y.getLeftChild() != Node.NIL) {
			y.getLeftChild().setParent(node);
		}
		y.setParent(node.getParent());

		if (node == root) {
			root = y;
		} else {
			if (node == node.getParent().getLeftChild()) {
				node.getParent().setLeftChild(y);
			} else {
				node.getParent().setRightChild(y);
			}
		}
		y.setLeftChild(node);
		node.setParent(y);
	}

	private void rightRotationAround(Node<T> node) {
		Node<T> x = node.getLeftChild();
		node.setLeftChild(x.getRightChild());

		if (x.getRightChild() != Node.NIL) {
			x.getRightChild().setParent(node);
		}

		x.setParent(node.getParent());

		if (node == root) {
			root = x;
		} else {
			if (node == node.getParent().getRightChild()) {
				node.getParent().setRightChild(x);
			} else {
				node.getParent().setLeftChild(x);
			}
		}
		x.setRightChild(node);
		node.setParent(x);
	}

	public boolean contains(T value) {
		return containsRecursive(root, value);
	}

	private boolean containsRecursive(Node<T> node, T value) {
		if (node == Node.NIL) {
			return false;
		}

		int compare = value.compareTo(node.getValue());
		switch (compare) {
		case 1:
			return containsRecursive(node.getRightChild(), value);
		case -1:
			return containsRecursive(node.getLeftChild(), value);
		default:
			return true;
		}
	}

	public void clear() {
		root = null;
	}

	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Returns the values in ascending order as an array.
	 * 
	 * @return array of the values in ascending order.
	 */
	public T[] toArray() {
		return (T[]) new Object[1];
	}

	public Node<T> getRoot() {
		return root;
	}

	public static void main(String[] args) {

		RedBlackTree<Integer> a = new RedBlackTree<>();

		a.insert(3);

		a.insert(5);
	}
}
