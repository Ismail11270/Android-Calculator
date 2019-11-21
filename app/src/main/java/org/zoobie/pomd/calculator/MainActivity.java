package org.zoobie.pomd.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.zoobie.pomd.calculator.activity.BmiActivity;
import org.zoobie.pomd.calculator.activity.CalculatorActivity;

public class MainActivity extends AppCompatActivity {

    private Button bmiButton, calculatorButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Calculators");

        bmiButton = findViewById(R.id.bmi);
        calculatorButton = findViewById(R.id.calculator);
        exitButton = findViewById(R.id.exit);

        bmiButton.setOnClickListener(v -> {
            startActivity(new Intent(this,BmiActivity.class));
        });

        calculatorButton.setOnClickListener(v -> {
            startActivity(new Intent(this, CalculatorActivity.class));
        });

        exitButton.setOnClickListener(v -> {
            android.os.Process.killProcess(android.os.Process.myPid());
        });
    }

}