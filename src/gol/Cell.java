package gol;

import javafx.scene.paint.Paint;

/**
 * Created by frodemathiassen on 17.02.2017.
 */
public class Cell {

    private int posX;
    private int posY;
    private int cellSize;
    private Paint cellColor;
    private boolean isAlive;
    private Cell neighbours[];
    private Cell generation [];


    public int getPosX(){
        return this.posX;
    }
    public int getPosY(){
        return this.posY;
    }
    public void setPosX(int x){
        this.posX=x;
    }
    public void setPosY(int y){
        this.posY=y;
    }
    public boolean getState(){
        return this.isAlive;
    }
    public void setState(boolean state){
        this.isAlive=state;
    }





}
