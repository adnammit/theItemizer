
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
import java.util.Scanner;

public class Manager extends Basic {
	
//***************************************************************************************
// 	
//	MANAGER :: FIELDS
//***************************************************************************************
	private Table inventory;
	private float balance;
	private String storeID;
	private String invName;
	
	
	
//***************************************************************************************
// 	
//	MANAGER :: METHODS
//***************************************************************************************	
	
	
//***************************************************************************************
// 	DEFAULT CONSTRUCTOR

	Manager(){
		super();
		this.inventory = new Table();
		this.balance = 0;
		this.storeID = "Guest";
		this.invName = "INVENTORY";
	}

	
//***************************************************************************************
// 	RUN PROGRAM
//		This moderates the session once it's been created. The user is shown a menu,
//	makes a selection, and the corresponding function is run. Loop until the user is done.
// 	Menu() returns one of the following values:
//		    1 = Display inventory
//			2 = Display balance
//		    3 = Create new item
//		    4 = Increase stock
//		    5 = Fill order
//			6 = Search
//		    0 = Quit

	public void run(String file){
		readInventory(file);
		welcome();
		char opt='\0';
	    do{
	    	opt = menu();
	    	
	    	switch(opt)
	    	{
		    	case 1: 
					System.out.println("\n[--- " + this.invName + " ---]");
		    		this.inventory.display();
		    		break;
		    	case 2:
		    		System.out.println("\nCURRENT BALANCE:\t$" + df.format(this.balance));	
		    		break;
		    	case 3:
		    		addItem();
		    		break;
		    	case 4:
		    		addStock();
		    		break;
		    	case 5:
		    		fillOrder();
		    		break;
		    	case 6:
		    		itemSearch();
		    		break;
		    	case 0:
		    		break;
		    	default:
		    		opt = 0;
	    	}
	    }while(opt != 0);
	}

	
//***************************************************************************************
// 	READ INVENTORY
//		Reads in the balance and data on all items from the file.
	
	public void readInventory(String file){
		int objType = 0;
		try{
		// Create a new Scanner object:
			Scanner in = new Scanner(new File(file));
			in.useDelimiter(System.getProperty("line.separator"));
		// Read in the balance first:
			if(in.hasNext()) 
				this.balance = in.nextFloat();
		// Then read the store ID data:
			if(in.hasNext()) 
				this.storeID = in.next();
		// Then read the inventory title:
			if(in.hasNext()) 
				this.invName = in.next();
		// Then read in the inventory line by line, creating new objects and adding them
		//	to inventory:
			while(in.hasNext()){
			// Read the first char to figure out if it's a bulk item or a single item:
				if(in.hasNext()){
					objType = in.nextInt();
					Item item = null;
				// Depending on the value of objType, create a bulk item or a single item:
					if (objType == 1)
						item = new Bulk(in.next());
					else
						item = new Single(in.next());
					// Developer: check that items are loading in correctly:
					//item.display();
				// ...and insert it into the data structure:
					this.inventory.insertRead(item);
				}
			
			}
			in.close();
		}catch (IOException e){
			e.printStackTrace();
			
		}	
		return;
	}
	
	
//***************************************************************************************
// 	WELCOME
//		Display a simple welcome message to the user.
	
	public void welcome(){
		System.out.println("[--- ITEMIZER ---] \nThe reliable source for managing small business assets since 2014");
		System.out.println("You are currently logged in as: " + this.storeID);
		System.out.println("CURRENT BALANCE:\t$" + df.format(this.balance));	
	}	
	
	
//***************************************************************************************
//  MENU
//		Display operation options to the user. Return their selection to 'run':
//		    1 = Display inventory
//			2 = Display balance
//		    3 = Create new item
//		    4 = Increase stock
//		    5 = Fill order
//			6 = Search
//		    0 = Quit

	public char menu(){
		char opt = '\0';
		
		System.out.println("\nSELECT FROM THE FOLLOWING OPTIONS:");

	// DISPLAY ALL:
		System.out.println("\t[1]\tDISPLAY INVENTORY");
		
	// DISPLAY BALANCE:
		System.out.println("\t[2]\tDISPLAY BALANCE");

	// CREATE NEW:
		System.out.println("\t[3]\tCREATE A NEW ITEM");

	// INCREASE EXISTING:
		System.out.println("\t[4]\tADD STOCK");

	// FILL ORDER:
		System.out.println("\t[5]\tFILL CUSTOMER ORDER");
		
	// SEARCH:
		System.out.println("\t[6]\tSEARCH FOR ITEM");

	// EXIT:
		System.out.println("\t[7]\tQUIT");

	// PICK ONE:
		System.out.print("Select 1-7:\t");

	    do{
		// Get selection from user:
			opt = input.next().charAt(0);
			opt = Character.toUpperCase(opt);
			
		// Make sure we set 'opt' to a good value (1, 2, 3, 4 or 0):
			if ((opt == 'D') || (opt == 'I') || (opt == '1'))
			    opt = 1;
			else if ((opt == 'B') || (opt == '2'))
			    opt = 2;
			else if ((opt == 'C') || (opt == 'N') || (opt == '3'))
			    opt = 3;
			else if ((opt == 'A') || (opt == '4'))
			    opt = 4;
			else if ((opt == 'F') || (opt == 'O') || (opt == '5'))
			    opt = 5;
			else if ((opt == 'S') || (opt == '6'))
			    opt = 6;
			else if ((opt == 'Q') || (opt == '7'))
			    opt = 0;
			else{
			    opt = 'N';
			    System.out.println("\nPlease re-enter your selection:\t");
			}
	    }while(opt == 'N');	
	    return opt;
	}
	
	
//***************************************************************************************
// 	CLOSE FILE
// 		Read out the store data and inventory data to the file.
	
	public void close(String file){
		try {
			BufferedWriter o = new BufferedWriter(new FileWriter(file));
		// Write balance and storeID, then pass it off to inventory:
			o.write(Float.toString(this.balance));
			o.newLine();
			o.write(this.storeID);
			o.newLine();
			o.write(this.invName);
			if (inventory.write(o) == false)
				System.out.println("No inventory to write");
			else
				System.out.println("[--- " + this.invName + " has been saved ---]");
			o.close();
		} catch (IOException e) {
			System.out.println("Error reading to file.");
			e.printStackTrace();
		}	
		goodbye();
	}
	
	
//***************************************************************************************
// 	ADD ITEM
//		Create a new item and add it to the inventory
	
	public void addItem(){
	// CREATE A NEW ITEM
		Item item = null;
		System.out.print("Would you like to create a bulk or single item? [B/S]\t");
		char opt = '\0';
	    do{
		// Get selection from user:
			opt = input.next().charAt(0);
			opt = Character.toUpperCase(opt);
		// Create either a Bulk or Single item, or ask user to re-enter:
			if (opt == 'B')
			    item = new Bulk();
			else if (opt == 'S')
				item = new Single();
			else{
			    opt = 'N';
			    System.out.println("\nPlease re-enter your selection:\t");
			}
	    }while(opt == 'N');	
		
	// READ IN THE NEW ITEM AND CHANGE THE BALANCE
		this.balance = item.read(balance);
	// ADD TO INVENTORY
		this.inventory.insert(item);
	// ECHO CHANGE
		System.out.println("You have added " + item.quantity + " " + item.desc 
				+ " to the inventory.");	
	}
	
	
//***************************************************************************************
// 	ADD STOCK
//		Increase the quantity of existing items. Adjust balance to pay for the new stock.
// 	We have to send balance in as an argument to "search" to make sure we have sufficient
//	funds for the adjustment.
	
	public void addStock(){
	// CHOOSE ITEM TO ALTER
		System.out.println("Enter a SKU or description to search for:\t");
		input.nextLine();
	// SEARCH
		String match = input.nextLine();
		float temp = inventory.search(match, balance, 'A');
	// ADJUST BALANCE
		if (temp == 0)
			System.out.println("Sorry, no results match " + match);
		else
			this.balance = temp;	
	}
	   
	
//***************************************************************************************
// 	FILL ORDER
//		Sell items. Remove quantity from inventory and adjust balance.
	
	public void fillOrder(){
	// CHOOSE ITEM TO ALTER
		System.out.println("Enter a SKU or description to search for:\t");
		input.nextLine();
	// SEARCH
		String match = input.nextLine();
		float temp = inventory.search(match, balance, 'R');
	// ADJUST BALANCE
		if (temp == 0)
			System.out.println("Sorry, no results match " + match);
		else
			this.balance = temp;		
	}
	
	
//***************************************************************************************
// 	ITEM SEARCH
//		Take user input and search for it in inventory.
	
	public void itemSearch(){
	// GET ITEM TO SEARCH FOR
		System.out.println("Enter a SKU or description to search for:\t");
		input.nextLine();
	// SEARCH
		String match = input.nextLine();
		// 'search()' has a return value that is used from addStock and fillOrder to 
		//	update the balance. Here it is simply used to report success or failure.
		float temp = inventory.search(match, balance, 'S');
		if (temp == 0)
			System.out.println("Sorry, no results match " + match);
	}


//***************************************************************************************
//	GOODBYE
//		Farewell, dear user.

	public void goodbye(){
		System.out.println("\n[--- SESSION ENDED for " + this.storeID + " ---]");
		System.out.println("\n\tThank you for choosing Itemizer.");
	}

}


//***************************************************************************************
//	END CODE
//***************************************************************************************

