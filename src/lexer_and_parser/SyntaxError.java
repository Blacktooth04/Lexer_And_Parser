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
public class SyntaxError extends Exception {
    // main and only method
    public SyntaxError(String error) {
        super("Input file is incorrect at: " + error);
    }
}
