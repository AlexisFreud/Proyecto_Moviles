package com.example.proyecto;

public class CaracterEspecial{
    private Ecuacion ecuacionA, ecuacionB;
    private String id;

    /*
    Nota:
        Lista de simbolos para funciones especiales:
            - f:  fraccion
            - e: exponencial (potencia)
            - se: exponencial cuadrado (^2)
            - sr: square root
            - r: n root
            - p: parentesis abre y cierra
            - d: derivada
            - ii: integral indefinida
            - id: integral definida
     */

    public CaracterEspecial(String id){
        this.ecuacionA = new Ecuacion();
        this.ecuacionB = new Ecuacion();
        this.id = id;
    }

    public Ecuacion getEcuacion() {
        return ecuacionA;
    }

    public Ecuacion getEcuacionB(){
        return this.ecuacionB;
    }

    public String getId() {
        return id;
    }

    public int getTotalElements(){
        return ecuacionA.getNumOfElements() + ecuacionB.getNumOfElements();
    }

    public void insert(String element, int position){
        if (this.ecuacionA.getNumOfElements() < position){
            this.ecuacionA.insert(element);
        }else{
            this.ecuacionB.insert(element);
        }
    }
}
