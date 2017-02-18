package gol;


import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle{

    public static final int SIZE=20;

    private boolean alive;
    private int posX;
    private int posY;
    private Color cellColor;
    private Rectangle cellShape;


    // Cell Constructor
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

    // Getters
    public boolean isAlive(){return this.alive;}
    public int getPosX(){return this.posX;}
    public int getPosY(){return this.posY;}
    public Color getCellColor(){return this.cellColor;}
    public int getSize(){return SIZE;}
    public Rectangle getCellShape(){return this.cellShape;}

}
