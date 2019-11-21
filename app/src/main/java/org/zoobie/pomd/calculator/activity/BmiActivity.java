package org.zoobie.pomd.calculator.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.zoobie.pomd.calculator.R;

public class BmiActivity extends AppCompatActivity {

    private EditText weightEt, heightEt;
    private TextView resultTv;
    private Button calcButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);

        weightEt = findViewById(R.id.weight);
        heightEt = findViewById(R.id.height);
        resultTv = findViewById(R.id.result);
        calcButton = findViewById(R.id.calcBmi);

        getSupportActionBar().setTitle("BMI Calculator");

        calcButton.setOnClickListener(v -> {
//            String weight = weightEt.getText().toString();
//            String height = heightEt.getText().toString();
            String message = "";
            try {
                float weigth = Float.parseFloat(weightEt.getText().toString());
                float height = Float.parseFloat(heightEt.getText().toString());

                float result = weigth / (height*height);
                message = "BMI = " + result;
            } catch(NumberFormatException e){
                message = "Missing input!";
            } finally {
                resultTv.setText(message);
            }
        });
    }
}
