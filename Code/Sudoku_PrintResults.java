import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Sudoku_PrintResults {
	public void runEverything() {
		String puzzleNums[]=new String[]{"001","002","010","015","025","026","048","051","062","076","081","082","090","095","099","100"};
		System.out.println("SIMPLE BACKTRACKING");
		System.out.println("Puzzle \t:\tNum Guesses");
		for (int i=0; i<puzzleNums.length;i++){
			String fileName = "puz-"+puzzleNums[i]+".txt";
			Sudoku_SimpleBackTracking sbt=new Sudoku_SimpleBackTracking();
			System.out.println(String.valueOf(puzzleNums[i])+"\t:\t"+String.valueOf(sbt.backtrackingSearch(new String[]{fileName})));
		}
		System.out.println("MRV BACKTRACKING");
		System.out.println("Puzzle \t:\tNum Guesses");
		for (int i=0; i<puzzleNums.length;i++){
			String fileName = "puz-"+puzzleNums[i]+".txt";
			Sudoku_MRVBackTracking smrv=new Sudoku_MRVBackTracking();
			System.out.println(String.valueOf(puzzleNums[i])+"\t:\t"+String.valueOf(smrv.mrvBacktrackingSearch(new String[]{fileName})));
		}
		System.out.println("MRV+AC3 BACKTRACKING");
		System.out.println("Puzzle \t:\tNum Guesses");
		for (int i=0; i<puzzleNums.length;i++){
			String fileName = "puz-"+puzzleNums[i]+".txt";
			Sudoku_MRVBackTrackingWithAC3 smrvac=new Sudoku_MRVBackTrackingWithAC3();
			System.out.println(String.valueOf(puzzleNums[i])+"\t:\t"+String.valueOf(smrvac.mrvBacktrackingSearchWithAC3(new String[]{fileName})));
		}
		System.out.println("MRV+Waterfall BACKTRACKING");
		System.out.println("Puzzle \t:\tNum Guesses");
		for (int i=0; i<puzzleNums.length;i++){
			String fileName = "puz-"+puzzleNums[i]+".txt";
			Sudoku_MRVBackTrackingWithWaterfall smrvwf=new Sudoku_MRVBackTrackingWithWaterfall();
			System.out.println(String.valueOf(puzzleNums[i])+"\t:\t"+String.valueOf(smrvwf.mrvBacktrackingSearchWithWaterfall(new String[]{fileName})));
		}
	}

	public static void main(String[] args) {
		Sudoku_PrintResults spr = new Sudoku_PrintResults();
		spr.runEverything();
	}
}