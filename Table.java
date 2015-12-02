
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

import java.io.*;

public class Table extends Basic{
	
	
//***************************************************************************************
// 	
//	TABLE :: FIELDS
//***************************************************************************************
	private Node head;


//***************************************************************************************
// 	
//	NODE :: METHODS
//***************************************************************************************

	
//***************************************************************************************
//	DEFAULT CONSTRUCTOR
	
	public Table(){
		super();
		this.head = null;
	}


//***************************************************************************************
//  DISPLAY : INVENTORY HEADER
	public void displayHeader(){
		System.out.println("\t[SKU]\t[#]\t[#PER]\t[COST]\t[MSRP]\t[DESCRIPTION]");
	}
//***************************************************************************************
//  DISPLAY : WRAPPER
	
	public void display(){
		if (head == null){
			System.out.println("\n[--- nothing in inventory ---]");
		}	
		else{
			displayHeader();
			display(head);
		}
		return;
	}

	
//***************************************************************************************
//  DISPLAY
// 		Recursive function. Display this data, and as long as the next item isn't the 
//	first item in the list, display the next item.
	
	public void display(Node node){
		node.display();
		if (node.goNext() != this.head)
			display(node.goNext());
		return;
	}
	
	
//***************************************************************************************
//  INSERT : READ
//		This is a special insert. We're reading in from a sorted file, so the most
//	efficient way to insert will be to always add at the end. We'll check a few special
//	cases first though:
//		- If head is null, create a new node.
//		- If there is only one item, insert before or after it.

	public void insertRead(Item i){
	// If the table is empty, create one new node, setting next and prev pointers to 
	//	point to itself.
	    if (this.head == null){
	    	this.head = new Node(i);
	    	this.head.setNext(head);
	    	this.head.setPrev(head);
	    }
	// we will always insert at the end. this works if there's only one item in the list too.
	    else{
	    	Node temp = this.head.goPrev();
	    	head.setPrev(new Node(i));
	    	head.goPrev().setNext(head);
	    	head.goPrev().setPrev(temp);
	    	temp.setNext(head.goPrev());
	    }
	    return;
	}
	
	
//***************************************************************************************
//  INSERT : WRAPPER
//		This one is different from insertRead. The item being inserted could be anything.
//	Special cases:
//		- If head is null, create a new node.
//		- If there is only one item, insert before or after it.
//		- If i is greater than the last thing in the list, insert it at the end.
//	Otherwise compare the difference of i and the first item to the difference of i and
//	the last item to decide whether going from the front or back will be more efficient.

	public void insert(Item i){
	// If the table is empty, create one new node, setting next and prev pointers to 
	//	point to itself.
	    if (this.head == null){
	    	this.head = new Node(i);
	    	this.head.setNext(head);
	    	this.head.setPrev(head);
	    }
	 // If there is only one item, add before or after it.
	    else if (this.head == this.head.goNext()){
	    	if (i.value() < this.head.value()){
	    		Node temp = this.head;
	    		this.head = new Node(i);
	    		head.setNext(temp);
	    		head.setPrev(temp);
	    		temp.setNext(head);
	    		temp.setPrev(head);
	    	}
	    	else{
	    		this.head.setNext(new Node(i));
	    		head.goNext().setNext(head);
	    		head.goNext().setPrev(head);
	    		head.setPrev(head.goNext());
	    	}
	    }
	// If i is bigger than the last item in the list, save us some time and insert it here.
	    else if (i.value() > head.goPrev().value()){
	    	Node temp = this.head.goPrev();
	    	head.setPrev(new Node(i));
	    	head.goPrev().setNext(head);
	    	head.goPrev().setPrev(temp);
	    	temp.setNext(head.goPrev());
	    }
	// If i is smaller than the first item in the list, save us some time and insert it here.
	    else if (i.value() < head.value()){
	    	Node temp = head;
	    	head = new Node(i);
	    	head.setNext(temp);
	    	head.setPrev(temp.goPrev());
	    	temp.goPrev().setNext(head);
	    	temp.setPrev(head);
	    }
	// Otherwise, compare the difference between i and the first item, and i and the last
	//	item to decide which direction is likely to be the least traversals.
	    else{
	    // If i is closer to next than it is to previous, insert from the beginning
	    	if ((Math.abs(i.value() - head.value())) < (Math.abs(i.value() - head.goPrev().value())))
	    		insertNext(head,i);
	    // Otherwise insert from the end:
	    	else
	    		insertPrev(head.goPrev(),i);
	    }
	    return;
	}
	

//***************************************************************************************
//  INSERT
//		This is the recursive function which does the repetitive work. Everything here is 
//	relative -- we're just looking to insert between something that's smaller and 
//	something that's bigger than what we're inserting.
//		We should never make it back to the end of the list.
	
	public void insertNext(Node node, Item i){	
			if ((i.value() > node.value()) && (i.value() < node.goNext().value())){
				Node temp = node.goNext();
				node.setNext(new Node(i));
				node.goNext().setNext(temp);
				node.goNext().setPrev(node);
				temp.setPrev(node.goNext());		
			}
			else
				insertNext(node.goNext(), i);
		    return;	
	}

	
	
//***************************************************************************************
//  INSERT
//		This is the recursive function which does the repetitive work. We know that the 
// 	last item in the list is larger than i or it would have been handled in the wrapper.
//	Therefore we can juse look for the case where i is inbetween the value of node and 
//	the previous node.
//		We should never make it back to the front of the list.
	
	public void insertPrev(Node node, Item i){	
		// If the item we're inserting is bigger than previous and smaller than this,
		//	insert here:
			if ((i.value() < node.value()) && (i.value() > node.goPrev().value())){
				Node temp = node.goPrev();
				node.setPrev(new Node(i));
				node.goPrev().setPrev(temp);
				node.goPrev().setNext(node);
				temp.setNext(node.goPrev());		
			}
			else
				insertPrev(node.goPrev(), i);
		    return;	
	}
	
	
//	SIMPLE INSERT FUNCTION:
//		Here is the simple insert function that I wrote first. It works just fine, but 
//	doesn't make use of the double-pointer functionality of the data structure, so I 
//	wrote the above functions, which are definitely more complicated, but hopefully they
//	are also more efficient as the data structure becomes larger. 
	
////***************************************************************************************
////  INSERT : WRAPPER
////		If head is empty, insert the first item here.
////	Special cases:
////		- If head is null, create a new node.
////		- If there is only one item, insert before or after it.
////		- If i is greater than the last thing in the list, insert it at the end.
////	Otherwise compare the difference of i and the first item to the difference of i and
////	the last item to decide whether going from the front or back will be more efficient.
//
//	public void insert(Item i){
//	// If the table is empty, create one new node, setting next and prev pointers to 
//	//	point to itself.
//	    if (this.head == null){
//	    	this.head = new Node(i);
//	    	this.head.setNext(head);
//	    	this.head.setPrev(head);
//	    }
//	 // If there is only one item:
//	    else if (this.head == this.head.goNext()){
//	    	if (i.value() < this.head.value()){
//	    		Node temp = this.head;
//	    		this.head = new Node(i);
//	    		head.setNext(temp);
//	    		head.setPrev(temp);
//	    		temp.setNext(head);
//	    		temp.setPrev(head);
//	    	}
//	    	else{
//	    		this.head.setNext(new Node(i));
//	    		head.goNext().setNext(head);
//	    		head.goNext().setPrev(head);
//	    		head.setPrev(head.goNext());
//	    	}
//	    }
//	    else
//	    	insert(head, i);
//	    return;
//	}
//	
//	
////***************************************************************************************
////  INSERT
////		This is the recursive function which actually does the work. Everything here is 
////	relative -- we're just looking to insert between something that's smaller and 
////	something that's bigger than what we're inserting.
//	
//	public void insert(Node node, Item i){	
//		// If we made it to the end of the list, just insert here:
//			if (node.goNext() == this.head){
//				node.setNext(new Node(i));
//				node.goNext().setPrev(node);
//				node.goNext().setNext(this.head);
//				this.head.setPrev(node.goNext());
//			}
//		// If the item we're inserting is bigger than previous and smaller than next,
//		//	insert here:
//			else if ((i.value() > node.value()) && (i.value() < node.goNext().value())){
//				Node temp = node.goNext();
//				node.setNext(new Node(i));
//				node.goNext().setNext(temp);
//				node.goNext().setPrev(node);
//				temp.setPrev(node.goNext());		
//			}
//			else
//				insert(node.goNext(), i);
//		    return;	
//	}
	
	
	
	
	
	
//***************************************************************************************
//	SEARCH: WRAPPER
//		Search for a match. Return either the new balance or 0 if no match is found.

	public float search(String match, float balance, char flag){
		return search(head, match, balance, flag);
	}
	

//***************************************************************************************
//	SEARCH
//		This function is called by several different functions, so 'flag' is used to 
//	tell us what to do with a match once we find it. The new balance is returned if
//	the inventory is modified - or 0 is returned if no changes are made or no match is
//	found.

	public float search(Node node, String match, float balance, char flag){
		float temp = 0;
		if (node != null){
		// If we have a match and the user wants to modify it, do so and return:
			if (node.isMatch(match) == true){
				System.out.print("Is ");
				node.displayName();
				System.out.print(" what you're looking for? [Y/N]\t");
				char reply = '\0';		
				reply = input.next().charAt(0);
				reply = Character.toUpperCase(reply);
			// If this is what the user wants, we'll either add stock, fill an order,
			//	or simply display the item.
				if(reply == 'Y'){
				// Call the appropriate function:
					if (flag == 'A')
						return node.addStock(balance);
					else if (flag == 'R')
						return node.removeStock(balance);
					else if (flag == 'S'){
						displayHeader();
						node.display();
						return 1;
					}
				}
			}
		// Otherwise keep looking:
			temp = search(node.goNext(), match, balance, flag);
		}
		return temp;		
	}
	
	
//***************************************************************************************
//	WRITE OUT TO FILE: WRAPPER
//		Returns false if there's nothing to write. Otherwise call recursive function.
	
	public boolean write(BufferedWriter o) throws IOException{
		if (head != null){
			write(head, o);
			return true;
		}
		return false;
	}

	
//***************************************************************************************
//	WRITE OUT TO FILE
	
	public void write(Node node, BufferedWriter o) throws IOException{
	    node.write(o);
	    if ((node.goNext() != null) && (node.goNext() != this.head)) 
	    	write(node.goNext(), o);
	}
	
}


//***************************************************************************************
//	END CODE
//***************************************************************************************

