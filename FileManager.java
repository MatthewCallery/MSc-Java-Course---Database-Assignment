/* Manages reading data from, and writing back to, files (tables) */

import java.util.*;
import java.io.*;

class FileManager {

  // Reads data from specified file and returns as list of strings
  List<String> scanFile(String fileName) {
    List<String> lines = new ArrayList<String>();

    try{
      File file = new File(fileName);
      Scanner in = new Scanner(file);
      while(in.hasNextLine()){
        lines.add(in.nextLine());
      }
      in.close();
    } catch (Exception e) { throw new Error(e); }

    return lines;
  }

  // Writes string of data to specified file
  void saveFile(String fileName, String tableContent){
    try{
      Writer fileWriter = new FileWriter(fileName, false);
      fileWriter.write(tableContent);
      fileWriter.flush();
      fileWriter.close();
    } catch (Exception e) { throw new Error(e); }
  }

  // Deletes file with given path name. Returns true if successful
  boolean deleteTable(String fileName) {
    File file = new File(fileName);
    return file.delete();
  }
}
