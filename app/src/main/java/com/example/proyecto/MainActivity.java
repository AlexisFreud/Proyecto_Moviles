package com.example.proyecto;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
                String s;
                try {
                    s = funcion.solve();
                }catch (ArithmeticException e){
                    e.printStackTrace();
                    s = "division by zero is not allowed to mortals yet";
                }
                resultado.setText(s);
                return false;
            }
        });

        buttonAc = findViewById(R.id.botonAC);
        buttonAc.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                funcion = new Ecuacion();
                resultado.setText(funcion.getEquationToShow());
                funcion.cambiarPosicion(true);
                return false;
            }
        });
    }

}
