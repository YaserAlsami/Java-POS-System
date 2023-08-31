
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
* @author Yaser Alsami
* create a basic inventory system for a produce stand using a dynamically allocated 
* data structure to hold the inventory. 
*
*/
public class Inventory {

   /** The inventory of FoodItems. */
   private ArrayList<FoodItem> inventory;

   /** The path. */
   private String PATH = "C:\\CST8130\\Assignment02\\";

   /** The text file regex.
    *$ = checks if a line end follows
    *[^\\s]+ = any characters except whitespace
    *\\. = dot
    *(?i) = ignore case checking for the following
    *\w = any word character = [a-zA-Z]
    *+ = finds one or several
    *{x} = occurs x times only
    */ 
   private String TEXT_FILE_REGEX = "^([^\\s]+)(\\.(?i)txt)$";


   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * a protected instructor and creating an arrayList of FoodItem type 
   */
   protected Inventory() {
       inventory= new ArrayList<FoodItem>();
   }


   /**
   * Gets the file name.
   *
   * @param scanner the scanner
   * @param toAsk the to ask
   * @return the file name
   */
   // -------------------------------------------------------------------------------------------------------------------------
   private String getFileName(Scanner scanner, String toAsk) {
       System.out.println("Name of the file to " + toAsk + ": "); //asking for the file name to save or read from
       String fileName = scanner.nextLine();

       if ( !Pattern.matches(TEXT_FILE_REGEX, fileName) ) { //check valid text file format
           return null;
       }

       return fileName;
   }
   // -------------------------------------------------------------------------------------------------------------------------
  /**
   * A method to print the item that is stored in the array
   * @return String of the values in the inventory
   */
   public String toString(){
       // returns a String of the values
       String toReturn = "";
       for ( int i=0 ; i<inventory.size() ; i ++) {//looping inside the array to access every index
           toReturn += inventory.get(i).toString();
         toReturn += "\n";
      }
      return toReturn;
   }

   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Checks if a FoodItem with the same code exists inside the array.
   *
   * @param item (FoodItem) to compare the code to
   * @return int value of the item's index in inventory, if doesn't match then returns -1
   */
   protected int alreadyExists (FoodItem item) {       

       for ( int i=0 ; i< inventory.size() ; i++ ) {// Returns the index of a FoodItem in the inventory array
           if (inventory.get(i).compareTo(item) == 0) {// with the same itemCode as the FoodItem object in the parameter list
               return i;
           }
       }
       return -1;// else returns -1
   }

   /**
   * Save to file.
   * save the current inventory data into the text file
   *
   * @param scanner the scanner
   * @throws IOException 
   */
   // -------------------------------------------------------------------------------------------------------------------------
   public void saveToFile(Scanner scanner)  {
   
       Formatter writer= null; //creating a an object named writer from the Formatter class

       try {//try/catch statement to make sure no exceptions occurs  
           String file = getFileName(scanner, "save to");//initializing the variable file and assigning it to the getFileName method 
           if (file==null) {//if the file empty it will throw new exception 
               throw new InputMismatchException();
           }
           File outputTextFile = new File(PATH +file );//creating a an object named outputTextFile from the File class and giving it a  the path of the file and the file name 
           if (!outputTextFile.exists()) {//if the file doesn't exists it will create a new file 
               outputTextFile.createNewFile();//calling createNewFile method to create the file 
           }

           int numOfItems=0;//initializing the variable number of items to 0
           String originalComponents = "";//initializing the variable original components to empty string 
           ArrayList<Integer> originalItemCodes= new ArrayList<Integer>();//creating a new arryList with Integer type
                  
          
           Scanner reader = new Scanner(outputTextFile);//creating a an object named reader from the Scanner class and giving it outputTextFile as the parameter 
           if (reader.hasNext()) {  //if file already has components
               numOfItems= Integer.valueOf(reader.nextLine());
                   
               while(reader.hasNext()) {//while loop to loop through the file and add every component of the file 
                   int itemCode = Integer.valueOf(reader.nextLine());//adding the item code 
                   originalItemCodes.add(itemCode);//calling the method to add the item code 
                   originalComponents+=scanner.nextLine(); //itemName
                   originalComponents+=scanner.nextLine(); //itemQuantityInStock
                   originalComponents+=scanner.nextLine(); //itemPrice
                   originalComponents+=scanner.nextLine(); //itemCost
               }//end of the while loop
           }//end if statement 
           reader.close();//closing the reader object for the scanner class 

           //open the output file
           writer = new Formatter( outputTextFile );
           writer.flush();//clear the file
           writer.format("%d", (int) numOfItems+inventory.size()); //write correct number of FoodItems
           if (!originalComponents.replace(" ", "").isEmpty()) {
               writer.format("\n%s", originalComponents); //add in the original data
           }

           for (FoodItem eachItem: inventory) {   //for loop to access every index inside inventory array 
            
               for (Integer originalCode: originalItemCodes) {//for loop to check for duplicate item code
                   if (eachItem.getItemCode()==originalCode) {
                       //duplicate item code
                       System.out.println("ERROR: Duplicate item code");
                       throw new InputMismatchException();
                   }
               }
               //append item type
               if (eachItem instanceof Fruit) { //checking if eachItem is istanceof fruit
                   writer.format("\n%s", "f");
               }else if (eachItem instanceof Vegetable) {//checking if eachItem is istanceof Vegetable
                   writer.format("\n%s", "v");
               } else if (eachItem instanceof Preserve) {//checking if eachItem is istanceof Preserve
                   writer.format("\n%s", "p");
               }else {
                   throw new InputMismatchException();
               }
                            
               eachItem.outputItem(writer); //append extra information
           }
       }
      catch (IOException x) {//catching the IOException
           System.out.println("ERROR: An error occured");// printing a message if the IOException occurs
      }
   catch (NumberFormatException x) {//catching the NumberFormatException
           System.out.println("ERROR: Invalid text file");// printing a message if the NumberFormatException occurs
       }catch (InputMismatchException x) {//catching the InputMismatchException
           System.out.println("ERROR: Invalid text file");// printing a message if the InputMismatchException occurs
       }
       try {
           writer.close();
       }catch (NullPointerException x) {}//catching the NullPointerException
   }//end of the method 

   /**
   * Read from file.
   *this method reads from .txt file and saves it to the inventory array 
   * @param scanner the scanner
   */
   // -------------------------------------------------------------------------------------------------------------------------
   public void readFromFile(Scanner scanner) {
       // Upon read, find a duplicate itemCode, abort reading in the file.
       // Valid FoodItems before that one should remain in the inventory and an appropriate error message should be displayed
       Scanner reader = null; 
       try {//try/catch statement 
           String file = getFileName(scanner, "read from"); //initializing the variable file and assigning it to getFileName method 
           if (file==null) {// if file is empty 
               throw new InputMismatchException(); //it will catch this InputMismatchException
           }//end if 
           File inputTextFile = new File(PATH + file);//creating a an object named inputTextFile from the File class and giving it path and file name as the parameter 
           if (!inputTextFile.exists()) {// if file doesn't exists 
               throw new FileNotFoundException(); //it will catch this FileNotFoundException
           }//end if 
           System.out.println("Reading from file..."); //print statement 

           reader = new Scanner(inputTextFile); //assigning the reader variable to scanner class
           int numOfItems = Integer.valueOf(reader.nextLine()); 
           if (!reader.hasNextLine()) {// if file is empty 
               System.out.println("ERROR: Empty text file");//print statement 
               reader.close();//closing the scanner object 
               return;
           }//end if 
           for (int i=0 ; i<numOfItems ; i++) { //for loop to make sure we dont get any exception 
        
               if (!addItem(reader, true)) {//if addItem is not successful 
                   throw new InputMismatchException();//it will throw a new InputMismatchException
               }//end if
           }//end for loop
           return;
       }catch (FileNotFoundException x) {//catching the FileNotFoundException 
           System.out.println("File Not Found, ignoring...");// printing a message if the FileNotFoundException occurs
       }catch (InputMismatchException x) {//catching the InputMismatchException 
           System.out.println("Error Encountered while reading the file, aborting...");;// printing a message if the InputMismatchException occurs
       }
       try {
           reader.close();//closing the reader object 
       }catch (NullPointerException x) {}//catching in-case of a NullPointerException
   }//end of the method 

   /**
   * Gets the type.
   *
   * @param itemType the item type
   * @return the type
   */
   // -------------------------------------------------------------------------------------------------------------------------
   private FoodItem getType(String itemType) {
       // checks the Item type and returns a instance of the corresponding FoodItem
       // returns null if invalid String
       switch (itemType) {
       case "f":
           return new Fruit();
       case "p":
           return new Preserve();
       case "v":
           return new Vegetable();
       default:
           return null;
       }
   }
   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Adds the FoodItem to the inventory.
   * this method adds the item from user input or form reading a file 
   * @param scanner from main is passed on
   * @param fromFile a boolean value specifying whether the data is being inputed from a file or not
   * @return true if successfully added item, otherwise false
   */
   public boolean addItem(Scanner scanner, boolean fromFile) {
       FoodItem newItemToAdd;
       newItemToAdd = null;

       if (fromFile) { //if adding from file
           newItemToAdd= getType(scanner.nextLine()); //using the scanner object to read through the text file
           if (newItemToAdd==null) {//if the file is empty it will return false 
               return false;
           }//end if 
       }//end if 
       else { //else if adding form user input 
           while (newItemToAdd==null) {//while loop to only breaks when new item is not null
               System.out.print("Do you wish to add a fruit(f), vegetable(v) or a preserve(p)? ");// print statement 
               newItemToAdd= getType( scanner.nextLine().toLowerCase() );
           }//end while loop
       }// end else statement 

       if ( !newItemToAdd.addItem(scanner, fromFile) ) {
           return false;
       }//end if 

  
       if ( alreadyExists(newItemToAdd) != -1) {// If the user tries to enter a code that already exists
           System.out.println("ERROR: Item code already exists");//print statement to let the user know 
           return false;//returns false 
       }else {//else if statement no duplicates 
           int indexToBePlaced=inventory.size(); //initializing the variable indexToBePlaced to inventory size array
           for(int i=inventory.size()-1 ; i>=0 ; i-- ) {
               if ( newItemToAdd.compareTo(inventory.get(i))<0 ) {                 
                   indexToBePlaced=i;
               }else {
                   break;
               }
           }//end of the for loop
           inventory.add(indexToBePlaced, newItemToAdd);
           return true; // successful
       }

   }//end of the method 


   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Update quantity of items: buy or sell item.
   * Reads in an itemCode to update and quantity to update by and updates that item by the input quantity in the inventory array.
   * The boolean parameter is used to denote whether buying operation (true) or selling operation (false) is occurring.
   * @param scanner the scanner from main is passed on
   * @param buyOrSell value of whether the user is buying or not
   * @return true if successfully modified the quantity, false if not
   */
   protected boolean updateQuantity (Scanner scanner, boolean buyOrSell) {
       String userInput;//declaring userInput variable as a String 
       String operation;//declaring operation variable as a String
       operation= buyOrSell ? "buy" : "sell"; //if statement if its tue it will be "buy" if false it will "sell"
       
       if (inventory.size()>0) {
           //need to have data in inventory
           try {//try/catch statement 
               FoodItem temp = new FoodItem();   // Create a temporary Food item to use its method
               System.out.print("Enter valid item code: ");//print statement 
               if (!temp.inputCode(scanner, false)){
                   return false;
               }//end if 

               int indexOfItem = alreadyExists(temp); //calling areadyExists method and giving it temp as its parameter  
               if ( indexOfItem == -1 ) { //checking if the item already exists 
                   System.out.println("Code not found in inventory...");//print statement to inform the user that the item code already exists 
                   throw new InputMismatchException();
               }


               //Get number of Item update
               System.out.printf("Enter valid quantity to %s: ", operation); // asking for the quantity 
               userInput= scanner.nextLine(); // use nextLine to get the whitespaces
               if (userInput.isEmpty()) {// checking if its empty 
                   throw new NumberFormatException();
               }
               int amount= Integer.valueOf(userInput);//Converting the amount to integer 
               if (amount<0 ) {//checking if its negative 
                   throw new NumberFormatException();
               }

               int buying = buyOrSell? 1 : -1; // selling: set the buying value to a negative so can subtract
               if ( inventory.get(indexOfItem).updateItem(amount*buying) ) {
                   return true;
               }else {
                   System.out.println("Insufficient stock in inventory...");//print statement to inform the user that there is not enough quantity 
                   throw new InputMismatchException();
               }

           } catch (NumberFormatException x) {//catching NumberFormatException
               System.out.printf("Invalid quantity...");
           } catch (InputMismatchException x) { //catching InputMismatchException

           }
       }//end if statement 
       System.out.printf("Error...could not %s item\n", operation);
       return false; // unsuccessful
   }//end of the method 

   /**
   * Search for item.
   * by comparing ever item code to the inventory array
   *
   * @param scanner the scanner
   */
   // -------------------------------------------------------------------------------------------------------------------------
   public void searchForItem(Scanner scanner) {
       System.out.print("Enter the code for the item: ");
       String userInput = scanner.nextLine();//reading the item code that the user enters

       try {//try/catch statement 
           int itemCode = Integer.valueOf(userInput);
           for (FoodItem each: inventory) {//for loop to access every index in the array 
               if (each.getItemCode() == itemCode) {
                   System.out.println("Item: "+ each.toString());//when it finds it it prints it out 
                   return;
               }
           }
       } catch (NumberFormatException x) {}
       System.out.println("Code not found in inventory...");

   }
}
