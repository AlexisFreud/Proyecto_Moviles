package com.example.proyecto;

import java.util.LinkedList;

public class Ecuacion {
    private LinkedList<String> polinomios;
    private int numOfElements;
    private int position;
    public static final String[] signos = {"+", "-", "*", "/"};
    /*
    Estilo:
        - x^n se escribe xn
        - (ax+b)^n se escribe (ax+b)n
     */

    public Ecuacion() {
        polinomios = new LinkedList<>();
        numOfElements = 0;
        position = 0;
    }

    public void insert(String element) {
        if (element.equals("\\times")){
            insert("*");
            return;
        }
        if (!this.polinomios.isEmpty()) {
            if (position == numOfElements) //insertar al final
            {
                this.insertEnd(element);
            }
            else
            {
                int posiciones = 0;         // Numero de chars totales
                for (int i = 0; i < this.polinomios.size(); i++) {
                    posiciones += this.polinomios.get(i).length();
                    if (posiciones == this.position) {
                        this.insertBetween(element, i);
                        break;
                    } else if (posiciones > this.position) {
                        this.insertInside(element, i, this.position - (posiciones - this.polinomios.get(i).length()));
                        break;
                    }
                }
            }
        } else {
            this.polinomios.add(element);
        }
        numOfElements++;
        position++;
    }

    public void delete()
    {
        if (position != 0 && numOfElements != 0)
        {
            int posiciones = 0;         // Numero de chars totales
            for (int i = 0; i < this.polinomios.size(); i++) {
                posiciones += this.polinomios.get(i).length();
                if (posiciones == this.position) { // Borrar ultimo elemento de String anterior
                    String polinomito = this.polinomios.get(i);
                    if (polinomito.length() == 1){
                        this.polinomios.remove(i);
                    }else{
                        this.polinomios.set(i, polinomito.substring(0, polinomito.length()-1));
                    }
                } else if (posiciones > this.position) {
                    String polinomito = this.polinomios.get(i);
                    if (polinomito.length() == 1){
                        this.polinomios.remove(i);
                    }else{
                        posiciones -= this.polinomios.get(i).length();
                        int posicionString = this.position-posiciones;
                        String substringA = polinomito.substring(0, posicionString);
                        String substringB = polinomito.substring(posicionString+1);
                        this.polinomios.set(i, substringA+substringB);
                    }
                }
            }
            numOfElements--;
            position--;
        }
    }

    private void insertInside(String element, int listPosition, int stringPosition)
    {
        String polinomio = this.polinomios.get(listPosition);
        String substringA = polinomio.substring(0, stringPosition);
        String substringB = polinomio.substring(stringPosition);
        if (isSigno(element)) // Separa el String en 2
        {
            this.polinomios.set(listPosition, substringA);
            this.polinomios.add(listPosition+1, element);
            this.polinomios.add(listPosition+2, substringB);
        }
        else // Inserta en medio del String
        {
            this.polinomios.set(listPosition, substringA+element+substringB);
        }
    }

    private void insertBetween(String element, int listPosition) {
        /* Decide donde insertar.
            - Si es un numero y a la derecha hay un numero, lo inserta en ese string
            - Si es un numero y a la izquierda hay un numero, lo inserta en ese string
            - Si no hay numeros a la izquierda o derecha, lo inserta ahi directamente
            - Si es un signo, y no es menos, lo inserta ahi mismo
        */
        if (!isSigno(element))
        {
            if (listPosition+1 < this.polinomios.size()-1 & !isSigno(this.polinomios.get(listPosition+1)))
            {
                this.polinomios.set(listPosition+1, element+this.polinomios.get(listPosition+1));
            }
            else if(!isSigno(this.polinomios.get(listPosition)))
            {
                this.polinomios.set(listPosition, element+this.polinomios.get(listPosition));
            }
            else
            {
                this.polinomios.add(listPosition+1, element);
            }
        }
        else
        {
            this.polinomios.add(listPosition+1, element);
        }
    }

    private void insertEnd(String element) {
        if (isSigno(this.polinomios.getLast())){ // Si el ultimo elem es signo, inserta
            this.polinomios.add(element);
        }else{
            this.polinomios.set(this.polinomios.size()-1, this.polinomios.getLast()+element);
        }
    }

    public void cambiarPosicion(boolean right){
        if (right){
            if (position < numOfElements){
                position++;
            }
        }else {
            if (position > 0){
                position--;
            }
        }
    }

    private boolean isSigno(String element){
        for(String s: signos){
            if (s.equals(element)){
                return true;
            }
        }
        return false;
    }

    public void addEnd(String element){
        if (!polinomios.isEmpty()){
            polinomios.set(polinomios.size()-1, polinomios.getLast()+element);
        }
    }

    public void imprimeEcuacion()
    {
        if (polinomios.isEmpty())
        {
            System.out.println("ecuacion vacía");
        }
        else
        {
            for (int i = 0; i < polinomios.size(); i++)
            {
                System.out.print(polinomios.get(i) + " ");
            }
            System.out.println();
        }
    }

    public String getEquationToShow(){
        String equation = "";
        int chars = 0;
        boolean line_positioned = false;
        if (position == 0){
            equation = "|";
            line_positioned = true;
        }
        for (int i = 0; i < this.polinomios.size(); i++) {
            if (line_positioned)
            {
                equation += this.polinomios.get(i);
            }
            else
            {
                chars += this.polinomios.get(i).length();
                if (chars == position) {
                    equation += this.polinomios.get(i) + "|";
                    line_positioned = true;
                }else if(chars > position){
                    chars -= this.polinomios.get(i).length();
                    for (int j = 0; j < this.polinomios.get(i).length(); j++) {
                        if (chars+j == position){
                            equation += "|";
                        }
                        equation += this.polinomios.get(i).charAt(j);
                    }
                    line_positioned = true;
                }else{
                    equation += this.polinomios.get(i);
                }
            }
        }
        return "$$" + equation + "$$";
    }

}class Polinomio{

    public Polinomio(String poli){

    }
}class Fraccion{

    public Fraccion(Polinomio numerador, Polinomio denominador){

    }
}
