import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    private ArrayList<String> groceryItems = new ArrayList<>();
    private ArrayList<String> groceryItemsCheckedOff = new ArrayList<>();
    private final String notCheckedOff = "Not Checked Off";
    private final String checkedOff = "Checked Off";

    public static void main(String[] args) {

        Main project = new Main(); // Create instance of Main class

        project.mainMenu();

    }

    public void sleep(int millis) {
        try {
            Thread.sleep(millis);
        }
        catch(InterruptedException ex) {
            System.out.println("Interrupted...");
        }
    }

    public void mainMenu() {
        // Handle all actions correlating to the main menu
        // Example: Calling methods according to entered user input through menu
        Scanner scnr = new Scanner(System.in);

        if(groceryItems.isEmpty()) {
            addTestCases(); // Add throwaway items to test the program
        }

        int userInput;

        System.out.println("Welcome to Grocery List Management!");
        System.out.println("1. Add Item to your Grocery List");
        System.out.println("2. Remove Item from your Grocery List");
        System.out.println("3. \"Check Off\" an Item in your Grocery List");
        System.out.println("4. Display your Grocery List");
        System.out.println("5. Exit");
        System.out.print("\nPlease enter a number correlating to the options above: ");

        userInput = isValidUserInput(scnr);
        switch (userInput) {
            case 1:
                addItemToList(scnr);
                break;
            case 2:
                removeItemFromList(scnr);
                break;
            case 3:
                checkOffItem(scnr);
                break;
            case 4:
                displayItemList();
                mainMenu();
                break;
            case 5:
                displayItemList();
                scnr.close();
                System.out.println("Goodbye!");
                System.exit(0);
        }
    }

    public int isValidUserInput(Scanner scnr) {
        // Check for valid user input using while loop
        // Use try/catch for exception handling
        // Returns the input after a valid one is entered

        int userMenuInput = 0;
        boolean validUserInput = false;

        while (!validUserInput) {
            try {
                userMenuInput = scnr.nextInt();

                if (userMenuInput < 0 || userMenuInput > 5) {
                    System.out.print("Please enter a valid menu option (1-5): ");
                } else {
                    validUserInput = true;
                }

            } catch (InputMismatchException e){
                System.out.print("Invalid input. Please enter a valid number (1-5): ");
                scnr.next(); // Consume invalid input
            }
        }

        return userMenuInput;
    }

    public void addItemToList(Scanner scnr) {
        String itemName;

        System.out.print("\nEnter the name of the item you would like to add: ");

        itemName = scnr.next();
        checkIfAddItemValid(itemName); // Call method to check if the item is valid (doesn't exist in the current list)

        mainMenu();
    }

    public void checkIfAddItemValid(String itemName) {
        boolean doesItemExist = false;

        for (String item : groceryItems) {
            if(itemName.equalsIgnoreCase(item)) {
                doesItemExist = true;
                break;
            }
        }

        if (doesItemExist) {
            System.out.println(itemName + " already exists... Returning to main menu!\n");
        } else {
            System.out.println("Item \"" + itemName + "\" has been added to your grocery list! Returning to main menu!\n");
            groceryItems.add(itemName);
            groceryItemsCheckedOff.add(notCheckedOff);
        }

        sleep(1000);
        mainMenu();
    }

    public void removeItemFromList(Scanner scnr) {
        String itemToRemove;

        if (groceryItems.isEmpty()) {
            System.out.println("Grocery list is empty... Returning to main menu!");
        } else {
            displayItemList();
            System.out.print("Choose an item by it's corresponding number or name to remove it: ");

            itemToRemove = scnr.next();
            isRemovedItemValid(itemToRemove);

            mainMenu();
        }
    }

    public void isRemovedItemValid(String itemToRemove) {
        // Check if remove item is valid using try/catch
        // Proper exception handling
        // TODO: Somehow get rid of duplicate try/catch blocks in this method and isCheckedItemValid();
        String targetItemRemove = null; // temporary storage for itemToRemove to avoid ConcurrentModificationException
        int itemRemoveArrayIndex = 0;

        try {
            itemRemoveArrayIndex = Integer.parseInt(itemToRemove) - 1;

            if (itemRemoveArrayIndex < 0 || itemRemoveArrayIndex >= groceryItems.size()) {
                System.out.println("That is not a valid item... Returning to main menu!");
                return;
            } else {
                targetItemRemove = groceryItems.get(itemRemoveArrayIndex); // Get the name of the item in the user specified index (removeItemArrayIndex)
                groceryItemsCheckedOff.set(itemRemoveArrayIndex, checkedOff);
            }
        } catch (NumberFormatException e) {
            for (String item : groceryItems) {
                if (item.equalsIgnoreCase(itemToRemove)) {
                    targetItemRemove = itemToRemove;

                    itemRemoveArrayIndex = groceryItems.indexOf(itemToRemove);

                    groceryItemsCheckedOff.set(itemRemoveArrayIndex, checkedOff);
                    break;
                }
            }
        }

        if (targetItemRemove != null) {
            System.out.println("Removed \"" + targetItemRemove + "\" from Grocery List! Returning to main menu!\n");
            groceryItems.remove(targetItemRemove);
        } else {
            System.out.println("That item doesn't exist... Returning to main menu!\n");
        }

        sleep(1000);
    }

    public void checkOffItem(Scanner scnr) {
        String itemToCheck;

        if (groceryItems.isEmpty()) {
            System.out.println("Your list of Groceries is empty..."); // add checked off thing
            sleep(1000);
        } else {
            displayItemList();
            System.out.print("Choose an item by it's corresponding number or name to check it off: ");

            itemToCheck = scnr.next();
            isCheckedItemValid(itemToCheck);

            mainMenu();
        }
    }

    public void isCheckedItemValid(String itemToCheck) {
        // Check if item to check off is valid using try/catch
        // Proper exception handling
        // TODO: Somehow get rid of duplicate try/catch blocks in this method and isRemovedItemValid();
        String targetItem = null; // temporary storage for itemToRemove to avoid ConcurrentModificationException
        int removeItemArrayIndex = -1;

        try {
            removeItemArrayIndex = Integer.parseInt(itemToCheck) - 1;

            if (removeItemArrayIndex < 0 || removeItemArrayIndex >= groceryItems.size()) {
                System.out.println("That is not a valid item... Returning to main menu!");
                return;
            } else {
                targetItem = groceryItems.get(removeItemArrayIndex); // Get the name of the item in the user specified index (removeItemArrayIndex)
                groceryItemsCheckedOff.set(removeItemArrayIndex, checkedOff);
            }
        } catch (NumberFormatException e) {
            for (String item : groceryItems) {
                if (item.equalsIgnoreCase(itemToCheck)) {
                    targetItem = itemToCheck;

                    removeItemArrayIndex = groceryItems.indexOf(itemToCheck);

                    groceryItemsCheckedOff.set(removeItemArrayIndex, checkedOff);
                    break;
                }
            }
        }

        System.out.println("Checked Off \"" + targetItem + "\" from Grocery List! Returning to main menu!\n");

        sleep(1000);

    }

    public void displayItemList() {
        if (groceryItems.isEmpty()) {
            System.out.println("Your list of Groceries is empty..."); // add checked off thing
            sleep(1000);
        }
        System.out.println("Your list of Grocery Items: ");
        for (int i = 0; i < groceryItems.size(); i++) {

            String isItemCheckedOff = isItemCheckedOff(i);


            System.out.println("   " + (i+1) + ". " + groceryItems.get(i) + " (" + isItemCheckedOff + ")");
        }
        System.out.println();
        sleep(3000);
    }

    public String isItemCheckedOff(int itemIndex) {
        String isItemChecked = groceryItemsCheckedOff.get(itemIndex);

        if (isItemChecked.equals(notCheckedOff)) {
            return "✗";
        } else {
            return "✓";
        }
    }

    public void addTestCases() {
        groceryItems.add("potato1");
        groceryItems.add("potato2");
        groceryItems.add("potato3");
        groceryItems.add("potato4");
        groceryItemsCheckedOff.add(notCheckedOff);
        groceryItemsCheckedOff.add(notCheckedOff);
        groceryItemsCheckedOff.add(notCheckedOff);
        groceryItemsCheckedOff.add(notCheckedOff);
    }
}