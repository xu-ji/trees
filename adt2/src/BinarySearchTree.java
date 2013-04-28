//recursive implementation

// before head, headcase, tailcase, 1 element, (empty case)

// K must have a superclass which is comparable
public class BinarySearchTree<K extends Comparable<? super K>, V> {

	private BSTEntry<K, V> root;
	private int size;
	
	public BinarySearchTree() {
		root = null;
		size =0;
	}
	
	public boolean isEmpty() {
		return size == 0 && root == null;
	}
	
	public void clear() {
		root = null;
		size = 0;
	}
	
	public V get(K key) throws NoSuchElementException {
		return getElem(root, key);
	}
	
	private V getElem(BSTEntry<K, V> top, K key) {
		if (top == null) {
			throw new NoSuchElementException("Key does not exist - get.");
		}
		
		int comp = key.compareTo(top.getKey());
		if (comp == 0) {
			return top.getValue();
		} else if (comp > 0) {
			return getElem(top.getRight(), key);
		} else {
			return getElem(top.getLeft(), key);
		}
	}
	
	public void put(K key, V value) {
		root = putElem(root, key, value);
	}
	
	private BSTEntry<K, V> putElem(BSTEntry<K, V> top, K key, V value) {
		if (top == null) {
			//head case, or leaf case
			size++;
			return new BSTEntry<K, V>(key, value);
		}
		
		int comp = key.compareTo(top.getKey());
		if (comp == 0) {
			top.setValue(value);
		} else if (comp > 0) {
			//don't just return - have to attach!
			top.setRight(putElem(top.getRight(), key, value));
		} else {
			top.setLeft(putElem(top.getLeft(), key, value));
		}
		return top;
	}
	
	public void remove(K key) throws NoSuchElementException {
		root = removeElem(root, key);
	}
	
	private BSTEntry<K, V> removeElem(BSTEntry<K, V> top, K key) {
		if (top == null) {
			throw new NoSuchElementException("Key: " + key + " not found - remove");
		}
		
		int comp = key.compareTo(top.getKey());
		if (comp == 0) {
			top = deleteElem(top);
			size--;
		} else if (comp > 0) {
			top.setRight(removeElem(top.getRight(), key));
		} else {
			top.setLeft(removeElem(top.getLeft(), key));
		}
		return top;
	}
	
	private BSTEntry<K, V> deleteElem(BSTEntry<K, V> top) {
		if (top.getLeft() == null && top.getRight() == null) {
			//no children
			return null;
		} else if (top.getRight() == null) {
			// 1 left child
			return top.getLeft();
		} else if (top.getLeft() == null) {
			// 1 right child
			return top.getRight();
		} else {
			// 2 children
			BSTEntry<K, V> replacementNode = getLeftMost(top.getRight());
			BSTEntry<K, V> newRight = deleteLeftMost(top.getRight());
			replacementNode.setRight(newRight);
			replacementNode.setLeft(top.getLeft());
			return replacementNode;
		}
	}
	
	//iterative
	private BSTEntry<K, V> getLeftMost(BSTEntry<K, V> top) {
		BSTEntry<K, V> curr = top;
		
		while (curr.getLeft() != null) {
			curr = curr.getLeft();
		}
		// now curr.getLeft() = null, hence curr is the leftmost.
		return curr;
	}
	//recursive
	private BSTEntry<K, V> getLeftMostRecursive(BSTEntry<K, V> top) {
		if (top.getLeft() == null) {
			return top;
		}
		return getLeftMostRecursive(top.getLeft());
	}
	
	// have to actually change the structure of the nodes - not just "finding"
	// easier to use recursive - automatic relinking of nodes
	private BSTEntry<K, V> deleteLeftMost(BSTEntry<K, V> top) {
		// doesn't have a left, hence is the leftmost
		if (top.getLeft() == null) {
			return top.getRight();
		}
		
		//it has a left
		top.setLeft(deleteLeftMost(top.getLeft()));
		return top;
	}
	
	@Override
	public String toString() {
		return recString(root);
	}
	
	private String recString(BSTEntry<K, V> top) {
		if (top == null) {
			return "";
		}
		StringBuilder sb = new StringBuilder();
		sb.append(recString(top.getLeft()));
		sb.append(" " + top.getKey() + "," + top.getValue() + " ");
		sb.append(recString(top.getRight()));
		return sb.toString();
	}
	
}
