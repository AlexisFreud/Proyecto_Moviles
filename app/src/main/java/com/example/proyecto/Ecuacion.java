package com.example.proyecto;

import java.util.LinkedList;

public class Ecuacion {
    private LinkedList<String> polinomios;
    private int numOfElements;
    private int position;
    private final String[] signos = {"+", "-", "*", "/"};
    private final String[] especiales = {"^", "{", "}", "f", "r", "a", "c", "\\", "s", "q", "t",
                                         "i", "n", "[", "]", "%", "\'", "b", "_"};
    private final String[] numeros = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ".", "x"};


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
                insert("\\frac{}{}");
                numOfElements += ("\\frac{}{}").length() - 1;
                this.position += 5;
                break;
            case "sr":
                insert("\\sqrt[2]{}");
                numOfElements += "\\sqrt[2]{}".length() - 1;
                this.position += 8;
                break;
            case "ro":
                insert("\\sqrt[]{}");
                numOfElements += "\\sqrt{}{}".length() - 1;
                this.position += 5;
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
                // Option 1
                /*insert("%\\frac{d}{dx}%()");
                numOfElements += "%\\frac{d}{dx}%()".length()-1;
                this.position += 14;
                break;*/

                // Option 2
                insert("f\'()");
                numOfElements += 3;
                this.position += 2;
                break;
            case "ii":
                insert("\\int()dx");
                numOfElements += "\\int()dx".length()-1;
                this.position += 4;
                break;
            case "id":
                insert("\\int_{}^{}()dx");
                numOfElements += "\\int_{}^{}()dx".length()-1;
                this.position += 8;
                break;
        }
    }

    private void eraseLeft(){
        if (getElement(position-1).equals("("))
        {
            especialDelete();
            int actualPosition = position;
            int parentesis = 1;
            while (parentesis > 0){
                position++;
                if (getElement(position-1).equals("(")){
                    parentesis++;
                }else if(getElement(position-1).equals(")")){
                    if (parentesis == 1){
                        parentesis--;
                        especialDelete();
                    }else{
                        parentesis--;
                    }
                }
            }
            position = actualPosition;
        }
        if (getElement(position-1).equals(")") || getElement(position).equals(")"))
        {
            int closeKeys = 1;
            while(closeKeys > 0){
                especialDelete();
                if (getElement(position-1).equals("(")){
                    especialDelete();
                    closeKeys--;
                }else if (getElement(position-1).equals(")")){
                    especialDelete();
                    closeKeys++;
                }
            }
            eraseLeft();
        }
        else if (getElement(position-1).equals("}") || getElement(position).equals("}"))
        {
            int closeKeys = 1;
            especialDelete();
            while(closeKeys > 0){
                if (getElement(position-1).equals("{")){
                    especialDelete();
                    closeKeys--;
                }else if (getElement(position-1).equals("}")){
                    especialDelete();
                    closeKeys++;
                }else{
                    especialDelete();
                }
            }
            eraseLeft();
        }
        else if (getElement(position-1).equals("]") || getElement(position).equals("]"))
        {
            int closeKeys = 1;
            especialDelete();
            while(closeKeys > 0){
                if (getElement(position-1).equals("[")){
                    especialDelete();
                    closeKeys--;
                }else if (getElement(position-1).equals("]")){
                    especialDelete();
                    closeKeys++;
                }else{
                    especialDelete();
                }
            }
            eraseLeft();
        }
        else if(getElement(position-1).equals("^"))
        {
            especialDelete();
        }
        else if(getElement(position-1).equals("t") || getElement(position-1).equals("c"))
        {
            eraseNElements(5);
        }
        else if(getElement(position-1).equals("x") &&
                 getElement(position-2).equals("d"))
        {
            eraseNElements(2);
            eraseLeft();
        }
    }

    private void eraseNElements(int N)
    {
        for (int i = 0; i < N; i++) {
            especialDelete();
        }
    }

    public void delete()
    {
        if (position != 0 && numOfElements != 0)
        {
            if (isSpecialToErase(position-1)){
                // Borra todo el caracter especial
                eraseLeft();
                return;
            }
            int posiciones = 0;         // Numero de chars totales
            for (int i = 0; i < this.polinomios.size(); i++) {
                posiciones += this.polinomios.get(i).length();
                if (posiciones == this.position) { // Borrar ultimo elemento de String anterior
                    String polinomito = this.polinomios.get(i);
                    if (polinomito.length() == 1)
                    {
                        this.polinomios.remove(i);
                        break;
                    }
                    else
                    {
                        this.polinomios.set(i, polinomito.substring(0, polinomito.length()-1));
                        break;
                    }
                } else if (posiciones > this.position) {
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

    private void especialDelete(){
        if (position != 0 && numOfElements != 0)
        {
            int posiciones = 0;         // Numero de chars totales
            for (int i = 0; i < this.polinomios.size(); i++) {
                posiciones += this.polinomios.get(i).length();
                if (posiciones == position) { // Borrar ultimo elemento de String anterior
                    String polinomito = this.polinomios.get(i);
                    if (polinomito.length() == 1)
                    {
                        this.polinomios.remove(i);
                        break;
                    }
                    else
                    {
                        this.polinomios.set(i, polinomito.substring(0, polinomito.length()-1));
                        break;
                    }
                } else if (posiciones > position) {
                    String polinomito = this.polinomios.get(i);
                    if (polinomito.length() == 1){
                        this.polinomios.remove(i);
                        break;
                    }else{
                        posiciones -= this.polinomios.get(i).length();
                        int posicionString = position-posiciones;
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

    private boolean isSpecialToErase(int position){
        String[] erasesableChars = {"(", ")", "{", "}"};
        String character = getElement(position);
        for (String c: erasesableChars
             ) {
            if (character.equals(c)){
                return true;
            }
        }
        return false;
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
                if (getElement(position).equals("f")){
                    position++;
                    cambiarPosicion(right);
                }else if (getElement(position).equals("s")){
                    position++;
                    cambiarPosicion(right);
                }else if(getElement(position).equals("d")){
                    position += 2;
                }else if(getElement(position).equals("i")){
                    position++;
                    cambiarPosicion(right);
                }
                /*else if(getElement(position).equals("%")){
                    while (!getElement(position).equals("%")){
                        position++;
                    }
                }*/
            }else{
                position = numOfElements;
            }
        }
        else
        {
            if (position > 0){
                position--;
                if (!validPosition(position, right)){
                    cambiarPosicion(right);
                }
                if (getElement(position).equals("f")){
                    position--;
                }else if (getElement(position).equals("s")){
                    position--;
                }else if(getElement(position).equals("i")){
                    position--;
                }else if(getElement(position).equals("x") &&
                        getElement(position-1).equals("d")){
                    position -= 2;
                }
                if (position < 0){
                    position = 0;
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
            if (prevElem.equals("{") || prevElem.equals("[")){
                return true;
            }else if(prevElem.equals("}")) {
                if (position > 0 & getElement(position).equals("{")) {
                    return false;
                } else {
                    return true;
                }
            }else if(position > 0 & getElement(position).equals("{")) {
                return false;
            }else if(isEspecial(prevElem)) {
                return false;
            }else if(getElement(position).equals("\'")){
                return false;
            }else{
                return true;
            }
        }
        else
        {
            String prevElem = getElement(position-1);
            if (prevElem.equals("{") || prevElem.equals("[")){
                return true;
            }else if(prevElem.equals("}")){
                if (position < numOfElements & getElement(position).equals("{")) {
                    return false;
                }else{
                    return true;
                }
            }else if(getElement(position).equals("{")){
                return false;
            }else if(isEspecial(prevElem)){
                return false;
            }else if(getElement(position).equals("\'")){
                return false;
            }else{
                return true;
            }
        }
    }

    public String getElement(int position){
        if (!this.polinomios.isEmpty())
        {
            if (position == this.numOfElements || position == this.numOfElements-1){
                System.out.print("if: 1" + "position: " + position + " element: ");
                return this.polinomios.getLast().substring(this.polinomios.getLast().length()-1);
            }
            else if(position == 0){
                System.out.print("if: 0 " + "position: " + position + " element: ");
                return polinomios.getFirst().charAt(0) + "";
            }
            else {
                int posiciones = 0;
                for (int i = 0; i < this.polinomios.size(); i++) {
                    posiciones += polinomios.get(i).length();
                    /*if (posiciones == position)
                    {
                        String polinomito = this.polinomios.get(i);
                        System.out.print("if: 2" + "position: " + position + "element: ");
                        return polinomito.substring(polinomito.length()-1);
                    }
                    else */if(posiciones > position)
                    {
                        posiciones -= polinomios.get(i).length();
                        for (int j = 0; j < polinomios.get(i).length(); j++) {
                            if (posiciones+j == position){
                                System.out.print("if: 3" + "position: " + position + " element: ");
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
        imprimeEcuacion();
        // equation = equation.replace("%", "");
        System.out.println("Ecuacion: "+equation);
        return "$$" + equation + "$$";
    }
    
    private boolean isEspecial(String caracter){
        for (String caracterEspecial: especiales
             ) {
            if (caracter.equals(caracterEspecial)){
                return true;
            }
        }
        return false;
    }
    
    private boolean isNumber(String element){
        for (String num: numeros
             ) {
            if (element.equals(num))
                return true;
        }
        return false;
    }

    private void orderEcuation(){
        System.out.println();
        for (int i = 0; i < numOfElements; i++) {
            System.out.print(getElement(i));
            System.out.println();
        }
    }

    // Ejemplo: x²- 3x + 5 - (15x-3)/(2x²-1)
    public void solve(){
        orderEcuation();
        // convertToPolinomial();
    }

    private void convertToPolinomial(){

    }
}

