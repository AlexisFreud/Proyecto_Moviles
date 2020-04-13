package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import io.github.kexanie.library.MathView;

public class MainActivity extends AppCompatActivity {
    
    MathView formula_two, ecuacion;
    MathView num1,num2,num3,num4,num5,num6,num7,num8,num9,num0;
    MathView op1,op2,op3,op4,op5,op6,op7,op8,op9,op10;
    MathView pl1,pl2,pl3,pl4,pl5,pl6,pl7,pl8,pl9,pl10;
    MathView rt1,rt2,rt3,rt4,rt5;

    TextView prueba;

    String tex = "This come from string. You can insert inline formula:" +
            " \\([ ]^2 + bx + c = 0\\) " +
            "or displayed formula: $$\\sum_{i=0}^n i^2 = \\frac{[]}{[]}$$";

    String numerosString[] = {"\\(\\space\\space1\\)","\\(\\space\\space2\\)","\\(\\space\\space3\\)","\\(\\space\\space4\\)","\\(\\space\\space5\\)","\\(\\space\\space6\\)"
            ,"\\(\\space\\space7\\)","\\(\\space\\space8\\)","\\(\\space\\space9\\)","\\(\\space\\space0\\)"};

    String operadoresString[] = {"\\(\\space+\\)","\\(\\space-\\)","\\(\\space\\space\\centerdot\\)","\\(\\space\\times\\)","\\(\\space\\space/\\)","\\(\\space\\div\\)"
            ,"\\(\\space\\space(\\)","\\(\\space\\space)\\)","\\(\\space=\\)","\\(\\space\\space\\: .\\)"};

    String potenciaLogaritmos[] = {"\\(\\space\\space x\\)","\\(\\space\\square^2\\)","\\(\\space\\square^{\\square}\\)","\\(\\space\\: \\frac{\\square}{\\square}\\)",
            "\\(\\sqrt{[]}\\)","\\(\\sqrt[\\square]{[]}\\)","\\((\\square)\\)","\\(\\space\\:\\frac{d}{dx}\\)","\\(\\space\\:\\smallint\\)","\\(\\:\\int_{\\square}^{\\square}\\)"};

    String recorrerTexto[] = {"\\(\\space\\space\\Leftarrow\\)","\\(\\space\\space\\Rightarrow\\)","\\(\\space\\space\\lhd\\)","\\(\\space\\space=\\)","\\(\\space\\space CE\\)"};

    String ecuacionString = "";

    LinkedList<String> notacion = new LinkedList<String>();
    int apuntador = 0;
    int tamano = 0;
    int num_parentesis = 0;
    boolean atras = false;
    int posiciones_atras = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                notacion.add(apuntador,"1");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        num2 = (MathView) findViewById(R.id.num2);
        num2.setText(numerosString[1]);
        num2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("2");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        num3 = (MathView) findViewById(R.id.num3);
        num3.setText(numerosString[2]);
        num3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("3");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        num4 = (MathView) findViewById(R.id.num4);
        num4.setText(numerosString[3]);
        num4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("4");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        num5 = (MathView) findViewById(R.id.num5);
        num5.setText(numerosString[4]);
        num5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("5");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        num6 = (MathView) findViewById(R.id.num6);
        num6.setText(numerosString[5]);
        num6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("6");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        num7 = (MathView) findViewById(R.id.num7);
        num7.setText(numerosString[6]);
        num7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("7");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        num8 = (MathView) findViewById(R.id.num8);
        num8.setText(numerosString[7]);
        num8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("8");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        num9 = (MathView) findViewById(R.id.num9);
        num9.setText(numerosString[8]);
        num9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("9");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        num0 = (MathView) findViewById(R.id.num0);
        num0.setText(numerosString[9]);
        num0.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("0");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        op1 = (MathView) findViewById(R.id.op1);
        op1.setText(operadoresString[0]);
        op1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("+");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        op2 = (MathView) findViewById(R.id.op2);
        op2.setText(operadoresString[1]);
        op2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("-");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        op3 = (MathView) findViewById(R.id.op3);
        op3.setText(operadoresString[2]);
        op3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("\\times");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        op4 = (MathView) findViewById(R.id.op4);
        op4.setText(operadoresString[3]);
        op4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("\\times");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        op5 = (MathView) findViewById(R.id.op5);
        op5.setText(operadoresString[4]);
        op5.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("/");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        op6 = (MathView) findViewById(R.id.op6);
        op6.setText(operadoresString[5]);
        op6.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("\\div");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        op7 = (MathView) findViewById(R.id.op7);
        op7.setText(operadoresString[6]);
        op7.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("(");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                num_parentesis++;
                return false;
            }
        });

        op8 = (MathView) findViewById(R.id.op8);
        op8.setText(operadoresString[7]);
        op8.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(num_parentesis != 0){
                    notacion.add(")");
                    ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                    num_parentesis--;
                }
                return false;
            }
        });

        op9 = (MathView) findViewById(R.id.op9);
        op9.setText(operadoresString[8]);
        op9.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("=");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        op10 = (MathView) findViewById(R.id.op10);
        op10.setText(operadoresString[9]);
        op10.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add(".");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        pl1 = (MathView) findViewById(R.id.pl1);
        pl1.setText(potenciaLogaritmos[0]);
        pl1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add("x");
                ecuacion.setText("$$" + getEcuacion(notacion) + "|$$");
                return false;
            }
        });

        pl2 = (MathView) findViewById(R.id.pl2);
        pl2.setText(potenciaLogaritmos[1]);
        pl2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                notacion.add(" ");
                ecuacion.setText("$$" + getEcuacion(notacion) + "\\square^2$$");
                apuntador = notacion.size();
                notacion.add(apuntador, "PEDOS");
                notacion.add("^2");
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
                if(posiciones_atras != notacion.size()){
                    posiciones_atras++;
                    atras = true;
                    apuntador = notacion.size()-posiciones_atras;
                    ecuacion.setText("$$"+ getEcuacion(notacion, apuntador) + "$$");
                }
                return false;
            }
        });

        rt2 = (MathView) findViewById(R.id.rt2);
        rt2.setText(recorrerTexto[1]);
        rt2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(posiciones_atras != 0 && atras == true){
                    posiciones_atras--;
                    apuntador = notacion.size()-posiciones_atras;
                    ecuacion.setText("$$"+ getEcuacion(notacion, apuntador) + "$$");
                }
                if(posiciones_atras == 0){
                    atras = false;
                }
                return false;
            }
        });

        rt3 = (MathView) findViewById(R.id.rt3);
        rt3.setText(recorrerTexto[2]);
        rt3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(notacion.size() != 0 && posiciones_atras != notacion.size()){
                    if(atras == false){
                        notacion.removeLast();
                        ecuacion.setText("$$"+ getEcuacion(notacion) + "|$$");
                    }
                    else{
                        notacion.remove(apuntador-1);
                        apuntador--;
                        ecuacion.setText("$$"+ getEcuacion(notacion, apuntador) + "$$");
                    }
                }
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