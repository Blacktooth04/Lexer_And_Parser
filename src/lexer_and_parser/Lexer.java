/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexer_and_parser;

/**
 *
 * @author russell3233
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author russell3233
 */
public class Lexer {
    // variables
    String currentToken;
    Token token;
    Scanner scanner; 
    File newFile;

    // constructor
    public Lexer(String file) throws FileNotFoundException {
        // create a new file to get contents of input file
        // then pass through scanner to create tokens
        newFile = new File(file);
        scanner = new Scanner(newFile);
        
        // not sure this is the best way to do this
//        scanner.useDelimiter("\\s+|\\,\\(\\)\\;\\:"); // ignore and remove this punctuation
    } // end constructor

    // close input file
    public void close() throws IOException{
        scanner.close();
    } // end close method

    // get the next token
    // need to figure out punctuation, no method for that
    public Token getNextToken() throws IOException, SyntaxError {
        currentToken = scanner.next(); // declare current token
        
// window parser
// maybe add an array containing keywords
// <editor-fold>
        if (currentToken.contains("Window")) { 
            return Token.WINDOW;
        } else if (currentToken.contains("Layout")) {
            return Token.LAYOUT;
        } else if (currentToken.contains("Panel")) {
            return Token.PANEL;
        } else if (currentToken.contains("Button")) {
            return Token.BUTTON;
        } else if (currentToken.contains("Label")) {
            return Token.LABEL;
        } else if (currentToken.contains("Group")) {
            return Token.GROUP;
        } else if (currentToken.contains("Radio")) {
            return Token.RADIO;
        } else if (currentToken.contains("End")) {
            if (currentToken.endsWith(".")) {
                currentToken = currentToken.replace(";", ""); // delete the semicolon
                return Token.PERIOD;
            } 
            if (currentToken.endsWith(";")) {
                currentToken = currentToken.replace(";", ""); // delete the semicolon
                return Token.END;
            }
        } else if (currentToken.contains("Flow")) {
            currentToken = currentToken.replace(":", ""); // delete the parentheses
            return Token.FLOW;
        } else if (currentToken.contains("Grid")) {
            currentToken = currentToken.replace(":", ""); // delete the parentheses
            return Token.GRID;
        } else if (currentToken.contains("Textfield")) {
            currentToken = currentToken.replace(":", ""); // delete the parentheses
            return Token.TEXTFIELD;
        } else if (currentToken.startsWith("\"") || currentToken.startsWith("\'")){
            currentToken = currentToken.replace("\"", ""); // delete the quotations
            currentToken = currentToken.replace("\'", ""); // in case single quotes are used
            currentToken = currentToken.replace(";", ""); // delete the semicolon
            return Token.STRING;
        } else if (currentToken.startsWith("(")) {
            while (!currentToken.endsWith(")")) {
                currentToken = currentToken.replace("(", ""); // delete the parentheses
                currentToken = currentToken.replace(",", ""); // delete the comma
                return Token.OPEN_PARENTHESES;            
            }
        } else if (currentToken.contains(")")) {
            currentToken = currentToken.replace(")", ""); // delete the parentheses
            currentToken = currentToken.replace(":", ""); // delete the colon
            return Token.CLOSE_PARENTHESES;
        }  else if (currentToken.endsWith(":")) {
            currentToken = currentToken.replace(":", ""); // delete the parentheses
            return Token.COLON;
        }  
        // clean this up
        else if (currentToken.startsWith("0")) {
            currentToken = currentToken.replace(";", ""); // delete the semicolon
            currentToken = currentToken.replace(",", ""); // delete the comma
            currentToken = currentToken.replace(":", ""); // delete the colon
            return Token.NUMBER;
        } else if (currentToken.startsWith("1")) {
            currentToken = currentToken.replace(";", ""); // delete the semicolon
            currentToken = currentToken.replace(",", ""); // delete the comma
            currentToken = currentToken.replace(":", ""); // delete the colon
            return Token.NUMBER;
        } else if (currentToken.startsWith("2")) {
            currentToken = currentToken.replace(";", ""); // delete the semicolon
            currentToken = currentToken.replace(",", ""); // delete the comma
            currentToken = currentToken.replace(":", ""); // delete the colon
            return Token.NUMBER;
        } else if (currentToken.startsWith("3")) {
            currentToken = currentToken.replace(";", ""); // delete the semicolon
            currentToken = currentToken.replace(",", ""); // delete the comma
            currentToken = currentToken.replace(":", ""); // delete the colon
            return Token.NUMBER;
        } else if (currentToken.startsWith("4")) {
            currentToken = currentToken.replace(";", ""); // delete the semicolon
            currentToken = currentToken.replace(",", ""); // delete the comma
            currentToken = currentToken.replace(":", ""); // delete the colon
            return Token.NUMBER;
        } else if (currentToken.startsWith("5")) {
            currentToken = currentToken.replace(";", ""); // delete the semicolon
            currentToken = currentToken.replace(",", ""); // delete the comma
            currentToken = currentToken.replace(":", ""); // delete the colon
            return Token.NUMBER;
        } else if (currentToken.startsWith("6")) {
            currentToken = currentToken.replace(";", ""); // delete the semicolon
            currentToken = currentToken.replace(",", ""); // delete the comma
            currentToken = currentToken.replace(":", ""); // delete the colon
            return Token.NUMBER;
        } else if (currentToken.startsWith("7")) {
            currentToken = currentToken.replace(";", ""); // delete the semicolon
            currentToken = currentToken.replace(",", ""); // delete the comma
            currentToken = currentToken.replace(":", ""); // delete the colon
            return Token.NUMBER;
        } else if (currentToken.startsWith("8")) {
            currentToken = currentToken.replace(";", ""); // delete the semicolon
            currentToken = currentToken.replace(",", ""); // delete the comma
            currentToken = currentToken.replace(":", ""); // delete the colon
            return Token.NUMBER;
        } else if (currentToken.startsWith("9")) {
            currentToken = currentToken.replace(";", ""); // delete the semicolon
            currentToken = currentToken.replace(",", ""); // delete the comma
            currentToken = currentToken.replace(":", ""); // delete the colon
            return Token.NUMBER;
        } 
        // clean the above up
        else {
            // need to throw exception
            // Syntactically incorrect input files should detect and report the first error.
            throw new SyntaxError(currentToken);
//            System.out.println("Input file is incorrect at: " + currentToken);
        } // end main if 
// </editor-fold>

        return token;
    } // end getNextToken

    // return lexeme
    public String Lexeme() {
        
        return currentToken;
    } // end lexeme return
    
} // end Lexer class
// </editor-fold>    
