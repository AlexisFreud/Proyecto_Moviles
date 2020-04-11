package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

import io.github.kexanie.library.MathView;

public class MainActivity extends AppCompatActivity {
    
    MathView formula_two, ecuacion;

    MathView formula_two;
    MathView num1,num2,num3,num4,num5,num6,num7,num8,num9,num0;
    MathView op1,op2,op3,op4,op5,op6,op7,op8,op9,op10;
    MathView pl1,pl2,pl3,pl4,pl5,pl6,pl7,pl8,pl9,pl10;
    MathView rt1,rt2,rt3,rt4,rt5;

    TextView prueba;

    String tex = "This come from string. You can insert inline formula:" +
            " \\([ ]^2 + bx + c = 0\\) " +
            "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{[]}{[]}$$";
    Button calcular;

    String tex = "$$(3+(2\\times10))10-30+-100$$";

    String numerosString[] = {"\\(\\space\\space1\\)","\\(\\space\\space2\\)","\\(\\space\\space3\\)","\\(\\space\\space4\\)","\\(\\space\\space5\\)","\\(\\space\\space6\\)"
            ,"\\(\\space\\space7\\)","\\(\\space\\space8\\)","\\(\\space\\space9\\)","\\(\\space\\space0\\)"};

    String operadoresString[] = {"\\(\\space+\\)","\\(\\space-\\)","\\(\\space\\space\\centerdot\\)","\\(\\space\\times\\)","\\(\\space\\space/\\)","\\(\\space\\div\\)"
            ,"\\(\\space\\space(\\)","\\(\\space\\space)\\)","\\(\\space=\\)","\\(\\space\\space\\: .\\)"};

    String potenciaLogaritmos[] = {"\\(\\space\\space x\\)","\\(\\space\\square^2\\)","\\(\\space\\square^{\\square}\\)","\\(\\space\\: \\frac{\\square}{\\square}\\)",
            "\\(\\sqrt{[]}\\)","\\(\\sqrt[\\square]{[]}\\)","\\((\\square)\\)","\\(\\space\\:\\frac{d}{dx}\\)","\\(\\space\\:\\smallint\\)","\\(\\:\\int_{\\square}^{\\square}\\)"};

    String recorrerTexto[] = {"\\(\\space\\space\\Leftarrow\\)","\\(\\space\\space\\Rightarrow\\)","\\(\\space\\space\\lhd\\)","\\(\\space\\space=\\)","\\(\\space\\space CE\\)"};

    String ecuacionString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calcular = findViewById(R.id.bt_calcular);
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muestraResultado();
            }
        });
    }

    public void muestraResultado(){
        String subTex = tex.replace("$", "");
        LinkedList<String> newTex = new LinkedList<>();
        newTex.add("(");
        newTex.add("3");
        newTex.add("+");
        newTex.add("(");
        newTex.add("2");
        newTex.add("10");
        newTex.add(")");
        newTex.add(")");
        newTex.add("\\times");
        newTex.add("10");
        newTex.add("-");
        newTex.add("30");
        newTex.add("+");
        newTex.add("-100");
        String result = solve_arithmetic(newTex);
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
    }

    private LinkedList<String> creaLink(String equation){
        char[] numsArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        LinkedList<String> lista = new LinkedList<>();
        for (int i = 0; i < equation.length(); i++) {
            for (int j = 0; j < numsArray.length; j++) {
                if (equation.charAt(i) == numsArray[j]){
                    String str = numsArray[j]+"";
                    boolean isNumber = true;
                    i++;
                    while(isNumber){
                        isNumber = false;
                        for (int k = 0; k < numsArray.length; k++) {
                            if (equation.charAt(i) == numsArray[k]) {
                                str += numsArray[k]+"";
                                isNumber = true;
                                i++;
                                break;
                            }
                        }
                    }
                    break;
                }
            }
            if(equation.charAt(i) == '('){
                lista.add("(");
            }else if(equation.charAt(i) == ')') {
                lista.add(")");
            }else if(equation.charAt(i) == '+') {
                lista.add("+");
            }else if(equation.charAt(i) == '-'){
                lista.add("-");
            }else if (equation.charAt(i) == '\\'){
                lista.add("\\times");
            }
        }
        return lista;
    }

    public String solve_arithmetic(LinkedList<String> equation){
        /*
        Use PEMDAS for operations order
            - P: Parentesis
            - E: Exponentes
            - M: Multiplicacion
            - D: Division
            - A: Adicion
            - S: Sustraccion
         */
        String finalString = "";
        LinkedList<String> equationAux = equation;
        LinkedList<String> operaciones = new LinkedList<>();
        System.out.println("Before start");
        for (int i = 0; i < equationAux.size(); i++) {
            System.out.print(equationAux.get(i) + " . ");
        }
        System.out.println();

        // Check for parentesis
        for (int i = 0; i < equationAux.size(); i++) {
            if (equationAux.get(i).equals("(")){
                LinkedList<String> aux = new LinkedList<>();
                i++;
                int parentesis = 1;
                while(parentesis > 0){
                    if (equationAux.get(i).equals("(")) {
                        parentesis++;
                        i++;
                    }else if(equationAux.get(i).equals(")")){
                        parentesis--;
                        i++;
                    }else {
                        aux.add(equationAux.get(i));
                        i++;
                    }
                }
                operaciones.add(solve_arithmetic(aux));
                i--;
            }
            else{
                operaciones.add(equationAux.get(i));
            }
        }
        equationAux = operaciones;
        operaciones = new LinkedList<>();

        System.out.println("After parentesis");
        for (int i = 0; i < equationAux.size(); i++) {
            System.out.print(equationAux.get(i) + " . ");
        }
        System.out.println();

        // Check for exponents

        // Check for multiplication
        boolean possible_multiplication = false;
        double numA = 0;
        for (int i = 0; i < equationAux.size(); i++) {
            if (isNumber(equationAux.get(i)) && possible_multiplication){
                double numB = Double.parseDouble(equationAux.get(i));
                numA *= numB;
            }else if(equationAux.get(i).equals("\\times")){
                double numB = Double.parseDouble(equationAux.get(i+1));
                numA *= numB;
                i++;
            }else if(isNumber(equationAux.get(i))){
                possible_multiplication = true;
                numA = Double.parseDouble(equationAux.get(i));
            }else if (possible_multiplication){
                operaciones.add(numA+"");
                operaciones.add(equationAux.get(i));
                possible_multiplication = false;
            }
        }
        if(numA != 0){
            operaciones.add(numA+"");
        }
        equationAux = operaciones;
        operaciones = new LinkedList<>();

        System.out.println("After multiplication");
        for (int i = 0; i < equationAux.size(); i++) {
            System.out.print(equationAux.get(i) + " . ");
        }
        System.out.println();

        // Check for division or fraction


        // Check for addition
        boolean possible_sum = false;
        numA = 0;
        for (int i = 0; i < equationAux.size(); i++) {
            if (isNumber(equationAux.get(i)) && possible_sum){
                double numB = Double.parseDouble(equationAux.get(i));
                numA += numB;
            }else if(equationAux.get(i).equals("+")) {
                double numB = Double.parseDouble(equationAux.get(i + 1));
                numA += numB;
                i++;
            }else if(equationAux.get(i).equals("-")){
                double numB = Double.parseDouble(equationAux.get(i+1));
                numA -= numB;
                i++;
            }else if(isNumber(equationAux.get(i))){
                possible_sum = true;
                numA = Double.parseDouble(equationAux.get(i));
            }else if (possible_sum){
                operaciones.add(numA+"");
                operaciones.add(equationAux.get(i));
            }
        }
        if(numA != 0){
            operaciones.add(numA+"");
        }
        equationAux = operaciones;

        System.out.println("End");
        for (int i = 0; i < equationAux.size(); i++) {
            System.out.print(equationAux.get(i) + " . ");
        }
        System.out.println();

        for (int i = 0; i < equationAux.size(); i++) {
            finalString += equationAux.get(i);
            System.out.println(equationAux.get(i));
        }
        System.out.println("Final string: " + finalString);
        return finalString;
    }

    private boolean isNumber(String str){
        char[] numsArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        for (int i = 0; i < numsArray.length; i++) {
            if (str.charAt(0) == numsArray[i]){
                return true;
            }else if(str.length() > 1 && str.charAt(0) == '-'){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();

        formula_two = (MathView) findViewById(R.id.formula_two);
        formula_two.setText(tex);

        ecuacion = (MathView) findViewById(R.id.ecuacion);

        prueba = findViewById(R.id.textView);

        setBotones();
    }

    public void setBotones(){

        num1 = (MathView) findViewById(R.id.num1);
        num1.setText(numerosString[0]);
        num1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "1";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        num2 = (MathView) findViewById(R.id.num2);
        num2.setText(numerosString[1]);
        num2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "2";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        num3 = (MathView) findViewById(R.id.num3);
        num3.setText(numerosString[2]);
        num3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "3";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        num4 = (MathView) findViewById(R.id.num4);
        num4.setText(numerosString[3]);
        num4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "4";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        num5 = (MathView) findViewById(R.id.num5);
        num5.setText(numerosString[4]);
        num5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "5";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        num6 = (MathView) findViewById(R.id.num6);
        num6.setText(numerosString[5]);
        num6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "6";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        num7 = (MathView) findViewById(R.id.num7);
        num7.setText(numerosString[6]);
        num7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "7";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        num8 = (MathView) findViewById(R.id.num8);
        num8.setText(numerosString[7]);
        num8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "8";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        num9 = (MathView) findViewById(R.id.num9);
        num9.setText(numerosString[8]);
        num9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "9";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        num0 = (MathView) findViewById(R.id.num0);
        num0.setText(numerosString[9]);
        num0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "0";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        op1 = (MathView) findViewById(R.id.op1);
        op1.setText(operadoresString[0]);
        op1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "+";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        op2 = (MathView) findViewById(R.id.op2);
        op2.setText(operadoresString[1]);
        op2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "-";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        op3 = (MathView) findViewById(R.id.op3);
        op3.setText(operadoresString[2]);
        op3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + ".";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        op4 = (MathView) findViewById(R.id.op4);
        op4.setText(operadoresString[3]);
        op4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "\\(\\times\\)";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        op5 = (MathView) findViewById(R.id.op5);
        op5.setText(operadoresString[4]);
        op5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "/";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        op6 = (MathView) findViewById(R.id.op6);
        op6.setText(operadoresString[5]);
        op6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "\\(\\div\\)";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        op7 = (MathView) findViewById(R.id.op7);
        op7.setText(operadoresString[6]);
        op7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "(";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        op8 = (MathView) findViewById(R.id.op8);
        op8.setText(operadoresString[7]);
        op8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + ")";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        op9 = (MathView) findViewById(R.id.op9);
        op9.setText(operadoresString[8]);
        op9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "=";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        op10 = (MathView) findViewById(R.id.op10);
        op10.setText(operadoresString[9]);
        op10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + ".";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        pl1 = (MathView) findViewById(R.id.pl1);
        pl1.setText(potenciaLogaritmos[0]);
        pl1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacionString = ecuacionString + "x";
                ecuacion.setText(ecuacionString);
                return false;
            }
        });

        pl2 = (MathView) findViewById(R.id.pl2);
        pl2.setText(potenciaLogaritmos[1]);
        pl2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        pl3 = (MathView) findViewById(R.id.pl3);
        pl3.setText(potenciaLogaritmos[2]);
        pl3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        pl4 = (MathView) findViewById(R.id.pl4);
        pl4.setText(potenciaLogaritmos[3]);
        pl4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        pl5 = (MathView) findViewById(R.id.pl5);
        pl5.setText(potenciaLogaritmos[4]);
        pl5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        pl6 = (MathView) findViewById(R.id.pl6);
        pl6.setText(potenciaLogaritmos[5]);
        pl6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        pl7 = (MathView) findViewById(R.id.pl7);
        pl7.setText(potenciaLogaritmos[6]);
        pl7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        pl8 = (MathView) findViewById(R.id.pl8);
        pl8.setText(potenciaLogaritmos[7]);
        pl8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        pl9 = (MathView) findViewById(R.id.pl9);
        pl9.setText(potenciaLogaritmos[8]);
        pl9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        pl10 = (MathView) findViewById(R.id.pl10);
        pl10.setText(potenciaLogaritmos[9]);
        pl10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        rt1 = (MathView) findViewById(R.id.rt1);
        rt1.setText(recorrerTexto[0]);
        rt1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        rt2 = (MathView) findViewById(R.id.rt2);
        rt2.setText(recorrerTexto[1]);
        rt2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        rt3 = (MathView) findViewById(R.id.rt3);
        rt3.setText(recorrerTexto[2]);
        rt3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        rt4 = (MathView) findViewById(R.id.rt4);
        rt4.setText(recorrerTexto[3]);
        rt4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        rt5 = (MathView) findViewById(R.id.rt5);
        rt5.setText(recorrerTexto[4]);
        rt5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                prueba.setText(ecuacion.getText());
                return false;
            }
        });
    }

}