/* Manages selecting, creating, and deleting directories (databases)
   in the 'Databases' directory */

import java.io.*;
import java.util.*;

class Database {
  private List<String> databaseNames = new ArrayList<String>();
  private List<String> tableNames = new ArrayList<String>();

  // Return list of directories in the 'Databases' directory
  List<String> listDatabases() {
    File[] files = new File("Databases").listFiles();

    databaseNames.clear();
    for(int i = 0; i < files.length; i++){
      if(files[i].isDirectory()){
        databaseNames.add(files[i].toString());
      }
    }
    return databaseNames;
  }

  // Return list of tables in a given database
  List<String> listTables(String path) {
    File[] files = new File(path).listFiles();

    tableNames.clear();
    for(int i = 0; i < files.length; i++){
      tableNames.add(files[i].toString());
    }
    return tableNames;
  }

  // Delete directory in the 'Databases' directory
  boolean deleteDatabase(String name) {
    File directory = new File(name);

    if(directory.exists()){
      File[] files = directory.listFiles();
      if(null != files){
        for(int i = 0; i < files.length; i++) {
          if(files[i].isDirectory()) {
            deleteDatabase(files[i].toString());
          }
          else{files[i].delete();}
        }
      }
    }
    return(directory.delete());
  }

  // Create a directory in the 'Databases' directory
  boolean createDatabase(String name) {
    File directory = new File("Databases/" + name);

    if(!directory.exists()){
      return directory.mkdir();
    }
    return false;
  }
}
