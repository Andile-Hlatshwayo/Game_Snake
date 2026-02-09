import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class The_Game extends JPanel implements ActionListener, KeyListener{
    int  width;
    int height;
    int tileSize=25;
    //Snake
    Tile snakeHead;
    ArrayList<Tile> snakeBody;

    //Food
    Tile food;
    Random random;

    //game logic
    Timer loop;
    int speedHorizontal, speedVertical;
    boolean gameOver=false;

    The_Game(int height, int width){
        this.height=height;
        this.width=width;
        setPreferredSize(new Dimension(this.width, this.height));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        snakeHead=new Tile(5,5);
        snakeBody=new ArrayList<>();

        food=new Tile(10,10);
        random=new Random();
        foodPlace();

        speedHorizontal=0; //if -1, the snake moves to the left
        speedVertical=0; //if 1 it goes downwards

        loop=new Timer(100,this);
        loop.start();
    }
    public boolean collision(Tile tile, Tile tile1){
        return tile.x==tile1.x&&tile.y==tile1.y;
    }
    public void paintComponent(Graphics graphic){
        super.paintComponent(graphic);
        draw(graphic);
    }
    public void draw(Graphics graphic){
       ///Grid lines
       ///for(int a=0;a<width/tileSize;a++){
       ///    //x1,x2,y1,y2
       ///    graphic.drawLine(a*tileSize,0,a*tileSize,height);//vertical
       ///    graphic.drawLine(0,a*tileSize,width,a*tileSize);//horizontal
       ///}
        //Food
        graphic.setColor(Color.red);
        graphic.fill3DRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize,true);
        //graphic.fillRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize);

        //Snake head
        graphic.setColor(Color.green);
        graphic.fill3DRect(snakeHead.x*tileSize,snakeHead.y*tileSize,tileSize,tileSize,true); //fillRect draws a solid (filled-in) rectangle on the screen.
        //graphic.fillRect(food.x*tileSize,food.y*tileSize,tileSize,tileSize);
        //Snake body
        for(int z=0;z<snakeBody.size();z++){
            Tile part=snakeBody.get(z);
            graphic.fill3DRect(part.x*tileSize,part.y*tileSize,tileSize,tileSize,true); //fillRect draws a solid (filled-in) rectangle on the screen.
            //graphic.fillRect(part.x*tileSize,part.y*tileSize,tileSize,tileSize); //fillRect draws a solid (filled-in) rectangle on the screen.

        }

        //Score
        graphic.setFont(new Font("Comic Sans",Font.PLAIN,16));
        if(gameOver){
            graphic.setColor(Color.red);
            graphic.drawString("Game Over: "+String.valueOf(snakeBody.size()),tileSize-16,tileSize);
        }else   graphic.drawString("Score: "+String.valueOf(snakeBody.size()),tileSize-16,tileSize);

    }
    public void foodPlace(){
        food.x=random.nextInt(width/tileSize);
        food.y=random.nextInt(height/tileSize);
    }
    public void move(){
        //eat food
        if (collision(snakeHead, food)) {
            snakeBody.add(new Tile(food.x,food.y));
            foodPlace();
        }
        //Snake body
        for(int z=snakeBody.size()-1;z>=0;z--){
            Tile part=snakeBody.get(z);
            if(z==0){
                part.x= snakeHead.x;
                part.y=snakeHead.y;
            }else{
                Tile behind=snakeBody.get(z-1);
                part.x= behind.x;
                part.y=behind.y;
            }
        }
        //Snake head
        snakeHead.x+=speedHorizontal;
        snakeHead.y+=speedVertical;

        //game over conditions
        for( int b=0;b<snakeBody.size();b++){
            Tile part=snakeBody.get(b);
            //collision with snake head
            if(collision(snakeHead,part)) gameOver=true;

        }
        if(snakeHead.x*tileSize<0||snakeHead.x*tileSize>width||snakeHead.y*tileSize<0||snakeHead.y*tileSize>height)gameOver=true;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();
        if(gameOver)loop.stop();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_UP&& speedVertical!=1){
            speedHorizontal=0;
            speedVertical=-1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN&& speedVertical!=-1){
            speedHorizontal=0;
            speedVertical=1;
        }
        else if(e.getKeyCode()==KeyEvent.VK_LEFT&& speedHorizontal!=1){
            speedHorizontal=-1;
            speedVertical=0;
        }
        else if(e.getKeyCode()==KeyEvent.VK_RIGHT&& speedHorizontal!=-1){
            speedHorizontal=1;
            speedVertical=0;
        }
    }

    /// Not using these ones
    @Override
    public void keyTyped(KeyEvent e) {

    }



    @Override
    public void keyReleased(KeyEvent e) {

    }
}
