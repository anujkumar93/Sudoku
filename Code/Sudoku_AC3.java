import java.io.IOException;
import java.util.*;

public class Sudoku_AC3 {
	public static boolean runAC3(boolean[][] varDomain){
		ArrayList<HashMap<Integer,Integer>> constraintList= buildConstraints(-1,-1);
		int x_i=-1, x_j=-1;
		while (!constraintList.isEmpty()){
			HashMap<Integer,Integer> currentConstraint = constraintList.get(0);
			constraintList.remove(0);
			for(int key: currentConstraint.keySet()) {
				x_i=key;
				x_j=currentConstraint.get(key);
			}
			if (revise(varDomain,currentConstraint)){
				boolean domainExists=false;
				for (int i=0; i<9; i++){
					if (varDomain[x_i][i]) domainExists=true;
				}
				if (!domainExists) return false;
				ArrayList<HashMap<Integer,Integer>> neighborList= buildConstraints(x_i, x_j);
				constraintList.addAll(neighborList);
			}
		}
		return true;
	}

	public static boolean revise(boolean[][] varDomain, HashMap<Integer,Integer> currentConstraint){
		boolean revised=false;
		int x_i=-1, x_j=-1;

		for(int key: currentConstraint.keySet()) {
			x_i=key;
			x_j=currentConstraint.get(key);
		}
		boolean isPossible=false;
		for (int i=0; i<9; i++){
			if (varDomain[x_i][i]){
				for (int j=0; j<9; j++){
					if (varDomain[x_j][j] && j!=i){
						isPossible=true;
					}
				}
				if (!isPossible){
					varDomain[x_i][i]=false;
					revised=true;
				}
				isPossible=false;
			}
		}
		return revised;
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

	public static ArrayList<HashMap<Integer,Integer>> buildConstraints(int x_i, int x_j){
		ArrayList constraintList = new ArrayList();
		HashMap intMap = new HashMap();
		for (int i=0; i<81; i++){
			if (x_i!=-1 && i!=x_i) continue;
			int coordinates2D[] = convertTo2D(i);
			int row = coordinates2D[0];
			int column = coordinates2D[1];
			for (int j=0; j<9; j++){ //adding row and column constraints
				int firstIntToMap = convertToLinear(new int[]{j,column});
				int secondIntToMap = convertToLinear(new int[]{row,j});
				if (firstIntToMap!=i && secondIntToMap!=i){
					if (firstIntToMap!=x_j){
						intMap.put(firstIntToMap, i);
						constraintList.add(intMap);
						intMap = new HashMap();
					}
					if (secondIntToMap!=x_j){
						intMap.put(secondIntToMap, i);
						constraintList.add(intMap);
						intMap = new HashMap();
					}
					if (x_i==-1){
						intMap.put(i, firstIntToMap);
						constraintList.add(intMap);
						intMap = new HashMap();
						intMap.put(i, secondIntToMap);
						constraintList.add(intMap);
						intMap = new HashMap();
					}
				} else if (firstIntToMap!=i) {
					if (firstIntToMap!=x_j){
						intMap.put(firstIntToMap, i);
						constraintList.add(intMap);
						intMap = new HashMap();
					}
					if (x_i==-1){
						intMap.put(i, firstIntToMap);
						constraintList.add(intMap);
						intMap = new HashMap();
					}
				} else if (secondIntToMap!=i) {
					if (secondIntToMap!=x_j){
						intMap.put(secondIntToMap, i);
						constraintList.add(intMap);
						intMap = new HashMap();
					}
					if (x_i==-1){
						intMap.put(i, secondIntToMap);
						constraintList.add(intMap);
						intMap = new HashMap();
					}
				}

			}
			//adding box constraints
			int firstIntToMap=-1, secondIntToMap=-1, thirdIntToMap=-1, fourthIntToMap=-1;
			if (row%3==0){
				if (column%3==0){
					firstIntToMap=convertToLinear(new int[]{row+1,column+1});
					secondIntToMap=convertToLinear(new int[]{row+1,column+2});
					thirdIntToMap=convertToLinear(new int[]{row+2,column+1});
					fourthIntToMap=convertToLinear(new int[]{row+2,column+2});
				} else if (column%3==1){
					firstIntToMap=convertToLinear(new int[]{row+1,column-1});
					secondIntToMap=convertToLinear(new int[]{row+1,column+1});
					thirdIntToMap=convertToLinear(new int[]{row+2,column-1});
					fourthIntToMap=convertToLinear(new int[]{row+2,column+1});
				} else if (column%3==2){
					firstIntToMap=convertToLinear(new int[]{row+1,column-1});
					secondIntToMap=convertToLinear(new int[]{row+1,column-2});
					thirdIntToMap=convertToLinear(new int[]{row+2,column-1});
					fourthIntToMap=convertToLinear(new int[]{row+2,column-2});
				}
			} else if (row%3==1){
				if (column%3==0){
					firstIntToMap=convertToLinear(new int[]{row-1,column+1});
					secondIntToMap=convertToLinear(new int[]{row-1,column+2});
					thirdIntToMap=convertToLinear(new int[]{row+1,column+1});
					fourthIntToMap=convertToLinear(new int[]{row+1,column+2});
				} else if (column%3==1){
					firstIntToMap=convertToLinear(new int[]{row-1,column-1});
					secondIntToMap=convertToLinear(new int[]{row-1,column+1});
					thirdIntToMap=convertToLinear(new int[]{row+1,column-1});
					fourthIntToMap=convertToLinear(new int[]{row+1,column+1});
				} else if (column%3==2){
					firstIntToMap=convertToLinear(new int[]{row-1,column-1});
					secondIntToMap=convertToLinear(new int[]{row-1,column-2});
					thirdIntToMap=convertToLinear(new int[]{row+1,column-1});
					fourthIntToMap=convertToLinear(new int[]{row+1,column-2});
				}
			} else if (row%3==2){
				if (column%3==0){
					firstIntToMap=convertToLinear(new int[]{row-1,column+1});
					secondIntToMap=convertToLinear(new int[]{row-1,column+2});
					thirdIntToMap=convertToLinear(new int[]{row-2,column+1});
					fourthIntToMap=convertToLinear(new int[]{row-2,column+2});
				} else if (column%3==1){
					firstIntToMap=convertToLinear(new int[]{row-1,column-1});
					secondIntToMap=convertToLinear(new int[]{row-1,column+1});
					thirdIntToMap=convertToLinear(new int[]{row-2,column-1});
					fourthIntToMap=convertToLinear(new int[]{row-2,column+1});
				} else if (column%3==2){
					firstIntToMap=convertToLinear(new int[]{row-1,column-1});
					secondIntToMap=convertToLinear(new int[]{row-1,column-2});
					thirdIntToMap=convertToLinear(new int[]{row-2,column-1});
					fourthIntToMap=convertToLinear(new int[]{row-2,column-2});
				}
			}
			if (firstIntToMap!=x_j){
				intMap.put(firstIntToMap, i);
				constraintList.add(intMap);
				intMap = new HashMap();
			}
			if (secondIntToMap!=x_j){
				intMap.put(secondIntToMap, i);
				constraintList.add(intMap);
				intMap = new HashMap();
			}
			if (thirdIntToMap!=x_j){
				intMap.put(thirdIntToMap, i);
				constraintList.add(intMap);
				intMap = new HashMap();
			}
			if (fourthIntToMap!=x_j){
				intMap.put(fourthIntToMap, i);
				constraintList.add(intMap);
				intMap = new HashMap();
			}
			if (x_i==-1){
				intMap.put(i, firstIntToMap);
				constraintList.add(intMap);
				intMap = new HashMap();
				intMap.put(i, secondIntToMap);
				constraintList.add(intMap);
				intMap = new HashMap();
				intMap.put(i, thirdIntToMap);
				constraintList.add(intMap);
				intMap = new HashMap();
				intMap.put(i, fourthIntToMap);
				constraintList.add(intMap);
				intMap = new HashMap();
			}
		}
		return constraintList;
	}
}