package redblacktree;

import java.util.NoSuchElementException;

public class RedBlackTree<K extends Comparable<? super K>, V> {

  Node<K, V> root;

  public RedBlackTree() {
    this.root = null;
  }

  RedBlackTree(Node<K, V> root) {
    this.root = root;
  }

  public void put(K key, V value) {

    Tuple<Node<K, V>, Node<K, V>> pair = findNode(key);

    Node<K, V> parent = pair.getX();
    Node<K, V> current = pair.getY();

    if (current != null) { // found an existing key
      current.setValue(value);
      return;
    }

    if (parent == null) { // empty tree
      root = new Node<K, V>(key, value, Colour.BLACK);
      return;
    }
    
    // current == null && parent != null
    /* create a new key */
    int comparison = key.compareTo(parent.getKey());
    Node<K, V> newNode = new Node<K, V>(key, value, Colour.RED);
    if (comparison < 0) {
      parent.setLeft(newNode);
    } else {
      assert comparison > 0;
      parent.setRight(newNode);
    }

    insertCaseOne(newNode);

  }

  // chain of insert functions
  // it is root - colour black and stop
  private void insertCaseOne(Node<K, V> current) {
	  if (current.getParent() == null) {
		  // it is root
		  current.setBlack();
		  return;
	  }
	  insertCaseTwo(current);
  }
  
  //it has a black parent - stop
  private void insertCaseTwo(Node<K, V> current) {
	  if (current.getParent().isBlack()) {
		  return;
	  }
	  insertCaseThree(current);
  }
  
  // checks if uncle is red or black - if uncle is red, then parent + uncle become black
  private void insertCaseThree(Node<K, V> current) {
	  Node<K, V> grandparent = current.getGrandparent();
	  Node<K, V> uncle = null;
	  Node<K, V> parent = current.getParent();
	  
	  // we want the case where uncle exists, and is red
	  // in all other cases, we make the parent black & uncle if exists, black
	  if (grandparent.getLeft() == parent) {
		  	// don't have to check for not null - if null, fine
			uncle = grandparent.getRight();
	  } else if (grandparent.getRight() == parent) {
			  uncle = grandparent.getLeft();
	  }
	  if (uncle != null) {
		  if (uncle.isRed()) {
			  //make both parent and uncle black, AND GRANDPARENT RED
			  parent.setBlack();
			  uncle.setBlack();
			  grandparent.setRed();
			  // recurse with grandparent
			  insertCaseOne(grandparent);
			  return;
		  }
	  }
	  // either uncle doesn't exist, or uncle exists and is black.
	  insertCaseFour(current, parent, grandparent);
  }
  
  // irons out any kinks
  private void insertCaseFour(Node<K, V> current, Node<K, V> parent, Node<K, V> grandparent) {
	  boolean swapped = false;
	  
	  //<
	  if (current == parent.getRight() && parent == grandparent.getLeft()) {
		parent.rotateLeft();
		// PARENT IS NOW CURRENT
		swapped = true;
	  } else if (current == parent.getLeft() && parent == grandparent.getRight()) {
		//>
		  parent.rotateRight();
		 //PARENT IS NOW CURRENT
		 swapped = true;
	  }
	  
	  if (swapped) {
		  Node<K, V> temp = parent;
		  parent = current;
		  current = temp;
	  }
	  insertCaseFive(current, parent, grandparent);
  }
  
  private void insertCaseFive(Node<K, V> current, Node<K, V> parent, Node<K, V> grandparent) {
	  grandparent.setRed();
	  parent.setBlack();
	  
	  if (grandparent.getLeft() == parent) {
		//veer left
		  grandparent.rotateRight();
	  } else if (grandparent.getRight() == parent) {
		  // veer right
		  grandparent.rotateLeft();
	  }
	  // IF GRANDPARENT WAS ROOT
	  if (root == grandparent) {
		  root = parent;
	  }
	  //terminates
  }
  
  private Tuple<Node<K, V>, Node<K, V>> findNode(K key) {
    Node<K, V> current = root;
    Node<K, V> parent = null;

    while (current != null) {
      parent = current;

      int comparison = key.compareTo(current.getKey());
      if (comparison < 0) {
        current = current.getLeft();
      } else if (comparison == 0) {
        break;
      } else {
        assert comparison > 0;
        current = current.getRight();
      }
    }
    return new Tuple<Node<K, V>, Node<K, V>>(parent, current);
  }

  public boolean contains(K key) {
    Tuple<Node<K, V>, Node<K, V>> pair = findNode(key);
    return pair.getY() != null;
  }

  public V get(K key) {
    Tuple<Node<K, V>, Node<K, V>> pair = findNode(key);
    Node<K, V> current = pair.getY();
    if (current == null) {
      throw new NoSuchElementException();
    }
    return current.getValue();
  }

  public void clear() {
    this.root = null;
  }

  public String toString() {
    return "RBT " + root + " ";
  }

}