
//***************************************************************************************
//
//	ACCOUNTING SOFTWARE
//  AMANDA RYMAN :: amanda.ryman@gmail.com
//  Portland State University :: Computer Science 202
//  JUNE 11, 2014
//  
//***************************************************************************************
//
//  ITEMIZER SOFTWARE
//      Program consists of the following classes:
//			o Basic: contains a scanner object and decimal format obeject. All other 
//				classes are derived from Basic.
//          o Manager: moderates program flow. data members include user-specific data
//              such as user name, inventory name, file to use, etc.
//          o Table: the class which moderates the BST which holds the inventory data
//          o Node: contains right and left pointers as well as a reference to an 
//              inventory item
//          o Item: an abstract class. contains data members for universal item specifics
//              such as price, cost, description, quantity and product number.
//          o Bulk: derived class of Item. Adds a field to keep track of how many items
//              come in a package.
//          o Single: just a single object, derived from Item.
//
//***************************************************************************************


import java.io.BufferedWriter;
import java.io.IOException;

//***************************************************************************************
//	
//	NODE :: FIELDS
//***************************************************************************************

public class Node extends Basic {
	private Node prev;
	private Node next;
	private Item data;

	
//***************************************************************************************
// 	
//	NODE :: METHODS
//***************************************************************************************

	Node(){
		super();
		data = null;
		this.prev = this.next = null;
	}
	
	Node(Item i){
		super();
		data = i;
		this.prev = this.next = null;
	}
	
	
//***************************************************************************************
//	NODE SETTERS AND GETTERS

	public Node goPrev() {
		return this.prev;
	}
	
	public void setPrev(Node prev) {
		this.prev = prev;
		return;
	}
	
	public Node goNext() {
		return this.next;
	}
	
	public void setNext(Node next) {
		this.next = next;
		return;
	}
	
	
//***************************************************************************************
//	NODE 'WRAPPER' FUNCTIONS FOR ITEM CLASS
//		These functions just gatekeep between the 'table' class and the 'item' class.
	
	
	public void display(){
		this.data.display();
	}
	
	public void displayName(){
		System.out.print(this.data.desc);
	}

	public int value(){
		return data.value();
	}
	
	public boolean isMatch(String s){
		if (this.data.isMatch(s) ==  true)
			return true;
		return false;
	}
	
	public float addStock(float balance){
		return this.data.addStock(balance);
	}
	
	public float removeStock(float balance){
		return this.data.removeStock(balance);
	}
	
	public void write(BufferedWriter o) throws IOException{
		this.data.write(o);
	}
	
}



//***************************************************************************************
//	END CODE
//***************************************************************************************
