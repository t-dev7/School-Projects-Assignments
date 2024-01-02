package com.mycompany.a3;
import java.util.Vector;

public class GameObjectCollection implements ICollection{
	
	private Vector<GameObject> theCollection; // storage for collection of game objects
	
	// constructor
	public GameObjectCollection() {		
		theCollection = new Vector<GameObject>();
	}

	// adds a new object to the vector 
	public void add(Object newObject) {			
		theCollection.addElement((GameObject) newObject);
	}
	
	// accessor to return Iterator
	public IIterator getIterator() {
		return new GameObjectVectorIterator ( ) ; 
		}
	
	// private nested iterator class 
	private class GameObjectVectorIterator implements IIterator{
		private int currElementIndex; // 
		
		// constructor
		public GameObjectVectorIterator() {
			currElementIndex = -1;
		}
		
		// confirms whether or not there is another element
		public boolean hasNext() {
			if (theCollection.size() <= 0)  
				return false;
			if (currElementIndex == theCollection.size() - 1 )
				return false;
			return true;
			}
		
		// gets the next object
		public Object getNext() {
			currElementIndex = currElementIndex + 1 ;
			return(theCollection.elementAt(currElementIndex));
			}
		
		public Object getObject() {
			return(theCollection.elementAt(currElementIndex));
			}
	}
}
