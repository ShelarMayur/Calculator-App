package com.mshel.calculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView displayOperator;
    private static final String PERFORM_OPERATION = "displayOperator";
    private static final String STATE_OF_OPERAND = "operand1";

    //Variables to hold the operands
    private Double operand1 = null;
    private Double operand2 = null;
    private String pendingOperation = "=";

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(PERFORM_OPERATION, displayOperator.getText().toString());
        if (operand1 != null)
            outState.putString(STATE_OF_OPERAND, operand1.toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String s = savedInstanceState.getString(PERFORM_OPERATION);
        displayOperator.setText(s);
        pendingOperation = s;
        if (savedInstanceState.getString(STATE_OF_OPERAND) != null)
            operand1 = Double.valueOf(savedInstanceState.getString(STATE_OF_OPERAND));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (EditText) (findViewById(R.id.result));
        newNumber = (EditText) (findViewById(R.id.newNumber));
        displayOperator = (TextView) (findViewById(R.id.operation));

        Button button0 = (Button) (findViewById(R.id.button0));
        Button button1 = (Button) (findViewById(R.id.button1));
        Button button2 = (Button) (findViewById(R.id.button2));
        Button button3 = (Button) (findViewById(R.id.button3));
        Button button4 = (Button) (findViewById(R.id.button4));
        Button button5 = (Button) (findViewById(R.id.button5));
        Button button6 = (Button) (findViewById(R.id.button6));
        Button button7 = (Button) (findViewById(R.id.button7));
        Button button8 = (Button) (findViewById(R.id.button8));
        Button button9 = (Button) (findViewById(R.id.button9));
        Button button00 = (Button) (findViewById(R.id.button00));


        Button buttonDivide = (Button) (findViewById(R.id.buttonDivide));
        Button buttonMultiply = (Button) (findViewById(R.id.buttonMultiply));
        Button buttonAdd = (Button) (findViewById(R.id.buttonAdd));
        Button buttonSubtract = (Button) (findViewById(R.id.buttonSubtract));
        Button buttonEquals = (Button) (findViewById(R.id.buttonEquals));
        Button buttonDot = (Button) (findViewById(R.id.buttonDot));
        Button buttonNeg = (Button) (findViewById(R.id.buttonNeg));
        Button buttonClear = (Button) (findViewById(R.id.buttonClear));
        Button buttonDel = (Button) (findViewById(R.id.buttonDel));


        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Button btn = (Button) view;
                newNumber.append(btn.getText().toString());
            }
        };
        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);
        buttonEquals.setOnClickListener(listener);
        button00.setOnClickListener(listener);

        View.OnClickListener operator = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayOperator.setText("");
                Button b = (Button) view;
                displayOperator.append(b.getText().toString());
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue.toString(), op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }

                pendingOperation = op;
                displayOperator.setText(pendingOperation);
            }
        };
        buttonMultiply.setOnClickListener(operator);
        buttonDivide.setOnClickListener(operator);
        buttonAdd.setOnClickListener(operator);
        buttonSubtract.setOnClickListener(operator);
        buttonEquals.setOnClickListener(operator);

        View.OnClickListener negatation = new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s = newNumber.getText().toString();
                if (s.length() == 0)
                    newNumber.setText("-");
                else {
                    try {
                        Double d = Double.valueOf(newNumber.getText().toString());
                        d *= -1;
                        newNumber.setText(d.toString());
                    } catch (NumberFormatException e) {
                        newNumber.setText("");
                    }
                }
            }
        };
        buttonNeg.setOnClickListener(negatation);

        View.OnClickListener clear = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                result.setText("");
                newNumber.setText("");
                displayOperator.setText("");
                operand1 = null;
                operand2 = null;
                pendingOperation = "=";
            }
        };
        buttonClear.setOnClickListener(clear);

        View.OnClickListener del = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringBuffer s15 = new StringBuffer();
                try {
                    String s10 = newNumber.getText().toString();
                    for (int i = 0; i < s10.length() - 1; i++) {
                        s15.insert(i, s10.charAt(i));
                    }
                    newNumber.setText(s15.toString());
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
            }
        };
        buttonDel.setOnClickListener(del);
    }

    private void performOperation(String value, String op) {
        if (operand1 == null) {
            operand1 = Double.parseDouble(value);
        } else {
            operand2 = Double.parseDouble(value);
            if (pendingOperation.equals("=")) {
                pendingOperation = op;
            }
            switch (pendingOperation) {
                case "+":
                    operand1 += operand2;
                    break;
                case "-":
                    operand1 -= operand2;
                    break;
                case "*":
                    operand1 *= operand2;
                    break;
                case "/":
                    if (operand2 == 0.0) {
                        operand1 = 0.0;
                    } else {
                        operand1 /= operand2;
                    }
                    break;
                case "=":
                    operand1 = operand2;
                    break;
            }
        }
        result.setText(String.valueOf(operand1));
        newNumber.setText("");
    }
}