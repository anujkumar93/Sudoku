package SudokuIO;

public class SimpleBackTracking {
	// int firstUnassigned;
	int[] solution = new int[81];

	public void BacktrackingSearch() {
		SudokuIO.makeSudokuPuzzle();
		// createVarDomains(SudokuIO.vars);
		// System.out.println(SudokuIO.vars[0]);
		solution = recursiveBckTracking(SudokuIO.vars);
		for (int i = 0; i < 81; i++) {
			System.out.print(solution[i] + " * ");
			if (i % 9 == 8)
				System.out.println();
		}

	}

	// private void createVarDomains(int[] vars) {
	// // TODO Auto-generated method stub
	// int column = 0;
	// int row = 0;
	// for (int i = 0; i < vars.length; i++) {
	// column = i % 9;
	// row = i / 9;
	// }
	// }

	public int[] recursiveBckTracking(int[] assignment) {
		int firstUnassigned = -1;
		int[] result = null;
		if (isASolution(assignment))
			return assignment;
		else {
			loop1: for (int i = 0; i < assignment.length; i++) {
				if (assignment[i] == 0) {
					firstUnassigned = i;
					break;
				}
			}
			int j = 0;
			boolean right = false;
			for (j = 1; j < 10; j++) {
				assignment[firstUnassigned] = j;
				if (checkConstraints(firstUnassigned, assignment, j)) {
					right = true;
					System.out.println(firstUnassigned + " -> " + j);
					for (int i = 0; i < 81; i++) {
						System.out.print(assignment[i] + " | ");
						if (i % 9 == 8)
							System.out.println();
					}
					result = recursiveBckTracking(assignment);
					if (result == null) {
						assignment[firstUnassigned] = 0;
						right = false;
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

	private boolean checkColumn(int[] assignment, int column, int j, int firstUnassigned) {
		boolean flag = true;
		loopColumn: for (int k = column; k < 72 + column && flag; k = k + 9) {
			System.out.println(k + ": " + assignment[k]);
			if (assignment[k] == j && k != firstUnassigned)
				flag = false;
		}
		return flag;
	}

	private boolean checkRow(int[] assignment, int row, int j, int firstUnassigned) {
		boolean flag = true;
		loopRow: for (int j2 = (row) * 9; j2 < (row + 1) * 9 && flag; j2++) {
			if (assignment[j2] == j && j2 != firstUnassigned)
				flag = false;
		}
		return flag;
	}

	public boolean checkBox(int[] assignment, int row, int column, int j, int firstUnassigned) {
		int boxRow = row % 3;
		System.out.println(boxRow);
		int boxClmn = column % 3;
		System.out.println(boxClmn);
		boolean flag = true;
		loopBox: for (int i = 0; i < 3; i++) {
			for (int b = (row + i - boxRow) * 9 + column - boxClmn; (b < (row + i - boxRow) * 9 + column - boxClmn + 3)
					&& flag; b++) {
				System.out.println(b + ": " + assignment[b]);
				if (assignment[b] == j && b != firstUnassigned)
					flag = false;
			}
		}
		return flag;
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
		SimpleBackTracking sbt = new SimpleBackTracking();
		sbt.BacktrackingSearch();
		// SudokuIO.makeSudokuPuzzle();
		// System.out.println( sbt.checkRow(SudokuIO.vars, 0, 4, 4));
	}
}
