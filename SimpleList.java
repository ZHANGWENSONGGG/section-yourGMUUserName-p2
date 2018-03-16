/**
 * This class is a generic linked list that you will use to implement
 * separate chaining in hash table. You can choose how the linked list
 * is organized as far as the required operations are supported with the
 * specified runtime overhead. You also need to define a basic iterator
 * for this class and implement efficient .hasNext() and .next() in it.
 *
 * @author Wensong Zhang
 * @version 03/09/2018
 */

import java.util.Iterator;

// your header comments

/**
 * Constructor
 * @param <T> Generic
 */
class SimpleList<T> implements Iterable<T>{
	
	// a linked list class 
	// you decide the internal attributes and node structure
	// but they should all be private

	private class Node<T>{
		T value;
		Node<T> next;

		public Node(T value){
			this.value = value;
			this.next = null;
		}
	}

	private Node<T> head;  	// first node
	private Node<T> tail;  	// last node
	private int size=0;

	/**
	 * Add method
	 * @param value T value
	 */
	public void add(T value){
		// add a new node to the end of the linked list to hold value
		// O(1) 
		Node<T> node = new Node<T>(value);

		if (size == 0){
			head = node;
		} else {
			tail.next = node;
		}
		tail = node;
		size++;
	}

	/**
	 * Remove method
 	 * @param value T
	 * @return boolean
	 */
	public boolean remove(T value){
		// given a value, remove the first occurrence of that value
		// return true if value removed
		// return false if value not present
		// O(N) where N is the number of nodes returned by size()
		int index = this.indexOf(value);
		if (index == -1){
			return false;
		}
		remove(index);
		return true;
	}

	private T remove(int index){
		// check index
		T toReturn;
		// remove

		//check
		if(index<0||index>=size){
			throw new RuntimeException();
		}

		//delete from beginning
		if (index == 0) {
			toReturn =head.value;
			head = head.next;
			if (size==1){
				tail = null;
			}
		}
		//delete from end of mid
		else {
			Node<T> previous = head;
			Node<T> current = head.next;

			for (int i =0; i < index - 1; i++){
				previous = current;
				current = current.next;
			}
			previous.next = current.next;
			toReturn = current.value;

			//delete from end
			if (index == size-1){
				tail = previous;
			}
		}
		size--;
		return toReturn;
	}

	/**
	 * Check index of value.
	 * @param value T
	 * @return index
	 */
	public int indexOf(T value){
		// return index of a value (0 to size-1)
		// if value not present, return -1
		// O(N)
		Node<T> current = head;
		for (int i = 0; i < size; i++){
			if (current.value.equals(value))
				return i;
			current = current.next;
		}
		return -1;
	}

	/**
	 * Contains method.
	 * @param value T
	 * @return boolean
	 */
	public boolean contains(T value){
		// return true if value is present
		// false otherwise
		// O(N) where N is the number of nodes returned by size()
		Node<T> current = head;
		for (int i =0; i<size; i++){
			if (current == null){
				return false;
			}
			else if (current.value == value){
				return true;
			}
			current = current.next;
		}
		return false;
	}

	/**
	 * Get method
	 * @param value T
	 * @return value or null
	 */
	public T get(T value){
		// search for the node with the specified value:
		// if not found, return null;
		// if found, RETURN VALUE STORED from linked list, NOT the incoming value
		// Note: two values might be considered "equal" but not identical
		//       example: Pair <k,v1> and <k,v2> "equal" for different v1 and v2 
		// O(N) where N is the number of nodes returned by size()

		Node<T> current = head;
		for (int i =0; i<size;i++){
			if (current.value == value){
				return current.value;
			}
			current = current.next;
		}
		return null;
	}

	/**
	 * Get size method
	 * @return size
	 */
	public int size(){
		//return how many nodes are there
		//O(1)
		return this.size;
	}

	/**
	 * Iterator
	 * @return Iterator
	 */
	public Iterator<T> iterator(){
		// return a basic iterator
		// .hasNext() and .next() required 
		// both should be of O(1)

		SimpleList<T> list = this;
		Iterator iter = new Iterator<T>() {
			Node<T> head = list.head;
			Node<T> current = null;

			@Override
			public boolean hasNext() {
				if (list.size == 0){
					return false;
				} else if (current == null){
					return true;
				} else if (current.next == null){
					return false;
				}
				return true;
			}
			@Override
			public T next() {
				if (list.size == 0){
					throw new RuntimeException();
				} else if (current == null){
					this.current = head;
					return current.value;
				} else if (current.next == null){
					throw new RuntimeException();
				}
				this.current = current.next;
				return current.value;
			}
		};
		return iter;
	}


	
	//----------------------------------------------------
	// example testing code... make sure you pass all ...
	// and edit this as much as you want!
	// also, consider add a toString() for your own debugging

	public static void main(String[] args){
		SimpleList<Integer> ilist = new SimpleList<Integer>();
		ilist.add(11);
		ilist.add(20);
		ilist.add(5);

		if (ilist.size()==3 && ilist.contains(5) && !ilist.contains(2) && ilist.indexOf(20) == 1){
			System.out.println("Yay 1");
		}

		if (!ilist.remove(new Integer(16)) && ilist.remove(new Integer(11)) && !ilist.contains(11) && ilist.get(20).equals(20)){
			System.out.println("Yay 2");
		}
		
		Iterator iter = ilist.iterator();
		if (iter.hasNext() && iter.next().equals(20) && iter.hasNext() && iter.next().equals(5) && !iter.hasNext()){
			System.out.println("Yay 3");
		}
		
	}

}