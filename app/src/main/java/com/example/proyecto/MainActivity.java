package com.example.proyecto;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

import io.github.kexanie.library.MathView;

public class MainActivity extends AppCompatActivity {

    MyMathView ecuacion;
    String numeros;
    MyMathView resultado;
    Ecuacion funcion;

    MathView button0,button1,button2,button3,button4,button5,button6,button7,button8,button9,buttonPunto,buttonX;
    MathView buttonDer,buttonIzq,buttonCalcular,buttonMas,buttonMenos,buttonPor,buttonDiv,buttonDelete,buttonAc;
    MathView buttonParentesis,buttonCuadrado,buttonPotencia,buttonDerivada,buttonIntegral,buttonIntegralDef;

    Button calcular;
    Button bt_integrar;
    Button polinomios;


    LinkedList<String> notacion = new LinkedList<>();
    int apuntador = 0;
    int num_parentesis = 0;
    boolean atras = false;
    int posiciones_atras = 0;
    boolean igual = false;
    boolean haveX = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        calcular = findViewById(R.id.bt_calcular);
        numeros = "";
        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String s = funcion.derivar();
                    resultado.setText(s);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        bt_integrar = findViewById(R.id.bt_integrar);
        bt_integrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s = funcion.integrar();
                    resultado.setText(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        polinomios = findViewById(R.id.bt_polinomios);
        polinomios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String s = funcion.dividePolinomios();
                    resultado.setText(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        funcion = new Ecuacion();
    }

    public void dividePolinomios(LinkedList<String> equation) throws Exception{
        LinkedList<String> polinomioA = new LinkedList<>();
        LinkedList<String> polinomioB = new LinkedList<>();
        int last_parentesis = 0;
        for (int i = 1; i < equation.size(); i++) {
            if (equation.get(i).equals(")")){
                last_parentesis = i+3;
                break;
            }else{
                polinomioA.add(equation.get(i));
            }
        }
        for (int i = last_parentesis; i<equation.size()-1;i++){
            polinomioB.add(equation.get(i));
        }
        for (int i = 0; i < polinomioA.size(); i++) {
            if (i != 0){
                if (polinomioA.get(i).equals("-")){
                    if (polinomioA.get(i+1).equals("x")){
                        polinomioA.set(i, "-1");
                    }else{
                        polinomioA.set(i, "-"+polinomioA.get(i+1));
                        polinomioA.remove(i+1);
                    }
                }else if(polinomioA.get(i).equals("x") && !isNumber(polinomioA.get(i-1))){
                    polinomioA.add(i, "1");
                    i++;
                }
            }else{
                if (polinomioA.getFirst().equals("-")){
                    if (polinomioA.get(1).equals("x")){
                        polinomioA.set(0, "-1");
                    }else{
                        polinomioA.set(0, "-"+polinomioA.get(1));
                    }
                }else if(polinomioA.getFirst().equals("x")){
                    polinomioA.add(0, "1");
                    i++;
                }
            }
        }

        for (int i = 0; i < polinomioB.size(); i++) {
            if (i != 0){
                if (polinomioB.get(i).equals("-")){
                    if (polinomioB.get(i+1).equals("x")){
                        polinomioB.set(i, "-1");
                    }else{
                        polinomioB.set(i, "-"+polinomioB.get(i+1));
                        polinomioB.remove(i+1);
                    }
                }else if(polinomioB.get(i).equals("x") && !isNumber(polinomioB.get(i-1))){
                    polinomioB.add(i, "1");
                    i++;
                }
            }else{
                if (polinomioB.getFirst().equals("-")){
                    if (polinomioB.get(1).equals("x")){
                        polinomioB.set(0, "-1");
                    }else{
                        polinomioB.set(0, "-"+polinomioB.get(1));
                    }
                }else if(polinomioB.getFirst().equals("x")){
                    polinomioB.add(0, "1");
                    i++;
                }
            }
        }

        LinkedList<Double> coeficientesA = new LinkedList<>();
        LinkedList<Double> coeficientesB = new LinkedList<>();
        for (int i = 0; i < polinomioA.size(); i++) {
            if (isNumber(polinomioA.get(i))){
                if (i-1 >= 0){
                    if (!polinomioA.get(i-1).equals("^")){
                        coeficientesA.add(Double.parseDouble(polinomioA.get(i)));
                    }
                }else{
                    coeficientesA.add(Double.parseDouble(polinomioA.get(i)));
                }
            }
        }
        for (int i = 0; i < polinomioB.size(); i++) {
            if (isNumber(polinomioB.get(i))){
                coeficientesB.add(Double.parseDouble(polinomioB.get(i)));
            }
        }
        double[] results = new double[coeficientesA.size()];
        for (int i = 0; i < results.length-1; i++) {
            results[i] = coeficientesA.get(i)/coeficientesB.get(0);
            for (int j = 0; j < coeficientesB.size(); j++) {
                coeficientesA.set(i+j, coeficientesA.get(i+j)-results[i]*coeficientesB.get(j));
            }
        }
        String result = "";
        result = results[0]+"x"+results[1];
        if (coeficientesA.getLast() != 0.0){
            result += "+\\frac{"+coeficientesA.getLast()+"}{"+coeficientesB.getFirst()+"x+"+coeficientesB.getLast()+"}";
        }
        this.resultado.setText("$$"+result+"$$");
    }

    public void muestraResultado(){
        String result = solve_arithmetic(notacion);
        if (result.equals("")){
            result = "0.0";
        }
        this.resultado.setText("Resultado: " + result);
    }

    public boolean isHaveX(LinkedList<String> equation){
        for (String num: equation){
            for (char c: num.toCharArray()){
                if (c == 'x'){
                    return true;
                }
            }
        }
        return false;
    }

    public String solve_withX(LinkedList<String> equation){
        String finalString = "Invalido." +
                "\nNo se puede calcular con x." +
                "\n Espere futuras actualizaciones.";
        return finalString;
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
        if (isHaveX(equation)){
            return solve_withX(equation);
        }
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
                        aux.add("(");
                        parentesis++;
                        i++;
                    }else if(equationAux.get(i).equals(")")){
                        if (parentesis != 1){
                            aux.add(")");
                        }
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
            if (Double.isInfinite(numA)){
                return "Infinity";
            }else if(Double.isNaN(numA)){
                return "Not defined";
            }
            if (isNumber(equationAux.get(i)) && possible_multiplication){
                double numB = Double.parseDouble(equationAux.get(i));
                numA *= numB;
            }else if(equationAux.get(i).equals("\\times")){
                if (equationAux.get(i+1).equals("-")){
                    numA *= -1;
                }else{
                    double numB = Double.parseDouble(equationAux.get(i+1));
                    numA *= numB;
                    i++;
                }
            }else if(equationAux.get(i).equals("/")){
                if (equationAux.get(i+1).equals("-")){
                    numA *= -1;
                }else{
                    double numB = Double.parseDouble(equationAux.get(i+1));
                    numA /= numB;
                    i++;
                }
            }else if(isNumber(equationAux.get(i))) {
                possible_multiplication = true;
                numA = Double.parseDouble(equationAux.get(i));
            }else if(equationAux.get(i) == "-" && isNumber(equationAux.get(i+1))) {
                equationAux.set(i, "+");
                double numC = Double.parseDouble(equationAux.get(i + 1));
                numC *= -1;
                equationAux.set(i + 1, numC + "");
                i--;
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
        if (finalString.equals("")){
            return "0.0";
        }
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
        ecuacion =  findViewById(R.id.ecuacion);
        ecuacion.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
        ecuacion.setText("$$|$$");

        resultado = findViewById(R.id.resultado);
        resultado.setHorizontalScrollBarEnabled(true);

        setBotones();
    }

    public boolean escribeNumero(String numero){
        funcion.insert(numero);
        ecuacion.setText(funcion.getEquationToShow());
        return false;
        /*
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
         */
    }

    public boolean escribeEspecial(String identifier){
        this.funcion.insertSpecial(identifier);
        this.ecuacion.setText(funcion.getEquationToShow());
        return false;
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setBotones(){
        setBotonesNumeros();
        setBotonesOperadores();
        setBotonesPotencias();
        setBotonesRt();
    }

    @SuppressLint("ClickableViewAccessibility")
    public boolean operadoresBasicos(String operador){
        funcion.insert(operador);
        this.ecuacion.setText(funcion.getEquationToShow());
        return false;
        /*
        if(atras == true){
            notacion.add(notacion.size()-posiciones_atras, operador);
            ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
            return false;
        }
        else{
            notacion.add(operador);
            ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
            return false;
        }
         */
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setBotonesNumeros(){
        button0 = findViewById(R.id.boton0);
        button0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("0");
            }
        });

        button1 = findViewById(R.id.boton1);
        button1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("1");
            }
        });

        button2 = findViewById(R.id.boton2);
        button2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("2");
            }
        });

        button3 = findViewById(R.id.boton3);
        button3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("3");
            }
        });

        button4 = findViewById(R.id.boton4);
        button4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("4");
            }
        });

        button5 = findViewById(R.id.boton5);
        button5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("5");
            }
        });

        button6 = findViewById(R.id.boton6);
        button6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("6");
            }
        });

        button7 = findViewById(R.id.boton7);
        button7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("7");
            }
        });

        button8 = findViewById(R.id.boton8);
        button8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("8");
            }
        });

        button9 = findViewById(R.id.boton9);
        button9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero("9");
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setBotonesOperadores(){
        buttonMas = findViewById(R.id.botonMas);
        buttonMas.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return operadoresBasicos("+");
            }
        });

        buttonMenos = findViewById(R.id.botonMenos);
        buttonMenos.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return operadoresBasicos("-");
            }
        });

        buttonPor = findViewById(R.id.botonPor);
        buttonPor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return operadoresBasicos("\\times");
            }
        });

        buttonDiv = findViewById(R.id.botonDiv);
        buttonDiv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("fr");
            }
        });

        buttonParentesis = findViewById(R.id.botonParentesis);
        buttonParentesis.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("pa");
            }
        });

        buttonPunto = findViewById(R.id.botonPunto);
        buttonPunto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeNumero(".");
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setBotonesPotencias(){
        buttonX = findViewById(R.id.botonX);
        buttonX.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                funcion.insert("x");
                ecuacion.setText(funcion.getEquationToShow());
                return false;
            }
        });

        buttonCuadrado = findViewById(R.id.botonCuadrado);
        buttonCuadrado.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("se");
            }
        });

        buttonPotencia = findViewById(R.id.botonPotencia);
        buttonPotencia.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("ee");
            }
        });

        buttonDerivada = findViewById(R.id.botonDerivada);
        buttonDerivada.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("de");
            }
        });

        buttonIntegral = findViewById(R.id.botonIntegral);
        buttonIntegral.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("ii");
            }
        });

        buttonIntegralDef = findViewById(R.id.botonIntegralDef);
        buttonIntegralDef.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("id");
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    public void setBotonesRt(){
        buttonIzq = findViewById(R.id.botonIzquierda);
        buttonIzq.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                funcion.cambiarPosicion(false);
                ecuacion.setText(funcion.getEquationToShow());
                return false;
            }
        });

        buttonDer = findViewById(R.id.botonDerecha);
        buttonDer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                funcion.cambiarPosicion(true);
                ecuacion.setText(funcion.getEquationToShow());
                return false;
            }
        });

        buttonDelete = findViewById(R.id.botonDEL);
        buttonDelete.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    funcion.delete();
                }catch (Exception e){
                    e.printStackTrace();
                }
                ecuacion.setText(funcion.getEquationToShow());
                return false;
            }
        });

        buttonCalcular = findViewById(R.id.botonCalcular);
        buttonCalcular.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String s = funcion.solve();
                resultado.setText(s);
                return false;
            }
        });

        buttonAc = findViewById(R.id.botonAC);
        buttonAc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ecuacion.setText("$$|$$");
                notacion = new LinkedList<String>();
                apuntador = 0;
                num_parentesis = 0;
                atras = false;
                posiciones_atras = 0;
                igual = false;
                resultado.setText("");
                haveX = false;
                funcion = new Ecuacion();
                resultado.setText(funcion.getEquationToShow());
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
