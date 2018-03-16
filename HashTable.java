import java.util.Iterator;

/**
 * This class is a generic hash table using separate chaining to resolve collision.
 * The table is initialized as an array of 11 spots but may need to be expanded. In
 * addition to the load, we will also be monitoring the average chain length of all
 * active buckets. You are required to expand and rehash to a bigger hash table if
 * the average chain length is > 1.2. The new table size must be a prime number larger
 * than twice the size before. A .nextPrime() is provided for you to use.
 *
 * @author Wensong Zhang
 * @version 03/09/2018
 */

// your header comment

class HashTable<T> {
	// this is the class that you need to write to implement a simple hash table 
	// with separate chaining
	
	// you decide which additional attributes to include in this class but they should all be private
	
	@SuppressWarnings("unchecked")
	private SimpleList<T>[] table = new SimpleList[11];
	private int itemCount = 0;
	private int numOfChains = 0;

	private void setItemCount(int num){
		this.itemCount = num;
	}

	private void setNumOfChains(int num){
		this.numOfChains = num;
	}

	/**
	 * Add method
	 * @param value T
	 * @return boolean
	 */
	public boolean add(T value) {
		// adds an item to the hash table
		// returns true if you successfully add value
		// returns false if the value can not be added (i.e. the value already exists in the set)
		if (contains(value)){
			return false;
		}

		int position = value.hashCode() % table.length;

		if (table[position] == null ){
			table[position] = new SimpleList<T>();
			numOfChains++;
		}
		itemCount++;

		table[position].add(value);

		// note: if the average chain length is > 1.2
		// must rehash to the next prime number larger
		// than twice the size before returning
		if (getAvgChainLength() > 1.2){
			int newSize = this.nextPrime(table.length*2);
			rehash(newSize);
		}
		// O(M) worst case, where M =  size returned by size()
		// O(1) or O(M/N) average case (where M/N is the load)
		// the average case can be amortized Big-O
		return true;
	}

	/**
	 * Remove method
	 * @param value T
	 * @return boolean
	 */
	public boolean remove(T value) {
		// removes a value from the hash table
		// returns true if you remove the item
		// returns false if the item could not be found

		int position = value.hashCode() % table.length;

		if (table[position] == null){
			return false;
		}
		itemCount--;
		return table[position].remove(value);


		// O(M) worst case, where M =  size returned by size()
		// O(1) or O(M/N) average case (where M/N is the load)
	}

	/**
	 * Contain method
	 * @param value T
	 * @return boolean
	 */
	public boolean contains(T value) {
		// returns true if the item can be found in the table

		int position = value.hashCode() % table.length;

		if(table[position] == null){
			return false;
		}
		if (table[position].contains(value)){
			return true;
		}

		// O(M) worst case, where M = size returned by size()
		// O(1) or O(M/N) average case (where M/N is the load)
		return false;
	}

	/**
	 * Get method
	 * @param value T
	 * @return T
	 */
	public T get(T value) {
		// return null if the item could not be found in hash table;
		// return the item FROM THE HASH TABLE if it was found.
		// NOTE: do NOT return the parameter value!!
		//       While "equal" they may not be the same.
		//       For example, When value is a PAIR<K,V>, 
		//       its "equals" methods returns true if just the keys are equal.
		int position = value.hashCode() % table.length;
		if (table[position] == null){
			return null;
		}
		return table[position].get(value);

		// O(M) worst case, where M = size returned by size()
		// O(1) or O(M/N) average case (where M/N is the load)
	}

	/**
	 * Rehash method
	 * @param newCapacity size
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int newCapacity) {
		// rehash to a larger table size (specified as the
		// parameter to this method)
		// O(M) where M = size returned by size()
		if (newCapacity < 2){ // can not rehash to something which can't be considered as a hash table!
			return false;
		}
		// - return true if table gets resized
		// - if the newCapacity will make the load to be more than 0.7, do not resize
		//   and return false
		if ((double)itemCount/(double) newCapacity > 0.7 ){
			return false;
		}

		Object[] arr = valuesToArray();

		table = new SimpleList[newCapacity]; // increase the table length

		setItemCount(0);
		setNumOfChains(0);

		for (Object i: arr){
			add((T) i);
		}

		return true;
				
	}

	/**
	 * Get size method
	 * @return size
	 */
	public int size() {
		// return the number of items in the table
		// O(1)
		return this.itemCount;
	}

	/**
	 * Get load method.
	 * @return load
	 */
	public double getLoad() {
		// return the load on the table
		// O(1)
		return (double)itemCount / (double)table.length;
	}

	/**
	 * Get Average Chain Length
	 * @return Average Chain Length
	 */
	public double getAvgChainLength(){
		// return the average length of non-empty chains in the hash table
		// O(1)
		//System.out.println("item# = " + itemCount + ", chain# = " + numOfChains);
		return (double)itemCount/(double)numOfChains;
	}

	/**
	 * valuesToArray
	 * @return Object
	 */
	public Object[] valuesToArray() {
		// take all the values in the table and put them
		// into an array (the array should be the same
		// size returned by the size() method -- no extra space!).
		// Note: it doesn't matter what order the items are
		// returned in, this is a set rather than a list.

		Object [] array = new Object[itemCount];
		int num = 0;
		for (int i = 0; i < table.length; i++){
			if (this.table[i] != null){
				Iterator iter = this.table[i].iterator();
				while (iter.hasNext()){
					array[num] = iter.next();
					num++;
				}
			}
		}
		// O(M) where M = size returned by size()   !
		return array;
	}


	/**
	 * inefficiently finds the next prime number >= x
	 * @param x size
	 * @return new size
	 */
	public int nextPrime(int x) {
		while(true) {
			boolean isPrime = true;
			for(int i = 2; i <= Math.sqrt(x); i++) {
				if(x % i == 0) {
					isPrime = false;
					break;
				}
			}
			if(isPrime) return x;
			x++;
		}
	}
	
	//------------------------------------
	// example test code... edit this as much as you want!
	public static void main(String[] args) {
		HashTable<String> names = new HashTable<>();

		if(names.add("Alice") && names.add("Bob") && !names.add("Alice") && names.size() == 2 && names.getAvgChainLength() == 1) 	{
			System.out.println("Yay 1");
		}

		if(names.remove("Bob")&& names.contains("Alice") && !names.contains("Bob") && names.valuesToArray()[0].equals("Alice") ) {
			System.out.println("Yay 2");
		}

		boolean loadOk = true;

		if(names.getLoad() != 1/11.0 || !names.rehash(10) || names.getLoad() != 1/10.0 || names.rehash(1)) {
			loadOk = false;
		}

		boolean avgOk = true;
		HashTable<Integer> nums = new HashTable<>();
		for(int i = 1; i <= 70 && avgOk; i++) {
			nums.add(i);
			double avg = nums.getAvgChainLength();
			System.out.println("i = "+ i + ", avg = " + avg);
			if(avg> 1.2 || (i < 12 && avg != 1) || (i >= 14 && i <= 23 && avg != 1) || (i >= 28 && i <= 47 && avg != 1) || (i >= 57 && i <= 70 && avg!= 1)) {
				avgOk = false;
			}
		}
		if(loadOk && avgOk) {
			System.out.println("Yay 3");

		}
		
	}
}