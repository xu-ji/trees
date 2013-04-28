
public class BSTEntry<K, V> {

	K key;
	V value;
	BSTEntry<K, V> left = null;
	BSTEntry<K, V> right = null;
	
	public BSTEntry() {
		key = null;
		value = null;
	}
	
	public BSTEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	public void setKey(K key) {
		this.key = key;
	}
	
	public K getKey() {
		return key;
	}
	
	public void setValue(V value) {
		this.value = value; 
	}
	
	public V getValue() {
		return value;
	}
	
	public BSTEntry<K, V> getLeft() {
		return left;
	}
	
	public void setLeft(BSTEntry<K, V> left) {
		this.left = left;
	}
	
	public BSTEntry<K, V> getRight() {
		return right;
	}
	
	public void setRight(BSTEntry<K, V> right) {
		this.right = right;
	}
}
