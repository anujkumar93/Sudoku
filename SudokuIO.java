import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;

public class SudokuIO {
  static final int NUM_VARS = 81;
  static int vars[] = new int[NUM_VARS];
  static boolean varDomain[][] = new boolean[NUM_VARS][9];
  
  //Takes input from a file and creates a Sudoku CSP from that information
  public static void makeSudokuPuzzle(String[] args){
    try{
      File inFile = null;
      if (0 < args.length) {
         inFile = new File(args[0]);
      } else {
         System.err.println("Invalid arguments count:" + args.length);
         System.exit(0);
      }
      FileReader file = new FileReader(inFile);
      for (int a = 0; a < NUM_VARS; a++){
        char ch;
        do{
          ch = (char)file.read();
        }while ((ch == '\n') || (ch == '\r') || (ch == ' '));
        if (ch == '-'){
          vars[a] = 0;
          for (int j = 0; j < 9; j++){
            varDomain[a][j] = true;
          }
        }
        else{
          String  s = "" + ch;
          Integer i = new Integer(s);
          vars[a] = i.intValue();
          for (int j = 0; j < 9; j++){
            if (j == i.intValue() - 1)
              varDomain[a][j] = true;
            else
              varDomain[a][j] = false;
          }
        }
      }
     
      file.close();
    }
    catch(IOException e){System.out.println("File read error: " + e);}
  }
  
  //Outputs the Sudoku board to the console and a file
  public static void printSudoku(){
    try{
      FileWriter ofile = new FileWriter("output.txt");
      for (int a = 0; a < 9; a++){
        for (int b = 0; b < 9; b++){
          int c = 9*a + b;
          if (vars[c] == 0){
            System.out.print("- ");
            ofile.write("- ");
          }
          else{
            System.out.print(vars[c] + " ");
            ofile.write(vars[c] + " ");
          }
        }
        System.out.println("");
        ofile.write("\r\n");
      }
      ofile.write("\r\n");
      for (int a = 0; a < 81; a++){
        for (int b = 0; b < 9; b++){
          ofile.write(varDomain[a][b]+" ");
        }
        ofile.write("\r\n");
      }
      ofile.close();
    }
    catch(IOException e){System.out.println("File read error: " + e);}
  }
  
  public static void main(String[] args) {
    makeSudokuPuzzle(args);
    printSudoku();
  }
}
