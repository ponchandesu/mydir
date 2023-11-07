//Reversi.java 2023年11月07 Ryusei Rikiishi

//ver2

import java.awt.*;
import javax.swing.*;

class Stone{
    public final static int black = 1;
    public final static int white = 2;
    private int obverse;
    Stone(){
        obverse=0;
    }

    void setObverse(int color){
        if(color == black || color == white){
            if(color == black || color == white) obverse = color;
            else System.out.println("黒か白でなければいけません");
        }
    }
    void paint(Graphics g,Point p,int rad){
        if(obverse == black){
            g.setColor(Color.black);
            g.fillOval(p.x-rad,p.y-rad,rad*2,rad*2);
        }else if(obverse == white){
            g.setColor(Color.white);
            g.fillOval(p.x-rad,p.y-rad,rad*2,rad*2);
        }
    }
}

class Board{
    Board(){}

    void paint(Graphics g,int unit_size){
        g.setColor(Color.black);
        g.fillRect(0,0,unit_size*10,unit_size*10);

        g.setColor(new Color(0,85,0));
        g.fillRect(unit_size,unit_size,unit_size*8,unit_size*8);

        g.setColor(Color.black);
        for(int i=1;i<10;i++){
            g.drawLine(unit_size,i*unit_size,unit_size*9,i*unit_size);
        }

        g.setColor(Color.black);
        for(int i=1;i<10;i++){
            g.drawLine(i*unit_size,unit_size,i*unit_size,unit_size*9);
        }

        for(int i=0;i<2;i++){
            for(int j=0;j<2;j++){
                g.fillRect((i*4*unit_size)+(unit_size*3)-4,(j*4*unit_size)+(unit_size*3)-4,8,8);
            }
        }

        Stone[][] stone = new Stone[8][8];
        int rad=(int)(unit_size*0.8)/2;
        for(int i=3;i<5;i++){
            for(int j=3;j<5;j++){
                if((i+j)%2==0){
                    Point p=new Point(unit_size*(i+1)+unit_size/2,unit_size*(j+1)+unit_size/2);
                    stone[i][j]=new Stone();
                    stone[i][j].setObverse(1);
                    stone[i][j].paint(g,p,rad);
                }else{
                    Point p=new Point(unit_size*(i+1)+unit_size/2,unit_size*(j+1)+unit_size/2);
                    stone[i][j]=new Stone();
                    stone[i][j].setObverse(2);
                    stone[i][j].paint(g,p,rad);
                }
            }
        }
    }
}

public class Reversi extends JPanel{
    public final static int UNIT_SIZE = 80;
    Board board = new Board();

    public Reversi(){
        setPreferredSize(new Dimension(UNIT_SIZE*10,UNIT_SIZE*10));
    }

    public void paintComponent(Graphics g){
        board.paint(g,UNIT_SIZE);
    }

    public static void main(String[] args){
        JFrame f = new JFrame();
        f.getContentPane().setLayout(new FlowLayout());
        f.getContentPane().add(new Reversi());
        f.pack();
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}
