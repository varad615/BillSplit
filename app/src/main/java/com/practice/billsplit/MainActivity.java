package com.practice.billsplit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public Button calculator, share;
    public TextView output;
    public EditText amount, people;
    private final TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String amountInput = amount.getText().toString().trim();
            String peopleInput = people.getText().toString().trim();

            calculator.setEnabled(!amountInput.isEmpty() && !peopleInput.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.amount);
        people = findViewById(R.id.people);
        calculator = findViewById(R.id.calc);
        output = findViewById(R.id.output);
        share = findViewById(R.id.send);

        amount.addTextChangedListener(watcher);
        people.addTextChangedListener(watcher);


        calculator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calc();
            }
        });


    }

    private void calc() {
        int num1 = Integer.parseInt(amount.getText().toString());
        int num2 = Integer.parseInt(people.getText().toString());
        int sum = num1 / num2;

        output.setText(String.valueOf(sum));
        share.setVisibility(View.VISIBLE);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent share = new Intent(Intent.ACTION_SEND);
                share.setType("text/plain");
                String body = "Amount is " + sum;
                String head = "Bill Split";
                share.putExtra(Intent.EXTRA_TEXT, body);
                share.putExtra(Intent.EXTRA_SUBJECT, head);
                startActivity(Intent.createChooser(share, "Choose Whom to share with"));
            }
        });

    }
}