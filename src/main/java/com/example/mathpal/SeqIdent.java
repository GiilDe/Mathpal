package com.example.mathpal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.Character.getNumericValue;
import static java.lang.Character.isDigit;

public class SeqIdent extends AppCompatActivity {

    TextView displayText;
    EditText inputText;

    private static double dSub;
    private static double qSub;
    private static double a;
    private static double b;
    private static double c;
    private static double x;
    private static double y;
    private static double[] strSer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seq_ident);

        displayText = (TextView)findViewById(R.id.displayText);
        inputText = (EditText)findViewById(R.id.inputText);
    }

    public void seqIdent(View view){
        a = 0;
        b = 0;
        c = 0;
        x = 0;
        y = 0;
        dSub = 0;
        qSub = 0;
        double[] sequence = whatIsSeq();
        strSer = new double[14];
        if(sequence.length != 1)
            displayText.setText("You entered: " + arrayToString(sequence) + "\n"
                    + sequenceFinal(sequence));
    }

    private double[] whatIsSeq(){
        double[] seq = new double[10];
        int numLength;
        int i = 0;
        int j = 0;
        String s = inputText.getText().toString();

        while(i < s.length()){
            if(isDigit(s.charAt(i))) {
                numLength = numLength(s, i + 1);
                seq[j] = num(s, i, numLength);
                j++;
                i = i + numLength;
            }
            else if(i > 0 && s.charAt(i) == '/' && (isDigit(s.charAt(i-1)) || (i-5-numLength("" + seq[j-1], 0) >= 0 && isSqrt(s, i-5-numLength("" + seq[j-1], 0)))) && (isDigit(s.charAt(i+1)) || isSqrt(s, i+1))){
                if(isDigit(s.charAt(i+1))){
                    numLength = numLength(s, i + 1) - 1;
                    seq[j - 1] = seq[j - 1] / num(s, i + 1, numLength);
                    i = i + numLength + 1;
                }
                else if(isSqrt(s, i+1)){
                    numLength = numLength(s, i+6) - 1;
                    seq[j - 1] = seq[j - 1]/Math.sqrt(num(s, i+6, numLength));
                    i = i + numLength + 6;
                }
            }
            else if(i > 0 && s.charAt(i) == '.' && isDigit(s.charAt(i-1)) && isDigit(s.charAt(i+1))){
                numLength = numLength(s, i+1) - 1;
                seq[j-1] = seq[j-1] + (double)num(s, i+1, numLength)/Math.pow(10, numLength);
                i = i + numLength + 1;
            }
            else if(s.charAt(i) == 's' && isSqrt(s, i)){
                numLength = numLength(s, i+5) - 1;
                seq[j] = Math.sqrt(num(s, i+5, numLength));
                j++;
                i = i + numLength + 5;
            }
            else i++;
        }

        if(j == 0 || i == 0)
            return new double[1];

        double[] seqFinal = new double[j];

        System.arraycopy(seq, 0, seqFinal, 0, j);

        return seqFinal;
    }

    private static boolean isSqrt(String s, int i){
        return(i < s.length() - 5 && s.substring(i, i+5).equals("sqrt(") && isDigit(s.charAt(i+5)));
    }

    private static int numLength(String s, int i){
        int num = 1;
        while(i < s.length() && isDigit(s.charAt(i))){
            num++;
            i++;
        }
        return num;
    }

    private static String arrayToString(double[] a){
        String s;
        if(isWhole(a[0]))
            s = "" + (int)a[0];
        else s = "" + a[0];
        for(int i = 1; i < a.length; i++){
            if(isWhole(a[i]))
                s = s + ", " + (int)a[i];
            else s = s + ", " + a[i];
        }
        return s;
    }

    private static boolean isWhole(double a){
        return(a%1 == 0);
    }

    private static int num(String s, int i, int length){
        int number = 0;
        int initial = i;
        for(int j = length; j > 0; j--){
            number = (int)(number + getNumericValue(s.charAt(i))*Math.pow(10, j-1));
            i++;
        }
        if(initial > 0 && s.charAt(initial-1) == '-')
            number = number*(-1);
        return number;
    }

    public static String geoToString(double[] arr){
        a = arr[0];
        b = arr[1];
        c = arr[2];
        String s;

        if(a == 1)
            s = toString(b) + "^(n-1)";
        else if(a == b)
            if(a < 0)
                s = "(" + toString(a) + ")^(n)";
            else s = toString(a) + "^(n)";
        else if(b < 0)
            s = toString(a) + "*" + "(" + toString(b) + ")" + "^(n-1)";
        else s = toString(a) + "*" + toString(b) + "^(n-1)";

        if(c != 0){
            if(c < 0){
                c = c*-1;
                s = s + " - " + toString(c);
            }
            else s = s + " + " + toString(c);
        }
        return s;
    }

    public static String sequenceFinal(double[] series){

        if(isAlgebric(series))
            return "This is an algebric sequence: \na(n) = " + toString(strSer);

        if(isGeometric(series))
            return "This is a geometric sequence: \na(n) = " + geoToString(strSer);

        if(isAlgebric(createDiffArr(series))){
            String s;
            subAlg(series);
            s = "This is a sequence which it's subtraction sequence is an algebric sequence: a(n) = " + toString(strSer);
            strSer = new double[14];
            isAlgebric(createDiffArr(series));
            s = s + "\nThe subtraction sequence is: " + arrayToString(createDiffArr(series)) + "\nwith a formula of a(n) = " + toString(strSer);
            return s;
        }

        if(isGeometric(createDiffArr(series))){
            String s;
            subGeo(series);
            s = "This is a sequence which it's subtraction sequence is a geometric sequence: a(n) = " + geoToString(strSer);
            strSer = new double[14];
            isGeometric(createDiffArr(series));
            s = s + "\nThe subtraction sequence is: " + arrayToString(createDiffArr(series)) + "\nwith a formula of a(n) = " + geoToString(strSer);
            return s;
        }

        boolean b;
        String str;
        if(series[0] > 0){
            b = false;
            str = "(-1)^(n+1)";
        }
        else{
            b = true;
            str = "(-1)^n";
        }

        if(isAlgebric(turnSign(series, b)))
            return "This is a sequence which is algebric in absolute value: a(n) = " + str + "*(" + toString(strSer) + ")";

        if(isAlgebric(createDiffArr(turnSign(series, b)))){
            String s;
            subAlg(turnSign(series, b));
            s = "This is a sequence which in absolute value has a subtraction sequence which is algebric: a(n) = " + str + "*(" + toString(strSer) + ")";
            strSer = new double[14];
            isAlgebric(createDiffArr(turnSign(series, b)));
            s = s + "\nThe subtraction sequence is: " + arrayToString(createDiffArr(turnSign(series, b))) + "\nwith a formula of a(n) = " + toString(strSer);
            return s;
        }

        if(isGeometric(createDiffArr(turnSign(series, b)))){
            String s;
            subGeo(turnSign(series, b));
            s = "This is a sequence which in absolute value has a subtraction sequence which is Geometric: a(n) = " + str + "*(" + geoToString(strSer) + ")";
            strSer = new double[14];
            isGeometric(createDiffArr(turnSign(series, b)));
            s = s + "\nThe subtraction sequence is: " + arrayToString(createDiffArr(turnSign(series, b))) + "\nwith a formula of a(n) = " + geoToString(strSer);
            return s;
        }

        if(isGeneralPolynomPositive(series))
            return "This is a general sequence*: \na(n) = " + toString(strSer);

        if(isGeneralPolynom(series))
            return "This is a general sequence: \na(n) = " + toString(strSer);

        return "The sequence was not identified";
    }

    public static boolean isAlgebric(double[] series){
        double d = series[1] - series[0];
        for(int i = 0; i < series.length - 1; i++)
            if(series[i+1] - series[i] != d)
                return false;
        double freeElement = series[0] - d;
        strSer[0] = d;
        strSer[1] = 1;
        strSer[2] = freeElement;
        strSer[3] = 0;
        return true;
    }

    public static boolean isGeometric(double[] series){
        double q = series[1]/series[0];
        for(int i = 0; i < series.length - 1 ;i++){
            if(series[i] != 0){
                if(series[i+1]/series[i] != q)
                    return false;
            }
            else return false;
        }
        strSer[0] = series[0];
        strSer[1] = q;
        strSer[2] = 0;
        return true;
    }

    private static void subAlg(double[] series){
        double[] subSeries = createDiffArr(series);
        dSub = subSeries[1] - subSeries[0];
        double free = series[0] - subSeries[0] + dSub;
        double nCoefficient = subSeries[0] - (3*dSub/2);
        double nSquare = dSub/2;
        strSer[0] = nSquare;
        strSer[1] = 2;
        strSer[2] = nCoefficient;
        strSer[3] = 1;
        strSer[4] = free;
        strSer[5] = 0;
    }

    private static void subGeo(double[] series){
        double[] subSeries = createDiffArr(series);
        qSub = subSeries[1]/subSeries[0];
        double freeI = series[0]-(subSeries[0]/(qSub-1));
        double byN = (subSeries[0]/(qSub-1));
        strSer[0] = byN;
        strSer[1] = qSub;
        strSer[2] = freeI;
    }

    private static double[] turnSign(double[] series, boolean oddOrEven){
        double[] absSeries = new double[series.length];
        for(int i = 0; i < series.length; i++){
            if(oddOrEven)
                absSeries[i] = (-1)*series[i];
            else absSeries[i] = series[i];
            oddOrEven = !oddOrEven;
        }
        return absSeries;
    }

    private static String toString(double a){
        if(isWhole(a)){
            int b = (int)a;
            return "" + b;
        }
        return "" + a;
    }

    private static String toString(double[] a){
        String s = "";
        String sign;
        int i = 0;
        if(a[i] == 1){
            if(a[i+1] == 0)
                s = s + "1";
            else if(a[i+1] == 1)
                s = s + "n";
            else s = s + "n^(" + toString(a[i+1]) + ")";
        }
        else{
            if(a[i+1] == 0)
                s = s + toString(a[i]);
            else if(a[i+1] == 1)
                s = s + toString(a[i]) + "n";
            else s = s + toString(a[i]) + "n^(" + toString(a[i+1]) + ")";
        }
        i = i + 2;
        while(i < a.length - 2){
            if(a[i] > 0)
                sign = " + ";
            else{
                a[i] = -a[i];
                sign = " - ";
            }
            if(a[i] == 1){
                if(a[i+1] == 0)
                    s = s + sign + "1";
                else if(a[i+1] == 1)
                    s = s + sign +"n";
                else s = s + sign +"n^(" + toString(a[i+1]) + ")";
            }
            else if(a[i] != 0){
                if(a[i+1] == 0)
                    s = s + sign + toString(a[i]);
                else if(a[i+1] == 1)
                    s = s + sign + toString(a[i]) + "n";
                else s = s + sign + toString(a[i]) + "n^(" + toString(a[i+1]) + ")";
            }
            i = i + 2;
        }
        return s;
    }

    private static boolean isGeneralPolynomPositive(double[] series){
        double coefficient;
        int n;
        double[] a = new double[series.length];
        double[] b = new double[series.length];
        System.arraycopy(series, 0, a, 0, a.length);
        System.arraycopy(series, 0, b, 0, b.length);
        int i = 0;
        int y = 0;
        int count = 0;
        while(y < 20 && !checkIfConst(a, 0) && a.length >= 2){
            if(checkIfConst(a)){
                coefficient = a[0]/factorial(i);
                strSer[count] = coefficient;
                strSer[count + 1] = i;
                a = new double[series.length];
                for(int j = 0; j < b.length; j++){
                    n = j + 1;
                    b[j] = b[j] - coefficient*Math.pow(n, i);
                }
                System.arraycopy(b, 0, a, 0, a.length);
                i = -1;
                count = count + 2;
            }
            else a = createDiffArr(a);
            i++;
            y++;
        }
        return(checkIfConst(a, 0));
    }

    private static boolean checkIfConst(double[] series){
        double start = series[0];
        for(int i = 0; i < series.length; i++){
            if(series[i] != start)
                return false;
        }
        return true;
    }

    private static boolean checkIfConst(double[] series, int a){
        for(int i = 0; i < series.length; i++){
            if(series[i] != a)
                return false;
        }
        return true;
    }

    private static double[] createDiffArr(double[] series){
        double[] diffArr = new double[series.length - 1];
        for(int i = 0; i < diffArr.length; i++){
            diffArr[i] = series[i+1] - series[i];
        }
        return diffArr;
    }

    private static int factorial(int n) {
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    private static boolean isGeneralPolynom(double[] series){
        int n = series.length;
        //for(m = -3.5; m <= 4; m = m + 0.5) {
        for (b = -15.5; b <= 15; b = b + 0.5) {
            for (c = -15; c <= 15; c = c + 0.5) {
                for (y = -3; y <= 4; y = y + 0.5) {
                    for (x = -3; x <= 4; x = x + 0.5) {
                        for (a = -15; a <= 15; a = a + 0.5) {
                            //if(m == -3.5)
                            //m = 0;
                            if(b == -15.5)
                                b = 0;
                            if(x == 0)
                                x = 0.5;
                            if(y == 0)
                                y = 0.5;
                            if (a * Math.pow(n /*+ m*/, x) + b * Math.pow(n, y) + c == series[n - 1] && checkOther(series)){
                                strSer[0] = a;
                                strSer[1] = x;
                                strSer[2] = b;
                                strSer[3] = y;
                                strSer[4] = c;
                                strSer[5] = 0;
                                return true;
                            }
                        }
                    }
                }
            }
        }
        //}
        return false;
    }

    private static boolean checkOther(double[] series){
        for(int i = series.length - 2; i >= 0; i--){
            if(a*Math.pow(i+1/*+m*/,x) + b*Math.pow(i+1,y) + c != series[i])
                return false;
        }
        return true;
    }
}


