//***************************************************************************************
//
//  AMANDA RYMAN :: CS202
//  HOMEWORK 5 :: ACCOUNTING SOFTWARE
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

//package hw5_amanda_ryman;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Basic {

	protected Scanner input;
	protected DecimalFormat df;
	
	Basic(){
		input = new Scanner(System.in);
		df = new DecimalFormat("#.##");
	}
}


//***************************************************************************************
//	END CODE
//***************************************************************************************

