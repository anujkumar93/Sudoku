import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Sudoku_Waterfall {
	public static void implementWaterFall(boolean[][] varDomain){
		int prevCount=countTotalTrueValues(varDomain);
		Sudoku_AC3.runAC3(varDomain);
		Sudoku_NakedVariable.runNakedVariable(varDomain);
		Sudoku_HiddenVariable.runHiddenVariable(varDomain);
		Sudoku_XWing.runXWing(varDomain);
		int count=countTotalTrueValues(varDomain);
		while (prevCount!=count){
			Sudoku_AC3.runAC3(varDomain);
			prevCount=count;
			count=countTotalTrueValues(varDomain);
			if (prevCount!=count) continue;
			Sudoku_NakedVariable.runNakedVariable(varDomain);
			prevCount=count;
			count=countTotalTrueValues(varDomain);
			if (prevCount!=count) continue;
			Sudoku_HiddenVariable.runHiddenVariable(varDomain);
			prevCount=count;
			count=countTotalTrueValues(varDomain);
			if (prevCount!=count) continue;
			Sudoku_XWing.runXWing(varDomain);
			prevCount=count;
			count=countTotalTrueValues(varDomain);
		}
	}

	public static int countTotalTrueValues(boolean[][] varDomain){
		int count=0;
		for (int i=0; i<81;i++){
		  count+=Sudoku_NakedVariable.numberOfDomainValues(varDomain,i);
		}
		return count;
	}
}