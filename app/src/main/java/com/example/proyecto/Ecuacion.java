package com.example.proyecto;

import java.util.LinkedList;

public class Ecuacion {
    private LinkedList<String> polinomios;
    private int numOfElements;
    private int position;
    public static final String[] signos = {"+", "-", "*", "/"};
    /*
    Estilo:
        - Para escribir caracteres especiales se escriben entre %.
          Ejemplo 1: x^n se traduce en
                    polinomios = {x%e[n]%}
          Ejemplo 2: ((ax+b)/(cx))/(dx+e) se traduce en
                    polinomios = {%d[%d[ax+b][cx]%][dx+e]%}
     */
        /*
    Nota:
        Lista de simbolos para funciones especiales:
            - fr:  fraccion
            - ee: exponencial (potencia)
            - se: exponencial cuadrado (^2)
            - sr: square root
            - ro: n root
            - pa: parentesis abre y cierra
            - de: derivada
            - ii: integral indefinida
            - id: integral definida
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
                        System.out.println("Insertar Between");
                        this.insertBetween(element, i);
                        break;
                    } else if (posiciones > this.position) {
                        System.out.println("Insertar inside");
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

    public void insertSpecial(String identifier){
        switch (identifier) {
            case "fr":
                insert("%fr[][]%");
                numOfElements += ("%fr[][]%").length() - 1;
                this.position += 3;
                break;
            case "sr":
                insert("%ro[2][]%");
                numOfElements += "%ro[2][]%".length() - 1;
                this.position += 6;
                break;
            case "ro":
                insert("%ro[][]%");
                numOfElements += "%ro[][]%".length() - 1;
                this.position += 3;
                break;
            case "ee":
                insert("^{}");
                numOfElements += "^{}".length() - 1;
                this.position += 1;
                break;
            case "se":
                insert("^{2}");
                numOfElements += "^{2}".length() - 1;
                this.position += 3;
                break;
            case "pa":
                insert("()");
                this.numOfElements++;
                break;
            case "de":
                insert("%de[]%");
                numOfElements += "%de[]%".length()-1;
                this.position += 3;
                break;
            case "ii":
                insert("%ii[]%");
                numOfElements += "%ii[]%".length()-1;
                this.position += 4;
                break;
            case "id":
                insert("%id[][][]%");
                numOfElements += "%id[][][]%".length()-1;
                this.position += 3;
                break;
        }
    }

    public void delete()
    {
        if (position != 0 && numOfElements != 0)
        {
            int posiciones = 0;         // Numero de chars totales
            for (int i = 0; i < this.polinomios.size(); i++) {
                posiciones += this.polinomios.get(i).length();
                if (posiciones == this.position) { // Borrar ultimo elemento de String anterior
                    System.out.println("Entroe a posiciones == position");
                    String polinomito = this.polinomios.get(i);
                    if (polinomito.length() == 1)
                    {
                        this.polinomios.remove(i);
                        break;
                    }
                    else if(polinomito.endsWith("%"))
                    {
                        System.out.println("Eliminar especial");
                    }
                    else
                    {
                        this.polinomios.set(i, polinomito.substring(0, polinomito.length()-1));
                        break;
                    }
                } else if (posiciones > this.position) {
                    System.out.println("Entro a posiciones > position");
                    String polinomito = this.polinomios.get(i);
                    if (polinomito.length() == 1){
                        this.polinomios.remove(i);
                        break;
                    }else{
                        posiciones -= this.polinomios.get(i).length();
                        int posicionString = this.position-posiciones;
                        String substringA = polinomito.substring(0, posicionString-1);
                        String substringB = polinomito.substring(posicionString);
                        this.polinomios.set(i, substringA+substringB);
                        break;
                    }
                }
            }
            numOfElements--;
            position--;
        }
    }

    private void especialDelete(int i, String polinomito)
    {

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
                this.polinomios.set(listPosition, this.polinomios.get(listPosition)+element);
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
        if (isSigno(this.polinomios.getLast()) || isSigno(element)){ // Si el ultimo elem es signo, inserta
            this.polinomios.add(element);
        }
        else{
            this.polinomios.set(this.polinomios.size()-1, this.polinomios.getLast()+element);
        }
    }

    public void cambiarPosicion(boolean right){
        if (right)
        {
            if (position < numOfElements){
                position++;
                if (!validPosition(position, right)){
                    cambiarPosicion(right);
                }
            }
        }
        else
        {
            if (position > 0){
                position--;
                if (!validPosition(position, right)){
                    cambiarPosicion(right);
                }
            }
        }
        /*
        if (right){
            if (position < numOfElements){
                String elementInPos = getElement(position);
                if (isEspecial(elementInPos)){
                    if (elementInPos.equals("{")) {
                        if (isInside) {
                            position++;
                            isInside = false;
                            cambiarPosicion(right);
                        } else {
                            isInside = true;
                            position++;
                        }
                    }else if (elementInPos.equals("}")){
                            isInside = false;
                            position++;
                    }
                    else{
                        position++;
                        cambiarPosicion(right);
                    }
                }else{
                    position++;
                }
            }
        }else {
            if (position > 0){
                String elementInPos = getElement(position);
                if (isEspecial(elementInPos)){
                    if (elementInPos.equals("}")) {
                        if (isInside) {
                            position--;
                            isInside = false;
                            cambiarPosicion(right);
                        } else {
                            isInside = true;
                            position--;
                        }
                    }else if (elementInPos.equals("{")){
                        isInside = false;
                        position--;
                    }
                    else{
                        position--;
                        cambiarPosicion(right);
                    }
                }else{
                    position--;
                }
            }
        }
         */
    }

    private boolean validPosition(int position, boolean right){
        if (right)
        {
            String prevElem = getElement(position-1);
            if (prevElem.equals("{")){
                return true;
            }else if(prevElem.equals("}")) {
                if (position > 0 & getElement(position).equals("{")) {
                    return false;
                } else {
                    return true;
                }
            }else if(position > 0 & getElement(position).equals("{")){
                return false;
            }else{
                return true;
            }
        }
        else
        {
            String prevElem = getElement(position-1);
            if (prevElem.equals("{")){
                return true;
            }else if(prevElem.equals("}")){
                if (position < numOfElements & getElement(position).equals("{")) {
                    return false;
                }else{
                    return true;
                }
            }else if(isEspecial(prevElem)){
                return false;
            }else{
                return true;
            }
        }
    }

    public String getElement(int position){
        if (!this.polinomios.isEmpty())
        {
            if (position < this.numOfElements){
                int posiciones = 0;
                for (int i = 0; i < this.polinomios.size(); i++) {
                    posiciones += polinomios.get(i).length();
                    if (posiciones == position)
                    {
                        String polinomito = this.polinomios.get(i);
                        return polinomito.substring(polinomito.length()-1);
                    }
                    else if(posiciones > position)
                    {
                        posiciones -= polinomios.get(i).length();
                        for (int j = 0; j < polinomios.get(i).length(); j++) {
                            if (posiciones+j == position){
                                return polinomios.get(i).charAt(j) + "";
                            }
                        }
                    }
                }
            }
        }
        return "";
    }

    private boolean isSigno(String element){
        for(String s: signos){
            if (s.equals(element)){
                return true;
            }
        }
        return false;
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
                System.out.println(polinomios.get(i) + " ");
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
        imprimeEcuacion();
        return "$$" + equation + "$$";
    }

    private final String[] especiales = {"^", "{", "}"};
    private boolean isEspecial(String caracter){
        for (String caracterEspecial: especiales
             ) {
            if (caracter.equals(caracterEspecial)){
                return true;
            }
        }
        return false;
    }

    private final String[] numeros = {"0", "1", "2", "3", "4", "5", "6", "7", "8",
                                      "9", ".", "x"};
    private boolean isNumber(String element){
        for (String num: numeros
             ) {
            if (element.equals(num))
                return true;
        }
        return false;
    }
}
