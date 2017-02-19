package gol;


import javafx.event.ActionEvent;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle{

    public  static final int CELLSIZE=25;
    private boolean alive;
    private int posX, posY;


    // Cell Constructor
    public Cell(){

    }
    public Cell(boolean alive){
        super(CELLSIZE,CELLSIZE);
        setAlive(alive);
    }


    public void setAlive(boolean alive){
        this.alive=alive;
        setFill(alive ? Color.BLACK : Color.WHITE);
    }


    // Setters
    public void setPosX(int x){this.posX=x;}
    public void setPosY(int y){this.posY=y;}
    public void setPosition(int x,int y){
        setPosX(x);
        setPosY(y);
    }

    // Getters
    public boolean isAlive(){return this.alive;}
    public int getPosX(){return this.posX;}
    public int getPosY(){return this.posY;}
    public int getSize(){return CELLSIZE;}

}
