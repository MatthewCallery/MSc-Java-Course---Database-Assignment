/* Controlling class for the DBMS */

import java.util.*;
import java.io.*;

class DB {
  private FileManager fileManager = new FileManager();
  private Table table = new Table();
  private Display display = new Display();
  private Database database = new Database();
  private boolean programDone = false;
  private boolean tableMenuDone = false;
  private boolean optionMenuDone = false;
  private String selectedDatabase = null;
  private String selectedTable = null;

  public static void main(String[] args) {
    DB program = new DB();
    program.run();
  }

  private void run() {
    while(!programDone){
      databaseMenuSwitch(display.selectMenu(1));
    }
    System.out.format("\n\nProgram has successfully exited\n\n");
  }

  // User input determines which option is implemented
  private void databaseMenuSwitch(int option) {
    switch(option){
      case 1:
        selectDatabase();
        while(!tableMenuDone){
          tableMenuSwitch(display.selectMenu(2));
        }
        break;
      case 2:
        createDatabase();
        break;
      case 3:
        deleteDatabase();
        break;
      case 4:
        programDone = true;
        break;
      default:
        System.err.print("Error: Invalid integer passed to ");
        System.err.println("databaseMenuSwitch()");
        System.exit(1);
    }
  }

  // Controls the selection of a database in the Databases folder
  private void selectDatabase() {
    List<String> listDatabases = database.listDatabases();
    selectedDatabase = display.selectFile(listDatabases, "Database");
    tableMenuDone = false;
  }

  // Controls the creation of a new database in the Databases folder
  private void createDatabase() {
    if(database.createDatabase(display.getNewDirectory())){
      display.printCreateFile(true, "Database");
    }
    else{display.printCreateFile(false, "Database");}
  }

  // Controls the deletion of a database from the Databases folder
  private void deleteDatabase() {
    List<String> listDatabases = database.listDatabases();
    selectedDatabase = display.selectFile(listDatabases, "Database");
    if(database.deleteDatabase(selectedDatabase)){
      display.printDeleteFile(true, "Database");
    }
    else{display.printDeleteFile(false, "Database");}
  }

  // User input determines which option is implemented
  private void tableMenuSwitch(int option) {
    switch(option){
      case 1:
        selectTable();
        while(!optionMenuDone){
          optionMenuSwitch(display.selectMenu(3));
        }
        break;
      case 2:
        createTable();
        break;
      case 3:
        deleteTable();
        break;
      case 4:
        tableMenuDone = true;
        break;
      default:
        System.err.print("Error: Invalid integer passed to ");
        System.err.println("tableMenuSwitch()");
        System.exit(1);
    }
  }

  // Load user selected table from the database
  private void selectTable() {
    List<String> listTables = database.listTables(selectedDatabase);
    selectedTable = display.selectFile(listTables, "Table");
    table = new Table();
    table.createFromFile(fileManager.scanFile(selectedTable));
    optionMenuDone = false;
  }

  // Create new table in the current database
  private void createTable() {
    table = new Table();
    selectedTable = display.getTableName();
    selectedTable = selectedDatabase + "/" + selectedTable + ".txt";
    table.setColumnNames(display.createTable());
    fileManager.saveFile(selectedTable, table.outputTable());
  }

  // Deletes user-requested table from the database
  private void deleteTable() {
    List<String> listTables = database.listTables(selectedDatabase);
    selectedTable = display.selectFile(listTables, "Table");
    if(fileManager.deleteTable(selectedTable)){
      display.printDeleteFile(true, "Table");
    }
    else{display.printDeleteFile(false, "Table");}
  }

  // User input determines which option is implemented
  private void optionMenuSwitch(int option) {
    switch(option){
      case 1:
        displayTable();
        break;
      case 2:
        displayTable();
        table.insertRecord(display.getNewRecord(table.getColumnList()));
        break;
      case 3:
        displayTable();
        deleteRecord();
        break;
      case 4:
        displayTable();
        updateRecord();
        break;
      case 5:
        displayTable();
        deleteColumn();
        break;
      case 6:
        fileManager.saveFile(selectedTable, table.outputTable());
        optionMenuDone = true;
        break;
      default:
        System.err.print("Error: Invalid integer passed to ");
        System.err.println("optionMenuSwitch()");
        System.exit(1);
    }
  }

  // Prints out current table to the console
  private void displayTable() {
    display.setColumnWidth(table.getMaxColumnWidth());
    display.setNumColumns(table.getNumColumns());
    display.printTable(table.outputTable());
  }

  // Controls the deletion of a record from the current table
  private void deleteRecord() {
    if(!table.deleteRecord(display.deleteRecord())){
      display.deleteRecordFailed();
    }
  }

  // Controls the updating process of a record in the current table
  private void updateRecord() {
    String key = display.getKey();
    String columnName = display.getColumnName();
    String newField = display.getNewField();
    if(!table.updateField(newField, key, columnName)){
      display.updateRecordFailed();
    }
  }

  private void deleteColumn() {
    table.deleteColumn(display.deleteColumn(table.getColumnList()));
  }
}
