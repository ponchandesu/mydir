//Calculator06_2.java 2023年10月31 Ryusei Rikiishi
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.geometry.*;
import javafx.event.*;
import java.text.*;

public class Calculator extends Application
{
    private TextField tf;
    private Button[][] bt = new Button[4][5];

    public static void main(String[] args)
    {
        launch(args);
    }

    public void start(Stage stage)throws Exception
    {
        tf = new TextField();
        tf.setEditable(false);
        tf.setMaxWidth(380);
        tf.setFont(Font.font("MonoSpace",40));
        tf.setAlignment(Pos.CENTER_RIGHT);

        String[][] bt_str = {{"CE","C","BS","/"},{"7","8","9","*"},{"4","5","6","-"},
                            {"1","2","3","+"},{"±","0",".","="}};
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                bt[i][j] = new Button(bt_str[j][i]);
                bt[i][j].setPrefWidth(95);
                bt[i][j].setPrefHeight(95);
                bt[i][j].setFont(Font.font("MonoSpace",30));
                bt[i][j].setOnAction(new ButtonEventHandler());
            }
        }

        GridPane gp = new GridPane();
        gp.setHgap(2);
        gp.setVgap(2);
        for(int i=0;i<4;i++){
            for(int j=0;j<5;j++){
                gp.add(bt[i][j],i,j);
            }
        }
        gp.setAlignment(Pos.CENTER);

        BorderPane bp = new BorderPane();
        bp.setTop(tf);
        bp.setAlignment(tf,Pos.CENTER);
        bp.setCenter(gp);

        Scene sc = new Scene(bp,400,600);
        stage.setScene(sc);
        stage.setTitle("電卓");
        stage.show();
    }
    class ButtonEventHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            String in = ((Button)e.getSource()).getText();
            StringBuffer stb = new StringBuffer(tf.getText());
            if(in == "="){
                String str = new String(stb.toString());
                String regex = "[+\\-]?[0-9]+\\.?[0-9]*[+\\-\\*/]{1}[0-9]+\\.?[0-9]*";
                if(str.matches(regex)){
                    String regex2 = "(?=[+\\-\\*/])";
                    String[] operands = str.toString().split(regex2);
                    double op1 = Double.parseDouble(operands[0]);
                    double op2 = Double.parseDouble(operands[1].substring(1));
                    char sign = operands[1].charAt(0);
                    double result=0;
                    if(sign=='+') result = op1 + op2;
                    else if(sign=='-') result = op1 - op2;
                    else if(sign=='*') result = op1 * op2;
                    else{
                        if(op2!=0){
                            result = op1 / op2;
                        }
                    }
                    NumberFormat nf = new DecimalFormat("0.000000");
                    if(op2==0){
                        str=("ERROR");
                    }else{
                        str=String.valueOf(nf.format(result));
                    }
                }
                tf.setText(str);
            }
            else if(in == "CE" || in == "C"){
                tf.setText("");
            }
            else if(in == "BS"){
                int len = stb.length();
                stb.deleteCharAt(len-1);
                String str = new String(stb.toString());
                tf.setText(str);
            }else if(in == "±"){
                String str = new String(stb.toString());
                String regex = "^[0-9].*";
                if(str.matches(regex)){
                    stb.insert(0,"-");
                    str = new String(stb.toString());
                    tf.setText(str);
                }
            }else{
                stb.append(in);
                String str = new String(stb.toString());
                tf.setText(str);
            }
        }
    }
}
