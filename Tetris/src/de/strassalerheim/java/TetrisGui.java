package de.strassalerheim.java;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Canvas;
import java.util.Timer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.wb.swt.SWTResourceManager;

public class TetrisGui {

    protected Shell shell;

    int sizeX = 10;
    int sizeY = 20;
    int sizeField = 15;
    int[] farbe = {0,0,0};

    Canvas[][] screen = new Canvas[sizeY][sizeX];
    int[][][] gamefield = new int[sizeY][sizeX][4];//[Y] [X] [Value 0/1|r|g|b]
    int[][][] gamefieldMove = new int[sizeY][sizeX][4];

    Timer timer = new Timer();
    
    Stone activeStone;
    /**
     * Launch the application.
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            TetrisGui window = new TetrisGui();
            window.open();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Open the window.
     */
    public void open() {
        Display display = Display.getDefault();
        createContents();
        shell.open();
        shell.layout();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
    }

    /**
     * Create contents of the window.
     */
    protected void createContents() {
        shell = new Shell();
        shell.setSize(341, 396);
        shell.setText("Tetris Java");

        Canvas canvas = new Canvas(shell, SWT.BORDER);
        canvas.setBounds(245, 101, 64, 64);
        canvas.setBackground(SWTResourceManager.getColor(255,0,0));

        shell.addKeyListener(new KeyListener() {
            boolean pressed = false;
            @Override
            public void keyPressed(org.eclipse.swt.events.KeyEvent p) {
                if (p.keyCode == SWT.ARROW_LEFT) {
                    if(pressed == false)
                        moveStone(activeStone, 1);
                }
                else if (p.keyCode == SWT.ARROW_DOWN) {
                    if(pressed == false)
                        moveStone(activeStone, 2);
                }
                else if (p.keyCode == SWT.ARROW_RIGHT) {
                    if(pressed == false)
                        moveStone(activeStone, 3);
                }
                pressed = true;
            }

            @Override
            public void keyReleased(org.eclipse.swt.events.KeyEvent r) {
                pressed = false;
            }
        });
        
        createScreen();

        gameStart();
    }

    public void gameStart() {
        activeStone = new Stone();
        farbe = activeStone.getFarbe();
        
        drawStone(gamefieldMove, activeStone);
        drawScreen();
        
        moveStone(activeStone, 2);
        
        /*timer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                playStone(stone);
                
            }
        }, arg1);*/
    }

    

    public void createScreen() {
        int positionY = sizeField;

        for (int i = 0; i < sizeY; i++) {
            int positionX = sizeField;

            for (int j = 0; j < sizeX; j++) {
                Canvas field = new Canvas(shell, SWT.BORDER);
                field.setBounds(positionX, positionY, sizeField, sizeField);
                field.setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GRAY));

                screen[i][j] = field;

                positionX = positionX + sizeField;
            }
            positionY = positionY + sizeField;
        }
    }

    public boolean moveStone(Stone stone, int direction){
        int a = 0;
        int b = 0;
        switch (direction) {
            case 1://nach links
                a = -1;
                break;
            case 2://nach unten
                b = 1;
                break;
            case 3://nach rechts
                a = 1;
                break;
            default://unten automatisch
                b = 1;
                break;
        }
        
        int x1 = stone.stoneCoords[0][0]+a, y1 = stone.stoneCoords[0][1]+b;
        int x2 = stone.stoneCoords[1][0]+a, y2 = stone.stoneCoords[1][1]+b;
        int x3 = stone.stoneCoords[2][0]+a, y3 = stone.stoneCoords[2][1]+b;
        int x4 = stone.stoneCoords[3][0]+a, y4 = stone.stoneCoords[3][1]+b;
        
        //keine bewegung wenn außerhalb spielfeld
        if(x1 < 0 || x2 < 0 || x3 < 0 || x4 < 0 || x1 == sizeX || x2 == sizeX || x3 == sizeX || x4 == sizeX){
            return false;
        }
        else if(y1 == sizeY || y2 == sizeY || y3 == sizeY || y4 == sizeY){
            drawStone(gamefield, stone);
            clearField();
            activeStone = new Stone();
            farbe = activeStone.getFarbe();
        }
        else{
          //Stein bewegen
            stone.stoneCoords = new int[][] {{x1, y1},
                                        {x2, y2},
                                        {x3, y3},
                                        {x4, y4} };
            drawStone(gamefieldMove, stone);
        }

//        if( returnCode == 0){
//            drawScreen();
//        }
//        else if (returnCode == 2){
//            drawStone(gamefield, stone);
//            activeStone = new Stone();
//            return true;
//        }
          return false;
    }
    
    private void drawStone(int[][][] gameField, Stone st){
        clearField();
        
        //Stein setzen
        try{
            for(int i = 0; i < st.stoneCoords.length; i++){// gamefield [Y] [X] [Value]
                
                gameField[st.stoneCoords[i][1]][st.stoneCoords[i][0]][0] = 1;  //True = Stone set
                gameField[st.stoneCoords[i][1]][st.stoneCoords[i][0]][1] = farbe[0];  //Red
                gameField[st.stoneCoords[i][1]][st.stoneCoords[i][0]][2] = farbe[1];//Green
                gameField[st.stoneCoords[i][1]][st.stoneCoords[i][0]][3] = farbe[2];//Blue
            }
        }
        catch(ArrayIndexOutOfBoundsException ae){
            int error = Integer.parseInt(ae.getMessage());
            
            if (error == -1 || error == sizeX){//Seitenwand berührt
                //return 1;
            }
            else{//Boden berührt
                //return 2;
            }
            
        }
        //return 0;
        
        drawScreen();
    }
    
    
    private void clearField(){
        //MoveArray zurücksetzten auf Hintergrund
        for(int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                gamefieldMove[y][x][0] = gamefield[y][x][0];
                gamefieldMove[y][x][0] = gamefield[y][x][1];
                gamefieldMove[y][x][0] = gamefield[y][x][2];
                gamefieldMove[y][x][0] = gamefield[y][x][3];
            }
        }
    }
    
    public void drawScreen(){    
        
        for(int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                screen[y][x].setBackground(SWTResourceManager.getColor(gamefield[y][x][1],gamefield[y][x][2],gamefield[y][x][3]));
            }
        }
        for(int y = 0; y < sizeY; y++){
            for(int x = 0; x < sizeX; x++){
                if(gamefieldMove[y][x][0] == 1){//wenn im array 1 steht dann liegt an dieser stelle ein Block; wtf
                    screen[y][x].setBackground(SWTResourceManager.getColor(gamefieldMove[y][x][1],gamefieldMove[y][x][2],gamefieldMove[y][x][3]));
                }
            }
        }
    }
}
