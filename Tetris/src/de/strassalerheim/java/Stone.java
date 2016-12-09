package de.strassalerheim.java;

import java.util.Random;

public class Stone {

	public int[][] stoneCoords; // X - Y
	int farbe[] = {0,0,0};
	public Stone(){
	    createStone();
	}

	public void createStone() {
		Random rnd = new Random();
		int random = rnd.nextInt(6);
		switch (random) {
		case 0:
			stoneCoords = new int[][] { { 3, 0 }, { 3, 1 }, { 4, 1 }, { 5, 1 } };
			break;
		case 1:
			stoneCoords = new int[][] { { 5, 0 }, { 3, 1 }, { 4, 1 }, { 5, 1 } };
			break;
		case 2:
			stoneCoords = new int[][] { { 4, 0 }, { 5, 0 }, { 4, 1 }, { 5, 1 } };
			break;
		case 3:
			stoneCoords = new int[][] { { 3, 0 }, { 4, 0 }, { 5, 0 }, { 6, 0 } };
			break;
		case 4:
			stoneCoords = new int[][] { { 4, 0 }, { 3, 1 }, { 4, 1 }, { 5, 1 } };
			break;
		case 5:
			stoneCoords = new int[][] { { 3, 0 }, { 3, 1 }, { 4, 1 }, { 4, 2 } };
			break;
		case 6:
			stoneCoords = new int[][] { { 4, 0 }, { 3, 1 }, { 4, 1 }, { 3, 2 } };
			break;

		}
		
		
	}
	
	public int[] getFarbe(){
	    Random rnd = new Random();
	    int[] farbe = {rnd.nextInt(255),rnd.nextInt(255),rnd.nextInt(255)};
	    return farbe;
	}

}
