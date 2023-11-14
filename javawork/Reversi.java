//Reversi.java 2023年11月14 21D8101023H Ryusei Rikiishi

//ver4

import java.awt.*;
import java.awt.event.*;
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

    int getObverse(){
        return obverse;
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

    Stone[][] board = new Stone[8][8];
    public int num_grid_black;
    public int num_grid_white;
        
    Board(){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                board[i][j] = new Stone();
            }
        }
        board[3][3].setObverse(1);
        board[4][4].setObverse(1);
        board[3][4].setObverse(2);
        board[4][3].setObverse(2);
    }

    void paint(Graphics g,int unit_size){

        int rad=(int)(unit_size*0.8)/2;

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

        
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                Point p=new Point(unit_size*(i+1)+unit_size/2,unit_size*(j+1)+unit_size/2);
                board[i][j].paint(g,p,rad);
            }
        }

    }

    boolean isOnBoard(int x,int y){
        
        if((x>=0&&x<8)
        &&(y>=0&&y<8)){
            return true;
        } else {
            return false;
        }
    }

    void setStone(int x,int y,int s){
        if(s==1){
            board[x][y].setObverse(1);
            evaluateBoard();
        }else if(s==2){
            board[x][y].setObverse(2);
            evaluateBoard();
        }
    }

    void evaluateBoard(){
        num_grid_black=64-countStone(1)-countStone(2);
        num_grid_white=64-countStone(1)-countStone(2);
    }

    int countStone(int s){
        int count = 0;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(board[i][j].getObverse()==s){
                    count++;
                }
            }
        }
        return count;
    }
}

public class Reversi extends JPanel{
    
    public final static int UNIT_SIZE = 80;
    Board board = new Board();

    public Reversi(){
        setPreferredSize(new Dimension(UNIT_SIZE*10,UNIT_SIZE*10));
        addMouseListener(new MouseProc());
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

    void EndMessageDialog(){
        int black = board.countStone(1);
        int white = board.countStone(2);
        String str;
        if(black==white)  str = "[黒:"+black+",白:"+white+"]で引き分け";
        else if(black > white) str = "[黒:"+black+",白:"+white+"]で黒の勝ち";
        else  str = "[黒:"+black+",白:"+white+"]で白の勝ち";

        JOptionPane.showMessageDialog(this,str,"ゲーム終了",
                                        JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
        
    }

    class MouseProc extends MouseAdapter{
        public void mouseClicked(MouseEvent me){
            Point point = me.getPoint();
            int btn = me.getButton();
            int x=point.x/UNIT_SIZE-1;
            int y=point.y/UNIT_SIZE-1;
            if(board.isOnBoard(x,y)){
                if(btn == MouseEvent.BUTTON1){
                    board.setStone(x,y,1);
                }
                if(btn == MouseEvent.BUTTON3){
                    board.setStone(x,y,2);
                }
            }
            repaint();
            if(board.num_grid_black==0&&board.num_grid_white==0){
                EndMessageDialog();
            }
        }
    }
}
