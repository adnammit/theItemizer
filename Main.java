
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


public class Main {

	public static void main(String[] args) {

	// INPUT FILE:
		String file = "inventory.txt";
				
	// CREATE A NEW SESSION:
		Manager session = new Manager();
		
	// RUN SESSION:
		session.run(file);

	// CLOSE SESSION:
		session.close(file);
	}
	
}


//***************************************************************************************
//	END CODE
//***************************************************************************************

