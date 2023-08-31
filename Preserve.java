
import java.util.Formatter;
import java.util.Scanner;

/**
 * @author Yaser Alsami
 * create a basic inventory system for a produce stand using a dynamically allocated 
 * data structure to hold the inventory. 
 * 
 * this class extends FoodItem
 *
 */
public class Preserve extends FoodItem {

   /** The jar size. */
   private float jarSize;

   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Instantiates a new Preserve.
   */
   protected Preserve() {}

   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * @return String of the values in the inventory
   * @see FoodItem#toString()
   */
   @Override
   public String toString(){
       String returnString = super.toString() + " size: " + jarSize + "mL";
       return returnString;
   }
   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Output item to the writer
   * just the data portion of it, no description needed
   *
   * @param writer the writer
   */
   public void outputItem(Formatter writer) {
       super.outputItem(writer);
       writer.format("\n%f", jarSize);
   }
   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Adds a Preserve item
   *
   * @param scanner from main, sither Scanner(System.in) or Scanner(File)
   * @return true, if successfully added Item
   */
   public boolean addItem(Scanner scanner, boolean fromFile) {//Adds item info and returns a boolean value of whether the operation was successful or not
       

       if (super.addItem(scanner, fromFile)) {//if successfully read the basic Food Item inputs  
           if (fromFile) {
               
               try {//try/catch statement 
                   if (!scanner.hasNextLine()) { //to check if its reading form file 
                       return false;
                   }
                   jarSize = Float.valueOf(scanner.nextLine());
                   return true;
               } catch (NumberFormatException x) {//catching 
                   return false;
               }
           }else {
               //Reading inputs from user
               String userInput;
               while (true) {
                   System.out.print("Enter the size of the jar in millilitres: ");// must be a positive number
                   userInput= scanner.nextLine(); // use nextLine to get the whitespace
                   if (userInput.replace(" ", "").isEmpty()) {//to make sure that the user adds a value
                       System.out.println("You must enter the size of the jar");
                       continue; //ask for input again
                   }
                   try {//try/catch statement 
                       float temp= Float.valueOf(userInput);//Converting String to Float 
                       if (temp < 0) {//to make sure there is no negative number been added
                           throw new NumberFormatException();
                       }else {
                           jarSize = temp;
                           return true;
                       }
                   } catch (NumberFormatException x) {//catches NumberFormatException
                       System.out.print("Invalid entry");
                       continue;
                   }//end for catch 
               }//end while loop
           }//end else 
       }//end if statement

       return false;
   }//end method


}//end class 
