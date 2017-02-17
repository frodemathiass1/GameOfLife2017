package gol;


import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class Cell {

    public static final int SIZE=20;

    private int posX;
    private int posY;
    private Paint cellColor;
    private boolean isAlive;


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
    public void setPosition(int x,int y){
        setPosX(x);
        setPosY(y);
    }
    public boolean getState(){
        return this.isAlive;
    }

    public void setState(boolean state){
        this.isAlive=state;

    }






}
