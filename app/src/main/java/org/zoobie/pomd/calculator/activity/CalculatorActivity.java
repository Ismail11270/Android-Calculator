package org.zoobie.pomd.calculator.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import org.zoobie.pomd.calculator.R;

public class CalculatorActivity extends AppCompatActivity {

    private Button[] numberPad;
    private Button[] operatorPad;
    private GridLayout keyboardGrid;
    private final String[] operators = {"ce", "c", "/", "+", "-", "x", "del", "=", "dot"};
    private String stringOutputTop = "";
    private String stringOutputBottom = "0";
    private Character operator = null;
    private Double a = null, b = null;
    private TextView outputTop, outputBottom;


    private boolean operatorExists = false;
    private boolean resetTheSecondLine = false;
    private boolean divisionByZero = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        getSupportActionBar().setTitle("Calculator");

        setupViews();

        for (Button b : numberPad) b.setOnClickListener(numPadClickListener);
        for (Button b : operatorPad) b.setOnClickListener(operatorPadClickListener);
    }

    private View.OnClickListener numPadClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(divisionByZero) {
                outputTop.setText("");
                divisionByZero = false;
            }
            if (resetTheSecondLine) {
                stringOutputBottom = "";
                resetTheSecondLine = false;
            }
            if (stringOutputBottom.equals("0")) {
                stringOutputBottom = "";
            }
            stringOutputBottom += v.getTag();
            outputBottom.setText(stringOutputBottom);
        }
    };

    private View.OnClickListener operatorPadClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(divisionByZero) {
                outputTop.setText("");
                divisionByZero = false;
            }
            switch (v.getTag().toString()) {
                case "ce":
                    stringOutputBottom = "0";
                    outputBottom.setText(stringOutputBottom);
                    break;
                case "c":
                    stringOutputTop = "";
                    stringOutputBottom = "0";
                    a = null;
                    b = null;
                    operator = null;
                    operatorExists = false;
                    outputBottom.setText(stringOutputBottom);
                    outputTop.setText(stringOutputTop);
                    resetTheSecondLine = false;
                    break;
                case "del":
                    if (!stringOutputBottom.equals("0") && !stringOutputBottom.equals("")) {
                        stringOutputBottom = stringOutputBottom.substring(0, stringOutputBottom.length() - 1);
                        if(stringOutputBottom.length() < 1) stringOutputBottom = "0";
                        outputBottom.setText(stringOutputBottom);
                    }
                    break;
                case "dot":
                    if (!stringOutputBottom.contains(".")) {
                        stringOutputBottom += ".";
                        outputBottom.setText(stringOutputBottom);
                    }
                    break;
                case "=":
                    if(operator==null) break;
                    b = Double.parseDouble(stringOutputBottom);
                    stringOutputBottom = calculate(operator, a, b);
                    outputBottom.setText(stringOutputBottom);
                    stringOutputTop += " " + b;
                    outputTop.setText(stringOutputTop);
                    try {
                        a = Double.parseDouble(stringOutputBottom);
                    }catch(NumberFormatException e){
                        a = 0d;
                    }
                    operator = null;
                    operatorExists = false;
                    resetTheSecondLine = true;
                    break;
                default:
                    if (!operatorExists) {
                        operatorExists = true;
                        operator = v.getTag().toString().charAt(0);
                        a = Double.parseDouble(stringOutputBottom);
                        stringOutputTop = stringOutputBottom + " " + operator;
                        stringOutputBottom = "0";
                        outputBottom.setText(stringOutputBottom);
                        outputTop.setText(stringOutputTop);
                    } else {
                        b = Double.parseDouble(stringOutputBottom);
                        stringOutputBottom = calculate(operator, a, b);
                        a = Double.parseDouble(stringOutputBottom);
                        outputBottom.setText(stringOutputBottom);
                        stringOutputTop += " " + b + " " + v.getTag();
                        operator = v.getTag().toString().charAt(0);
                        outputTop.setText(stringOutputTop);
                        resetTheSecondLine = true;
                        b = null;
                    }
                    break;
            }
        }
    };

    private void clearInputFields(){
        stringOutputBottom = "0";
        stringOutputTop = "";

    }


    private String calculate(Character operator, Double a, Double b) {
        switch (operator) {
            case '+':
                return Double.toString(a + b);
            case '-':
                return Double.toString(a - b);
            case 'x':
                return Double.toString(a * b);
            case '/':
                if(b == 0) {
                    divisionByZero = true;
                    return "Can't divide by zero";
                }
                return Double.toString(a / b);
            default:
                return "";
        }
    }

    private void setupViews() {
        keyboardGrid = findViewById(R.id.keyBoardGrid);
        numberPad = new Button[10];


        for (int i = 0; i < numberPad.length; i++) {
            numberPad[i] = keyboardGrid.findViewWithTag(i + "");
        }

        operatorPad = new Button[operators.length];
        for (int i = 0; i < operatorPad.length; i++) {
            operatorPad[i] = keyboardGrid.findViewWithTag(operators[i]);
        }

        outputTop = findViewById(R.id.calculatorInput1);
        outputBottom = findViewById(R.id.calculatorInput2);
        outputBottom.setText(stringOutputBottom);
    }
}
