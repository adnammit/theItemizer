
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
import java.util.Scanner;

//***************************************************************************************
//	
//	SINGLE ITEM :: FIELDS
//***************************************************************************************

public class Single extends Item {
	
	
	
//***************************************************************************************
// 	
//	SINGLE ITEM :: METHODS
//***************************************************************************************

	
//***************************************************************************************
//	DEFAULT CONSTRUCTOR
	
		Single(){
			super();
		}
		
		
//***************************************************************************************
//	COPY CONSTRUCTOR
		
		Single(Single s){
			super(s); // should work -- single doesn't have any more data than item does
		}	
		

//***************************************************************************************
//	PARSE ITEM
//			Reads in data from external file.
			
		Single(String line){
			Scanner sLine = new Scanner(line);
			sLine.useDelimiter("\\s*,\\s*");
			this.num = sLine.nextInt();
			this.quantity = sLine.nextInt();
			this.cost = sLine.nextFloat();
			this.msrp = sLine.nextFloat();
			this.desc = sLine.next();
		}

		
//***************************************************************************************
//	READ ITEM
//		Create a new item from user input.

		public float read(float balance){
		// DESCRIPTION:
			System.out.println("\nITEM DESCRIPTION:\t");
			this.desc = input.nextLine();	
		// PRODUCT NUMBER:
			System.out.println("\nITEM SKU:\t");
			this.num = input.nextInt();
			input.nextLine();		// buffer clear...? necessary?
		// QUANTITY:
			System.out.println("\nQUANTITY:\t");
			this.quantity = input.nextInt();
			input.nextLine();
		// COST:
			System.out.println("\nWHOLESALE COST:\t");
			this.cost = input.nextFloat();
			input.nextLine();
		// MSRP:
			System.out.println("\nMSRP:\t");
			this.msrp = input.nextFloat();
			input.nextLine();

		// Check that we have enough $$$ to buy this new stock:
			while (balance < (this.quantity * this.cost)){
				System.out.println("\nBalance is insufficient for this purchase.\nRe-enter quantity:");
				this.quantity = input.nextInt();
				input.nextLine();
			}; 
		// Change the balance and we're done.
			balance -= (this.quantity * this.cost);
			System.out.println("Balance after adding " + this.desc + " is $" + df.format(balance));
			return balance;
		}

		
//***************************************************************************************
// 	WRITE ITEM
//		Writes item out to external file in format:  \nS,SKU,quantity,cost,msrp,Description
			
		public void write(BufferedWriter out) throws IOException{
			out.write("\n0\n" + this.num + "," + this.quantity + "," + this.cost + "," 
					+ this.msrp + "," + this.desc);
		}
		
//***************************************************************************************
//	DISPLAY ITEM	
			
		public void display(){
			System.out.println("\t" + this.num + "\t" + this.quantity + "\tSingle\t" 
					+ this.cost + "\t" + this.msrp + "\t" + this.desc);
			return;
		}
		
		
}


//***************************************************************************************
//END CODE
//***************************************************************************************

