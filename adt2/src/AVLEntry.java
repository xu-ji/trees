public class AVLEntry<K, V> extends BSTEntry<K, V>{

	private K key;
	private V value;
	private AVLEntry<K, V> left = null;
	private AVLEntry<K, V> right = null;
	
	public AVLEntry() {
		key = null;
		value = null;
	}
	
	public AVLEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
//  The same, but can't use from BSTtree?!!	
	public K getKey() {
		return key;
	}
	
	public void setKey(K key) {
		this.key = key;
	}
	
	public V getValue() {
		return value;
	}
	
	public void setValue(V value) {
		this.value = value;
	}
	
	public AVLEntry<K, V> getLeft() {
		return left;
	}
	
	public void setLeft(AVLEntry<K, V> left) {
		this.left = left;
	}
	
	public AVLEntry<K, V> getRight() {
		return right;
	}
	
	public void setRight(AVLEntry<K, V> right) {
		this.right = right;
	}
	
	/* AVL METHODS */
	
	public AVLEntry<K, V> rotateLeft(){
		AVLEntry<K, V> newTop = right;
		AVLEntry<K, V> newRight = right.getLeft();
		right = newRight;
		newTop.setLeft(this);
		return newTop; 
	}
	
	public AVLEntry<K, V> rotateLeftRight() {
		AVLEntry<K, V> newLeft = left.rotateLeft();
		left = newLeft;
		return rotateRight();
	}
	
	public AVLEntry<K, V> rotateRight() {
		AVLEntry<K, V> newTop = left;
		AVLEntry<K, V> newLeft = left.getRight();
		left = newLeft;
		newTop.setRight(this);
		return newTop;
	}
	
	public AVLEntry<K, V> rotateRightLeft() {
		AVLEntry<K, V> newRight = right.rotateRight();
		right = newRight;
		return rotateLeft();
	}

}
