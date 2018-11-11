package com.example.mathpal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Set;

public class TrigoCheck extends AppCompatActivity {

    EditText base11, step11;
    EditText base12, step12;
    EditText base13, step13;

    EditText base21, step21;
    EditText base22, step22;
    EditText base23, step23;

    TextView outputText;

    Double[] bases;
    Double[] steps;

    String s1,s2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trigo_check);

        base11 = (EditText)findViewById(R.id.base11);
        base12 = (EditText)findViewById(R.id.base12);
        base13 = (EditText)findViewById(R.id.base13);

        base21 = (EditText)findViewById(R.id.base21);
        base22 = (EditText)findViewById(R.id.base22);
        base23 = (EditText)findViewById(R.id.base23);

        step11 = (EditText)findViewById(R.id.step11);
        step12 = (EditText)findViewById(R.id.step12);
        step13 = (EditText)findViewById(R.id.step13);

        step21 = (EditText)findViewById(R.id.step21);
        step22 = (EditText)findViewById(R.id.step22);
        step23 = (EditText)findViewById(R.id.step23);

        outputText = (TextView)findViewById(R.id.outputText);

        bases = new Double[6];
        steps = new Double[6];

    }


    void TrigCheck(View view){
        String s;
        s = base11.getText().toString();
        if(!s.isEmpty())
            bases[0] = Double.parseDouble(s);
        else bases[0] = Double.parseDouble("0");

        s = base12.getText().toString();
        if(!s.isEmpty())
            bases[1] = Double.parseDouble(s);
        else bases[1] = Double.parseDouble("0");

        s = base13.getText().toString();
        if(!s.isEmpty())
            bases[2] = Double.parseDouble(s);
        else bases[2] = Double.parseDouble("0");

        s = base21.getText().toString();
        if(!s.isEmpty())
            bases[3] = Double.parseDouble(s);
        else bases[3] = Double.parseDouble("0");

        s = base22.getText().toString();
        if(!s.isEmpty())
            bases[4] = Double.parseDouble(s);
        else bases[4] = Double.parseDouble("0");

        s = base23.getText().toString();
        if(!s.isEmpty())
            bases[5] = Double.parseDouble(s);
        else bases[5] = Double.parseDouble("0");
        ///////
        s = step11.getText().toString();
        if(!s.isEmpty())
            steps[0] = Double.parseDouble(s);
        else steps[0] = Double.parseDouble("0");

        s = step12.getText().toString();
        if(!s.isEmpty())
            steps[1] = Double.parseDouble(s);
        else steps[1] = Double.parseDouble("0");

        s = step13.getText().toString();
        if(!s.isEmpty())
            steps[2] = Double.parseDouble(s);
        else steps[2] = Double.parseDouble("0");

        s = step21.getText().toString();
        if(!s.isEmpty())
            steps[3] = Double.parseDouble(s);
        else steps[3] = Double.parseDouble("0");

        s = step22.getText().toString();
        if(!s.isEmpty())
            steps[4] = Double.parseDouble(s);
        else steps[4] = Double.parseDouble("0");

        s = step23.getText().toString();
        if(!s.isEmpty())
            steps[5] = Double.parseDouble(s);
        else steps[5] = Double.parseDouble("0");


        boolean b = areGroupsSame(bases, steps);
        if(b){
            outputText.setText("Groups are equal!");
        }
        else{
            outputText.setText("Groups are not equal!");
        }
    }

    boolean areGroupsSame(Double[] bases, Double[] steps){
        HashMap<Double, Integer> numbers1 = new HashMap<Double, Integer>();
        HashMap<Double, Integer> numbers2 = new HashMap<Double, Integer>();

        for(int i = 0; i < 3; i++) {
            if(bases[i] != 0 || steps[i] != 0)
                insertNums(numbers1, bases[i], steps[i], i);
            if(bases[i+3] != 0 || steps[i+3] != 0)
                insertNums(numbers2, bases[i+3], steps[i+3], i);
        }

        return areEqual(numbers1, numbers2);
    }

    void insertNums(HashMap<Double, Integer> numbers, Double base, Double step, int type){
        Double value = base;
        while(!numbers.containsKey(value) || numbers.get(value) != type){
            numbers.put(value, type);
            value = (value + step)%360;
        }
    }

    boolean areEqual(HashMap<Double, Integer> numbers1, HashMap<Double, Integer> numbers2){
        Set<Double> keys1 = numbers1.keySet();
        if(keys1.size() != numbers2.size()) {
            return false;
        }

        for (Double key : keys1) {
            if (!numbers2.containsKey(key)){
                return false;
            }
        }

        return true;
    }
}

