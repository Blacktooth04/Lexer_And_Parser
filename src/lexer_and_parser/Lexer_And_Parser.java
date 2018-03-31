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
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

/**
 *
 * @author russell3233
 */
public class Lexer_And_Parser {
    Lexer lexer;
    String text; // reusable for any String input
    JFrame mainFrame; // create the main window
    ButtonGroup buttons; // for storing the buttons together
    JButton button;
    JRadioButton radioButton;
    JPanel panel; // for creating the different panels
    Token token; // for storing string as tokens
    Boolean main; // help decide whether we are setting the main layout or panel layout
    
    public static void main(String[] args) throws SyntaxError, IOException {
        // call input parser
        new Lexer_And_Parser();
    } // end main

    // constructor
    public Lexer_And_Parser() throws SyntaxError, FileNotFoundException, IOException {

        main = true;
        
        // use JFileChooser to find file
        // then read through the input and tokenize
        JFileChooser choice = new JFileChooser("."); // "." sets to current directory
        // set current directory to users home
//        choice.setCurrentDirectory(new File(System.getProperty("user.home")));

        String filename = "";
        int result = choice.showOpenDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                filename = choice.getSelectedFile().toString(); // for verifying the file selected
                lexer = new Lexer(choice.getSelectedFile().toString()); // run choice through the Lexer class
                JOptionPane.showMessageDialog(null, "You selected " + filename); // display filename and location
                break;
            case JFileChooser.CANCEL_OPTION:
                JOptionPane.showMessageDialog(null, "You didn't select anything.");
                break;
            case JFileChooser.ERROR_OPTION:
                JOptionPane.showMessageDialog(null, "An error occurred.");
                break;
            default:
                break;
        } // end choice switch

        // run tokens through the GUI builder
        this.GUI();
        lexer.close(); // close the input file
    } // end input parser

    // create whatever GUI based on input
    public boolean GUI() throws SyntaxError, IOException {
        // GUI -> Window String ‘(‘ NUMBER ‘,’ NUMBER ‘)’ layout widgets End ‘.’

        int height, width; // store the height and width of the window

        // start parse
        token = lexer.getNextToken();

        if (token == Token.WINDOW) { // first token should be window, else false
            mainFrame = new JFrame(); 
            token = lexer.getNextToken();

            if (token == Token.STRING) {
                mainFrame.setTitle(lexer.Lexeme());
                token = lexer.getNextToken(); 

                if (token == Token.OPEN_PARENTHESES) {
                    width = Integer.parseInt(lexer.Lexeme());
                    token = lexer.getNextToken();
                    if (token == Token.CLOSE_PARENTHESES) {
                        height = Integer.parseInt(lexer.Lexeme());
                        token = lexer.getNextToken();
                        mainFrame.setSize(width, height);
                        if (this.Layout()) {
                            if (this.Widgets()){
                                if (token == Token.PERIOD) {
                                    mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // shut program down on close
                                    mainFrame.setVisible(true);
                                    return true;
                                }
                            } // end widgets
                        } // end layout
                    } // end token.CLOSE_PARENTHESES
                } // end token.open_parenthese if
            } // end token.string if
        } // end token.window if
        return false; // false if first token != Window
    } // end GUI

// other methods
// <editor-fold>
    // create Layout based on input
    public boolean Layout() throws SyntaxError, IOException {
        // layout -> Layout layout_type ‘:’
        if(token == Token.LAYOUT){
            token = lexer.getNextToken();
            if(this.LayoutType()){
                if(token == Token.COLON){
                    token = lexer.getNextToken();
                    return true;
                }
                return true;
            }
        }
        return false;    
    } // end Layout

    // create Layout type based on input
    public boolean LayoutType() throws SyntaxError, IOException {
        // layout_type -> Flow | Grid ‘(‘ NUMBER ‘,’ NUMBER [‘,’ NUMBER ‘,’ NUMBER] ‘)’
        // either flow or grid
        // grid contains (int rows, int cols, int hgap, int vgap)
        int rows;
        int columns;
        int hgap;
        int vgap;
        
        if(token == Token.FLOW){
            // see if we are setting the main layout or the panel
            if (main) { 
                mainFrame.setLayout(new FlowLayout());
                token = lexer.getNextToken();
//                main = false;
            return true;
            } else {
                panel.setLayout(new FlowLayout());
                return true;
            }
        } else if(token == Token.GRID){
            // see if we are setting the main layout or the panel
            // rows, columns, hgap, vgap
            if (main) { 
                token = lexer.getNextToken();
                if(token == Token.OPEN_PARENTHESES){
                    rows = Integer.parseInt(lexer.Lexeme());
                    token = lexer.getNextToken();
                    if(token == Token.NUMBER){
                        columns = Integer.parseInt(lexer.Lexeme());
                        token = lexer.getNextToken();
                        if(token == Token.NUMBER){
                            hgap = Integer.parseInt(lexer.Lexeme());
                            token = lexer.getNextToken();
                            if(token == Token.CLOSE_PARENTHESES){
                                vgap = Integer.parseInt(lexer.Lexeme());
                                mainFrame.setLayout(new GridLayout(rows, columns, hgap, vgap));
                                return true;
                            }
                        }
                    } else if (token == Token.CLOSE_PARENTHESES){ // if gaps are not used
                                columns = Integer.parseInt(lexer.Lexeme());
                                mainFrame.setLayout(new GridLayout(rows, columns));
                                return true;
                    } 
                }            
            } else {
                token = lexer.getNextToken();
                if(token == Token.OPEN_PARENTHESES){
                    rows = Integer.parseInt(lexer.Lexeme());
                    token = lexer.getNextToken();
                    if(token == Token.NUMBER){
                        columns = Integer.parseInt(lexer.Lexeme());
                        token = lexer.getNextToken();
                        if(token == Token.NUMBER){
                            hgap = Integer.parseInt(lexer.Lexeme());
                            token = lexer.getNextToken();
                            if(token == Token.CLOSE_PARENTHESES){
                                vgap = Integer.parseInt(lexer.Lexeme());
                                panel.setLayout(new GridLayout(rows, columns, hgap, vgap));
                                mainFrame.add(panel);
                                return true;
                            }
                        }
                    } else if (token == Token.CLOSE_PARENTHESES){ // if gaps are not used
                                columns = Integer.parseInt(lexer.Lexeme());
                                panel.setLayout(new GridLayout(rows, columns));
                                mainFrame.add(panel);
                                return true;
                    } 
                }
            }
        }            
        return false;
    } // end LayoutType

    // create Widgets based on input
    public boolean Widgets() throws SyntaxError, IOException {
        // widgets -> widget widgets | widgets
        if (this.Widget()) {
            if(this.Widgets()){
                return true;
            }
            return true;
        }
        return false;
    } // end Widgets

    // create Widget based on input
    public boolean Widget() throws SyntaxError, IOException {
        // -> Button STRING ‘;’ | Group radio_buttons End ‘;’ | Label STRING ‘;’ | Panel layout widgets END ‘;’ | Textfield Number ‘;’
        int length;
        
        if(token == Token.BUTTON){
            token = lexer.getNextToken();
            if (token == Token.STRING){
                text = lexer.Lexeme();
                button = new JButton(text);
                if (main) {
                    mainFrame.add(button);
                } else {
                    panel.add(button);
                }
                token = lexer.getNextToken();
                return true;
            }/* else {
                System.out.println("Returning false");
                return false;
            }*/
        } else if(token == Token.GROUP){
            buttons = new ButtonGroup();
            token = lexer.getNextToken();
            if(RadioButtons()){
                if(token == Token.END){
                    token = lexer.getNextToken();
                    return true;
                }
                return true;
            }
        } else if(token == Token.LABEL){
            token = lexer.getNextToken();
            if(token == Token.STRING){
                text = lexer.Lexeme();
                if (main) {
                    mainFrame.add(new JLabel(text));
                } else {
                    panel.add(new JLabel(text));
                }
                token = lexer.getNextToken();
                return true;
            }
        } else if(token == Token.PANEL){
            main = false;
            mainFrame.add(panel = new JPanel());
            token = lexer.getNextToken();
            if(Layout()){
                token = lexer.getNextToken();
                if(Widgets()){
                    if(token == Token.END){
                        token = lexer.getNextToken();
                        if(token == Token.SEMICOLON){
                            token = lexer.getNextToken();
                            return true;
                        }
                    }
                }
            }
        } else if(token == Token.TEXTFIELD){
            token = lexer.getNextToken();
            if(token == Token.NUMBER){
                length = Integer.parseInt(lexer.Lexeme());
                token = lexer.getNextToken();
                if (main) {
                    mainFrame.add(new JTextField(length));
                } else {
                    panel.add(new JTextField(length));
                }
                return true;
            }
        }
        return false;
    } // end Widget

    // create radio buttons based on input
    public boolean RadioButtons() throws SyntaxError, IOException {
        // radio_buttons -> radio_button radio_buttons | radio_button
        if(RadioButton()){
            if(RadioButtons()){
                return true;
            }
            return true;
        }
        return false;    
    } // end RadioButtons

    // create radio button based on input
    public boolean RadioButton() throws SyntaxError, IOException {
        // radio_button -> Radio STRING ‘;’
        if (token == Token.RADIO) {
            token = lexer.getNextToken();
            text = lexer.Lexeme();
            radioButton = new JRadioButton(text);
            buttons.add(radioButton);
            if (main) {
                mainFrame.add(radioButton);
            } else {
                panel.add(radioButton);
            }
            token = lexer.getNextToken();
            if (token == Token.END) {
                token = lexer.getNextToken();
                return true;
            }
            return true;
        }
        return false;
    } // end RadioButton
 // /editor-fold>

    
} // end main class
// </editor-fold>



// enumerator to store the values of the Tokens
enum Token {WINDOW, STRING, NUMBER, END, LAYOUT, FLOW, GRID,
BUTTON, GROUP, LABEL, PANEL, TEXTFIELD, RADIO, /* and the chars */
OPEN_PARENTHESES, CLOSE_PARENTHESES, COMMA, PERIOD, COLON,
SEMICOLON, EOF} ;