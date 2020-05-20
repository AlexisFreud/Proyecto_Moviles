package com.example.proyecto;

import java.util.LinkedList;

public class Ecuacion {
    private LinkedList<String> polinomios;
    private int numOfElements;
    private int position;
    private final String[] signos = {"+", "-", "*", "/"};
    private final String[] especiales = {"^", "{", "}", "f", "r", "a", "c", "\\", "s", "q", "t",
            "i", "n", "[", "]", "%", "\'", "b", "_"};
    private final String[] numeros = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "."};
    private String equationToTransform;

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
                this.position += 11;
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
            if (getElement(position).equals("d")){
                position += 2;
                eraseNElements(2);
            }
            position = actualPosition;
            eraseLeft();
        }
        else if (getElement(position-1).equals(")") || getElement(position).equals(")"))
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
            int N = 2;
            int auxPos = position-2;
            int integrales = 1;
            while(integrales > 0){
                if(getElement(auxPos).equals("x") &&
                        getElement(auxPos-1).equals("d"))
                {
                    integrales++;
                }else if(getElement(auxPos).equals("i") &&
                        getElement(auxPos-1).equals("\\"))
                {
                    integrales--;
                }
                auxPos--;
                N++;
            }
            eraseNElements(N);
            eraseLeft();
        }else if(getElement(position-1).equals("\'")){
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
        if(character.equals("x") &&
                getElement(position-1).equals("d"))
        {
            return true;
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
                System.out.print("if: 1" + " position: " + position + " element: ");
                return this.polinomios.getLast().substring(this.polinomios.getLast().length()-1);
            }
            else if(position == 0){
                System.out.print("if: 0 " + " position: " + position + " element: ");
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
                                System.out.print("if: 3" + " position: " + position + " element: ");
                                return polinomios.get(i).charAt(j) + "";
                            }
                        }
                    }
                }
            }
        }

        return "";
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

    private boolean isSigno(String element){
        for(String s: signos){
            if (s.equals(element)){
                return true;
            }
        }
        return false;
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

    private boolean isNumber(String str){
        char[] numsArray = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '.'};
        if(!hasX(str)) {
            for (int i = 0; i < numsArray.length; i++) {
                if (str.charAt(0) == numsArray[i]) {
                    return true;
                } else if (str.length() > 1 && str.charAt(0) == '-') {
                    return true;
                }
            }
        } else{
            return false;
        }
        return false;
    }

    private boolean hasX(String str){
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == 'x'){
                return true;
            }
        }
        return false;
    }

    /*
        insert("\\frac{}{}");
        insert("\\sqrt[2]{}");
        insert("\\sqrt[]{}");
        insert("\\int()dx");
        insert("\\int_{}^{}()dx");

        insert("^{}");
        insert("^{2}");
        insert("()");
        insert("f\'()");
     */

    private LinkedList<String> equationToTokens(){
        getEquationToTransform();
        LinkedList<String> tokens = new LinkedList<>();
        char[] equation = equationToTransform.toCharArray();
        boolean sameNumber = false;
        for (int i = 0; i < equation.length; i++) {
            if (equation[i] == '\\'){
                sameNumber = false;
                String s = "";
                s += equation[i];
                i++;
                while (equation[i] != '{' & equation[i] != '[' & equation[i] != '('){
                    s += equation[i];
                    i++;
                }
                i--;
                tokens.add(s);
            }else if(equation[i] == '{' || equation[i] == '[' || equation[i] == '(' ||
                    equation[i] == '}' || equation[i] == ']' || equation[i] == ')' ||
                    equation[i] == '^' || isSigno(""+equation[i])) {
                tokens.add(equation[i] + "");
                sameNumber = false;
            }else if (equation[i] == 'd' || equation[i] == 'f'){
                tokens.add(equation[i] + "" + equation[i+1]);
                i++;
                sameNumber = false;
            }else if (sameNumber){
                int listSize = tokens.size();
                String s = tokens.getLast() + equation[i];
                tokens.set(listSize-1, s);
            }else{
                sameNumber = true;
                tokens.add(equation[i]+"");
            }
        }
        return tokens;
    }

    private void printElementAndNumber(){
        System.out.println();
        for (int i = 0; i < numOfElements; i++) {
            System.out.print(getElement(i));
            System.out.println();
        }
    }

    private void getEquationToTransform(){
        equationToTransform = "";
        for (String s: this.polinomios
        ) {
            equationToTransform += s;
        }
        System.out.println();
    }

    // Ejemplo: x²- 3x + 5 - (15x-3)/(2x²-1)
    public void solve(){
        LinkedList<String> tokens = equationToTokens();
        System.out.print(tokens.toString());
        System.out.println(":WEBADA");
        /*
        for (int i = 0; i < tokens.size(); i++) {
            /*
            Use PEMDAS for operations order
            - P: Parentesis
            - E: Exponentes
            - M: Multiplicacion
            - D: Division
            - A: Adicion
            - S: Sustraccion

            if (getTypeToken(tokens.get(i)) == 6){
                // Parentesis
                int start = i;
                tokens.remove(i);
                int parentesis = 1;
                LinkedList<String> aux = new LinkedList<>();
                while (getTypeToken(tokens.get(i)) != 6 || parentesis > 0){
                    if (getTypeToken(tokens.get(i)) == 6){
                        parentesis++;
                    }else if(getTypeToken(tokens.get(i)) == 7){
                        parentesis--;
                    }
                    aux.add(tokens.get(i));
                    tokens.remove(i);
                }
                tokens.add(start, algebra(aux));
            }
        }*/
        System.out.println(solve_arithmetic(tokens));
    }

    public String solve_arithmetic(LinkedList<String> equation) {
        /*
        Use PEMDAS for operations order
            - P: Parentesis
            - E: Exponentes
            - M: Multiplicacion
            - D: Division
            - A: Adicion
            - S: Sustraccion
         */
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
            if (equationAux.get(i).equals("(")) {
                LinkedList<String> aux = new LinkedList<>();
                i++;
                int parentesis = 1;
                while (parentesis > 0) {
                    if (equationAux.get(i).equals("(")) {
                        aux.add("(");
                        parentesis++;
                        i++;
                    } else if (equationAux.get(i).equals(")")) {
                        if (parentesis != 1) {
                            aux.add(")");
                        }
                        parentesis--;
                        i++;
                    } else {
                        aux.add(equationAux.get(i));
                        i++;
                    }
                }
                operaciones.add(solve_arithmetic(aux));
                i--;

            } else {
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
        for (int i = 0; i < equationAux.size(); i++) {
            String exponent = "";
            if (equationAux.get(i).equals("^") && isNumber(equationAux.get(i - 1))) {
                double num = Double.parseDouble(equationAux.get(i - 1));
                operaciones.remove(operaciones.size() - 1);
                i++;
                if (equationAux.get(i).equals("{")) {
                    LinkedList<String> aux = new LinkedList<>();
                    i++;
                    int parentesis = 1;
                    while (parentesis > 0) {
                        if (equationAux.get(i).equals("{")) {
                            aux.add("{");
                            parentesis++;
                            i++;
                        } else if (equationAux.get(i).equals("}")) {
                            if (parentesis != 1) {
                                aux.add("}");
                            }
                            parentesis--;
                            i++;
                        } else {
                            aux.add(equationAux.get(i));
                            i++;
                        }
                    }
                    // operaciones.add(solve_arithmetic(aux));
                    exponent = solve_arithmetic(aux);
                    i--;
                } else {
                    operaciones.add(equationAux.get(i));
                }
                double newNum = Math.pow(num, Double.parseDouble(exponent));
                operaciones.add(newNum + "");
            } else if (equationAux.get(i).equals("^") && hasX(equationAux.get(i - 1))) {
                String valorX = equationAux.get(i - 1);
                operaciones.remove(operaciones.size() - 1);
                i++;
                if (equationAux.get(i).equals("{")) {
                    LinkedList<String> aux = new LinkedList<>();
                    i++;
                    int parentesis = 1;
                    while (parentesis > 0) {
                        if (equationAux.get(i).equals("{")) {
                            aux.add("{");
                            parentesis++;
                            i++;
                        } else if (equationAux.get(i).equals("}")) {
                            if (parentesis != 1) {
                                aux.add("}");
                            }
                            parentesis--;
                            i++;
                        } else {
                            aux.add(equationAux.get(i));
                            i++;
                        }
                    }
                    exponent = solve_arithmetic(aux);
                    i--;
                } else {
                    operaciones.add(equationAux.get(i));
                }
                String newNum = valorX + "^" + exponent;

                operaciones.add(newNum);
            } else {
                operaciones.add(equationAux.get(i));
            }
        }

        equationAux = operaciones;
        operaciones = new LinkedList<>();

        System.out.println("After exponents");
        for (int i = 0; i < equationAux.size(); i++) {
            System.out.print(equationAux.get(i) + " . ");
        }
        System.out.println();

        if (equationAux.get(0).equals("-")) {
            if (isNumber(equationAux.get(1))) {
                equationAux.add(0, "0");
            }
            if (hasX(equationAux.get(1))) {
                equationAux.removeFirst();
                equationAux.set(0, "-" + equationAux.getFirst());
            }
        }

        System.out.println("After minus");
        for (int i = 0; i < equationAux.size(); i++) {
            System.out.print(equationAux.get(i) + " . ");
        }
        System.out.println();

        LinkedList<String> noX = new LinkedList<>();
        LinkedList<String> withX = new LinkedList<>();

        if(equationAux.size() == 1){
            if(hasX(equationAux.getFirst())){
                withX.add(equationAux.removeFirst());
            } else {
                noX.add(equationAux.removeFirst());
            }
        } else {
            int tmp = 0;
            if(equationAux.getFirst().equals("0") && equationAux.get(1).equals("-")){
                noX.add(equationAux.removeFirst());
                noX.add(equationAux.removeFirst());
                noX.add(equationAux.removeFirst());
            } else {
                if(hasX(equationAux.getFirst())){
                    withX.add(equationAux.getFirst());
                } else {
                    noX.add(equationAux.getFirst());
                }
                for(int i = 1; i < equationAux.size(); i++){
                    if(equationAux.get(i).equals("*")){
                        /*if(equationAux.get(i-1).equals(equationAux.getFirst())){
                            if(isNumber(equationAux.getFirst()) && hasX(equationAux.get(i+1))){
                                withX.add(noX.removeLast());
                                withX.add(equationAux.get(i));
                                withX.add(equationAux.get(i+1));
                                i++;
                                i++;
                            } else if(hasX(equation.getFirst()) && isNumber(equationAux.get(i+1))){
                                withX.add(equationAux.get(i));
                                withX.add(equationAux.get(i+1));
                                i++;
                                i++;
                            }
                        }*/
                        if(equationAux.get(i+1).equals(equationAux.getLast())){
                            if(hasX(equationAux.getLast()) && isNumber(equationAux.get(i-1))){
                                withX.add(noX.removeLast());
                                withX.add(withX.size()-1,noX.removeLast());
                                withX.add(equationAux.get(i));
                                withX.add(equationAux.getLast());
                                break;
                            } else if(isNumber(equationAux.getLast()) && hasX(equationAux.get(i-1))){
                                withX.add(equationAux.get(i));
                                withX.add(equationAux.getLast());
                                break;
                            }
                        } else {
                            if(hasX(equationAux.get(i+1)) && isNumber(equationAux.get(i-1))){
                                withX.add(noX.removeLast());
                                withX.add(withX.size()-1,noX.removeLast());
                                withX.add(equationAux.get(i));
                                withX.add(equationAux.get(i+1));
                                i++;
                                i++;
                            } else if(isNumber(equationAux.get(i+1)) && hasX(equationAux.get(i-1))){
                                withX.add(equationAux.get(i));
                                withX.add(equationAux.get(i+1));
                                i++;
                                i++;
                            }
                        }
                    }
                    if(hasX(equationAux.get(i+1))){
                        withX.add(equationAux.get(i));
                        withX.add(equationAux.get(i+1));
                        i++;
                    } else {
                        noX.add(equationAux.get(i));
                        noX.add(equationAux.get(i+1));
                        i++;
                    }
                }
            }
        }

        LinkedList<String> withXTMP = withX;
        for(int i = 0; i < withXTMP.size(); i++){
            if(withXTMP.size() == 1){
                break;
            } else if (withXTMP.get(i) == "-"){
                if(isNumber(withXTMP.get(i+1))){
                    Double tmp = Double.parseDouble(withXTMP.get(i+1));
                    tmp = tmp*-1;
                    withXTMP.remove(i);
                    withXTMP.set(i,tmp+"");
                }
            }
        }

        withX = withXTMP;

        System.out.println(noX.toString());
        System.out.println(withX.toString());
        equationAux = noX;

        if(!equationAux.isEmpty()){
            // Check for multiplication
            boolean possible_multiplication = false;
            double numA = 0;
            for (int i = 0; i < equationAux.size(); i++) {
                if (Double.isInfinite(numA)) {
                    return "Infinity";
                } else if (Double.isNaN(numA)) {
                    return "Not defined";
                }
                if (isNumber(equationAux.get(i)) && possible_multiplication) {
                    double numB = Double.parseDouble(equationAux.get(i));
                    numA *= numB;
                } else if (equationAux.get(i).equals("*")) {
                    if (equationAux.get(i + 1).equals("-")) {
                        if (isNumber(equationAux.get(i + 2))) {
                            numA *= -1;
                        }
                    } else {
                        if (isNumber(equationAux.get(i + 1))) {
                            double numB = Double.parseDouble(equationAux.get(i + 1));
                            numA *= numB;
                            i++;
                        }
                    }
                } else if (equationAux.get(i).equals("/")) {
                    if (equationAux.get(i + 1).equals("-")) {
                        numA *= -1;
                    } else {
                        double numB = Double.parseDouble(equationAux.get(i + 1));
                        numA /= numB;
                        i++;
                    }
                } else if (isNumber(equationAux.get(i))) {
                    possible_multiplication = true;
                    numA = Double.parseDouble(equationAux.get(i));
                } else if (equationAux.get(i) == "-" && isNumber(equationAux.get(i + 1))) {
                    equationAux.set(i, "+");
                    double numC = Double.parseDouble(equationAux.get(i + 1));
                    numC *= -1;
                    equationAux.set(i + 1, numC + "");
                    i--;
                } else if (possible_multiplication) {
                    operaciones.add(numA + "");
                    operaciones.add(equationAux.get(i));
                    possible_multiplication = false;
                }
            }

            if (numA != 0) {
                operaciones.add(numA + "");
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
            System.out.println();
            for (int i = 0; i < equationAux.size(); i++) {
                if (isNumber(equationAux.get(i)) && possible_sum) {
                    double numB = Double.parseDouble(equationAux.get(i));
                    numA += numB;
                } else if (equationAux.get(i).equals("+")) {
                    double numB = Double.parseDouble(equationAux.get(i + 1));
                    numA += numB;
                    i++;
                } else if (equationAux.get(i).equals("-")) {
                    double numB = Double.parseDouble(equationAux.get(i + 1));
                    numA -= numB;
                    i++;
                } else if (isNumber(equationAux.get(i))) {
                    possible_sum = true;
                    numA = Double.parseDouble(equationAux.get(i));
                } else if (possible_sum) {
                    operaciones.add(numA + "");
                    operaciones.add(equationAux.get(i));
                }
            }
            if (numA != 0) {
                operaciones.add(numA + "");
            }
            equationAux = operaciones;
        }
        /*
        if(!withX.isEmpty()){
            boolean possible_multiplication = false;
            String numA = "";
            Double numA2 = 0.0;
            for (int i = 0; i < withX.size(); i++) {
                if (possible_multiplication) {
                    if(isNumber(withX.get(i))){
                        Double numB = Double.parseDouble(withX.get(i));
                    } else{
                        String numB = withX.get(i);
                        if((numA.charAt(0) == '-' && numB.charAt(0) == '-')){
                            if(numA.length() == 3){

                            } else if(numB.length() == 3){

                            } else {

                            }
                        } else if((numA.charAt(0) != '-' && numB.charAt(0) != '-')) {
                            if(numA.length() == 2){

                            }
                        } else {
                            numA = numA + "*" + numB;
                        }
                    }
                } else if (withX.get(i).equals("*") && numA != "") {
                    if(isNumber(withX.get(i+1))){
                        Double numB = Double.parseDouble(withX.get(i+1));
                        if(numA.length() == 2){ //26343634636x

                        } else if(numA.length() == 3){ //-3x

                        } else { //2x^2

                        }
                    } else {
                        String numB = withX.get(i + 1);
                        numA = numA + "*" + numB;
                        i++;
                    }
                } else if (withX.get(i).equals("/") && numA != "") {
                    if(isNumber(withX.get(i+1))){

                    } else{
                        String numB = withX.get(i + 1);
                        numA = numA+"/"+numB;
                        i++;
                    }
                } else if (withX.get(i).equals("*") && numA2 != 0.0) {
                    if(isNumber(withX.get(i+1))){

                    } else {
                        String numB = withX.get(i + 1);
                        numA = numA + "*" + numB;
                        i++;
                    }
                } else if (withX.get(i).equals("/") && numA2 != 0.0) {
                    if(isNumber(withX.get(i+1))){

                    } else{
                        String numB = withX.get(i + 1);
                        numA = numA+"/"+numB;
                        i++;
                    }
                } else if (hasX(withX.get(i))) {
                    possible_multiplication = true;
                    numA = withX.get(i);
                } else if (isNumber(withX.get(i))){
                    possible_multiplication = true;
                    numA2 = Double.parseDouble(withX.get(i));
                } else if (possible_multiplication) {
                    operacionesX.add(numA + "");
                    operacionesX.add(withX.get(i));
                    possible_multiplication = false;
                }
            }

            if (numA != "") {
                operacionesX.add(numA + "");
            }
            withX = operacionesX;
            operacionesX = new LinkedList<>();

            System.out.println("After multiplication");
            for (int i = 0; i < withX.size(); i++) {
                System.out.print(withX.get(i) + " . ");
            }
            System.out.println();

            // Check for addition
            boolean possible_sum = false;
            numA = "";
            System.out.println();
            for (int i = 0; i < equationAux.size(); i++) {
                if (isNumber(equationAux.get(i)) && possible_sum) {
                    double numB = Double.parseDouble(equationAux.get(i));
                    numA += numB;
                } else if (equationAux.get(i).equals("+")) {
                    double numB = Double.parseDouble(equationAux.get(i + 1));
                    numA += numB;
                    i++;
                } else if (equationAux.get(i).equals("-")) {
                    double numB = Double.parseDouble(equationAux.get(i + 1));
                    numA -= numB;
                    i++;
                } else if (isNumber(equationAux.get(i))) {
                    possible_sum = true;
                    numA = Double.parseDouble(equationAux.get(i));
                } else if (possible_sum) {
                    operaciones.add(numA + "");
                    operaciones.add(equationAux.get(i));
                }
            }
            if (numA != 0) {
                operaciones.add(numA + "");
            }
            equationAux = operaciones;
        }*/

        LinkedList<String> operacionesX = new LinkedList<>();
        if(!withX.isEmpty()){
            double[] numA = {0.0,0.0};
            for(int i = 0; i < withX.size(); i++){
                if(withX.get(i).equals("*")){
                    double[] numB = getNumAndPotencia(withX.get(i+1));
                    double num = numB[0]*numA[0];
                    double potencia = numB[1]+numA[1];
                    numA[0] = num;
                    numA[1] = potencia;
                    if(!operacionesX.isEmpty() && !isSigno(operacionesX.getLast())) {
                        operacionesX.set(operacionesX.size() - 1, numA[0] + "x^" + numA[1]);
                    } else {
                        operacionesX.add(numA[0] + "x^" + numA[1]);
                    }
                    i++;
                } else if(!isSigno(withX.get(i))){
                    operacionesX.add(withX.get(i));
                    numA = getNumAndPotencia(withX.get(i));
                } else {
                    operacionesX.add(withX.get(i));
                }
            }

            for(int i = 0; i < operacionesX.size(); i++){
                if(!isSigno(operacionesX.get(i)) && operacionesX.get(i).charAt(operacionesX.get(i).length()-1) == 'x'){
                    double[] num = getNumAndPotencia(operacionesX.get(i));
                    operacionesX.set(i, num[0]+"x^"+num[1]);
                }
            }

            System.out.println(operacionesX.toString());

            LinkedList<String> operacionesPegadas = new LinkedList<>();

            if(operacionesX.size()%2 == 0){
                for(int i = 1; i < operacionesX.size(); i++){
                    if(!isSigno(operacionesX.get(i))){
                        operacionesPegadas.add(operacionesX.get(i-1)+operacionesX.get(i));
                    }
                }
            } else {
                operacionesPegadas.add(operacionesX.get(0));
                for(int i = 1; i < operacionesX.size(); i++){
                    if(!isSigno(operacionesX.get(i))){
                        operacionesPegadas.add(operacionesX.get(i-1)+operacionesX.get(i));
                    }
                }
            }

            int k = 0;
            int l = 0;
            double uno, dos;
            for(int i = 0; i < operacionesPegadas.size()-1; i++){
                for(int j = 0; j < operacionesPegadas.size()-i-1; j++){
                    while(operacionesPegadas.get(j).charAt(k) != '^'){
                        k++;
                    }
                    uno = Double.parseDouble(operacionesPegadas.get(j).substring(k+1));
                    while(operacionesPegadas.get(j+1).charAt(l) != '^'){
                        l++;
                    }
                    dos = Double.parseDouble(operacionesPegadas.get(j+1).substring(l+1));
                    k = 0;
                    l = 0;
                    if( uno < dos){
                        String tmp = operacionesPegadas.get(j);
                        operacionesPegadas.set(j, operacionesPegadas.get(j+1));
                        operacionesPegadas.set(j+1, tmp);
                    }
                }
            }

            System.out.println(operacionesPegadas.toString()+":WEBADA");

            LinkedList<String> operacionesDespegadas = new LinkedList<>();
            for(int i = 0; i < operacionesPegadas.size(); i++){
                if(operacionesPegadas.get(i).charAt(0) == '+'){
                    operacionesDespegadas.add("+");
                    operacionesDespegadas.add(operacionesPegadas.get(i).substring(1));
                } else if(operacionesPegadas.get(i).charAt(0) == '-'){
                    operacionesDespegadas.add("-");
                    operacionesDespegadas.add(operacionesPegadas.get(i).substring(1));
                } else {
                    operacionesDespegadas.add("+");
                    operacionesDespegadas.add(operacionesPegadas.get(i));
                }
            }

            System.out.println(operacionesDespegadas.toString()+";WEBAda");

            withX = operacionesDespegadas;

            for(int i = 1; i < withX.size(); i++){

            }

        }

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

    private String algebra(LinkedList<String> tokens){
        String result = "";
        /*
            Use PEMDAS for operations order
            - P: Parentesis
            - E: Exponentes
            - M: Multiplicacion
            - D: Division
            - A: Adicion
            - S: Sustraccion
        */

        // Parentesis
        for (int i = 0; i < tokens.size(); i++) {
            if (getTypeToken(tokens.get(i)) == 6){
                // Parentesis
                int start = i;
                int parentesis = 1;
                LinkedList<String> aux = new LinkedList<>();
                while (getTypeToken(tokens.get(i)) != 6 || parentesis > 0){
                    if (getTypeToken(tokens.get(i)) == 6){
                        parentesis++;
                    }else if(getTypeToken(tokens.get(i)) == 7){
                        parentesis--;
                    }
                    aux.add(tokens.get(i));
                    tokens.remove(i);
                }
                if (i < tokens.size()-1 & getTypeToken(tokens.get(i+1)) == 1){
                    // Caso ()^n

                }else{
                    tokens.add(start, algebra(aux));
                }
                i--;
            }
        }

        // Exponentes

        // Multiplicacion

        // Division

        // Suma

        // Resta

        return result;
    }

    private int getTypeToken(String token)
    {
        switch (token){
            case "^":
                return 1;
            case "{":
                return 2;
            case "}":
                return 3;
            case "[":
                return 4;
            case "]":
                return 5;
            case "(":
                return 6;
            case ")":
                return 7;
            case "\\frac":
                return 8;
            case "\\int":
                return 9;
            case "\\int_":
                return 10;
            case "f\'":
                return 11;
            case "dx":
                return 12;
            case "\\sqrt":
                return 13;
            case "+":
                return 14;
            case "-":
                return 15;
            case "*":
                return 16;
            default:
                return 0;
        }
    }

    /*
    \frac{}{}
    \frac{3x^{2} - 2x }{x^{2}} + f'(2x)
    \frac  {  3x  ^   {  2   }   -   2x  }    {  x   ^    {  2   }   }   +   f'(  2x  )

    f'(5x²-(3+5x))
    f' ( 5x ^ { 2 } - ( 3 + 5x ) )
     */

    public String dividePolinomios(){
        getEquationToTransform();
        dividirPolinomios(equationToTokens());
        return "";
    }

    private LinkedList<String> dividirPolinomios(LinkedList<String> tokens){
        /*
        Reducir los exponentes al minimo antes de ordenar, de manera que el exponente sea
        solo un número y no una suma de ellos.
         */
        LinkedList<String> completeEquation = new LinkedList<>();
        LinkedList<String> numerador = new LinkedList<>();
        LinkedList<String> denominador = new LinkedList<>();

        // Find the start of the polinomial (\frac declaration)
        int numToken = 0;
        while (!tokens.get(numToken).equals("\\frac")){
            completeEquation.add(tokens.get(numToken));
            numToken++;
        }
        numToken += 2;     // numToken -> {
        int keys = 1;
        while (keys > 0)    // fill numerador
        {
            numerador.add(tokens.get(numToken));
            numToken++;
            if (tokens.get(numToken).equals("{")){
                keys++;
            }else if (tokens.get(numToken).equals("}")){
                keys--;
            }
        }
        // numToken -> }
        numToken += 2;         // numToken points to one step over {
        keys = 1;
        while (keys > 0)    // fill denominador
        {
            denominador.add(tokens.get(numToken));
            numToken++;
            if (tokens.get(numToken).equals("{")){
                keys++;
            }else if (tokens.get(numToken).equals("}")){
                keys--;
            }
        }
        // Check for polinomial functions
        if (!isPolinomio(numerador) || !isPolinomio(denominador)){
            return tokens;
        }
        solvePolinomialDivision(numerador, denominador);

        return completeEquation;
    }

    private void solvePolinomialDivision(LinkedList<String> num, LinkedList<String> den) {
        /*
        Reducir los exponentes al minimo antes de ordenar, de manera que el exponente sea
        solo un número y no una suma de ellos.
         */
        LinkedList<Monomio> numerador = ordenarPolinomio(num);
        LinkedList<Monomio> denominador = ordenarPolinomio(den);
        System.out.println("Numerador");
        for (Monomio m: numerador
        ) {
            System.out.println(m.toString());
        }
        System.out.println("\nDenominador");
        for (Monomio m :
                denominador) {
            System.out.println(m.toString());
        }
    }

    private boolean isPolinomio(LinkedList<String> poli){
        if (!poli.isEmpty()){
            for (String s: poli
            ) {
                switch (s) {
                    case "\\sqrt":
                        return false;
                    case "(":
                        return false;
                    case "f\'":
                        return false;
                    case "\\int":
                        return false;
                    case "\\int_":
                        return false;
                    default:
                        return true;
                }
            }
        }
        return false;
    }

    private LinkedList<Monomio> ordenarPolinomio(LinkedList<String> polinomial){
        LinkedList<String> poli = polinomial;
        LinkedList<Monomio> orderedPoli = new LinkedList<>();
        String expresion;
        String exponente;
        // Elimina elementos de Poli mientras los agrega a orderedPoli.
        while (!poli.isEmpty()){
            if (isSigno(poli.getFirst())){
                if (poli.getFirst().equals("-")){
                    String signo  = poli.getFirst();
                    poli.remove(0);
                    poli.set(0, signo+poli.getFirst());
                }else{
                    poli.remove(0);
                }

            }
            expresion = poli.getFirst();
            if (poli.size() > 1){
                if (poli.get(1).equals("^")){
                    for (int i = 0; i < 3; i++) {
                        poli.remove(0);
                    }
                    exponente = poli.getFirst();
                    poli.remove(0);
                }else{
                    exponente = "0";
                }
            }else{
                exponente = "0";
            }
            poli.remove(0);
            if (expresion.contains("x")){
                if (exponente.equals("0")){
                    orderedPoli.add(new Monomio(expresion.substring(0, expresion.length()-1), "1"));
                }else{
                    orderedPoli.add(new Monomio(expresion.substring(0, expresion.length()-1), exponente));
                }
            }else{
                orderedPoli.add(new Monomio(expresion, exponente));
            }
        }
        int pos = 0;
        int biggestExponent = 0;
        for (int i = 0; i < orderedPoli.size()-1; i++) {
            biggestExponent = orderedPoli.get(i).getGrado();
            pos = i;
            for (int j = i; j < orderedPoli.size(); j++) {
                if (biggestExponent < orderedPoli.get(j).getGrado()){
                    pos = j;
                    biggestExponent = orderedPoli.get(j).getGrado();
                }
            }
            Monomio a = orderedPoli.get(i);
            orderedPoli.set(i, orderedPoli.get(pos));
            orderedPoli.set(pos, a);
        }
        return orderedPoli;
    }

    public String factorizar(){
        getEquationToTransform();
        return factorizar(equationToTokens());
    }

    private String factorizar(LinkedList<String> equation){
        String result;
        try {
            result = factorComun(equation);
            if (!result.equals("")){
                return result;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            result = binomioCuadrado(equation);
            if (!result.equals("")){
                return result;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        try{
            result = diferenciaCuadrados(equation);
            if (!result.equals("")){
                return result;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("$$"+"No se puede factorizar"+"$$");
        return "";
    }

    private String factorComun(LinkedList<String> equation) throws Exception{
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
            }else if(equation.get(i).equals("*")) {
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

    private String binomioCuadrado(LinkedList<String> equation) throws Exception{
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

    private String diferenciaCuadrados(LinkedList<String> equation) throws Exception{
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

    private boolean cuadrado(double a, double b){
        if(Math.floor(Math.sqrt(b)) == Math.sqrt(b) && Math.floor(Math.sqrt(a)) == Math.sqrt(a)){
            return true;
        }
        return false;
    }

    public double[] getNumAndPotencia(String token){
        // 15345354x^652
        // -x^3
        // x^4
        // -2x^3
        // x
        // -x
        // 1245344
        // 5645648x
        // -45646
        // -4564x
        double[] numAndPotencia = new double[2];
        int i = 0;
        if (!token.contains("x")){
            numAndPotencia[0] = Double.parseDouble(token);
            numAndPotencia[1]  = 0;
            return numAndPotencia;
        }
        else if(token.charAt(0) == '-' && token.charAt(1) == 'x'){
            numAndPotencia[0] = -1.0;
        }else if(token.charAt(0) == 'x'){
            numAndPotencia[0] = 1.0;
        }else{
            while (token.charAt(i) != 'x'){
                i++;
            }
            numAndPotencia[0] = Double.parseDouble(token.substring(0, i));
        }

        if (!token.contains("^")){
            numAndPotencia[1] = 1;
            return numAndPotencia;
        }
        numAndPotencia[1] = Double.parseDouble(token.substring(i+2));
        return numAndPotencia;
    }
}
class Monomio implements Comparable {
    double expresion;       // The expresion but without x
    int grado;

    public Monomio(String expresion, String grado){
        this.expresion = Double.parseDouble(expresion);
        this.grado = Integer.parseInt(grado);
    }

    public String[] translateMonomio(){
        String[] translated = {expresion+"", "^", "{", grado+"", "}"};
        return translated;
    }

    public int getGrado(){
        return this.grado;
    }

    public double getExpresion(){
        return this.expresion;
    }

    @Override
    public int compareTo(Object o) {
        return this.grado-((Monomio)o).getGrado();
    }

    public String toString(){
        return expresion+"x^"+grado;
    }
}
