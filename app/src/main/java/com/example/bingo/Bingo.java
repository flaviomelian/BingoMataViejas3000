package com.example.bingo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Bingo extends Activity implements View.OnClickListener {

    private TextView lolo;
    private Button button;

    @Override
    protected void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.main);
        this.lolo = findViewById(R.id.textView);
        this.button = findViewById(R.id.button);
        this.button.setClickable(true);
        this.button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("button", "button pressed");
        startActivity(new Intent(this, Game.class));
    }
}
