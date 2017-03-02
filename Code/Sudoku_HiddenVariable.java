import java.io.IOException;
import java.util.*;
import java.util.stream.IntStream;

public class Sudoku_HiddenVariable {
	public static void runHiddenVariable(boolean[][] varDomain){
		for (int i=0;i<9;i++){
			checkHiddenVariableInRow(varDomain,i);
		}
		for (int i=0;i<9;i++){
			checkHiddenVariableInColumn(varDomain,i);
		}
		for (int i=0;i<9;i++){
			checkHiddenVariableInBox(varDomain,i);
		}
	}

	public static void checkHiddenVariableInRow(boolean[][] varDomain, int rowNum){
		checkHiddenSingleInRow(varDomain,rowNum);
		checkHiddenPairInRow(varDomain,rowNum);
		checkHiddenTripletsInRow(varDomain,rowNum);
		checkHiddenQuadrupletsInRow(varDomain,rowNum);
	}

	public static void checkHiddenVariableInColumn(boolean[][] varDomain, int columnNum){
		checkHiddenSingleInColumn(varDomain,columnNum);
		checkHiddenPairInColumn(varDomain,columnNum);
		checkHiddenTripletsInColumn(varDomain,columnNum);
		checkHiddenQuadrupletsInColumn(varDomain,columnNum);
	}

	public static void checkHiddenVariableInBox(boolean[][] varDomain, int boxNum){
		checkHiddenSingleInBox(varDomain,boxNum);
		checkHiddenPairInBox(varDomain,boxNum);
		checkHiddenTripletsInBox(varDomain,boxNum);
		checkHiddenQuadrupletsInBox(varDomain,boxNum);
	}

	public static void checkHiddenSingleInRow(boolean[][] varDomain, int rowNum){
		for (int n=0; n<9; n++){
			int count=0,col=-1;
			for (int i=0; i<9; i++){
				if (varDomain[convertToLinear(new int[]{rowNum,i})][n]){
					count++;
					col=i;
				}
			}
			if (count==1){
				for (int i=0; i<9; i++){
					if (i!=n){
						varDomain[convertToLinear(new int[]{rowNum,col})][i]=false;
					}
				}
			}
		}
	}

	public static void checkHiddenPairInRow(boolean[][] varDomain, int rowNum){
		for (int m=0; m<8; m++){
			for (int n=m+1; n<9; n++){
				int count=0,col1=-1,col2=-1;
				for (int i=0; i<9; i++){
					if (varDomain[convertToLinear(new int[]{rowNum,i})][m] || varDomain[convertToLinear(new int[]{rowNum,i})][n]){
						count++;
						if (col1==-1) col1=i;
						else col2=i;
					}
				}
				if (count==2){
					for (int i=0; i<9; i++){
						if (i!=n && i!=m){
							varDomain[convertToLinear(new int[]{rowNum,col1})][i]=false;
							varDomain[convertToLinear(new int[]{rowNum,col2})][i]=false;
						}
					}
				}
			}
		}
	}

	public static void checkHiddenTripletsInRow(boolean[][] varDomain, int rowNum){
		for (int l=0;l<7;l++){
			for (int m=l+1; m<8; m++){
				for (int n=m+1; n<9; n++){
					int count=0,col1=-1,col2=-1,col3=-1;
					for (int i=0; i<9; i++){
						if (varDomain[convertToLinear(new int[]{rowNum,i})][l] || varDomain[convertToLinear(new int[]{rowNum,i})][m]
							 || varDomain[convertToLinear(new int[]{rowNum,i})][n]){
							count++;
							if (col1==-1) {col1=i;}
							else if (col2==-1) {col2=i;}
							else {col3=i;}
						}
					}
					if (count==3){
						for (int i=0; i<9; i++){
							if (i!=l && i!=n && i!=m){
								varDomain[convertToLinear(new int[]{rowNum,col1})][i]=false;
								varDomain[convertToLinear(new int[]{rowNum,col2})][i]=false;
								varDomain[convertToLinear(new int[]{rowNum,col3})][i]=false;
							}
						}
					}
				}
			}
		}
	}

	public static void checkHiddenQuadrupletsInRow(boolean[][] varDomain, int rowNum){
		for (int k=0;k<6;k++){
			for (int l=k+1;l<7;l++){
				for (int m=l+1; m<8; m++){
					for (int n=m+1; n<9; n++){
						int count=0,col1=-1,col2=-1,col3=-1,col4=-1;
						for (int i=0; i<9; i++){
							if (varDomain[convertToLinear(new int[]{rowNum,i})][k] || varDomain[convertToLinear(new int[]{rowNum,i})][l]
								|| varDomain[convertToLinear(new int[]{rowNum,i})][m] || varDomain[convertToLinear(new int[]{rowNum,i})][n]){
								count++;
								if (col1==-1) {col1=i;}
								else if (col2==-1) {col2=i;}
								else if (col3==-1) {col3=i;}
								else {col4=i;}
							}
						}
						if (count==4){
							for (int i=0; i<9; i++){
								if (i!=k && i!=l && i!=n && i!=m){
									varDomain[convertToLinear(new int[]{rowNum,col1})][i]=false;
									varDomain[convertToLinear(new int[]{rowNum,col2})][i]=false;
									varDomain[convertToLinear(new int[]{rowNum,col3})][i]=false;
									varDomain[convertToLinear(new int[]{rowNum,col4})][i]=false;
								}
							}
						}
					}
				}
			}
		}
	}

	public static void checkHiddenSingleInColumn(boolean[][] varDomain, int columnNum){
		for (int n=0; n<9; n++){
			int count=0,row=-1;
			for (int i=0; i<9; i++){
				if (varDomain[convertToLinear(new int[]{i,columnNum})][n]){
					count++;
					row=i;
				}
			}
			if (count==1){
				for (int i=0; i<9; i++){
					if (i!=n){
						varDomain[convertToLinear(new int[]{row,columnNum})][i]=false;
					}
				}
			}
		}
	}

	public static void checkHiddenPairInColumn(boolean[][] varDomain, int columnNum){
		for (int m=0; m<8; m++){
			for (int n=m+1; n<9; n++){
				int count=0,row1=-1,row2=-1;
				for (int i=0; i<9; i++){
					if (varDomain[convertToLinear(new int[]{i,columnNum})][m] || varDomain[convertToLinear(new int[]{i,columnNum})][n]){
						count++;
						if (row1==-1) row1=i;
						else row2=i;
					}
				}
				if (count==2){
					for (int i=0; i<9; i++){
						if (i!=n && i!=m){
							varDomain[convertToLinear(new int[]{row1,columnNum})][i]=false;
							varDomain[convertToLinear(new int[]{row2,columnNum})][i]=false;
						}
					}
				}
			}
		}
	}

	public static void checkHiddenTripletsInColumn(boolean[][] varDomain, int columnNum){
		for (int l=0;l<7;l++){
			for (int m=l+1; m<8; m++){
				for (int n=m+1; n<9; n++){
					int count=0,row1=-1,row2=-1,row3=-1;
					for (int i=0; i<9; i++){
						if (varDomain[convertToLinear(new int[]{i,columnNum})][l] || varDomain[convertToLinear(new int[]{i,columnNum})][m]
							 || varDomain[convertToLinear(new int[]{i,columnNum})][n]){
							count++;
							if (row1==-1) {row1=i;}
							else if (row2==-1) {row2=i;}
							else {row3=i;}
						}
					}
					if (count==3){
						for (int i=0; i<9; i++){
							if (i!=l && i!=n && i!=m){
								varDomain[convertToLinear(new int[]{row1,columnNum})][i]=false;
								varDomain[convertToLinear(new int[]{row2,columnNum})][i]=false;
								varDomain[convertToLinear(new int[]{row3,columnNum})][i]=false;
							}
						}
					}
				}
			}
		}
	}

	public static void checkHiddenQuadrupletsInColumn(boolean[][] varDomain, int columnNum){
		for (int k=0;k<6;k++){
			for (int l=k+1;l<7;l++){
				for (int m=l+1; m<8; m++){
					for (int n=m+1; n<9; n++){
						int count=0,row1=-1,row2=-1,row3=-1,row4=-1;
						for (int i=0; i<9; i++){
							if (varDomain[convertToLinear(new int[]{i,columnNum})][k] || varDomain[convertToLinear(new int[]{i,columnNum})][l]
								|| varDomain[convertToLinear(new int[]{i,columnNum})][m] || varDomain[convertToLinear(new int[]{i,columnNum})][n]){
								count++;
								if (row1==-1) {row1=i;}
								else if (row2==-1) {row2=i;}
								else if (row3==-1) {row3=i;}
								else {row4=i;}
							}
						}
						if (count==4){
							for (int i=0; i<9; i++){
								if (i!=k && i!=l && i!=n && i!=m){
									varDomain[convertToLinear(new int[]{row1,columnNum})][i]=false;
									varDomain[convertToLinear(new int[]{row2,columnNum})][i]=false;
									varDomain[convertToLinear(new int[]{row3,columnNum})][i]=false;
									varDomain[convertToLinear(new int[]{row4,columnNum})][i]=false;
								}
							}
						}
					}
				}
			}
		}
	}

	public static void checkHiddenSingleInBox(boolean[][] varDomain, int boxNum){
		for (int n=0; n<9; n++){
			int count=0,cell=-1;
			for (int i=(27*(boxNum/3)+3*(boxNum%3)) ; i<(27*(boxNum/3)+3*(boxNum%3))+21 ; i+=((i%3==2)?7:1)){
				if (varDomain[i][n]){
					count++;
					cell=i;
				}
			}
			if (count==1){
				for (int i=0; i<9; i++){
					if (i!=n){
						varDomain[cell][i]=false;
					}
				}
			}
		}
	}

	public static void checkHiddenPairInBox(boolean[][] varDomain, int boxNum){
		for (int m=0; m<8; m++){
			for (int n=m+1; n<9; n++){
				int count=0,cell1=-1,cell2=-1;
				for (int i=(27*(boxNum/3)+3*(boxNum%3)) ; i<(27*(boxNum/3)+3*(boxNum%3))+21 ; i+=((i%3==2)?7:1)){
					if (varDomain[i][m] || varDomain[i][n]){
						count++;
						if (cell1==-1) cell1=i;
						else cell2=i;
					}
				}
				if (count==2){
					for (int i=0; i<9; i++){
						if (i!=n && i!=m){
							varDomain[cell1][i]=false;
							varDomain[cell2][i]=false;
						}
					}
				}
			}
		}
	}

	public static void checkHiddenTripletsInBox(boolean[][] varDomain, int boxNum){
		for (int l=0;l<7;l++){
			for (int m=l+1; m<8; m++){
				for (int n=m+1; n<9; n++){
					int count=0,cell1=-1,cell2=-1,cell3=-1;
					for (int i=(27*(boxNum/3)+3*(boxNum%3)) ; i<(27*(boxNum/3)+3*(boxNum%3))+21 ; i+=((i%3==2)?7:1)){
						if (varDomain[i][l] || varDomain[i][m] || varDomain[i][n]){
							count++;
							if (cell1==-1) {cell1=i;}
							else if (cell2==-1) {cell2=i;}
							else {cell3=i;}
						}
					}
					if (count==3){
						for (int i=0; i<9; i++){
							if (i!=l && i!=n && i!=m){
								varDomain[cell1][i]=false;
								varDomain[cell2][i]=false;
								varDomain[cell3][i]=false;
							}
						}
					}
				}
			}
		}
	}

	public static void checkHiddenQuadrupletsInBox(boolean[][] varDomain, int boxNum){
		for (int k=0;k<6;k++){
			for (int l=k+1;l<7;l++){
				for (int m=l+1; m<8; m++){
					for (int n=m+1; n<9; n++){
						int count=0,cell1=-1,cell2=-1,cell3=-1,cell4=-1;
						for (int i=(27*(boxNum/3)+3*(boxNum%3)) ; i<(27*(boxNum/3)+3*(boxNum%3))+21 ; i+=((i%3==2)?7:1)){
							if (varDomain[i][k] || varDomain[i][l] || varDomain[i][m] || varDomain[i][n]){
								count++;
								if (cell1==-1) {cell1=i;}
								else if (cell2==-1) {cell2=i;}
								else if (cell3==-1) {cell3=i;}
								else {cell4=i;}
							}
						}
						if (count==4){
							for (int i=0; i<9; i++){
								if (i!=k && i!=l && i!=n && i!=m){
									varDomain[cell1][i]=false;
									varDomain[cell2][i]=false;
									varDomain[cell3][i]=false;
									varDomain[cell4][i]=false;
								}
							}
						}
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