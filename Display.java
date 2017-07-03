/* Receive input from users as text for DBMS. Print menus
   and tables as text and issue instructions to users */

import java.io.*;
import java.util.*;

class Display {
  private int maxColumnWidth = 12;
  private int numColumns = 0;
  private String rowDiv = "------------";

  // Ensure column width is large enough for all fields in the table
  void setColumnWidth(int columnSize){
    String newRowDiv = "";
    if(columnSize > (maxColumnWidth - 3)){
      maxColumnWidth = columnSize + 3;
      for(int i = 0; i < maxColumnWidth; i++){
        newRowDiv = newRowDiv + "-";
      }
      rowDiv = newRowDiv;
    }
  }

  void setNumColumns(int numColumns) {
    this.numColumns = numColumns;
  }

  // Prints out a formatted table with columns lined up
  void printTable(String tableData) {
    String[] lines = tableData.split("\n");

    System.out.println();
    printRowDiv();
    for(int i = 0; i < lines.length; i++){
      String[] words = lines[i].split(",");
      for(int j = 0; j < words.length; j++){
        System.out.format("%-" + maxColumnWidth + "s", words[j]);
      }
      System.out.println();
      if(i == 0){printRowDiv();}
    }
    printRowDiv();
    System.out.println();
  }

  // Print row divider formatting for table
  void printRowDiv() {
    for(int i = 0; i < numColumns; i++){
      System.out.format("-%s", rowDiv);
    }
    System.out.println();
  }

  // Returns menu selection input by the user
  Integer selectMenu(int option) {
    boolean done = false;
    Scanner sc = new Scanner(System.in);
    int maxSelection = 0;
    String selection = null;

    maxSelection = switchPrintMenu(option);

    while(!done){
      System.out.print("Select option (e.g. 1): ");
      selection = sc.next();
      if(checkSelection(selection, maxSelection)){done = true;}
      else{System.out.format("\nInvalid selection, try again\n\n");}
    }
    return Integer.parseInt(selection);
  }

  // Input determines which menu is printed to the console
  private Integer switchPrintMenu(int option) {
    switch(option){
      case 1:
        printDatabaseMenu();
        return 4;
      case 2:
        printTableMenu();
        return 4;
      case 3:
        printOptionMenu();
        return 6;
      default:
        System.err.println("Error: Incorrect integer passed to selectMenu()");
        System.exit(1);
    }
    return 0;
  }

  // Determine if user input is valid for menu
  private boolean checkSelection(String selection, int maxNumber) {
    if(selection.length() > 1) return false;
    if(!Character.isDigit(selection.charAt(0))) return false;
    if(selection.charAt(0) < '1') return false;
    if(Integer.parseInt(selection) > maxNumber) return false;
    return true;
  }

  // Print initial menu to console
  private void printDatabaseMenu() {
    System.out.format("\n%s%s", rowDiv, rowDiv);
    System.out.format("\nDatabase Menu\n%s%s\n", rowDiv, rowDiv);
    System.out.println("1. Select Database");
    System.out.println("2. Create Database");
    System.out.println("3. Delete Database");
    System.out.println("4. Exit Program");
    System.out.format("%s%s\n\n", rowDiv, rowDiv);
  }

  // Print table menu to console
  private void printTableMenu() {
    System.out.format("\n%s%s", rowDiv, rowDiv);
    System.out.format("\nTable Menu\n%s%s\n", rowDiv, rowDiv);
    System.out.println("1. Select Table");
    System.out.println("2. Create Table");
    System.out.println("3. Delete Table");
    System.out.println("4. Database Menu");
    System.out.format("%s%s\n\n", rowDiv, rowDiv);
  }

  // Print option menu to console
  private void printOptionMenu() {
    System.out.format("\n%s%s", rowDiv, rowDiv);
    System.out.format("\nOptions\n%s%s\n", rowDiv, rowDiv);
    System.out.println("1. Display Table");
    System.out.println("2. Insert Row");
    System.out.println("3. Delete Row");
    System.out.println("4. Update Record");
    System.out.println("5. Delete Column");
    System.out.println("6. Save Table");
    System.out.format("%s%s\n\n", rowDiv, rowDiv);
  }

  // User selects database or table they want to access
  String selectFile(List<String> fileNames, String listType){
    boolean done = false;
    Scanner sc = new Scanner(System.in);
    String selection = null;

    printList(fileNames, listType);

    while(!done){
      System.out.print("Select option (e.g. 1): ");
      selection = sc.next();
      if(checkSelection(selection, fileNames.size())){done = true;}
      else{System.out.format("\nInvalid selection, try again\n\n");}
    }
    return fileNames.get(Integer.parseInt(selection) - 1);
  }

  // Prints list of databases or tables depending on the input
  private void printList(List<String> fileNames, String listType) {
    System.out.format("\n%s%s", rowDiv, rowDiv);
    System.out.format("\n%s List:\n%s%s\n", listType, rowDiv, rowDiv);
    for(int i = 0; i < fileNames.size(); i++){
      System.out.format("%d. %s\n", (i + 1), fileNames.get(i));
    }
    System.out.format("%s%s\n\n", rowDiv, rowDiv);
  }

  // Prints message re. success of deleting a database
  void printDeleteFile(boolean success, String fileType) {
    if(success == true){
      System.out.format("\n%s successfully deleted\n\n", fileType);
    }
    else{System.out.format("\nUnable to delete %s\n\n", fileType);}
  }

  // Prints message re. success of creating a database
  void printCreateFile(boolean success, String fileType) {
    if(success == true){
      System.out.format("\n%s successfully created\n\n", fileType);
    }
    else{System.out.format("\nUnable to create %s\n\n", fileType);}
  }

  // User inputs the name of the new database they want to create
  String getNewDirectory() {
    Scanner sc = new Scanner(System.in);
    boolean done = false;
    String directoryName = null;

    while(!done){
      System.out.println("Enter new database name (letters only):");
      directoryName = sc.next();
      if(checkDirectoryName(directoryName)){done = true;}
      else{System.out.format("\nInvalid name, try again\n\n");}
    }
    return directoryName;
  }

  // check new database name input by user is all letters
  private boolean checkDirectoryName(String name) {
    int len = name.length();

    for(int i = 0; i < len; i++){
      if(!Character.isLetter(name.charAt(i))){
        return false;
      }
    }
    return true;
  }

  String getNewRecord(List<String> columnList) {
    String newRecord = "";
    String selection = "";
    Scanner sc = new Scanner(System.in);
    boolean done = false;
    int init = 0;

    if(columnList.get(0).equals("Key")){init = 1;}

    for(int i = init; i < columnList.size(); i++){
      while(!done){
        System.out.format("%s: ", columnList.get(i));
        selection = sc.next();
        if(checkFieldEntry(selection)){done = true;}
        else{System.out.format("\nInvalid entry, try again\n\n");}
      }
      newRecord = newRecord + selection;
      newRecord = newRecord + ",";
      done = false;
    }
    newRecord = newRecord.substring(0, newRecord.length() - 1);
    return newRecord;
  }

  // Checks user has not input field containing control characters
  private boolean checkFieldEntry(String selection) {
    int len = selection.length();

    if(len < 1){return false;}

    for(int i = 0; i < len; i++){
      if(selection.charAt(i) == ',' || selection.charAt(i) == '\n'){
        return false;
      }
      if(selection.charAt(i) == '.'){return false;}
    }
    return true;
  }

  // User inputs key of record they want to delete
  String deleteRecord() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter the key of the record you wish to delete: ");
    String input = sc.next();
    return input;
  }

  // Warn user if deleting record has failed
  void deleteRecordFailed() {
    System.out.format("\nWarning: Failed to delete record\n");
  }

  // User enters name of table they want to create
  String getTableName() {
    Scanner sc = new Scanner(System.in);
    String selection = null;
    boolean done = false;

    while(!done){
      System.out.format("\nEnter table name: ");
      selection = sc.next();
      if(checkFieldEntry(selection)){done = true;}
      else{System.out.format("\nInvalid entry, try again\n\n");}
    }
    return selection;
  };

  // User enters column names for their new table
  String createTable() {
    Scanner sc = new Scanner(System.in);
    String selection = "";
    String newTable = "";

    while(!selection.equals("exit")){
      System.out.format("\nEnter next column name (type 'exit' to finish):\n");
      selection = sc.next();
      if(checkFieldEntry(selection) && !selection.equals("exit")){
        newTable = newTable + selection;
        newTable = newTable + ",";
      }
      else if(selection.equals("exit")){}
      else{System.out.format("\nInvalid entry, try again\n\n");}
    }
    if(!newTable.equals("")){
      newTable = newTable.substring(0, newTable.length() - 1);
    }
    return newTable;
  }

  // User enters key of record they want to update
  String getKey() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter the key of the record you wish to update: ");
    String input = sc.next();
    return input;
  }

  // User enters name of column they wish to update a field in
  String getColumnName() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Which column do you want to update?: ");
    String input = sc.next();
    return input;
  }

  // User enters the string they wish to replace a field with
  String getNewField() {
    Scanner sc = new Scanner(System.in);
    boolean done = false;
    String input = "";

    while(!done){
      System.out.print("Enter the new field: ");
      input = sc.next();
      if(checkFieldEntry(input)){done = true;}
      else{System.out.format("\nInvalid entry, try again\n\n");}
    }
    return input;
  }

  // Warns user if updating record failed
  void updateRecordFailed(){
    System.out.format("\nWarning: Failed to update record\n");
  }

  String deleteColumn(List<String> columnList) {
    Scanner sc = new Scanner(System.in);
    boolean done = false;
    String input = "";

    while(!done){
      System.out.print("Enter a column name: ");
      input = sc.next();
      if(columnList.contains(input)){done = true;}
      else{System.out.format("\nInvalid entry, try again\n\n");}
    }
    return input;
  }

  // If -ea is used, run unit tests
  public static void main(String[] args) {
    boolean testing = false;
    assert(testing = true);
    Display program = new Display();
    if (testing) program.test();
    else {
        System.err.println("Use:");
        System.err.println("  java -ea Display     for testing");
        System.exit(1);
    }
  }

  // Calls unit tests
  private void test() {
    testSetColumnWidth();
  }

  // Check maxColumnWidth being set correctly
  private void testSetColumnWidth() {
    setColumnWidth(7);
    assert(maxColumnWidth == 12);
    setColumnWidth(9);
    assert(maxColumnWidth == 12);
    setColumnWidth(10);
    assert(maxColumnWidth == 13);
    setColumnWidth(15);
    assert(maxColumnWidth == 18);
  }
}
