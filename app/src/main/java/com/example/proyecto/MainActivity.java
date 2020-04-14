package com.example.proyecto;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

import io.github.kexanie.library.MathView;

public class MainActivity extends AppCompatActivity {
    
    MathView formula_two, ecuacion;
    MathView num1,num2,num3,num4,num5,num6,num7,num8,num9,num0;
    MathView op1,op2,op3,op4,op5,op6,op7,op8,op9,op10;
    MathView pl1,pl2,pl3,pl4,pl5,pl6,pl7,pl8,pl9,pl10;
    MathView rt1,rt2,rt3,rt4,rt5;
    String numeros;

    TextView prueba;

    Button calcular;

    String tex = "$$(3+(2\\times10))10-30+-100$$";

    String numerosString[] = {"\\(\\space\\space1\\)","\\(\\space\\space2\\)","\\(\\space\\space3\\)","\\(\\space\\space4\\)","\\(\\space\\space5\\)","\\(\\space\\space6\\)"
            ,"\\(\\space\\space7\\)","\\(\\space\\space8\\)","\\(\\space\\space9\\)","\\(\\space\\space0\\)"};

    String operadoresString[] = {"\\(\\space+\\)","\\(\\space-\\)","\\(\\space\\space\\centerdot\\)","\\(\\space\\times\\)","\\(\\space\\space/\\)","\\(\\space\\div\\)"
            ,"\\(\\space\\space(\\)","\\(\\space\\space)\\)","\\(\\space=\\)","\\(\\space\\space\\: .\\)"};

    String potenciaLogaritmos[] = {"\\(\\space\\space x\\)","\\(\\space\\square^2\\)","\\(\\space\\square^{\\square}\\)","\\(\\space\\: \\frac{\\square}{\\square}\\)",
            "\\(\\sqrt{[]}\\)","\\(\\sqrt[\\square]{[]}\\)","\\((\\square)\\)","\\(\\space\\:\\frac{d}{dx}\\)","\\(\\space\\:\\smallint\\)","\\(\\:\\int_{\\square}^{\\square}\\)"};

    String recorrerTexto[] = {"\\(\\space\\space\\Leftarrow\\)","\\(\\space\\space\\Rightarrow\\)","\\(\\space\\space\\lhd\\)","\\(\\space\\space=\\)","\\(\\space\\space CE\\)"};

    LinkedList<String> notacion = new LinkedList<>();
    int apuntador = 0;
    int num_parentesis = 0;
    boolean atras = false;
    int posiciones_atras = 0;
    boolean igual = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calcular = findViewById(R.id.bt_calcular);
        numeros = "";
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                muestraResultado();
            }
        });
    }

    public void muestraResultado(){
        /*String subTex = tex.replace("$", "");
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
        newTex.add("-100");*/
        String result = solve_arithmetic(notacion);
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

        formula_two = findViewById(R.id.formula_two);
        formula_two.setText(tex);

        ecuacion =  findViewById(R.id.ecuacion);
        ecuacion.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        ecuacion.setText("$$|$$");

        prueba = findViewById(R.id.textView);

        setBotones();
    }

    public boolean escribeNumero(String numero){
        if(atras == true){
            String num = notacion.get(notacion.size()-posiciones_atras);
            if (isNumber(num)){
                notacion.set(notacion.size()-posiciones_atras, num+numero);
            }else{
                notacion.add(notacion.size()-posiciones_atras, numero);
            }
            ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
            return false;
        }
        else{
            if (!notacion.isEmpty()){
                String num = notacion.getLast();
                if(isNumber(num)){
                    notacion.set(notacion.size()-1, num+numero);
                }else{
                    notacion.add(numero);
                }
            }else{
                notacion.add(numero);
            }
            ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
            return false;
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setBotones(){

        num1 = findViewById(R.id.num1);
        num1.setText(numerosString[0]);
        num1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("1");
            }
        });

        num2 = findViewById(R.id.num2);
        num2.setText(numerosString[1]);
        num2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("2");
            }
        });

        num3 = findViewById(R.id.num3);
        num3.setText(numerosString[2]);
        num3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("3");
            }
        });

        num4 = findViewById(R.id.num4);
        num4.setText(numerosString[3]);
        num4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("4");
            }
        });

        num5 = findViewById(R.id.num5);
        num5.setText(numerosString[4]);
        num5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("5");
            }
        });

        num6 = findViewById(R.id.num6);
        num6.setText(numerosString[5]);
        num6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("6");
            }
        });

        num7 = findViewById(R.id.num7);
        num7.setText(numerosString[6]);
        num7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("7");
            }
        });

        num8 = findViewById(R.id.num8);
        num8.setText(numerosString[7]);
        num8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("8");
            }
        });

        num9 = findViewById(R.id.num9);
        num9.setText(numerosString[8]);
        num9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("9");
            }
        });

        num0 = findViewById(R.id.num0);
        num0.setText(numerosString[9]);
        num0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("0");
            }
        });

        op1 = findViewById(R.id.op1);
        op1.setText(operadoresString[0]);
        op1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "+");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("+");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
            }
        });

        op2 = findViewById(R.id.op2);
        op2.setText(operadoresString[1]);
        op2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "-");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("-");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
            }
        });

        op3 = findViewById(R.id.op3);
        op3.setText(operadoresString[2]);
        op3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "\\times");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\times");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
            }
        });

        op4 = findViewById(R.id.op4);
        op4.setText(operadoresString[3]);
        op4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "\\times");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\times");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
            }
        });

        op5 = findViewById(R.id.op5);
        op5.setText(operadoresString[4]);
        op5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "/");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("/");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
            }
        });

        op6 = findViewById(R.id.op6);
        op6.setText(operadoresString[5]);
        op6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "\\div");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\div");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
            }
        });

        op7 = findViewById(R.id.op7);
        op7.setText(operadoresString[6]);
        op7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                num_parentesis++;
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "(");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("(");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
            }
        });

        op8 = findViewById(R.id.op8);
        op8.setText(operadoresString[7]);
        op8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(num_parentesis != 0){
                    num_parentesis--;
                    if(atras == true){
                        notacion.add(notacion.size()-posiciones_atras, ")");
                        ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                        return false;
                    }
                    else{
                        notacion.add(")");
                        ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                        return false;
                    }
                }
                return false;
            }
        });

        op9 = findViewById(R.id.op9);
        op9.setText(operadoresString[8]);
        op9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(igual != true){
                    igual = true;
                    if(atras == true){
                        notacion.add(notacion.size()-posiciones_atras, "=");
                        ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                        return false;
                    }
                    else{
                        notacion.add("=");
                        ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                        return false;
                    }
                }
                return  false;
            }
        });

        op10 = findViewById(R.id.op10);
        op10.setText(operadoresString[9]);
        op10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero(".");
            }
        });

        pl1 =  findViewById(R.id.pl1);
        pl1.setText(potenciaLogaritmos[0]);
        pl1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "x");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("x");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
            }
        });

        pl2 =  findViewById(R.id.pl2);
        pl2.setText(potenciaLogaritmos[1]);
        pl2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "\\square");
                    notacion.add(notacion.size()-posiciones_atras, "^2");
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\square");
                    notacion.add("^2");
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$" + getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    atras = true;
                    return false;
                }
            }
        });

        pl3 =  findViewById(R.id.pl3);
        pl3.setText(potenciaLogaritmos[2]);
        pl3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "\\square");
                    notacion.add(notacion.size()-posiciones_atras, "^\\square");
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\square");
                    notacion.add("^\\square");
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$" + getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    atras = true;
                    return false;
                }
            }
        });

        pl4 =  findViewById(R.id.pl4);
        pl4.setText(potenciaLogaritmos[3]);
        pl4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "\\frac{");
                    notacion.add(notacion.size()-posiciones_atras, "\\square");
                    notacion.add(notacion.size()-posiciones_atras, "}{");
                    notacion.add(notacion.size()-posiciones_atras, "\\square");
                    notacion.add(notacion.size()-posiciones_atras, "}");
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\frac{");
                    notacion.add("\\square");
                    notacion.add("}{");
                    notacion.add("\\square");
                    notacion.add("}");
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$" + getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    atras = true;
                    return false;
                }
            }
        });

        pl5 =  findViewById(R.id.pl5);
        pl5.setText(potenciaLogaritmos[4]);
        pl5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "\\sqrt{");
                    notacion.add(notacion.size()-posiciones_atras, "[]");
                    notacion.add(notacion.size()-posiciones_atras, "}");
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\sqrt{");
                    notacion.add("[]");
                    notacion.add("}");
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$" + getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    atras = true;
                    return false;
                }
            }
        });

        pl6 =  findViewById(R.id.pl6);
        pl6.setText(potenciaLogaritmos[5]);
        pl6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "\\sqrt[");
                    notacion.add(notacion.size()-posiciones_atras, "\\square");
                    notacion.add(notacion.size()-posiciones_atras, "]{");
                    notacion.add(notacion.size()-posiciones_atras, "[]");
                    notacion.add(notacion.size()-posiciones_atras, "}");
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\sqrt[");
                    notacion.add("\\square");
                    notacion.add("]{");
                    notacion.add("[]");
                    notacion.add("}");
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$" + getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    atras = true;
                    return false;
                }
            }
        });

        pl7 =  findViewById(R.id.pl7);
        pl7.setText(potenciaLogaritmos[6]);
        pl7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "(");
                    notacion.add(notacion.size()-posiciones_atras, "\\square");
                    notacion.add(notacion.size()-posiciones_atras, ")");
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("(");
                    notacion.add("\\square");
                    notacion.add(")");
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$" + getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    atras = true;
                    return false;
                }
            }
        });

        pl8 =  findViewById(R.id.pl8);
        pl8.setText(potenciaLogaritmos[7]);
        pl8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "\\frac{d}{dx}");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\frac{d}{dx}");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
            }
        });

        pl9 =  findViewById(R.id.pl9);
        pl9.setText(potenciaLogaritmos[8]);
        pl9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "\\smallint");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\smallint");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
            }
        });

        pl10 =  findViewById(R.id.pl10);
        pl10.setText(potenciaLogaritmos[9]);
        pl10.setOnTouchListener(new View.OnTouchListener() {
            // \\int_{\\square}^{\\square}
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(atras == true){
                    notacion.add(notacion.size()-posiciones_atras, "\\int_{");
                    notacion.add(notacion.size()-posiciones_atras, "\\square");
                    notacion.add(notacion.size()-posiciones_atras, "}^{");
                    notacion.add(notacion.size()-posiciones_atras, "\\square");
                    notacion.add(notacion.size()-posiciones_atras, "}");
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\int_{");
                    notacion.add("\\square");
                    notacion.add("}^{");
                    notacion.add("\\square");
                    notacion.add("}");
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$" + getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    atras = true;
                    return false;
                }
            }
        });

        rt1 =  findViewById(R.id.rt1);
        rt1.setText(recorrerTexto[0]);
        rt1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(posiciones_atras != notacion.size()){
                    posiciones_atras++;
                    atras = true;
                    apuntador = notacion.size()-posiciones_atras;
                    if(apuntador+1 < notacion.size()){
                        if(notacion.get(apuntador+1).equals("^2")){
                            notacion.set(apuntador+1, "2");
                        }
                        else{
                            ecuacion.setText("$$"+ getEcuacion(notacion, apuntador) + "$$");
                        }
                    }
                    else{
                        ecuacion.setText("$$"+ getEcuacion(notacion, apuntador) + "$$");
                    }
                }
                return false;
            }
        });

        rt2 =  findViewById(R.id.rt2);
        rt2.setText(recorrerTexto[1]);
        rt2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(posiciones_atras != 0 && atras == true){
                    if(notacion.size()-posiciones_atras > 0){
                        if(notacion.get(notacion.size()-posiciones_atras).compareTo("^2") == 0 && notacion.get(notacion.size()-posiciones_atras-1).compareTo("^2") == 0){
                            notacion.set(notacion.size()-posiciones_atras-1, "2");
                            posiciones_atras--;
                            apuntador = notacion.size()-posiciones_atras;
                            ecuacion.setText("$$"+ getEcuacion(notacion, apuntador) + "$$");
                        }
                        else{
                            posiciones_atras--;
                            apuntador = notacion.size()-posiciones_atras;
                            ecuacion.setText("$$"+ getEcuacion(notacion, apuntador) + "$$");
                        }
                    }
                    else{
                        posiciones_atras--;
                        apuntador = notacion.size()-posiciones_atras;
                        ecuacion.setText("$$"+ getEcuacion(notacion, apuntador) + "$$");
                    }
                }
                if(posiciones_atras == 0){
                    atras = false;
                }
                return false;
            }
        });

        rt3 =  findViewById(R.id.rt3);
        rt3.setText(recorrerTexto[2]);
        rt3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String aux;
                if(notacion.size() != 0 && posiciones_atras != notacion.size()){
                    if(atras == false){
                        aux = notacion.removeLast();
                        if(aux.compareTo(")") == 0){
                            num_parentesis++;
                        }
                        if(aux.compareTo("=") == 0){
                            igual = false;
                        }
                        if(aux.compareTo("^2") == 0){
                            if(!notacion.isEmpty()){
                                if(notacion.getLast().compareTo("\\square") == 0){
                                    notacion.removeLast();
                                }
                            }
                        }
                        if(aux.compareTo("^\\square") == 0){
                            if(!notacion.isEmpty()){
                                if(notacion.getLast().compareTo("\\square") == 0){
                                    notacion.removeLast();
                                }
                            }
                        }
                        ecuacion.setText("$$"+ getEcuacion(notacion) + "|$$");
                    }
                    else{
                        aux = notacion.remove(notacion.size()-posiciones_atras-1);
                        if(aux.compareTo(")") == 0){
                            num_parentesis++;
                        }
                        if(aux.compareTo("=") == 0){
                            igual = false;
                        }
                        if(aux.compareTo("^2") == 0){
                            if(!notacion.isEmpty()){
                                if(notacion.get(notacion.size()-posiciones_atras-1).compareTo("\\square") == 0){
                                    notacion.remove(notacion.size()-posiciones_atras-1);
                                }
                            }
                        }
                        if(aux.compareTo("^\\square") == 0){
                            if(!notacion.isEmpty()){
                                if(notacion.get(notacion.size()-posiciones_atras-1).compareTo("\\square") == 0){
                                    notacion.remove(notacion.size()-posiciones_atras-1);
                                }
                            }
                        }
                        ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    }
                }
                return false;
            }
        });

        //BOTON DE EVALUAR
        rt4 =  findViewById(R.id.rt4);
        rt4.setText(recorrerTexto[3]);
        rt4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        rt5 =  findViewById(R.id.rt5);
        rt5.setText(recorrerTexto[4]);
        rt5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacion.setText("$$|$$");
                notacion = new LinkedList<String>();
                apuntador = 0;
                num_parentesis = 0;
                atras = false;
                posiciones_atras = 0;
                igual = false;
                return false;
            }
        });
    }

    public String getEcuacion(LinkedList<String> notacion){
        String ecuacion = "";
        for(int i = 0; i < notacion.size(); i++){
            ecuacion = ecuacion + notacion.get(i);
        }
        return  ecuacion;
    }

    public String getEcuacion(LinkedList<String> notacion,int apuntador){
        String ecuacion = "";
        int i = 0;
        while(i < apuntador){
            ecuacion = ecuacion + notacion.get(i);
            i++;
        }
        ecuacion = ecuacion + "|";
        while(apuntador < notacion.size()){
            ecuacion = ecuacion + notacion.get(apuntador);
            apuntador++;
        }
        return  ecuacion;
    }

}