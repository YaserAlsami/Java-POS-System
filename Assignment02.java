
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
/**
* @author Yaser Alsami
* create a basic inventory system for a produce stand using a dynamically allocated 
* data structure to hold the inventory. 
* 
*
*/
public class Assignment02 {


   /**
   * Displays the menu.
   */
   private void displayMenu(){
       //Displays the menu options
       System.out.println("Please select one of the following:\n"
               + "1: Add Item to Inventory" + "\n"
               + "2: Display Current Inventory" + "\n"
               + "3: Buy Item(s)" + "\n"
               + "4: Sell Item(s)" + "\n"
               + "5: Search for Item" + "\n"
               + "6: Save Inventory to File" + "\n"
               + "7: Read Inventory from File" + "\n"
               + "8: To Exit");
   }

   /**
   * The main method.
   *
   * @param args Basic arguments
 * @throws IOException 
   */
   public static void main(String[] args)  {
       Scanner scanner = new Scanner(System.in); //creating a scanner object 

       Assignment02 program = new Assignment02();//creating an object from Assignment 2 class
       int userOption=0; //initializing the variable user option to 0
       int[] optionRange = {1, 8};// creating an array to get the users input


       Inventory inventory = new Inventory();

       while( userOption != optionRange[1]) {//while loop to to verify the users input and make sure no exception occurs 
           program.displayMenu();// calling a method to display the menu

           //get user input
           try {
               //check the string for whitespaces
               String userInput = scanner.nextLine();//use nextLine to get the spaces
               if (userInput.isEmpty()) {  // to check if there is any white space 
                   throw new InputMismatchException();//to catch the exception if there is any
               }
               userOption= Integer.valueOf(userInput);//calling the method valueOf form the integer wrapper class

           } catch (InputMismatchException x) {//catching the InputMismatchException 
               System.out.println("Please enter a valid integer");// printing a message if the InputMismatchException occurs
               continue;
           } catch (NumberFormatException x) {//catching the NumberFormatException
               System.out.println("Please enter an integer");;// printing a message if the NumberFormatException occurs
               continue;
           }


           // if acceptable input, perform the corresponding actions
           switch(userOption) {

           //--------------------------------------------------------------------
           case 1:
               // Add Item to Inventory
               if (inventory.addItem(scanner, false)) {// calling the addItem method from the inventory class  
               }
               break;//break out of the loop
               //--------------------------------------------------------------------
           case 2:
              
               System.out.println("Inventory: \n" + inventory.toString());  // Display Current Inventory from toString method
               break;//break out of the loop
               //--------------------------------------------------------------------
           case 3:
              
               if (inventory.updateQuantity(scanner, true)) {// in case 3 calls the updateQuantity method in Inventory class
               }
               break;//break out of the loop
               //--------------------------------------------------------------------
           case 4:
               // Sell Item(s)
               if (inventory.updateQuantity(scanner, false)) {// in case 4 calls the updateQuantity method in Inventory class
               }
               break;//break out of the loop
               //--------------------------------------------------------------------
           case 5:
               // Search for Item
               // view a FoodItem given the itemCode
               inventory.searchForItem(scanner);
               break;//break out of the loop
               //--------------------------------------------------------------------
           case 6:
               // Save Inventory to File
               inventory.saveToFile(scanner);// to save to a file using saveTFile method
               break;//break out of the loop
               //--------------------------------------------------------------------              
           case 7:
        	   try {
               inventory.readFromFile(scanner); // Read Inventory from File using the readFromFile method
        	   }catch (NumberFormatException e) {
        		   System.out.println("Error Encountered while reading the file, aborting...");
        	   }
               
               break;//break out of the loop
               //--------------------------------------------------------------------  
           case 8: // the program terminates
               
               System.out.println("Exiting...");// To Exit when option 8 is chosen 
               break;//break out of the loop
               //--------------------------------------------------------------------
           default:
               
               System.out.println("Incorrect value entered");//to print out the default message
           }
           //--------------------------------------------------------------------
       }//end of the while loop
       scanner.close(); // close Scanner
   }//end main 
}//end class


