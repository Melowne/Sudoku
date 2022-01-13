package org.openjfx.javafx_archetype_simple;

import java.util.Arrays;

public class Solver {
	private boolean checkRules(int[][]mat,int i,int j,int e) {
		
		return (!Arrays.asList(row(mat,i)).contains(e)&&
				!Arrays.asList(col(mat,j)).contains(e)&&checkbox(mat,i,j,e))?true:false; 

	}
	private boolean checkbox(int mat[][],int i,int j,int e) {
		for (int j2 = i-i%3; j2 < i-i%3+3; j2++) {
			for (int k = j-j%3; k < j-j%3+3; k++) {
				if(mat[j2][k]==e)return false;
			}
		}
		
		return true;
	}
	private Integer[] col(int mat[][],int col) {
		Integer[] arr=new Integer[mat.length];
		for (int i = 0; i < arr.length; i++) {
				arr[i]=mat[i][col];
			}
		return arr;
	}
	private Integer[] row(int mat[][],int row) {
		Integer[] arr=new Integer[mat.length];
		for (int i = 0; i < arr.length; i++) {
				arr[i]=mat[row][i];
			}
		return arr;
	}
	private int[] givcoord(int mat[][]) {
		var arr=new int[2];
		for (int i = 0; i < 81; i++) {
			if(mat[i/9][i%9]==0) {arr[0]=i/9;arr[1]=i%9;return arr;}
		}
		arr[0]=-1;
		return arr;
	}
	public boolean solve(int mat[][]) {
		if(givcoord(mat)[0]==-1)return true;

		int i=givcoord(mat)[0],j=givcoord(mat)[1];

	for (int j1 = 1; j1 <= mat.length; j1++) {
		if(checkRules(mat, i, j, j1)) {
			mat[i][j]=j1;
			if(solve(mat))return true;
			else mat[i][j]=0;
		}
		}return false;
	}
}
