package de.strassalerheim.java;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Canvas;
import java.awt.event.KeyAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import com.sun.glass.events.KeyEvent;
import com.sun.xml.internal.ws.api.pipe.Fiber.Listener;

public class TetrisGui {

    protected Shell shell;

    int sizeX = 10;
    int sizeY = 20;
    int sizeField = 15;

    Canvas[][] screen = new Canvas[sizeY][sizeX];
    int[][][] gamefield = new int[sizeY][sizeX][4];//[Y] [X] [Value 0/1|r|g|b]
    int[][][] gamefieldMove = new int[sizeY][sizeX][4];

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
        shell.setText("SWT Application");

        Canvas canvas = new Canvas(shell, SWT.BORDER);
        canvas.setBounds(245, 101, 64, 64);
        canvas.setBackground(SWTResourceManager.getColor(255,0,0));

        createScreen();

        gameStart();
    }

    public void gameStart() {
        Stone st1 = new Stone();
        st1.createStone();
        
        drawStone(st1);
        drawScreen();
        
        moveStone(st1, 2);
        
        playStone(st1);
        
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

    public void playStone(Stone stone) {

            shell.addKeyListener(new KeyListener() {

                @Override
                public void keyPressed(org.eclipse.swt.events.KeyEvent p) {
                    if (p.keyCode == SWT.ARROW_LEFT) {
                        moveStone(stone, 1);
                    }
                    else if (p.keyCode == SWT.ARROW_DOWN) {
                        moveStone(stone, 2);
                    }
                    else if (p.keyCode == SWT.ARROW_RIGHT) {
                        moveStone(stone, 3);
                    }
                }

                @Override
                public void keyReleased(org.eclipse.swt.events.KeyEvent r) {
                }
            });
    }


    public void moveStone(Stone stone, int direction){
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
        
        //Stein bewegen               X                  Y
        stone.stone = new int[][] {{stone.stone[0][0]+a, stone.stone[0][1]+b},
                                {stone.stone[1][0]+a, stone.stone[1][1]+b},
                                {stone.stone[2][0]+a, stone.stone[2][1]+b},
                                {stone.stone[3][0]+a, stone.stone[3][1]+b} };
        
        clearField();
        drawStone(stone);
        drawScreen();
    }
    
    private void drawStone(Stone st){
        //Stein setzen
        for(int i = 0; i < st.stone.length; i++){// gamefield [Y] [X] [Value]
            gamefieldMove[st.stone[i][1]][st.stone[i][0]][0] = 1;  //True = Stone set
            gamefieldMove[st.stone[i][1]][st.stone[i][0]][1] = 255;  //Red
            gamefieldMove[st.stone[i][1]][st.stone[i][0]][2] = 0;//Green
            gamefieldMove[st.stone[i][1]][st.stone[i][0]][3] = 255;//Blue
        }
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
