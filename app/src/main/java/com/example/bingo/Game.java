package com.example.bingo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Game extends Activity implements View.OnClickListener {

    private Button play;
    private Button exit;
    private int[] numbers;
    private Random random;
    private TextView currentNumber, selectedNumbers;
    private int counter;
    private TextView[] nums;
    //private Thread throwNumbers;

    private TableLayout table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        this.random = new Random();
        this.currentNumber = findViewById(R.id.currentNumber);
        this.selectedNumbers = findViewById(R.id.selectedNumbers);
        this.numbers = new int[90];
        this.table = findViewById(R.id.table);
        this.nums = new TextView[90];
        this.counter = 0;
        fillNums();
        shuffle(this.numbers, this.random);
        this.play = findViewById(R.id.play);
        this.exit = findViewById(R.id.exit);
        this.play.setClickable(true);
        this.exit.setClickable(true);
        this.play.setOnClickListener(this);
        this.exit.setOnClickListener(this);
        //this.throwNumbers = new Thread(new ThrowNumbers(this, this.currentNumber, this.selectedNumbers, this.usedNumbers, this.random, this.counter, this.numbers, this.play, this.nums));
    }

    private void shuffle(int[] array, Random random) {
        int aux = 0;
        for (int i = 0; i < array.length; i++) {
            int j = random.nextInt(array.length);
            aux = array[i];
            array[i] = array[j];
            array[j] = aux;
        }
    }

    private void fillNums() {
        for (int i = 1; i <= 90; i++) {
            int id = getResources().getIdentifier("tv" + i, "id", getPackageName());
            this.nums[i - 1] = findViewById(id);
            this.numbers[i - 1] = i;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.play) {
            if (this.counter == 90) startActivity(new Intent(this, End.class));
            //int rand = this.random.nextInt(90) + 1;
            this.currentNumber.setText(String.valueOf(this.numbers[this.counter]));
            lookFor(this.numbers[this.counter]);
            this.counter++;
            this.selectedNumbers.setText(manageIncrement());
            //if (this.counter > 90) startActivity(new Intent(this, End.class));
        }else finish();
    }

    private void lookFor(int rand) {
        for (int i = 0; i < 90; i++)
            if (Integer.parseInt(this.nums[i].getText().toString()) == rand) {
                this.nums[i].setTypeface(null, Typeface.BOLD);
                this.nums[i].setTextColor(Color.BLUE);
                return;
            }
    }

    private String manageIncrement(){
        String out = "";
        String[] arr = this.selectedNumbers.getText().toString().split(" ");
        arr[1] = this.counter + "";
        for (String s : arr) out += s + " ";
        return out.trim();
    }


}

//====================================================================================//
//***ESTA MIERDA NO SE ESTÁ USANDO PORQUE NO FUNCIONA, YA QUISIERA YO QUE FUERA ASI***//
//====================================================================================//
class ThrowNumbers implements Runnable{

    private Game activity;
    private TextView currentNumber, selectedNumbers;
    private ArrayList<Integer> usedNumbers;
    private int counter;
    private Random random;
    private int[] numbers;
    private volatile boolean isWaiting;
    private boolean isRunning;
    private Button play;
    private TextView[] nums;

    public ThrowNumbers(Game activity, TextView currentNumber, TextView selectedNumbers, ArrayList<Integer> usedNumbers, Random random, int counter, int[] numbers, Button play, TextView[] nums) {
        this.activity = activity;
        this.currentNumber = currentNumber;
        this.selectedNumbers = selectedNumbers;
        this.usedNumbers = usedNumbers;
        this.random = random;
        this.counter = counter;
        this.numbers = numbers;
        this.play = play;
        this.nums = nums;
        this.isWaiting = false;
    }

    @Override
    public void run() {
        this.isRunning = true;
        //if (this.usedNumbers.size() == 90) return;
        int rand = this.random.nextInt(90) + 1;
        if (!this.usedNumbers.contains(rand)){
            this.usedNumbers.add(rand);
            this.currentNumber.setText(String.valueOf(rand));
            try{
                Thread.sleep(2500);
            } catch (InterruptedException e) {}
            lookFor(rand);
        }else this.activity.onClick(this.play);
        this.counter++;
        if(this.counter > 90) this.activity.startActivity(new Intent(this.activity, End.class));
        this.selectedNumbers.setText(manageIncrement());
    }

    private void lookFor(int rand) {
        for (int i = 0; i < 90; i++)
            if (this.numbers[i] == rand) {
                this.nums[i].setTypeface(null, Typeface.BOLD);
                this.nums[i].setTextColor(Color.BLUE);
                return;
            }
    }

    private String manageIncrement(){
        String out = "";
        String[] arr = this.selectedNumbers.getText().toString().split(" ");
        arr[1] = this.counter + "";
        for (String s : arr) out += s + " ";
        return out.trim();
    }
}
