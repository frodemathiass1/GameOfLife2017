package gol;


import javafx.event.ActionEvent;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle{

    public  static int SIZE=25;
    private boolean alive;
    private int posX, posY;
    private Color cellColor;
    private Rectangle cellShape;


    // Cell Constructor
    public Cell(){

    }
    public Cell(int x,int y,boolean alive){
        this.setPosition(x,y);
        this.setAlive(alive);
        this.setCellShape();
    }

    // Setters
    public void setPosX(int x){this.posX=x;}
    public void setPosY(int y){this.posY=y;}
    public void setPosition(int x,int y){
        setPosX(x);
        setPosY(y);
    }

    /*public void setCellSize(int s){
        this.SIZE=s;
    }*/

    public void setAlive(boolean alive){
        this.alive=alive;
        // Sets Cell color based on true or false/D.O.A
        setColor(alive);

    }

    public void setColor(boolean alive){
        setCellShape();
        if(alive){
            // Alive cell
            this.cellColor=Color.BLACK;

        }
        // Dead cell
        this.cellColor=Color.WHITE;
    }

    public void setCellShape(){
        this.cellShape=new Rectangle(SIZE,SIZE);

    }

    public void setSize(int size){
        this.SIZE=size;
    }

    // Getters
    public boolean isAlive(){return this.alive;}
    public int getPosX(){return this.posX;}
    public int getPosY(){return this.posY;}
    public Color getCellColor(){return this.cellColor;}
    public int getSize(){return SIZE;}
    public Rectangle getCellShape(){return this.cellShape;}

}
