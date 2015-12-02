
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

//package hw5_amanda_ryman;

import java.io.BufferedWriter;
import java.io.IOException;


//***************************************************************************************
//	
//	ITEM :: FIELDS
//***************************************************************************************

abstract class Item extends Basic {
	protected int num;
	protected String desc;
	protected float cost;
	protected float msrp;
	protected int quantity;
	
	
	
//***************************************************************************************
// 	
//	ITEM :: METHODS
//***************************************************************************************


//***************************************************************************************
//	DEFAULT CONSTRUCTOR
	
	Item(){
		super();
		this.num = quantity = 0;
		this.cost = msrp = 0;
		this.desc = null;
	}
		
	
//***************************************************************************************
//		COPY CONSTRUCTOR
	
	Item(Item i){
		super();
		this.num = i.num;
		this.desc = i.desc;
		this.cost = i.cost;
		this.msrp = i.msrp;
		this.quantity = i.quantity;	
	}


//***************************************************************************************
//	ABSTRACT FUNCTIONS:
	
	public abstract void display();
	public abstract float read(float balance);
	public abstract void write(BufferedWriter out) throws IOException;

	
//***************************************************************************************
//	GET VALUE
//		Returns value of the item so it can be compared to other items and sorted.

	public int value(){
		return num;
	}

	
//***************************************************************************************
//	IS MATCH
//		Use a recursive loop to compare character by character to see if the string 
//	passed in is a substring of this item's description.
	
	public boolean isMatch(String s){
	// Check the SKU first:
		String sku = this.num + "";
		if (s.equals(sku))
			return true;
	// If that's not a match, look at the 'description' field: 
		int max = this.desc.length() - s.length();
		boolean match = false;
		findMatch: for (int i = 0; i <= max; i++) {
			int n = s.length();
			int j = i;
			int k = 0;
			while (n-- != 0) {
				if (Character.toLowerCase(this.desc.charAt(j++)) != Character.toLowerCase(s.charAt(k++))) {
					continue findMatch;
				}
			}
			match = true;
			break findMatch;
		}
		return match;
	}
	

//***************************************************************************************
//	ADD QUANTITY
//		Allows user to add stock to an item. Takes balance as an argument and checks that
//	there are sufficient funds for the purchase. 
//		Returns balance - (quantity added X wholesale cost of the item)

	public float addStock(float balance){
		int add = 0;
		float total = 0;
		System.out.println("Current quantity of " + this.desc + " is " + this.quantity);
		System.out.print("Enter number to add to stock:\t");
		add = input.nextInt();
		input.nextLine();
		total = add * this.cost;
	// Check that we have enough $$$ to buy this new stock:
		while (balance < (total)){
			System.out.println("\nBalance is insufficient for this purchase.\nRe-enter quantity:");
			add = input.nextInt();
			input.nextLine();
			total = add * this.cost;
		}; 
	// Now we can alter the object quantity and return the new balance:
		this.quantity += add;
		balance -= total;
	// Echo changes:
		System.out.println("Quantity for " + this.desc + " has been changed to " + this.quantity);
		System.out.println("New Balance is: $" + df.format(balance));
		return balance;
	}

	
//***************************************************************************************
//	REMOVE STOCK
//		Allows user to fill a customer order, removing stock from inventory. 
//	Takes balance as an argument and checks that there is sufficient stock for the 
//	sale. 
//		Returns balance + (quantity sold X msrp)

	public float removeStock(float balance){
		int change = 0;
		float total = 0;
		System.out.println("Current quantity of " + this.desc + " is " + this.quantity);
		System.out.print("Enter quantity to sell to customer order:\t");
		change = input.nextInt();
		input.nextLine();
	// Check that we have enough stock to sell:
		while (change > this.quantity){
			System.out.println("\nStock is insufficient for this transaction.\nRe-enter quantity:");
			change = input.nextInt();
			input.nextLine();
		}; 
	// Calculate our earnings for this sale:
		total = change * this.msrp;
	// Now we can alter the object quantity and return the new balance:
		this.quantity -= change;
		balance += total;
	// Echo changes:
		System.out.println("You have sold " + change + " units of " + this.desc + " for $" + df.format(total));
		System.out.println("New Balance is: $" + df.format(balance));
		return balance;
	}
}


//***************************************************************************************
//	END CODE
//***************************************************************************************

