package com.example.proyecto;

import java.util.Hashtable;
import java.util.LinkedList;

public class Ecuacion {
    private LinkedList<String> polinomios;
    private int numOfElements;
    private int position;
    private int currentSpecialId;
    private Hashtable<String, CaracterEspecial> caracteresEspeciales;
    public static final String[] signos = {"+", "-", "*", "/"};
    //public static final String[] especiales = {"d", "e", "s"};
    /*
    Estilo:
        - Cualquier conjunto de caracteres especiales se agrega en un nuevo
          elemento de la LinkedList polinomios, y se inserta entre %.
          Si es un conjunto compuesto, no problema, entra en el mismo elemento.
          Ejemplo 1: x^n se traduce en
                    polinomios = {x%e[n]%}
          Ejemplo 2: ((ax+b)/(cx))/(dx+e) se traduce en
                    polinomios = {%d[%d[ax+b][cx]%][dx+e]%}

        - x^n se escribe xe[n]
        - (ax+b)^n se escribe (ax+b)e[n]
        - (ax+b)/(cx+d) se escribe d[ax+b][cx+d]
        - ((ax+b)/(cx))/(dx+e) se escrbe d[d[ax+b][cx]][dx+e]
    */
    /*
        Los caracteres especiales se registraran por su id. Un id se compone de:
            - simbolo de funcion especial
            - numero de identificiacion (currentSpecialId).
            Ejemplo: f1, e2, r3, e4, p5
        Los caracteres especiales se registran en la LinkedList con su id entre signos '%'
            Ejempplo: {x%es1%, +, 2x} representa x^2+2x
     */

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

    public Ecuacion() {
        polinomios = new LinkedList<>();
        numOfElements = 0;
        position = 0;
        currentSpecialId = 0;
        caracteresEspeciales = new Hashtable<>();
    }

    public void insert(String element) {
        if (element.equals("\\times")){
            insert("*");
            return;
        }
        boolean especial = false;
        if (isSpecial(element)) {
            element += currentSpecialId+"%";
            String nameOfSpecialCharacter = element.substring(1, element.length()-1);
            caracteresEspeciales.put(nameOfSpecialCharacter,
                    new CaracterEspecial(nameOfSpecialCharacter));
            currentSpecialId++;
            especial = true;
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
                    posiciones += getNumOfElementsInList(this.polinomios.get(i));
                    if (posiciones == this.position) {
                        this.insertBetween(element, i);
                        break;
                    } else if (posiciones > this.position) {
                        this.insertInside(element, i,
                                posiciones-getNumOfElementsInList(this.polinomios.get(i)));
                        break;
                    }
                }
            }
        } else {
            System.out.println("Insertado normal");
            this.polinomios.add(element);
        }
        numOfElements++;
        if (especial){
            int i = 0;
            while (element.charAt(i) != '['){
                position++;
            }
        }
        position++;
    }

    public int getNumOfElementsInList(String listElement){
        int numOfElements = 0;
        for (int i = 0; i < listElement.length(); i++) {
            if (listElement.charAt(i) == '%'){
                i++;
                int start = i;
                while (listElement.charAt(i) != '%'){
                    i++;
                }
                numOfElements += caracteresEspeciales.get(listElement.substring(start, i)).getTotalElements()+1;
            }else{
                numOfElements++;
            }
        }
        return numOfElements;
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
                    break;
                } else if (posiciones > this.position) {
                    String polinomito = this.polinomios.get(i);
                    if (polinomito.length() == 1){
                        this.polinomios.remove(i);
                    }else{
                        posiciones -= this.polinomios.get(i).length();
                        int posicionString = this.position-posiciones;
                        String substringA = polinomito.substring(0, posicionString-1);
                        String substringB = polinomito.substring(posicionString);
                        this.polinomios.set(i, substringA+substringB);
                    }
                    break;
                }
            }
            numOfElements--;
            position--;
        }
    }

    private void insertInside(String elementToInsert, int listPosition, int posiciones)
    {
        String polinomio = this.polinomios.get(listPosition);
        int objective = position-posiciones;
        int actualPositionInString = 0;
        int realPositions = 0;
        int auxiliar;
        while (realPositions != objective){
            auxiliar = actualPositionInString;
            actualPositionInString = nextPosition(actualPositionInString, polinomio);
            if (actualPositionInString-auxiliar == 1){
                realPositions++;
            }else{
                realPositions += caracteresEspeciales.get(polinomio.substring(auxiliar+1,
                        actualPositionInString-1)).getTotalElements();
                if (realPositions > objective){
                    caracteresEspeciales.get(polinomio.substring(auxiliar+1,
                            actualPositionInString-1)).insert(elementToInsert,
                            realPositions-objective);
                    return;
                }
            }
        }
        String substringA = polinomio.substring(0, actualPositionInString);
        String substringB = polinomio.substring(actualPositionInString);
        if (isSigno(elementToInsert)) // Separa el String en 2
        {
            this.polinomios.set(listPosition, substringA);
            this.polinomios.add(listPosition+1, elementToInsert);
            this.polinomios.add(listPosition+2, substringB);
        }
        else // Inserta en medio del String
        {
            this.polinomios.set(listPosition, substringA+elementToInsert+substringB);
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

    /*public void insertarEspecial(String type){
        String toInsert = getSpecialToInsert(type);
        if (!this.polinomios.isEmpty()) {
            if (position == numOfElements) //insertar al final
            {
                this.insertEspecilEnd(toInsert);
            }
            else
            {
                int posiciones = 0;         // Numero de chars totales
                for (int i = 0; i < this.polinomios.size(); i++) {
                    posiciones += this.polinomios.get(i).length();
                    if (posiciones == this.position)
                    {
                        this.insertSpecialBetween(toInsert, i);
                        break;
                    }
                    else if (posiciones > this.position)
                    {
                        this.insertSpecialInside(toInsert, i, posiciones-this.polinomios.get(i).length());
                        break;
                    }
                }
            }
        } else {
            this.polinomios.add(toInsert);
        }
        numOfElements++;
        position++;
    }

    public void insertEspecilEnd(String toInsert){
        if (isSigno(this.polinomios.getLast())){ // Si el ultimo elem es signo, inserta
            this.polinomios.add(this.getSpecialToInsert(toInsert));
        }else{
            this.polinomios.set(this.polinomios.size()-1, this.polinomios.getLast()+toInsert);
        }
    }*/

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

    public int nextPosition(int actualPosition, String element){
        if (element.charAt(actualPosition+1) == '%'){
            int i = 2;
            while(element.charAt(actualPosition+i) != '%')
            {
                i++;
            }
            return actualPosition+i;
        }else {
            return actualPosition+1;
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

    private boolean isSpecial(String element){
        return element.startsWith("%");
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

    public String solve(){ // Solo para sumas, restas, multiplicacion

        return "";
    }

    public String getEquationContent(){
        return "";
    }

    public int getNumOfElements(){
        return this.numOfElements;
    }
}

class Monomio{
    private String content;
    private String id;

    /*
    Un monomio es un conjunto de numeros, x y signos especiales.
     */

    public Monomio(String id, String element){
        this.id = id;
        this.content = element;
    }


}
