import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Sudoku_NakedVariable {
	public static void runNakedVariable(boolean[][] varDomain){
		for (int i=0;i<9;i++){
			checkNakedVariableInRow(varDomain,i);
		}
		for (int i=0;i<9;i++){
			checkNakedVariableInColumn(varDomain,i);
		}
		for (int i=0;i<9;i++){
			checkNakedVariableInBox(varDomain,i);
		}
	}

	public static void checkNakedVariableInRow(boolean[][] varDomain, int rowNum){
		checkNakedPairInRow(varDomain,rowNum);
		checkNakedTripletsInRow(varDomain,rowNum);
		checkNakedQuadrupletsInRow(varDomain,rowNum);
	}

	public static void checkNakedVariableInColumn(boolean[][] varDomain, int columnNum){
		checkNakedPairInColumn(varDomain,columnNum);
		checkNakedTripletsInColumn(varDomain,columnNum);
		checkNakedQuadrupletsInColumn(varDomain,columnNum);
	}

	public static void checkNakedVariableInBox(boolean[][] varDomain, int boxNum){
		checkNakedPairInBox(varDomain,boxNum);
		checkNakedTripletsInBox(varDomain,boxNum);
		checkNakedQuadrupletsInBox(varDomain,boxNum);
	}

	public static int numberOfDomainValues(boolean[][] varDomain, int pos){
		int count=0;
		for (int i=0; i<9; i++){
			if (varDomain[pos][i]) count++;
		}
		return count;
	}

	public static boolean checkIfDomainsMatch(boolean[][] varDomain, int[] pos, int size){
		boolean result=false;
		boolean unionDomain[] = new boolean[9];
		for (int i=0; i<size; i++){
			for (int j=0; j<9; j++){
				if (varDomain[pos[i]][j]){
					unionDomain[j]=true;
				}
			}
		}
		int count=0;
		for (int i=0; i<9; i++){
			if (unionDomain[i]) count++;
		}
		if (count==size) result=true;
		return result;
	}

	public static void checkAndRemoveValuesFromOtherColumnsInRow(boolean[][] varDomain, int rowNum, int[] pos, int size){
		boolean unionDomain[] = new boolean[9];
		for (int i=0; i<size; i++){
			for (int j=0; j<9; j++){
				if (varDomain[pos[i]][j]){
					unionDomain[j]=true;
				}
			}
		}
		int count=0;
		for (int i=0; i<9; i++){
			if (unionDomain[i]) count++;
		}
		if (count==size) {
			for (int i=0; i<9; i++){
				if (unionDomain[i]){
					for (int j=0;j<9;j++){
						int posToAlter = convertToLinear(new int[]{rowNum,j});
						if (!IntStream.of(pos).anyMatch(x -> x == posToAlter)){
							varDomain[posToAlter][i]=false;
						}
					}
				}
			}
		}
	}

	public static void checkNakedPairInRow(boolean[][] varDomain, int rowNum){
		for (int i=0; i<8; i++){
			for (int j=i+1; j<9; j++){
				int firstPosition=convertToLinear(new int[]{rowNum,i});
				int secondPosition=convertToLinear(new int[]{rowNum,j});
				checkAndRemoveValuesFromOtherColumnsInRow(varDomain, rowNum, new int[]{firstPosition,secondPosition}, 2);
			}
		}
	}

	public static void checkNakedTripletsInRow(boolean[][] varDomain, int rowNum){
		for (int i=0; i<7; i++){
			for (int j=i+1; j<8; j++){
				for (int k=j+1; k<9; k++){
					int firstPosition=convertToLinear(new int[]{rowNum,i});
					int secondPosition=convertToLinear(new int[]{rowNum,j});
					int thirdPosition=convertToLinear(new int[]{rowNum,k});
					checkAndRemoveValuesFromOtherColumnsInRow(varDomain, rowNum, 
						new int[]{firstPosition,secondPosition,thirdPosition}, 3);
				}
			}
		}
	}

	public static void checkNakedQuadrupletsInRow(boolean[][] varDomain, int rowNum){
		for (int i=0; i<6; i++){
			for (int j=i+1; j<7; j++){
				for (int k=j+1; k<8; k++){
					for (int l=k+1;l<9;l++){
						int firstPosition=convertToLinear(new int[]{rowNum,i});
						int secondPosition=convertToLinear(new int[]{rowNum,j});
						int thirdPosition=convertToLinear(new int[]{rowNum,k});
						int fourthPosition=convertToLinear(new int[]{rowNum,l});
						checkAndRemoveValuesFromOtherColumnsInRow(varDomain, rowNum, 
							new int[]{firstPosition,secondPosition,thirdPosition,fourthPosition}, 4);
					}
				}
			}
		}
	}

	public static void checkAndRemoveValuesFromOtherRowsInColumn(boolean[][] varDomain, int columnNum, int[] pos, int size){
		boolean unionDomain[] = new boolean[9];
		for (int i=0; i<size; i++){
			for (int j=0; j<9; j++){
				if (varDomain[pos[i]][j]){
					unionDomain[j]=true;
				}
			}
		}
		int count=0;
		for (int i=0; i<9; i++){
			if (unionDomain[i]) count++;
		}
		if (count==size) {
			for (int i=0; i<9; i++){
				if (unionDomain[i]){
					for (int j=0;j<9;j++){
						int posToAlter = convertToLinear(new int[]{j,columnNum});
						if (!IntStream.of(pos).anyMatch(x -> x == posToAlter)){
							varDomain[posToAlter][i]=false;
						}
					}
				}
			}
		}
	}

	public static void checkNakedPairInColumn(boolean[][] varDomain, int columnNum){
		for (int i=0; i<8; i++){
			for (int j=i+1; j<9; j++){
				int firstPosition=convertToLinear(new int[]{i,columnNum});
				int secondPosition=convertToLinear(new int[]{j,columnNum});
				checkAndRemoveValuesFromOtherRowsInColumn(varDomain, columnNum, new int[]{firstPosition,secondPosition}, 2);
			}
		}
	}

	public static void checkNakedTripletsInColumn(boolean[][] varDomain, int columnNum){
		for (int i=0; i<7; i++){
			for (int j=i+1; j<8; j++){
				for (int k=j+1; k<9; k++){
					int firstPosition=convertToLinear(new int[]{i,columnNum});
					int secondPosition=convertToLinear(new int[]{j,columnNum});
					int thirdPosition=convertToLinear(new int[]{k,columnNum});
					checkAndRemoveValuesFromOtherRowsInColumn(varDomain, columnNum, 
						new int[]{firstPosition,secondPosition,thirdPosition}, 3);
				}
			}
		}
	}

	public static void checkNakedQuadrupletsInColumn(boolean[][] varDomain, int columnNum){
		for (int i=0; i<6; i++){
			for (int j=i+1; j<7; j++){
				for (int k=j+1; k<8; k++){
					for (int l=k+1;l<9;l++){
						int firstPosition=convertToLinear(new int[]{i,columnNum});
						int secondPosition=convertToLinear(new int[]{j,columnNum});
						int thirdPosition=convertToLinear(new int[]{k,columnNum});
						int fourthPosition=convertToLinear(new int[]{l,columnNum});
						checkAndRemoveValuesFromOtherRowsInColumn(varDomain, columnNum, 
							new int[]{firstPosition,secondPosition,thirdPosition,fourthPosition}, 4);
					}
				}
			}
		}
	}

	public static void checkAndRemoveValuesFromOthersInBox(boolean[][] varDomain, int boxNum, int[] pos, int size){
		boolean unionDomain[] = new boolean[9];
		for (int i=0; i<size; i++){
			for (int j=0; j<9; j++){
				if (varDomain[pos[i]][j]){
					unionDomain[j]=true;
				}
			}
		}
		int count=0;
		for (int i=0; i<9; i++){
			if (unionDomain[i]) count++;
		}
		if (count==size) {
			for (int i=0; i<9; i++){
				if (unionDomain[i]){
					for (int j=(27*(boxNum/3)+3*(boxNum%3)) ; j<(27*(boxNum/3)+3*(boxNum%3))+21 ; j+=((j%3==2)?7:1)){
						int temp=j;
						if (!IntStream.of(pos).anyMatch(x -> x == temp)){
							varDomain[j][i]=false;
						}
					}
				}
			}
		}
	}

	public static void checkNakedPairInBox(boolean[][] varDomain, int boxNum){
		for (int i=(27*(boxNum/3)+3*(boxNum%3)) ; i<(27*(boxNum/3)+3*(boxNum%3))+20 ; i+=((i%3==2)?7:1)){
			for (int j=i+((i%3==2)?7:1) ; j<(27*(boxNum/3)+3*(boxNum%3))+21 ; j+=((j%3==2)?7:1)){
				checkAndRemoveValuesFromOthersInBox(varDomain, boxNum, new int[]{i,j}, 2);
			}
		}
	}

	public static void checkNakedTripletsInBox(boolean[][] varDomain, int boxNum){
		for (int i=(27*(boxNum/3)+3*(boxNum%3)) ; i<(27*(boxNum/3)+3*(boxNum%3))+19 ; i+=((i%3==2)?7:1)){
			for (int j=i+((i%3==2)?7:1) ; j<(27*(boxNum/3)+3*(boxNum%3))+20 ; j+=((j%3==2)?7:1)){
				for (int k=j+((j%3==2)?7:1) ; k<(27*(boxNum/3)+3*(boxNum%3))+21 ; k+=((k%3==2)?7:1)){
					checkAndRemoveValuesFromOthersInBox(varDomain, boxNum, new int[]{i,j,k}, 3);
				}
			}
		}
	}

	public static void checkNakedQuadrupletsInBox(boolean[][] varDomain, int boxNum){
		for (int i=(27*(boxNum/3)+3*(boxNum%3)) ; i<(27*(boxNum/3)+3*(boxNum%3))+12 ; i+=((i%3==2)?7:1)){
			for (int j=i+((i%3==2)?7:1) ; j<(27*(boxNum/3)+3*(boxNum%3))+19 ; j+=((j%3==2)?7:1)){
				for (int k=j+((j%3==2)?7:1) ; k<(27*(boxNum/3)+3*(boxNum%3))+20 ; k+=((k%3==2)?7:1)){
					for (int l=k+((k%3==2)?7:1) ; l<(27*(boxNum/3)+3*(boxNum%3))+21 ; l+=((l%3==2)?7:1)){
						checkAndRemoveValuesFromOthersInBox(varDomain, boxNum, new int[]{i,j,k,l}, 4);
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