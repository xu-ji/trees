
// put the comparable in the 1st one - it's then inserted automatically into BST
public class AVLTree<K extends Comparable<? super K>, V> extends BinarySearchTree<K, V>{

	private AVLEntry<K, V> root;
	private int size;
	
	public AVLTree() {
		root = null;
		size = 0;
	}
	
//	The same:
//	public boolean isEmpty() {
//		return size == 0 && root == null;
//	}
//	
//	public void clear() {
//		size = 0;
//		root = null;
//	}
//	
//	public V get(K key) throws NoSuchElementException {
//		
//	}
	
	//rewrite, for minor differences
	@Override
	public void put(K key, V value) {
		root = putElem(root, key, value);
	}
	
	//THREE REBALANCES IN PUT
	// NOT override - because putElem in super is private, hence unaccessable 
	private AVLEntry<K, V> putElem(AVLEntry<K, V> top, K key, V value) {
		if (top == null) {
			//HEADCASE
			size++;
			return new AVLEntry<K, V>(key, value);
		}
		int comp = key.compareTo(top.getKey());
		if (comp == 0) {
			top.setValue(value);
		} else if (comp > 0) {
			top.setRight(rebalance(putElem(top.getRight(), key, value)));
		} else {
			//comp < 0
			top.setLeft(rebalance(putElem(top.getLeft(), key, value)));
		}
		return rebalance(top);
	}
	
	public void remove(K key) throws NoSuchElementException {
		root = removeElem(root, key);
	}
	
	//THREE REBALANCES IN REMOVE
	private AVLEntry<K, V> removeElem(AVLEntry<K, V> top, K key) {
		if (top == null) {
			throw new NoSuchElementException("Attempting to remove item that doesn't exist.");
		}
		
		int comp = key.compareTo(top.getKey());
		if (comp == 0) {
			top = deleteElem(top);
			size--;
		} else if (comp > 0) {
			top.setRight(rebalance(removeElem(top.getRight(), key)));
		} else {
			//comp < 0
			top.setLeft(rebalance(removeElem(top.getLeft(), key)));
		}
		return rebalance(top);
	}
	
	private AVLEntry<K, V> deleteElem(AVLEntry<K, V> top) {
		if (top.getLeft() == null && top.getRight() == null) {
			//leaf
			return null;
		} else if (top.getLeft() == null) {
			// has right child
			return top.getRight();
		} else if (top.getRight() == null) {
			// has left child
			return top.getLeft();
		} else {
			// 2 children
			AVLEntry<K, V> replacementNode = getLeftMost(top.getRight());
			AVLEntry<K, V> newRight = deleteLeftMost(top.getRight());
			replacementNode.setRight(newRight);
			replacementNode.setLeft(top.getLeft());
			return rebalance(replacementNode);
		}
	}
	
	private AVLEntry<K, V> getLeftMost(AVLEntry<K, V> top) {
		if (top.getLeft() == null) {
			return top;
		}
		return getLeftMost(top.getLeft());
	}
	
	private AVLEntry<K, V> deleteLeftMost(AVLEntry<K, V> top) {
		if (top.getLeft() == null) {
			return top.getRight();
		}
		top.setLeft(deleteLeftMost(top.getLeft()));
		return top;
	}
	
	private AVLEntry<K, V> rebalance(AVLEntry<K, V> top) {
		AVLEntry<K, V> leftChild = null;
		AVLEntry<K, V> rightChild = null; 
		AVLEntry<K, V> leftLeftChild = null;
		AVLEntry<K, V> leftRightChild = null;
		AVLEntry<K, V> rightLeftChild = null;
		AVLEntry<K, V> rightRightChild = null;
		
		//ASSIGNMENTS
		
		if (top == null) {
			return null;
		}
		
		if (top.getLeft() == null && top.getRight() == null) {
			return top;
		}
		
		if (top.getLeft()== null) {
			// only has right child
			rightChild = top.getRight();
			rightLeftChild = rightChild.getLeft();
			rightRightChild = rightChild.getRight();
		}
		if (top.getRight()== null) {
			// only has left child
			leftChild = top.getLeft();
			leftLeftChild = leftChild.getLeft();
			leftRightChild = leftChild.getRight();
		}
		
		// SORTING
		if (getHeight(leftChild) > getHeight(rightChild) + 1) {
			if (getHeight(leftLeftChild) > getHeight(leftRightChild)) {
				top = top.rotateRight();
			} else {
				// left left child is less, or equal to, its pair
				top = top.rotateLeftRight();
			}
		} else if (getHeight(rightChild) > getHeight(leftChild) + 1) {
			if (getHeight(rightRightChild) > getHeight(rightLeftChild)) {
				top = top.rotateLeft();
			} else {
				// right right child is less, or equal to, its pair
				top = top.rotateRightLeft();
			}
		}
		return top;
	}
	
	private int getHeight(AVLEntry<K, V> top) {
		if (top == null) {
			return 0;
		}
		return 1 + Math.max(getHeight(top.getLeft()), getHeight(top.getRight()));
	}
	
	@Override
	public String toString() {
		return print(root);
	}
	
	
	public String print(AVLEntry<K, V> node) {
		if (node == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("  [  ");
		sb.append(print(node.getLeft()));
		sb.append("(" + node.getKey() + ", " + node.getValue() + ")");
		sb.append(print(node.getRight()));
		sb.append("  ]  ");
		
		return sb.toString();
	}
}
