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
    private void checkWin (int x){

        int row = x-x%3; //номер строки - проверяем только её
        if (mas[row]==mas[row+1] && mas[row]==mas[row+2]){
            Toast toast = Toast.makeText(getApplicationContext(),firstMove + " 1Win!", Toast.LENGTH_SHORT);
            toast.show();
            resetGame();
            return;
        }
        //поиск совпадений по вертикали
        int column = x%3; //номер столбца - проверяем только его
        if (mas[column]==mas[column+3])
            if (mas[column]==mas[column+6]){
                Toast toast = Toast.makeText(getApplicationContext(),firstMove + " 2Win!", Toast.LENGTH_SHORT);
                toast.show();
                resetGame();
                return;
            }
        //мы здесь, значит, первый поиск не положительного результата
        //если значение n находится на одной из граней - возвращаем false
        if (x%2!=0){
                    if (checkNoWin()){
                        resetGame();
                    }
                    return;
        }
        //проверяем принадлежит ли к левой диагонали значение
        if (x%4==0){
            //проверяем есть ли совпадения на левой диагонали
            if (mas[0] == mas[4] &&
                    mas[0] == mas[8]){
                Toast toast = Toast.makeText(getApplicationContext(),firstMove + " 3Win!", Toast.LENGTH_SHORT);
                toast.show();
                resetGame();
            }
            if (x!=4) {
                if (checkNoWin()){
                    resetGame();
                }
                return;
            }
        }
        if (mas[2] == mas[4] && mas[2] == mas[6])
        {
            Toast toast = Toast.makeText(getApplicationContext(),firstMove + " 4Win!", Toast.LENGTH_SHORT);
            toast.show();
            resetGame();
        }
        if (checkNoWin()){
            resetGame();
        }
    }
    private void setIcon (ImageView img, int x){

        if (mas[x] <1 && firstMove == false){
            mas[x] = 1;
            img.setImageResource(R.drawable.mzero);
            checkWin(x);
            firstMove = true;
            //место бота

        }
        else if (mas[x] <1 && firstMove == true){
            mas[x] = 2;
            img.setImageResource(R.drawable.mkrest);
            checkWin(x);
            firstMove = false;
            //место бота


        }
    }


}
