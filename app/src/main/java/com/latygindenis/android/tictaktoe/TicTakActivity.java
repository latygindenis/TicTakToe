package com.latygindenis.android.tictaktoe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class TicTakActivity extends AppCompatActivity implements View.OnClickListener {

   ImageView icon0;
   ImageView icon1;
   ImageView icon2;
   ImageView icon3;
   ImageView icon4;
   ImageView icon5;
   ImageView icon6;
   ImageView icon7;
   ImageView icon8;
   ImageView [] masIcon;

   Button resetButton;

   int resZero = R.drawable.mzero;
   int resKrest = R.drawable.mkrest;
   boolean firstMove = false; //false - человек, true - компьютер
   int []mas = new int[9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tak);

        icon0 = findViewById(R.id.icon);
        icon1 = findViewById(R.id.icon1);
        icon2 = findViewById(R.id.icon2);
        icon3 = findViewById(R.id.icon3);
        icon4 = findViewById(R.id.icon4);
        icon5 = findViewById(R.id.icon5);
        icon6 = findViewById(R.id.icon6);
        icon7 = findViewById(R.id.icon7);
        icon8 = findViewById(R.id.icon8);
        resetButton = (Button)findViewById(R.id.restartbutton);

        masIcon = new ImageView[]{icon0, icon1, icon2, icon3, icon4, icon5, icon6, icon7, icon8};

        for (int i=0; i<9; i++)
        {
            masIcon[i].setOnClickListener(this);
        }
        resetButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.icon: setIcon(icon0, 0); break;
            case R.id.icon1:setIcon(icon1, 1); break;
            case R.id.icon2: setIcon(icon2, 2); break;
            case R.id.icon3: setIcon(icon3, 3); break;
            case R.id.icon4: setIcon(icon4, 4); break;
            case R.id.icon5: setIcon(icon5, 5); break;
            case R.id.icon6: setIcon(icon6, 6); break;
            case R.id.icon7: setIcon(icon7, 7); break;
            case R.id.icon8: setIcon(icon8, 8); break;
            case R.id.restartbutton: resetGame(); break;
        }
    }
    private void resetGame(){
        for (int i=0; i<9; i++)
        {
            mas[i]=0;
            masIcon[i].setImageResource(R.color.backiconcolor);
        }
        if (!firstMove){
            int botTurn = fortuneMap();
            mas[botTurn] = 2;
            masIcon[botTurn].setImageResource(R.drawable.mkrest);
        }

        firstMove = !firstMove;

    }
    private boolean checkNoWin(){
        for (int i=0; i<9; i++)
        {
            if (mas[i] == 0)
            {
                return false;
            }
        }
       return true;
    }
    private int checkWin (int x){ // 0 - нет победы, 1 - победа, 2 - ничья

        int row = x-x%3; //номер строки - проверяем только её


        if (mas[row]==mas[row+1] && mas[row]==mas[row+2] && mas[row]!=0){
            resetGame();
            return 1;
        }
        //поиск совпадений по вертикали
        int column = x%3; //номер столбца - проверяем только его
        if (mas[column]==mas[column+3] && mas[column]==mas[column+6] && mas[column]!=0) {
                resetGame();
                return 1;
            }
        //мы здесь, значит, первый поиск не положительного результата
        //если значение n находится на одной из граней - возвращаем false
        if (x%2!=0){
            if (checkNoWin()){
                resetGame();
                return 2;
            }
            return 0;
        }
        //проверяем принадлежит ли к левой диагонали значение
        if (x%4==0){
            //проверяем есть ли совпадения на левой диагонали
            if (mas[0] == mas[4] && mas[0] == mas[8]){
                resetGame();
                return 1;
            }
            if (x!=4) {
                if (checkNoWin()){
                    resetGame();
                    return 2;
                }

                return 0;
            }
        }
        if (mas[2] == mas[4] && mas[2] == mas[6])
        {
            resetGame();
            return 1;
        }
        if (checkNoWin()){
            resetGame();
            return 2;
        }

        return 0;
    }
    private void setIcon (ImageView img, int x){

        if (mas[x] <1){
            mas[x] = 1;
            img.setImageResource(R.drawable.mzero);
            int humanWin = checkWin(x);
            if (humanWin == 0){
                int botTurn = fortuneMap();
                mas[botTurn] = 2;
                masIcon[botTurn].setImageResource(R.drawable.mkrest);
                int botWin = checkWin(botTurn);

                if (botWin == 1){
                    Toast toast1 = Toast.makeText(getApplicationContext(),"Робот победил!" , Toast.LENGTH_SHORT);
                    toast1.show();
                }
                else if (botWin == 2){
                    Toast toast1 = Toast.makeText(getApplicationContext(), "Ничья" , Toast.LENGTH_SHORT);
                    toast1.show();
                }
            }
            else if (humanWin==1){
                Toast toast2 = Toast.makeText(getApplicationContext(),"Вы выиграли!" , Toast.LENGTH_SHORT);
                toast2.show();
            }
            else if (humanWin==2){
                Toast toast2 = Toast.makeText(getApplicationContext(),"Ничья" , Toast.LENGTH_SHORT);
                toast2.show();
            }
        }
    }

    private int fortuneMap (){
        int [] bufMap = mas.clone();

    //Предупреждение ситуаций вида "oo_" "o_o"  "_oo"

        for (int i=0; i<9; i+=3) // по строкам
        {
            if (bufMap[i] == 1 && bufMap[i+1] == 1 && bufMap[i+2] == 0){
                return i+2;
            }
            if (bufMap[i] ==1 && bufMap[i+1]==0 && bufMap[i+2]==1){
                return i+1;
            }
            if (bufMap[i] == 0 && bufMap[i+1]==1 && bufMap[i+2] == 1){
                return i;
            }
        }

        for (int i=0; i<3; i++) // по столбцам
        {
            if (bufMap[i] == 1 && bufMap[i+3] == 1 && bufMap[i+6] == 0){
                return i+6;
            }
            if (bufMap[i] ==1 && bufMap[i+3]==0 && bufMap[i+6]==1){
                return i+3;
            }
            if (bufMap[i] == 0 && bufMap[i+3]==1 && bufMap[i+6] == 1){
                return i;
            }
        }

        if (bufMap[0] == 1 && bufMap[4] == 1 && bufMap[8] == 0){//Главная диагональ
            return 8;
        }
        if (bufMap[0] ==1 && bufMap[4]==0 && bufMap[8]==1){
            return 4;
        }
        if (bufMap[0] == 0 && bufMap[4]==1 && bufMap[8] == 1){
            return 0;
        }


        if (bufMap[2] == 1 && bufMap[4] == 1 && bufMap[6] == 0){//Побочная диагональ
            return 6;
        }
        if (bufMap[2] ==1 && bufMap[4]==0 && bufMap[6]==1){
            return 4;
        }
        if (bufMap[2] == 0 && bufMap[4]==1 && bufMap[6] == 1){
            return 2;
        }

        //Построение "карты" путых клеток с коэффициентами (Клетка с самым высоким коэффициентом выбирается ботом

        for (int i=0; i<9; i+=3) // по строкам
        {
            if (bufMap[i] == 1 || bufMap[i+1] == 1 || bufMap [i+2] == 1) //Если есть хоть один враг
            {
                continue;
            }
            if (i==3){
                if (bufMap[i]!=2){
                    bufMap[i] += 20;
                }
                if (bufMap[i+1]!=2){
                    bufMap[i+1]+=20;
                }
                if (bufMap[i+2]!=2){
                    bufMap[i+2]+=20;
                }
            }
            else {
                if (bufMap[i]!=2){
                    bufMap[i] += 10;
                }
                if (bufMap[i+1]!=2){
                    bufMap[i+1]+=10;
                }
                if (bufMap[i+2]!=2){
                    bufMap[i+2]+=10;
                }
            }
        }
        for (int i=0; i<3; i++) // по столбцам
        {
            if (bufMap[i] == 1 || bufMap[i+3] == 1 || bufMap [i+6] == 1){
                continue;
            }
            if (i==1){
                if (bufMap[i] !=2){
                    bufMap[i] += 20;
                }
                if (bufMap[i+3] !=2){
                    bufMap[i+3] +=20;
                }
                if (bufMap[i+6]!=2){
                    bufMap[i+6] +=20;
                }
            }
            else {
                if (bufMap[i] !=2){
                    bufMap[i] += 10;
                }
                if (bufMap[i+3] !=2){
                    bufMap[i+3] +=10;
                }
                if (bufMap[i+6]!=2){
                    bufMap[i+6] +=10;
                }
            }
        }
        if (bufMap[0] != 1 && bufMap[4] !=1 && bufMap [8] !=1){ //Главная диагональ
            if (bufMap[0]!=2){
                bufMap[0]+=10;
            }
            if (bufMap[4]!=2){
                bufMap[4]+=10;
            }
            if (bufMap[8]!=2){
                bufMap[8]+=10;
            }
        }
        if (bufMap[2] != 1 && bufMap[4] !=1 && bufMap [6] !=1){ //Побочная диагональ
            if (bufMap[2]!=2){
                bufMap[2]+=10;
            }
            if (bufMap[4]!=2){
                bufMap[4]+=10;
            }
            if (bufMap[6]!=2){
                bufMap[6]+=10;
            }
        }
        int maxZnach=0, maxX=0;
        for (int i=0; i<9; i++){
            if (bufMap[i]>=maxZnach && bufMap[i]!=1 && bufMap[i]!=2){
                maxZnach = bufMap[i];
                maxX = i;
            }
        }
        return maxX;
    }
}
