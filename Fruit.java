
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
public class Fruit extends FoodItem {

   /** The orchard name. */
   private String orchardName;


   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Instantiates a new Fruit.
   */
   protected Fruit() {}
   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * @return String of the values in the inventory
   * @see FoodItem#toString()
   */
   @Override
   public String toString(){
       String returnString = super.toString() + " orchard supplier: " + orchardName;//a toString method to print rhe orchard supplier as needed in fruits
       return returnString;
   }
   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Output item to the writer
   * just the data portion of it
   *
   * @param writer the writer
   */
   public void outputItem(Formatter writer) {
       super.outputItem(writer);// it will output the orchardName from the writer object form the text file
       writer.format("\n%s", orchardName);
   }
   // -------------------------------------------------------------------------------------------------------------------------
   /**
   * Adds a Fruit item
   *
   * @param scanner from main, sither Scanner(System.in) or Scanner(File)
   * @return true, if successfully added Item
   */
   public boolean addItem(Scanner scanner, boolean fromFile) {  //Adds item info and returns a boolean value of whether the operation was successful or not
     

       if (super.addItem(scanner, fromFile)) { //if successfully read the basic Food Item inputs
          
           if (fromFile) {//if true that means its reading from file 
               
               if (!scanner.hasNextLine()) {
                   return false;
               }
               orchardName = scanner.nextLine();
               return true;
           }else {
               //Reading inputs from user
               String userInput;
               while (true) {
                   System.out.print("Enter the name of the orchard supplier: "); // can be any input
                   userInput= scanner.nextLine(); // use nextLine to get the whitespaces
                   if (userInput.replace(" ", "").isEmpty()) {
                       System.out.println("You must enter the name of the orchard supplier");
                       continue; //ask for input again
                   }
                   orchardName = userInput;
                   return true;
               }
           }
       }

       return false;
   }


}
