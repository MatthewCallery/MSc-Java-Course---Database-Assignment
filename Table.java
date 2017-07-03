/* Class stores a collection of rdatabase ecords as well as field
   names. If -ea is used, run the unit tests */

import java.util.*;

class Table {
  private List<Record> Table = new ArrayList<Record>();
  private List<String> columnList = new ArrayList<String>();
  private List<Integer> keyList = new ArrayList<Integer>();
  private int numColumns = 0;
  private int maxColumnWidth = 0;
  private int nextKey = 1000;

  int getNumRecords() {
    return Table.size();
  }

  int getNumColumns() {
    return numColumns;
  }

  int getMaxColumnWidth() {
    return maxColumnWidth;
  }

  List<String> getColumnList() {
    List<String> temp = columnList;
    return temp;
  }

  // Overloaded method. First inserts at 'bottom' of table
  void insertRecord(String row) {
    Table.add(createRecord(row, true));
  }

  // Inserts at specified position index
  void insertRecord(String row, int index) {
    Table.add(index, createRecord(row, true));
  }

  // addKey parameter determines if key needs to be added to record
  void insertRecord(String row, boolean addKey) {
    Table.add(createRecord(row, addKey));
  }

  /* Removes from Table all of the Records whose index is between
  fromIndex(inclusive) and toIndex(inclusive) */
  void deleteRecordRange(int fromIndex, int toIndex) {
    for(int i = toIndex; i >= fromIndex; i--){
      Table.remove(i);
    }
  }

  Record selectRecord(int rowIndex) {
    Record temp = Table.get(rowIndex);
    return temp;
  }

  void deleteColumn(String columnName) {
    int columnIndex = columnList.indexOf(columnName);
    int numRows = getNumRecords();

    columnList.remove(columnIndex);

    for(int i = 0; i < numRows; i++){
      Record temp = Table.get(i);
      temp.deleteField(columnIndex);
      Table.remove(i);
      Table.add(i, temp);
    }
    numColumns -= 1;
  }

  boolean deleteRecord(String key) {
    for(int i = 0; i < Table.size(); i++){
      if(Table.get(i).getField(0).equals(key)){
        Table.remove(i);
        return true;
      }
    }
    return false;
  }

  // Returns new record from a string of fields separated by commas
  private Record createRecord(String row, boolean addKey) {
    Record newRecord = new Record();
    String[] words = row.split(",");

    nextKey++;
    if(addKey == true){
      newRecord.addField(Integer.toString(nextKey));
      keyList.add(nextKey);
    }
    else{checkKey(words[0]);}

    for(int i = 0; i < words.length; i++){
      newRecord.addField(words[i]);
      checkFieldSize(words[i].length());
    }
    return newRecord;
  }

  // Determine if maxColumnWidth needs to be increased
  private void checkFieldSize(int wordLength){
    if(wordLength > maxColumnWidth){
      maxColumnWidth = wordLength;
    }
  }

  // Creates a table from a file format
  void createFromFile(List<String> lines) {
    boolean needsKey = true;

    if(lines.get(0).substring(0, 3).equals("Key")){needsKey = false;}
    setColumnNames(lines.get(0));

    for(int i = 1; i < lines.size(); i++){
      insertRecord(lines.get(i), needsKey);
    }
  }

  // Checks key is a valid format (integer)
  private void checkKey(String key) {
    boolean validKey = true;
    int len = key.length();

    if(len < 1) validKey = false;
    for(int i = 0; i < len; i++){
      if(!Character.isDigit(key.charAt(i))) validKey = false;
    }
    if(validKey == false){
      System.err.println("Error: Table contains invalid key");
      System.exit(1);
    }
    if(keyList.contains(Integer.parseInt(key))){
      System.err.println("Error: Table contains duplicate key");
      System.exit(1);
    }
    if(Integer.parseInt(key) > nextKey){
      nextKey = Integer.parseInt(key) + 1;
    }
  }

  // Creates table (with 0 records) from string of column names
  void setColumnNames(String names) {
    String[] words = names.split(",");

    this.numColumns = words.length;

    for(int i = 0; i < this.numColumns; i++){
      columnList.add(words[i]);
      if(words[i].length() > maxColumnWidth){
        maxColumnWidth = words[i].length();
      }
    }
    if(!words[0].equals("Key")){columnList.add(0, "Key"); numColumns++;}
  }

  // Returns string containing table data for writing to file
  String outputTable() {
    String data = "";
    data = outputColumnList(data);
    data = outputRecords(data);
    return data;
  }

  // Appends data from list of column names to string 'data'
  private String outputColumnList(String data) {
    for(int i = 0; i < numColumns; i++){
      data += columnList.get(i);
      data += ",";
    }
    data = data.substring(0, data.length() - 1);
    data += "\n";
    return data;
  }

  // Appends data from records to string 'data'
  private String outputRecords(String data) {
    for(int x = 0; x < getNumRecords(); x++){
      Record temp = Table.get(x);
      for(int y = 0; y < temp.getNumFields(); y++){
        data += temp.getField(y);
        data += ",";
      }
      data = data.substring(0, data.length() - 1);
      data += "\n";
    }
    data = data.substring(0, data.length() - 1);
    return data;
  }

  boolean updateField(String newField, String key, String columnName) {
    int index = -1;
    if(!columnList.contains(columnName)){return false;}
    else{index = columnList.indexOf(columnName);}
    for(int i = 0; i < Table.size(); i++){
      if(Table.get(i).getField(0).equals(key)){
        Table.get(i).setField(index, newField);
        return true;
      }
    }
    return false;
  }

  // If -ea is used, run unit tests
  public static void main(String[] args) {
    boolean testing = false;
    assert(testing = true);
    Table program = new Table();
    if (testing) program.test();
    else {
        System.err.println("Use:");
        System.err.println("  java -ea Table     for testing");
        System.exit(1);
    }
  }

  // Calls unit tests
  private void test() {
    testGetNumRecords();
    Table.clear();
    testGetMaxColumnWidth();
    Table.clear();
    testInsertRecord();
    Table.clear();
    testDeleteRecord();
    Table.clear();
    testDeleteRecordRange();
    Table.clear();
    testSetColumnNames();
    columnList.clear();
    Table.clear();
    testDeleteColumn();
    columnList.clear();
    Table.clear();
    testOutputColumnList();
    columnList.clear();
    Table.clear();
    testOutputRecords();
  }

  // test return of number of rows in table
  private void testGetNumRecords() {
    assert(getNumRecords() == 0);
    insertRecord("dog");
    assert(getNumRecords() == 1);
  }

  // test maxColumnWidth being set correctly when records inserted
  private void testGetMaxColumnWidth() {
    insertRecord("dog,cat,monkey");
    assert(maxColumnWidth == 6);
  }

  // test overloaded method to insert record
  private void testInsertRecord() {
    assert(getNumRecords() == 0);
    insertRecord("beckham");
    assert(getNumRecords() == 1);
    insertRecord("rooney");
    assert(getNumRecords() == 2);
    insertRecord("cole", 0);
    assert(Table.get(0).getField(1).equals("cole"));
    assert(Table.get(1).getField(1).equals("beckham"));
  }

  // test row is successfully deleted from table
  private void testDeleteRecord() {
    nextKey = 1000;
    assert(getNumRecords() == 0);
    insertRecord("badger");
    insertRecord("ferret");
    deleteRecord("1001");
    assert(getNumRecords() == 1);
    deleteRecord("1002");
    assert(getNumRecords() == 0);
  }

  // test range of records are successfully deleted from table
  private void testDeleteRecordRange() {
    assert(getNumRecords() == 0);
    insertRecord("badger");
    insertRecord("ferret");
    insertRecord("minx");
    deleteRecordRange(0, 1);
    assert(getNumRecords() == 1);
  }

  // test table is successfully created with passed column names
  private void testSetColumnNames() {
    setColumnNames("fname,lname,age,height,weight");
    //take into account key has been added as a column
    assert(numColumns == 6);
    assert(columnList.get(1).equals("fname"));
    assert(columnList.get(5).equals("weight"));
  }

  // test column successfully deleted from table
  private void testDeleteColumn() {
    setColumnNames("fname,lname,age,height,weight");
    insertRecord("Matthew,Callery,25,187,82");
    assert(numColumns == 6);
    assert(columnList.get(3).equals("age"));
    deleteColumn("age");
    assert(numColumns == 5);
    assert(columnList.get(3).equals("height"));
  }

  // test column name data is output in correct format for writing to file
  private void testOutputColumnList() {
    setColumnNames("fname,lname,age,height,weight");
    assert(outputColumnList("").equals("Key,fname,lname,age,height,weight\n"));
  }

  // test record data is output in correct format for writing to file
  private void testOutputRecords(){
    nextKey = 1000;
    insertRecord("matthew,callery,25");
    insertRecord("owen,farrell,26");
    assert(outputRecords("").equals("1001,matthew,callery,25\n1002,owen,farrell,26"));
  }
}
