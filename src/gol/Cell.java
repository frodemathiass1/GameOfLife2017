package gol;


import javafx.scene.paint.Color;

public class Cell {

    public static final int SIZE=20;

    private boolean alive;
    private int posX;
    private int posY;
    private Color cellColor;


    // Cell Constructor
    public Cell(int x,int y,boolean alive,int SIZE){
        this.setPosition(x,y);
        this.setAlive(alive);
    }

    // Setters
    public void setPosX(int x){this.posX=x;}
    public void setPosY(int y){this.posY=y;}
    public void setPosition(int x,int y){
        setPosX(x);
        setPosY(y);
    }
    public void setAlive(boolean alive){
        this.alive=alive;
        // Sets Cell color based on true or false/D.O.A
        setColor(alive);

    }
    public void setColor(boolean alive){
        if(alive){
            // Alive cell
            this.cellColor=Color.BLACK;
        }
        // Dead cell
        this.cellColor=Color.WHITE;
    }

    // Getters
    public boolean isAlive(){return this.alive;}
    public int getPosX(){return this.posX;}
    public int getPosY(){return this.posY;}
    public Color getCellColor(){return this.cellColor;}

}
