/**
 * This class represents the cell position in a 2-D grid. In addition to the
 * normal accessors, you also need to implement .equals and .hashCode to compare two positions and
 * to return an integer hash code. Remember to follow hash contract and pick a hash function that is
 * easy to compute and distribute well.
 *
 * @author zhang
 * @version 03/09/2018
 */
class Position{
	// this is the class that represent one cell position in a 2D grid

	// row and column
	private int row;
	private int col;

	/**
	 * Constructor of Position class
	 * @param row row
	 * @param col col
	 */
	public Position(int row, int col){
		// constructor to initialize your attributes
		this.row = row;
		this.col = col;
	}

	// accessors of row and column
	/**
	 * Getter method of row
	 * @return row
	 */
	public int getRow(){
		return this.row;
	}

	/**
	 * Getter method of col.
	 * @return col
	 */
	public int getCol(){
		return this.col;
	}

	/**
	 * To string method.
	 * @return string
	 */
	public String toString(){
		// return string representation of a position
		// row R and col C must be represented as <R,C> with no spaces
		return "<" + this.row + "," + this.col + ">";
	}

	/**
	 * Check equal
	 * @param obj Object
	 * @return T/F
	 */
	@Override
	public boolean equals(Object obj){
		// check whether two positions are the same
		// return true if they are of the same row and the same column
		// return false otherwise
		if(obj == null){
			return false;
		}
		if(!(obj instanceof Position)){
			return false;
		}
		Position p = (Position)obj;
		return this.row == p.getRow() && this.col == p.getCol();
	}

	/**
	 * Compute the HashCode
	 * @return hashcode
	 */
	@Override
	public int hashCode(){
		// compute an integer hash code for this object
		// must follow hash contract and distribute well
		// If x and y are equal, must have same hash code.

		return Integer.valueOf(Integer.toString(row) + Integer.toString(col));
	}

	

	//----------------------------------------------------
	// example testing code... make sure you pass all ...
	// and edit this as much as you want!


	public static void main(String[] args){
		Position p1 = new Position(3,5);
		Position p2 = new Position(3,6);
		Position p3 = new Position(2,6);
		
		if (p1.getRow()==3 && p1.getCol()==5 && p1.toString().equals("<3,5>")){
			System.out.println("Yay 1");
		}

		if (!p1.equals(p2) && !p2.equals(p3) && p1.equals(new Position(3,5))){
			System.out.println("Yay 2");
		}
		
		if (p1.hashCode()!=p3.hashCode() && p1.hashCode()!=(new Position(5,3)).hashCode() &&
			p1.hashCode() == (new Position(3,5)).hashCode()){
			System.out.println("Yay 3");			
		}
		
		System.out.println(p1.toString()+" "+p1.hashCode());
		System.out.println(p2.toString()+" "+p2.hashCode());
		System.out.println(p3.toString()+" "+p3.hashCode());
	}
	
}