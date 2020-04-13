package com.example.proyecto;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;

import io.github.kexanie.library.MathView;

public class MainActivity extends AppCompatActivity {
    
    MathView formula_two;
    MathView num1,num2,num3,num4,num5,num6,num7,num8,num9,num0;
    MathView op1,op2,op3,op4,op5,op6,op7,op8,op9,op10;
    MathView pl1,pl2,pl3,pl4,pl5,pl6,pl7,pl8,pl9,pl10;
    MathView rt1,rt2,rt3,rt4,rt5;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onResume() {
        super.onResume();

        formula_two = (MathView) findViewById(R.id.formula_two);
        formula_two.setText(tex);

        num1 = (MathView) findViewById(R.id.num1);
        num1.setText(numerosString[0]);
        num2 = (MathView) findViewById(R.id.num2);
        num2.setText(numerosString[1]);
        num3 = (MathView) findViewById(R.id.num3);
        num3.setText(numerosString[2]);
        num4 = (MathView) findViewById(R.id.num4);
        num4.setText(numerosString[3]);
        num5 = (MathView) findViewById(R.id.num5);
        num5.setText(numerosString[4]);
        num6 = (MathView) findViewById(R.id.num6);
        num6.setText(numerosString[5]);
        num7 = (MathView) findViewById(R.id.num7);
        num7.setText(numerosString[6]);
        num8 = (MathView) findViewById(R.id.num8);
        num8.setText(numerosString[7]);
        num9 = (MathView) findViewById(R.id.num9);
        num9.setText(numerosString[8]);
        num0 = (MathView) findViewById(R.id.num0);
        num0.setText(numerosString[9]);

        op1 = (MathView) findViewById(R.id.op1);
        op1.setText(operadoresString[0]);
        op2 = (MathView) findViewById(R.id.op2);
        op2.setText(operadoresString[1]);
        op3 = (MathView) findViewById(R.id.op3);
        op3.setText(operadoresString[2]);
        op4 = (MathView) findViewById(R.id.op4);
        op4.setText(operadoresString[3]);
        op5 = (MathView) findViewById(R.id.op5);
        op5.setText(operadoresString[4]);
        op6 = (MathView) findViewById(R.id.op6);
        op6.setText(operadoresString[5]);
        op7 = (MathView) findViewById(R.id.op7);
        op7.setText(operadoresString[6]);
        op8 = (MathView) findViewById(R.id.op8);
        op8.setText(operadoresString[7]);
        op9 = (MathView) findViewById(R.id.op9);
        op9.setText(operadoresString[8]);
        op10 = (MathView) findViewById(R.id.op10);
        op10.setText(operadoresString[9]);

        pl1 = (MathView) findViewById(R.id.pl1);
        pl1.setText(potenciaLogaritmos[0]);
        pl2 = (MathView) findViewById(R.id.pl2);
        pl2.setText(potenciaLogaritmos[1]);
        pl3 = (MathView) findViewById(R.id.pl3);
        pl3.setText(potenciaLogaritmos[2]);
        pl4 = (MathView) findViewById(R.id.pl4);
        pl4.setText(potenciaLogaritmos[3]);
        pl5 = (MathView) findViewById(R.id.pl5);
        pl5.setText(potenciaLogaritmos[4]);
        pl6 = (MathView) findViewById(R.id.pl6);
        pl6.setText(potenciaLogaritmos[5]);
        pl7 = (MathView) findViewById(R.id.pl7);
        pl7.setText(potenciaLogaritmos[6]);
        pl8 = (MathView) findViewById(R.id.pl8);
        pl8.setText(potenciaLogaritmos[7]);
        pl9 = (MathView) findViewById(R.id.pl9);
        pl9.setText(potenciaLogaritmos[8]);
        pl10 = (MathView) findViewById(R.id.pl10);
        pl10.setText(potenciaLogaritmos[9]);

        rt1 = (MathView) findViewById(R.id.rt1);
        rt1.setText(recorrerTexto[0]);
        rt2 = (MathView) findViewById(R.id.rt2);
        rt2.setText(recorrerTexto[1]);
        rt3 = (MathView) findViewById(R.id.rt3);
        rt3.setText(recorrerTexto[2]);
        rt4 = (MathView) findViewById(R.id.rt4);
        rt4.setText(recorrerTexto[3]);
        rt5 = (MathView) findViewById(R.id.rt5);
        rt5.setText(recorrerTexto[4]);

    }

}