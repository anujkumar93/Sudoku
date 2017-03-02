public class Sudoku_MRVBackTrackingWithWaterfall {
	int guess = 0;

	public int mrvBacktrackingSearchWithWaterfall(String[] args) {
		SudokuIO.makeSudokuPuzzle(args);
		alterVarDomain();
		Sudoku_Waterfall.implementWaterFall(SudokuIO.varDomain);
		recursiveBckTrackingWithMrvWaterfall(SudokuIO.vars, SudokuIO.varDomain);
		return guess;
	}

	private void alterVarDomain() {
		for (int i = 0; i < SudokuIO.vars.length; i++) {
			if (SudokuIO.vars[i] == 0) {
				for (int j = 0; j < 9; j++) {
					SudokuIO.varDomain[i][j] = checkConstraints(i, SudokuIO.vars, j + 1);
				}
			}
		}
	}

	public int[] recursiveBckTrackingWithMrvWaterfall(int[] vars, boolean[][] varDomain) {
		int mostConstrainedCell = -1;
		int[] result = vars;
		boolean[][] tempDomain = null;
		if (isASolution(result))
			return result;
		else {
			int min = 10;
			for (int i = 0; i < vars.length; i++) {
				int counter = 0;
				if (vars[i] == 0){
					for (int a = 0; a < 9; a++){
						if (varDomain[i][a])
							counter++;
					}
					if (counter == 0) {
						return null;
					}
					if (counter < min) {
						min = counter;
						mostConstrainedCell = i;
					}
				}
			}
			int numberDomainValues=0;
			for (int j = 0; j < 9; j++) {
				if (varDomain[mostConstrainedCell][j]){
					numberDomainValues++;
				}
			}
			guess+=(numberDomainValues-1);
			boolean correct = false;
			for (int j = 1; j < 10; j++) {
				if (varDomain[mostConstrainedCell][j - 1]){
					vars[mostConstrainedCell] = j;
					if (checkConstraints(mostConstrainedCell, vars, j)) {
						correct = true;
						tempDomain = executeWaterfall(mostConstrainedCell, j, varDomain);
						result = recursiveBckTrackingWithMrvWaterfall(vars, tempDomain);
						if (result == null) {
							vars[mostConstrainedCell] = 0;
							correct = false;
						} else {
							return result;
						}
					}
				}
			}
			if (!correct) {
				vars[mostConstrainedCell] = 0;
				result = null;
			} 
			return result;
		}
	}

	private boolean[][] executeWaterfall(int firstUnassigned, int number, boolean[][] domain) {
		boolean[][] temp = new boolean[81][9];
		for (int i = 0; i < 81; i++) {
			for (int k1 = 0; k1 < 9; k1++) {
				if (i==firstUnassigned){
					if (k1==number){
						temp[i][k1]=true;
					} else temp[i][k1]=false;
				} else temp[i][k1] = domain[i][k1];
			}
		}
		Sudoku_Waterfall.implementWaterFall(temp);
		return temp;
		
	}

	public boolean checkConstraints(int firstUnassigned, int[] vars, int j) {
		int column = firstUnassigned % 9;
		int row = firstUnassigned / 9;
		boolean flag = true;

		loopRow: for (int j2 = (row) * 9; j2 < (row + 1) * 9 && flag; j2++) {
			if (vars[j2] == j && j2 != firstUnassigned)
				flag = false;
		}
		loopColumn: for (int k = column; k <= 72 + column && flag; k += 9) {
			if (vars[k] == j && k != firstUnassigned)
				flag = false;
		}
		int boxRow = row % 3;
		int boxClmn = column % 3;
		loopBox: for (int i = 0; i < 3; i++) {
			for (int b = (row + i - boxRow) * 9 + column - boxClmn; (b < (row + i - boxRow) * 9 + column - boxClmn + 3)
					&& flag; b++) {
				if (vars[b] == j && b != firstUnassigned)
					flag = false;
			}
		}
		return flag;
	}

	private boolean isASolution(int[] assignment) {
		for (int i = 0; i < assignment.length; i++)
			if (assignment[i] == 0)
				return false;
		return true;
	}

	public static void main(String[] args) {
		Sudoku_MRVBackTrackingWithWaterfall sbt = new Sudoku_MRVBackTrackingWithWaterfall();
		sbt.mrvBacktrackingSearchWithWaterfall(args);
	}
}
