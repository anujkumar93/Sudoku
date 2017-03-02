import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Sudoku_XWing {
	public static void runXWing(boolean[][] varDomain){
		for (int i=0;i<8;i++){
			for (int j=i+1;j<9;j++){
				checkXWingForRow(varDomain,i,j);
			}
		}
		for (int i=0;i<8;i++){
			for (int j=i+1;j<9;j++){
				checkXWingForColumn(varDomain,i,j);
			}
		}
	}

	public static void checkXWingForRow(boolean[][] varDomain, int rowNum1, int rowNum2){
		for (int n=0; n<9; n++){
			int count=0,col1=-1,col2=-1;
			for (int i=0; i<9; i++){
				if (varDomain[convertToLinear(new int[]{rowNum1,i})][n] || varDomain[convertToLinear(new int[]{rowNum2,i})][n]){
					count++;
					if (col1==-1) col1=i;
					else col2=i;
				}
			}
			if (count==2){
				for (int i=0; i<9; i++){
					if (i!=rowNum1 && i!=rowNum2){
						varDomain[convertToLinear(new int[]{i,col1})][n]=false;
						varDomain[convertToLinear(new int[]{i,col2})][n]=false;
					}
				}
			}
		}
	}

	public static void checkXWingForColumn(boolean[][] varDomain, int colNum1, int colNum2){
		for (int n=0; n<9; n++){
			int count=0,row1=-1,row2=-1;
			for (int i=0; i<9; i++){
				if (varDomain[convertToLinear(new int[]{i,colNum1})][n] || varDomain[convertToLinear(new int[]{i,colNum2})][n]){
					count++;
					if (row1==-1) row1=i;
					else row2=i;
				}
			}
			if (count==2){
				for (int i=0; i<9; i++){
					if (i!=colNum1 && i!=colNum2){
						varDomain[convertToLinear(new int[]{row1,i})][n]=false;
						varDomain[convertToLinear(new int[]{row2,i})][n]=false;
					}
				}
			}
		}
	}

	public static int[] convertTo2D(int linearInt){
		int coordinates[] = new int[2];
		coordinates[0] = linearInt/9;
		coordinates[1] = linearInt%9;
		return coordinates;
	}

	public static int convertToLinear(int[] coordinates){
		int linearInt=coordinates[0]*9+coordinates[1];
		return linearInt;
	}
}