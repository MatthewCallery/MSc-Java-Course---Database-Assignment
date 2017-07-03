/* Class holds any type of database record as a collection
   of fields which are strings. If -ea is used, run the
   unit tests */

import java.util.*;

class Record {
  private List<String> Record = new ArrayList<String>();

  int getNumFields() {
    return Record.size();
  }

  // Overloaded method. First inserts at end of record
  void addField(String field) {
    Record.add(field);
  }

  //Inserts at specified position index
  void addField(String field, int index) {
    Record.add(index, field);
  }

  String getField(int index) {
    checkIndex(index, "getField()");
    return Record.get(index);
  }

  void setField(int index, String field) {
    checkIndex(index, "setField()");
    Record.set(index, field);
  }

  boolean containsField(String field) {
    return Record.contains(field);
  }

  void deleteField(int index) {
    checkIndex(index, "deleteField()");
    Record.remove(index);
  }

  // Exits program if index > number of fields
  private void checkIndex(int index, String methodName) {
    if(index >= getNumFields() || index < 0){
      System.err.println("Error: Invalid index passed to " + methodName);
      System.exit(1);
    }
  }

  // If -ea is used, run unit tests
  public static void main(String[] args) {
    boolean testing = false;
    assert(testing = true);
    Record program = new Record();
    if (testing) program.test();
    else {
        System.err.println("Use:");
        System.err.println("  java -ea Record     for testing");
        System.exit(1);
    }
  }

  // Calls unit tests
  private void test() {
    testGetNumFields();
    Record.clear();
    testAddField();
    Record.clear();
    testGetFields();
    Record.clear();
    testSetField();
    Record.clear();
    testContainsField();
    Record.clear();
    testDeleteField();
  }

  // test return of size of ArrayList
  private void testGetNumFields() {
    assert(getNumFields() == 0);
    Record.add("dog");
    assert(getNumFields() == 1);
    Record.add("fish");
    assert(getNumFields() == 2);
  }

  // test adding string to ArrayList
  private void testAddField() {
    assert(getNumFields() == 0);
    addField("cat");
    assert(Record.get(0).equals("cat"));
    addField("mouse");
    assert(Record.get(1).equals("mouse"));
    addField("rabbit", 1);
    assert(Record.get(1).equals("rabbit"));
    addField("hamster", 0);
    assert(Record.get(0).equals("hamster"));
  }

  // test getting string
  private void testGetFields() {
    addField("nike");
    assert(getField(0).equals("nike"));
    addField("adidas");
    assert(getField(1).equals("adidas"));
  }

  // test setting string
  private void testSetField() {
    addField("disney");
    assert(getField(0).equals("disney"));
    setField(0, "pixar");
    assert(getField(0).equals("pixar"));
  }

  // test checking string is present in ArrayList
  private void testContainsField() {
    addField("churchill");
    assert(containsField("churchill") == true);
    addField("blair");
    assert(containsField("blair") == true);
    assert(containsField("cameron") == false);
  }

  // test string is successfully deleted from ArrayList
  private void testDeleteField() {
    addField("bacon");
    assert(getNumFields() == 1);
    deleteField(0);
    assert(getNumFields() == 0);
  }
}
