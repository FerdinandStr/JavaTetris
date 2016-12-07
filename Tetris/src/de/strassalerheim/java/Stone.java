package de.strassalerheim.java;

import java.util.Random;

public class Stone {

	public int[][] stone; // X - Y

	public static void main(String[] args) {

	}

	public void createStone() {
		Random rnd = new Random();
		int random = rnd.nextInt(6);
		switch (random) {
		case 0:
			stone = new int[][] { { 3, 0 }, { 3, 1 }, { 4, 1 }, { 5, 1 } };
			break;
		case 1:
			stone = new int[][] { { 5, 0 }, { 3, 1 }, { 4, 1 }, { 5, 1 } };
			break;
		case 2:
			stone = new int[][] { { 4, 0 }, { 5, 0 }, { 4, 1 }, { 5, 1 } };
			break;
		case 3:
			stone = new int[][] { { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 } };
			break;
		case 4:
			stone = new int[][] { { 4, 0 }, { 3, 1 }, { 4, 1 }, { 5, 1 } };
			break;
		case 5:
			stone = new int[][] { { 3, 0 }, { 3, 1 }, { 4, 1 }, { 4, 2 } };
			break;
		case 6:
			stone = new int[][] { { 4, 0 }, { 3, 1 }, { 4, 1 }, { 3, 2 } };
			break;

		}
		
		
	}

}
