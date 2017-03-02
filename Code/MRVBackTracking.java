public class MRVBackTracking {
	// int firstUnassigned;
	int guess = 0;
	int[] solution = new int[81];
	int[][] size = new int[81][1];

	public void BacktrackingSearch(String[] args) {
		SudokuIO.makeSudokuPuzzle(args);
		createVarDomains();
		solution = recursiveBckTracking(SudokuIO.vars, SudokuIO.varDomain, -1);
		for (int i = 0; i < 81; i++) {
			System.out.print(solution[i] + " * ");
			if (i % 9 == 8)
				System.out.println();
		}
		System.out.println(this.guess);
	}

	private void createVarDomains() {
		// TODO Auto-generated method stub
		for (int i = 0; i < SudokuIO.vars.length; i++) {
			if (SudokuIO.vars[i] != 0) {
				// size[i] = 10;
				for (int j = 0; j < 9; j++) {
					SudokuIO.varDomain[i][j] = false;
				}
				// SudokuIO.varDomain[i][SudokuIO.vars[i]-1] = true;
			} else {
				for (int j = 0; j < 9; j++) {
					SudokuIO.varDomain[i][j] = checkConstraints(i, SudokuIO.vars, j + 1);
				}
			}
		}

		System.out.println(SudokuIO.varDomain[28][2] + "*****");
	}

	public int[] recursiveBckTracking(int[] assignment, boolean[][] domain, int lastUnassigned) {
		int firstUnassigned = -1;
		int[] result = null;
		boolean[][] tempDomain = null;
		boolean[][] newDomain = new boolean[81][9];
		// boolean[][] temp = new boolean[81][9];
		for (int i = 0; i < 81; i++) {
			for (int k1 = 0; k1 < 9; k1++) {
				newDomain[i][k1] = domain[i][k1];
			}

		}
		if (isASolution(assignment))
			return assignment;
		else {
			int min = 10;
			int minNumber = 0;
			int counter = 0;
			loop1: for (int i = 0; i < assignment.length; i++) {
				counter = 0;
				if (i == lastUnassigned || assignment[i] != 0)
					continue;
				if (assignment[i] == 0) {
					for (int a = 0; a < 9; a++)
						if (newDomain[i][a])
							counter++;
				}
				if (counter == 0) {
					System.out.println(i + "**");
					return null;
				}
				if (counter < min) {
					min = counter;
					minNumber = i;
				}
			}
			firstUnassigned = minNumber;
			System.out.println(firstUnassigned + " : " + min);
			int j = 0;
			boolean right = false;
			for (j = 1; j < 10; j++) {
				if (!newDomain[firstUnassigned][j - 1])
					continue;
				this.guess++;
				assignment[firstUnassigned] = j;
				if (checkConstraints(firstUnassigned, assignment, j)) {
					right = true;
					System.out.println(firstUnassigned + " -> " + j);
					tempDomain = forwardChecking(firstUnassigned, assignment, j, newDomain);
//					for (int i = 0; i < 81; i++) {
//						System.out.print(assignment[i] + " | ");
//						if (i % 9 == 8)
//							System.out.println();
//					}
					result = recursiveBckTracking(assignment, tempDomain, firstUnassigned);
					if (result == null) {
						assignment[firstUnassigned] = 0;
						right = false;
						// newDomain[firstUnassigned][j]=false;
						continue;
					} else {
						return result;
					}
				}
			}
			if (!right) {
				assignment[firstUnassigned] = 0;
				return null;
			} else
				result = assignment;
			return result;

		}

	}

	private boolean[][] forwardChecking(int firstUnassigned, int[] assignment, int number, boolean[][] domain) {
		boolean[][] temp = new boolean[81][9];
		for (int i = 0; i < 81; i++) {
			for (int k1 = 0; k1 < 9; k1++) {
				temp[i][k1] = domain[i][k1];
			}

		}
//		for (int i = 0; i < 81; i++) {
//			for (int k1 = 0; k1 < 9; k1++) {
//				temp[i][k1] = checkConstraints(firstUnassigned, assignment, k1 + 1);
//			}
//		}
		int column = firstUnassigned%9;
		int row = firstUnassigned/9;
		loopColumn: for (int k = column; k <= 72 + column; k = k + 9) {
			temp[k][number-1]=false;
		}
		loopRow: for (int j2 = (row) * 9; j2 < (row + 1) * 9; j2++) {
			temp[j2][number-1]=false;
		}
		int boxRow = row % 3;
		int boxClmn = column % 3;
		loopBox: for (int i = 0; i < 3; i++) {
			for (int b = (row + i - boxRow) * 9 + column - boxClmn; (b < (row + i - boxRow) * 9 + column - boxClmn + 3); b++) {
			temp[b][number-1]=false;
			}
		}

		return temp;
		
	}

	public boolean checkConstraints(int firstUnassigned, int[] assignment, int j) {
		int column = firstUnassigned % 9;
		int row = firstUnassigned / 9;
		boolean flag = true;

		loopRow: for (int j2 = (row) * 9; j2 < (row + 1) * 9 && flag; j2++) {
			if (assignment[j2] == j && j2 != firstUnassigned)
				flag = false;
		}
		loopColumn: for (int k = column; k < 72 + column && flag; k = k + 9) {
			if (assignment[k] == j && k != firstUnassigned)
				flag = false;
		}
		int boxRow = row % 3;
		int boxClmn = column % 3;
		loopBox: for (int i = 0; i < 3; i++) {
			for (int b = (row + i - boxRow) * 9 + column - boxClmn; (b < (row + i - boxRow) * 9 + column - boxClmn + 3)
					&& flag; b++) {
				if (assignment[b] == j && b != firstUnassigned)
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
		// SudokuIO.makeSudokuPuzzle();

		MRVBackTracking sbt = new MRVBackTracking();
		sbt.BacktrackingSearch(args);
		// System.out.println(sbt.checkConstraints(28, SudokuIO.vars, 6));
		// SudokuIO.vars[10]=2;
		// System.out.println( sbt.checkRow(SudokuIO.vars, 3, 3, 28));
		// System.out.println( sbt.checkBox(SudokuIO.vars,3, 1, 3, 28));
	}
}
