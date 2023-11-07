//Sudoku 力石竜成 23,10,26
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.input.*;
import javafx.scene.text.*;
import javafx.scene.paint.*;
import javafx.event.*;
import javafx.collections.*;
import javafx.geometry.*;
import java.util.*;
import java.util.Scanner;
import java.io.*;
public class Sudoku extends Application{
    private static int[][] board=new int[9][9];
    private TextField[][] tf=new TextField[9][9];
    private ArrayList<ComboBox<String>> cb_list
        =new ArrayList<ComboBox<String>>();
    private Button[][] bt=new Button[1][1];
    private Label lb;
    private String[] numbers = {"1","2","3","4","5","6","7","8","9"};
    private int check[][] = new int[9][9];
    public static void main(String[] args){
        String fname="input.txt";

        if(args.length>0) fname=args[0];
        try{
            Scanner sc=new Scanner(new File(fname));
            for(int i=0; i<9;i++){
                for(int j=0;j<9;j++){
                    board[i][j] = sc.nextInt();
                    if(board[i][j]<0||9<board[i][j]) board[i][j]=0;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        
        launch(args);
    }

    public void start(Stage stage)throws Exception{
        lb=new Label("各コンボボックスで数字を選択してください");
        
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j]==0){
                    tf[i][j]=new TextField();
                    tf[i][j].setOnAction(new InputEventHandler());
                }else{
                    tf[i][j]=new TextField(String.valueOf(board[i][j]));
                    tf[i][j].setEditable(false);
                    tf[i][j].setBackground(new Background(new BackgroundFill(Color.GRAY,null,null)));
                }
                tf[i][j].setMaxWidth(50);
                tf[i][j].setFont(Font.font("MonoSpace",20));
            }
        }
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                check[i][j]=board[i][j];
            }
        }
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                ComboBox<String> cb = new ComboBox<String>();
                ObservableList<String> ol = 
                    FXCollections.observableArrayList();
                for(int n=1;n<10;n++){
                    if(jdNum(i,j,n))ol.add(numbers[n-1]);
                }
                cb.setItems(ol);
                cb.setOnAction(new InputEventHandler());
                cb_list.add(cb);
            }
        }
       
        GridPane[][] sub_gp1=new GridPane[3][3];
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                sub_gp1[i][j]=new GridPane();
                sub_gp1[i][j].setHgap(5);
                sub_gp1[i][j].setVgap(5);
                
                for(int k=0;k<3;k++){
                    for(int l=0;l<3;l++){
                        if(board[i*3+k][j*3+l]==0){
                            sub_gp1[i][j].add(cb_list.get((i*3+k)*9+(j*3)+l),l,k);
                        }else{
                            sub_gp1[i][j].add(tf[i*3+k][j*3+l],l,k);
                        }
                    }
                }
                sub_gp1[i][j].setAlignment(Pos.CENTER);
            }
        }

        bt[0][0]=new Button("リセット");
        bt[0][0].setOnAction(new ResetEventHandler());

        GridPane gp1=new GridPane();
        gp1.setHgap(30);
        gp1.setVgap(30);
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                gp1.add(sub_gp1[i][j],j,i);
            }
        }
        gp1.setAlignment(Pos.CENTER);

        GridPane sub_gp2=new GridPane();
        sub_gp2.setHgap(15);
        sub_gp2.setVgap(15);
        sub_gp2.add(bt[0][0],0,0);
        sub_gp2.setAlignment(Pos.CENTER);

        BorderPane bp=new BorderPane();
        bp.setTop(gp1);
        bp.setCenter(lb);
        bp.setBottom(sub_gp2);

        Scene sc=new Scene(bp,700,500);
        stage.setScene(sc);
        stage.setTitle("数独");
        stage.show();
    }

    private void createCheck(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
                if(board[i][j]==0){
                    if(cb_list.get(i*9+j).getValue() != null){
                        check[i][j] = Integer.parseInt(cb_list.get(i*9+j).getValue().toString());
                    }else{
                        check[i][j] = 0;
                    }
                }else{
                    check[i][j] = Integer.parseInt(tf[i][j].getText().toString());
                }
            }
        }
    }

    private void printCheck(){
        for(int i=0;i<9;i++){
            for(int j=0;j<9;j++){
               System.out.print(check[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private boolean jdNum(int i,int j,int n){
        //行の判定
        for(int k=0;k<9;k++){
           if(check[i][k]==n){
            return false;
           }
        }
        //列の判定
        for(int l=0;l<9;l++){
            if(check[l][j]==n){
                return false;
           }
        }
        //ブロックごとの判定
        int col=i/3;
        int row=j/3;
        for(int k=0;k<3;k++){
            for(int l=0;l<3;l++){
                if(check[col*3+k][row*3+l]==n){
                    return false;
                }
            }
        }
        return true;
    }
    class InputEventHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            int jd=0;
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    if(board[i][j]==0){
                        if(cb_list.get(i*9+j).getValue() != null){
                            check[i][j] = Integer.parseInt(cb_list.get(i*9+j).getValue().toString());
                        }else{
                            check[i][j] = 0;
                            jd=1;
                        }
                    }else{
                        check[i][j] = Integer.parseInt(tf[i][j].getText().toString());
                    }
                }
            }
            if(jd!=1){
            //判定に使う変数の初期化
                
                int a[]=new int[10];
                for(int i=0;i<9;i++){
                    a[i]=0;
                }

                //行の判定
                for(int i=0;i<9;i++){
                    for(int j=0;j<9;j++){
                        a[check[i][j]]=1;
                    }
                    for(int k=1;k<10;k++){
                        if(a[k]!=1) jd=1;
                        else a[k]=0;
                    }
                }
                
                //列の判定
                for(int j=0;j<9;j++){
                    for(int i=0;i<9;i++){
                        a[check[i][j]]=1;
                    }
                    for(int k=1;k<10;k++){
                        if(a[k]!=1) jd=1;
                        else a[k]=0;
                    }
                }

                //ブロックごとの判定
                for(int i=0;i<3;i++){
                    for(int j=0;j<3;j++){
                        for(int k=0;k<3;k++){
                            for(int l=0;l<3;l++){
                                a[check[i*3+k][j*3+l]]=1;
                            }
                        }
                        for(int k=1;k<10;k++){
                            if(a[k]!=1) jd=1;
                            else a[k]=0;
                        }
                    }
                }
            }
            if(jd==0){
                lb.setText("ゲームクリア！おめでとうございます！！");
            }
            /*
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    ComboBox<String> cb = new ComboBox<String>();
                    ObservableList<String> ol = 
                        FXCollections.observableArrayList();
                    for(int n=1;n<10;n++){
                        if(jdNum(i,j,n))ol.add(numbers[n-1]);
                    }
                    cb_list.get(i*9+j).setItems(ol);
                }
            }
            */
        }
    }

    class ResetEventHandler implements EventHandler<ActionEvent>{
        public void handle(ActionEvent e){
            for(int i=0;i<9;i++){
                for(int j=0;j<9;j++){
                    cb_list.get(i*9+j).setValue(null);
                }
            }
            lb.setText("各コンボボックスで数字を選択してください");
        }
    }
}
