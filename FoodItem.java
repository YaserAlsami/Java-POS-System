
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
* @author Yaser Alsami
* create a basic inventory system for a produce stand using a dynamically allocated 
* data structure to hold the inventory. 
*
*/
public class FoodItem implements Comparable<FoodItem>{


   /** The item's code. */
   private int itemCode;

   /** The item's name. */
   private String itemName;

   /** The item's price. */
   private float itemPrice;

   /** The item's quantity in stock. */
   private int itemQuantityInStock;

   /** The item's cost. */
   private float itemCost;

   /** The cents format. */
   private DecimalFormat centsFormat = new DecimalFormat("#0.00");

   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Instantiates a new food item.
   */
   protected FoodItem () {
       centsFormat.setRoundingMode(RoundingMode.HALF_UP); // rounds numbers up if place greater than 5
   }

   // -------------------------------------------------------------------------------------------------------------------------

   /**
   * Returns a String value of the values in the inventory
   *
   * @return String of the values in the inventory
   */
   public String toString(){
       // Displays the all data members in the class
       // Item: <code> <name> <quantity> price: $<price> cost: $<cost>

       String returnString = "Item: " + itemCode +//to print the code for the item
               " " + itemName + // to print the name for the item
               " " + itemQuantityInStock +//to print the quantity for the item
               " price: $" + centsFormat.format(itemPrice) +//to print the price for the item to be sold at
               " cost: $" + centsFormat.format(itemCost) ;//to print the cost for the item that it was bought at

       return returnString;

   }
   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Output item to the writer
   * just the data portion of it, no description needed
   *
   * @param writer the writer
   */
   public void outputItem(java.util.Formatter writer) {
       writer.format("\n%d", itemCode); //to output the item code 
       writer.format("\n%s", itemName);//to output the item name 
       writer.format("\n%d", itemQuantityInStock); //to output item quantity 
       writer.format("\n%.2f", itemPrice);//to output price of the item 
       writer.format("\n%.2f", itemCost);//to output the cost of the item
   }
   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Gets the item code
   *
   * @return the item code
   */
   public int getItemCode() {
       return itemCode;
   }

   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Update item's quantity.
   *
   * @param amount the amount to add (can be positive or negative)
   * @return true, if able to successfully update the quantity, false if not
   */
   protected boolean updateItem (int amount){
       // Updates the quantity field by amount (note amount could be positive or negative);
       // Method returns true if successful, otherwise returns false
       // itemQuantityInStock field can never be less than 0

       if ( (itemQuantityInStock + amount) < 0 ) {
           return false;
       } else {
           itemQuantityInStock += amount;
           return true;
       }
   }

   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Compares 2 FoodItem objects together based on their item code,
   * and returns a corresponding int result
   * @param fooditem FoodItem object to compare to
   * @return int value of the compared result
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
   @SuppressWarnings("javadoc")
@Override
   public int compareTo(FoodItem foodItem) {
       if (this.itemCode > foodItem.itemCode) {
           return 1;
       } else if (this.itemCode == foodItem.itemCode) {
           return 0;
       } else {
           return -1;
       }
   }

   // -------------------------------------------------------------------------------------------------------------------------
   /**
   *       // Reads from the Scanner object passed in
   *        and fills the data member fields of the class with valid data;
       // Method returns true if program successfully reads in all fields,
       // otherwise returns false
   *
   * @param scanner from main, sither Scanner(System.in) or Scanner(File)
   * @param fromFile a boolean value specifying whether the data is being inputted from a file or not
   * @return true, if successful
   */
   public boolean addItem(Scanner scanner, boolean fromFile){
 

       inputCode(scanner, fromFile); //calling the inputCode method and giving it its parameter 

       if (fromFile) {//if true adds the item from reading it from a text file 
           
           try {
               itemName = scanner.nextLine(); // reading the item name from the text file
               itemQuantityInStock = Integer.valueOf(scanner.nextLine());//reading item quantity from the text file
               itemPrice = Float.valueOf(scanner.nextLine());//reading the price of the item from the text file
               itemCost = Float.valueOf(scanner.nextLine());//reading the cost of the item from the text file
               return true;
           } catch (InputMismatchException x) {// to catch the InputMismatchException
               System.out.println("ERROR: invalid file");//Printing the a message when it catches exception
               return false;
           } catch (NumberFormatException x) {//to catch the NumberFormatException
               System.out.println("ERROR: invalid file data");//printing a message when it catches the exception
               return false;
           }

       }else {//else means that the user is manually entering the values
           String userInput;//declaring the variable userInput 
           int tempInt;//declaring the variable tempInt
           float tempFloat;//declaring the variable tempFloat
           int currentStep = 1;//initializing the currentStep
         
           //check
           while (currentStep!= 5) {//start of the while loop to breaks until it reaches the last step (asking for the supplier name of the item in f for example)
               try {// try/catch to catch any exception if any 
                   switch (currentStep) {

                   case 1://in case one the program asks the user for the item code 

                       System.out.print("Enter the name for the item: ");// can be any input except empty string
                       userInput= scanner.nextLine(); // use nextLine to get the whitespaces
                       if (userInput.replace(" ", "").isEmpty()) { //if whitespace it will throw the exception
                           throw new InputMismatchException(); 
                       }
                       
                       itemName = userInput; //assigning the userInput to itemName
                       currentStep+=1; //incrementing current step when this case is done
                       break;//breaking out of the case

                   case 2://case 2 is to get the quantity for the item

                       System.out.print("Enter the quantity for the item: ");//printing a message to ask for the quantity 
                       userInput= scanner.nextLine(); // use nextLine to get the whitespaces
                       if (userInput.isEmpty()) {//if the whitespace it will throw exception 
                           throw new InputMismatchException();
                       }
                       tempInt= Integer.valueOf(userInput); //assigning the tempInt variable to userInput and calling the valueOf method to convert it to an integer 
                       // check if positive
                       if (tempInt < 0) {
                           throw new InputMismatchException();//if its negative it will throw new exception 
                       }
                       itemQuantityInStock = tempInt; //assigning the itemQuantityInStock to tempInt
                       currentStep+=1;//incrementing current step when this case is done
                       break;//breaking out of the case 

                   case 3: //case 3 is to get the cost of the item 

                       System.out.print("Enter the cost of the item: ");//printing a message to ask for the cost of the item 
                       userInput= scanner.nextLine(); // use nextLine to get the whitespaces
                       if (userInput.isEmpty()) {//if the whitespace it will throw exception
                           throw new InputMismatchException();
                       }

                       tempFloat= Float.valueOf( centsFormat.format( Float.valueOf(userInput) ) );//assigning the tempInt variable to userInput and calling the valueOf method to convert it to an float
                       // check if positive
                       if (tempFloat < 0) {
                           throw new InputMismatchException();//if its negative it will throw new exception 
                       }
                       itemCost = tempFloat;//assigning the itemCost to tempFloat
                       currentStep+=1;//incrementing current step when this case is done
                       break;//breaking out of the case 

                   case 4://case 3 is asking for the sales price of the item
                       System.out.print("Enter the sales price of the item: ");//printing a message to ask for the cost of the item 
                       userInput= scanner.nextLine(); // use nextLine to get the whitespaces
                       if (userInput.isEmpty()) {//if the whitespace it will throw exception
                           throw new InputMismatchException();
                       }
                       tempFloat= Float.valueOf( centsFormat.format( Float.valueOf(userInput) ) );//assigning the tempInt variable to userInput and calling the valueOf method to convert it to an float
                       // check if positive
                       if (tempFloat <= 0) {
                           throw new InputMismatchException();//if its negative it will throw new exception
                       }
                       itemPrice = tempFloat;//assigning the itemPrice to tempFloat
                       currentStep+=1;//incrementing current step when this case is done
                       break;//breaking out of the case 
                   }

               } catch (NumberFormatException x) { //catching the NumberFormatException 
                   System.out.println("Invalid entry");// printing a message if the NumberFormatException occurs
               } catch (InputMismatchException x) {//catching the InputMismatchException 
                   System.out.println("Invalid entry");// printing a message if the InputMismatchException occurs
               }
           }//end of the while loop
           return true;
       }//end of the else statement


   }//end of the method


   /**
   * Asks and receives an item code from the user, then checks if it is the right format (int).
   *
   * @param scanner from main, sither Scanner(System.in) or Scanner(File)
   * @param fromFile a boolean value specifying whether the data is being inputed from a file or not
   * @return true, if successfully got itemCode from user
   */
   public boolean inputCode(Scanner scanner, boolean fromFile){
       // Reads a valid itemCode from the Scanner object and returns true/false if successful

       String userInput; //declaring the variable userInput 
       if (!fromFile) { //check where is it getting the data from the user
           System.out.print("Enter the code for the item: "); //if its not from a file it will print this message 
       }
       userInput= scanner.nextLine();// if its getting the data from file

       try {
           // must be an int
           if (userInput.replace(" ", "").isEmpty()) {//if the whitespace it will throw exception
               throw new InputMismatchException();
           }
           itemCode= Integer.valueOf(userInput);//assigning the itemCode to userInput
           return true;
       } catch (NumberFormatException x) {
           if (!fromFile) { //check where is it getting the data from the user and its invalid 
               System.out.println("Invalid entry");
               inputCode( scanner,  fromFile);  //recalling the method to get the data  if its invalid  
           }
       }
       return false;

   }

}
