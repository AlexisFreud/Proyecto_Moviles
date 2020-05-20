package com.example.proyecto;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.LinkedList;

import io.github.kexanie.library.MathView;

public class MainActivity extends AppCompatActivity {

    MyMathView ecuacion;
    MathView num1,num2,num3,num4,num5,num6,num7,num8,num9,num0;
    MathView op1,op2,op3,op4,op5,op6,op7,op8,op9,op10;
    MathView pl1,pl2,pl3,pl4,pl5,pl6,pl7,pl8,pl9,pl10;
    MathView rt1,rt2,rt3,rt4,rt5;
    String numeros;
    MyMathView resultado;
    Ecuacion funcion;

    Button calcular;
    Button bt_factorizar;
    Button polinomios;

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
    boolean haveX = false;

    public void factorizar(LinkedList<String> equation){
        String result;
        try {
            result = factorComun(equation);
            if (!result.equals("")){
                Toast.makeText(this, "Factor comun", Toast.LENGTH_LONG).show();
                this.resultado.setText("$$"+result+"$$");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            result = binomioCuadrado(equation);
            if (!result.equals("")){
                Toast.makeText(this, "Binomio al cuadrado", Toast.LENGTH_LONG).show();
                this.resultado.setText("$$"+result+"$$");
                return;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            result = diferenciaCuadrados(equation);
            if (!result.equals("")){
                Toast.makeText(this, "Diferencia de cuadrados", Toast.LENGTH_LONG).show();
                this.resultado.setText("$$"+result+"$$");
                return;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        this.resultado.setText("$$"+"No se puede factorizar"+"$$");
    }

    public String factorComun(LinkedList<String> equation) throws Exception{
        LinkedList<String> fixed = new LinkedList<>();
        for (int i = 0; i < equation.size(); i++) {
            if (equation.get(i).equals("-")){
                if (i != 0){
                    if (!equation.get(i-1).equals("(")){
                        fixed.add("+");
                    }
                }
                fixed.add("-"+equation.get(i+1));
                i++;
            }else if(equation.get(i).equals("\\times")) {
                continue;
            }else{
                fixed.add(equation.get(i));
            }
        }
        for (int i = 0; i < fixed.size(); i++) {
            System.out.println(fixed.get(i) + " . ");
        }
        if (fixed.get(fixed.size()-2).equals("x") && isNumber(fixed.get(fixed.size()-3))) {
            String aStr = fixed.get(fixed.size()-3);
            String bStr = fixed.get(2);
            fixed.set(2, aStr);
            fixed.set(fixed.size()-3, bStr);
            fixed.remove(fixed.size()-2);
            fixed.add(3, "x");
        }
        if (!fixed.getLast().equals(")")){
            return "";
        }
        // Fixed is now of the form c(ax+b)
        double c = Double.parseDouble(fixed.getFirst());
        double a = Double.parseDouble(fixed.get(2));
        double b = Double.parseDouble(fixed.get(5));
        return c*a+"x+"+c*b;
    }

    public String binomioCuadrado(LinkedList<String> equation) throws Exception{
        LinkedList<String> fixed = new LinkedList<>();
        for (int i = 0; i < equation.size(); i++) {
            if (equation.get(i).equals("-")){
                if (i != 0){
                    fixed.add("+");
                }
                fixed.add("-"+equation.get(i+1));
                i++;
            }else if(equation.get(i).equals("\\times")){
                continue;
            }else{
                fixed.add(equation.get(i));
            }
        }
        if(fixed.getLast().equals("2") && fixed.get(fixed.size()-2).equals("^")){
            if(fixed.get(fixed.size()-6).equals("x") && fixed.get(fixed.size()-7).equals("(")) {
                String x = "x^2";
                String ab2 = (Double.parseDouble(fixed.get(fixed.size()-4)))*2+"x";
                double b = Double.parseDouble(fixed.get(fixed.size()-4));
                b = b*b;
                return x+"+"+ab2+"+"+b;
            }
            else if(fixed.get(fixed.size()-6).equals("-x") && fixed.get(fixed.size()-8).equals("(")){
                String x = "x^2";
                String ab2 = (Double.parseDouble(fixed.get(fixed.size()-4)))*2+"x";
                double b = Double.parseDouble(fixed.get(fixed.size()-4));
                b = b*b;
                return x+"+-"+ab2+"+"+b;
            }
            else if(fixed.get(fixed.size()-6).equals("x")){
                double a = Double.parseDouble(fixed.get(fixed.size()-7));
                a = a*a;
                String x = "x^2";
                String ab2 = (Double.parseDouble(fixed.get(fixed.size()-4))*Double.parseDouble(fixed.get(fixed.size()-7)))*2+"x";
                double b = Double.parseDouble(fixed.get(fixed.size()-4));
                b = b*b;
                return a+x+"+"+ab2+"+"+b;
            }
            else if(fixed.get(fixed.size()-4).equals("-x") && fixed.get(fixed.size()-2).equals("^") && ((fixed.get(fixed.size()-5).equals("+")) || fixed.get(fixed.size()-5).equals("-"))){
                String x = "x^2";
                String ab2 = (Double.parseDouble(fixed.get(fixed.size()-7)))*2+"x";
                double b = Double.parseDouble(fixed.get(fixed.size()-7));
                b = b*b;
                return x+"+-"+ab2+"+"+b;
            }
            else if(fixed.get(fixed.size()-4).equals("x") && fixed.get(fixed.size()-2).equals("^")){
                double a = Double.parseDouble(fixed.get(fixed.size()-5));
                a = a*a;
                String x = "x^2";
                String ab2 = (Double.parseDouble(fixed.get(fixed.size()-5))*Double.parseDouble(fixed.get(fixed.size()-7)))*2+"x";
                double b = Double.parseDouble(fixed.get(fixed.size()-7));
                b = b*b;
                return a+x+"+"+ab2+"+"+b;
            }

        }
        return "";
    }

    public String diferenciaCuadrados(LinkedList<String> equation) throws Exception{
        LinkedList<String> fixed = new LinkedList<>();
        for (int i = 0; i < equation.size(); i++) {
            if (equation.get(i).equals("-")){
                if (i != 0){
                    fixed.add("+");
                }
                fixed.add("-"+equation.get(i+1));
                i++;
            }else if(equation.get(i).equals("\\times")){
                continue;
            }else{
                fixed.add(equation.get(i));
            }
        }
        if(fixed.getLast().equals("2") && fixed.get(fixed.size() - 2).equals("^") && fixed.get(fixed.size()-3).equals("x") && Double.parseDouble(fixed.get(fixed.size()-4)) < 0){
            Toast.makeText(this, "P", Toast.LENGTH_SHORT).show();
            double b = Math.abs(Double.parseDouble(fixed.get(fixed.size()-4)));
            double a = Double.parseDouble(fixed.get(fixed.size()-6));
            if(cuadrado(a,b)){
                return "("+Math.sqrt(a)+"-"+Math.sqrt(b)+"x) ("+Math.sqrt(a)+"+"+Math.sqrt(b)+"x)";
            }
            else{
                return "";
            }
        }
        else if(fixed.get(fixed.size()-3).equals("2") && fixed.get(fixed.size()-4).equals("^") && fixed.get(fixed.size()-4).equals("x") && Double.parseDouble(fixed.getLast()) < 0){
            double b = Math.abs(Double.parseDouble(fixed.getLast()));
            double a = Double.parseDouble(fixed.getFirst());
            if(cuadrado(a,b)){
                String s = "(" + Math.sqrt(a) + "x -" + Math.sqrt(b) + ") (" + Math.sqrt(a) + "x +" + Math.sqrt(b) + ")";
                return s;
            }
            else{
                return "";
            }
        }
        else if(Double.parseDouble(fixed.getFirst()) < 0 && fixed.getLast().equals("2") && fixed.get(fixed.size()-2).equals("^") && fixed.get(fixed.size()-3).equals("x") && fixed.get(fixed.size()-5).equals("+")){
            double b = Math.abs(Double.parseDouble(fixed.getFirst()));
            double a = Double.parseDouble(fixed.get(fixed.size()-4));
            if(cuadrado(a,b)){
                String s = "(" + Math.sqrt(a) + "x -" + Math.sqrt(b) + ") (" + Math.sqrt(a) + "x +" + Math.sqrt(b) + ")";
                return s;
            }
        }
        else if(Double.parseDouble(fixed.getFirst()) < 0 && fixed.get(fixed.size()-2).equals("+") && fixed.get(fixed.size()-3).equals("2") && fixed.get(fixed.size()-4).equals("^") && fixed.get(fixed.size()-5).equals("x")){
            double a = Double.parseDouble(fixed.getLast());
            double b = Math.abs(Double.parseDouble(fixed.getFirst()));
            if(cuadrado(a,b)){
                String s = "(" + Math.sqrt(a) + " -" + Math.sqrt(b) + "x ) (" + Math.sqrt(a) + " +" + Math.sqrt(b) + "x )";
                return s;
            }
        }
        return "";
    }

    public boolean cuadrado(double a, double b){
        if(Math.floor(Math.sqrt(b)) == Math.sqrt(b) && Math.floor(Math.sqrt(a)) == Math.sqrt(a)){
            return true;
        }
        return false;
    }

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
        bt_factorizar = findViewById(R.id.bt_factorizar);
        bt_factorizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                funcion.factorizar();
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
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setBotonesOperadores(){
        op1 = findViewById(R.id.op1);
        op1.setText(operadoresString[0]);
        op1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return operadoresBasicos("+");
            }
        });

        op2 = findViewById(R.id.op2);
        op2.setText(operadoresString[1]);
        op2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return operadoresBasicos("-");
            }
        });

        op3 = findViewById(R.id.op3);
        op3.setText(operadoresString[2]);
        op3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return operadoresBasicos("\\times");
            }
        });

        op4 = findViewById(R.id.op4);
        op4.setText(operadoresString[3]);
        op4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return operadoresBasicos("\\times");
            }
        });

        op5 = findViewById(R.id.op5);
        op5.setText(operadoresString[4]);
        op5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("fr");
            }
        });

        op6 = findViewById(R.id.op6);
        op6.setText(operadoresString[5]);
        op6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("fr");
            }
        });

        op7 = findViewById(R.id.op7);
        op7.setText(operadoresString[6]);
        op7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                //num_parentesis++;
                return escribeEspecial("pa");
            }
        });

        op8 = findViewById(R.id.op8);
        op8.setText(operadoresString[7]);
        op8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("pa");
                /*
                if(num_parentesis != 0){
                    num_parentesis--;
                    return operadoresBasicos(")");
                }
                return false;
                 */
            }
        });

        op9 = findViewById(R.id.op9);
        op9.setText(operadoresString[8]);
        op9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(igual != true){
                    igual = true;
                    return operadoresBasicos("=");
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
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setBotonesPotencias(){
        pl1 =  findViewById(R.id.pl1);
        pl1.setText(potenciaLogaritmos[0]);
        pl1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                funcion.insert("x");
                ecuacion.setText(funcion.getEquationToShow());
                return false;
                /*
                haveX = true;
                if(atras){
                    notacion.add(notacion.size()-posiciones_atras, "x");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("x");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
                 */
            }
        });

        pl2 =  findViewById(R.id.pl2);
        pl2.setText(potenciaLogaritmos[1]);
        pl2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("se");
                /*
                if(atras){
                    notacion.add(notacion.size()-posiciones_atras, "\\square");
                    notacion.add(notacion.size()-posiciones_atras, "^");
                    notacion.add(notacion.size()-posiciones_atras, "2");
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\square");
                    notacion.add("^");
                    notacion.add("2");
                    posiciones_atras++;
                    posiciones_atras++;
                    posiciones_atras++;
                    ecuacion.setText("$$" + getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    atras = true;
                    return false;
                }
                 */
            }
        });

        pl3 =  findViewById(R.id.pl3);
        pl3.setText(potenciaLogaritmos[2]);
        pl3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("ee");
                /*
                if(atras){
                    notacion.add(notacion.size()-posiciones_atras, "\\square");
                    notacion.add(notacion.size()-posiciones_atras, "^");
                    notacion.add(notacion.size()-posiciones_atras, "\\square");
                }
                else{
                    notacion.add("\\square");
                    notacion.add("^");
                    notacion.add("\\square");
                    atras = true;
                }
                posiciones_atras++;
                posiciones_atras++;
                posiciones_atras++;
                ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                return false;
                 */
            }
        });

        pl4 =  findViewById(R.id.pl4);
        pl4.setText(potenciaLogaritmos[3]);
        pl4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("fr");
                /*
                if(atras){
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
                 */
            }
        });

        pl5 =  findViewById(R.id.pl5);
        pl5.setText(potenciaLogaritmos[4]);
        pl5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("sr");
                /*
                if(atras){
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
                 */
            }
        });

        pl6 =  findViewById(R.id.pl6);
        pl6.setText(potenciaLogaritmos[5]);
        pl6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("ro");
                /*
                if(atras){
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
                 */
            }
        });

        pl7 =  findViewById(R.id.pl7);
        pl7.setText(potenciaLogaritmos[6]);
        pl7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("pa");
                /*
                if(atras){
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
                 */
            }
        });

        pl8 =  findViewById(R.id.pl8);
        pl8.setText(potenciaLogaritmos[7]);
        pl8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("de");
                /*
                if(atras){
                    notacion.add(notacion.size()-posiciones_atras, "\\frac{d}{dx}");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\frac{d}{dx}");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
                 */
            }
        });

        pl9 =  findViewById(R.id.pl9);
        pl9.setText(potenciaLogaritmos[8]);
        pl9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("ii");
                /*
                 if(atras){
                    notacion.add(notacion.size()-posiciones_atras, "\\smallint");
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                    return false;
                }
                else{
                    notacion.add("\\smallint");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    return false;
                }
                 */
            }
        });

        pl10 =  findViewById(R.id.pl10);
        pl10.setText(potenciaLogaritmos[9]);
        pl10.setOnTouchListener(new View.OnTouchListener() {
            // \\int_{\\square}^{\\square}
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return escribeEspecial("id");
                /*
                if(atras){
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
                 */
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    public void setBotonesRt(){
        rt1 =  findViewById(R.id.rt1);
        rt1.setText(recorrerTexto[0]);
        rt1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                funcion.cambiarPosicion(false);
                ecuacion.setText(funcion.getEquationToShow());
                /*
                if(posiciones_atras != notacion.size()){
                    posiciones_atras++;
                    atras = true;
                    ecuacion.setText("$$"+ getEcuacion(notacion, notacion.size()-posiciones_atras) + "$$");
                }
                 */
                return false;
            }
        });

        rt2 =  findViewById(R.id.rt2);
        rt2.setText(recorrerTexto[1]);
        rt2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                funcion.cambiarPosicion(true);
                ecuacion.setText(funcion.getEquationToShow());
                /*
                if(posiciones_atras != 0 && atras == true){
                    if(notacion.size()-posiciones_atras > 0){
                        posiciones_atras--;
                        apuntador = notacion.size()-posiciones_atras;
                        ecuacion.setText("$$"+ getEcuacion(notacion, apuntador) + "$$");
                    }
                }
                if(posiciones_atras == 0){
                    atras = false;
                }
                 */
                return false;
            }
        });

        rt3 =  findViewById(R.id.rt3);
        rt3.setText(recorrerTexto[2]);
        rt3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                try {
                    funcion.delete();
                }catch (Exception e){
                    e.printStackTrace();
                }
                ecuacion.setText(funcion.getEquationToShow());
                return false;
                /*
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

                 */
            }
        });

        //BOTON DE EVALUAR
        rt4 =  findViewById(R.id.rt4);
        rt4.setText(recorrerTexto[3]);
        rt4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String s = funcion.solve();
                resultado.setText(s);
                //Toast.makeText(getApplicationContext(), "Tosta", Toast.LENGTH_SHORT).show();
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
