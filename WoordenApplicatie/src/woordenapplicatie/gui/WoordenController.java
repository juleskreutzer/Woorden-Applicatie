package woordenapplicatie.gui;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

/**
 * FXML Controller class
 *
 * @author frankcoenen
 */
public class WoordenController implements Initializable {
    
   private static final String DEFAULT_TEXT =   "Een, twee, drie, vier\n" +
                                                "Hoedje van, hoedje van\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "Heb je dan geen hoedje meer\n" +
                                                "Maak er één van bordpapier\n" +
                                                "Eén, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van, hoedje van\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier\n" +
                                                "\n" +
                                                "En als het hoedje dan niet past\n" +
                                                "Zetten we 't in de glazenkas\n" +
                                                "Een, twee, drie, vier\n" +
                                                "Hoedje van papier";
    
    @FXML
    private Button btAantal;
    @FXML
    private TextArea taInput;
    @FXML
    private Button btSorteer;
    @FXML
    private Button btFrequentie;
    @FXML
    private Button btConcordantie;
    @FXML
    private TextArea taOutput;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        taInput.setText(DEFAULT_TEXT);
    }
    
    /**
     * Just a simple method to split given text into an array.
     * @param s Sequence of chars you want to split
     * @return Array containing all words
     */
    private String[] splitter(String s)
    {
        String text = null;
        if(s == null) {
            text = taInput.getText();
        }
        text = s;
        
        if(text.equals("")) throw new IllegalArgumentException("No text to split!");
        
        // Now comes the magic
        return text.split("[^a-zA-Z]+");
    }
    
    @FXML
    private void aantalAction(ActionEvent event) {
         Collection<String> col = new HashSet<>();
         
         String text = taInput.getText();
         String[] x = splitter(text);
         
         int length = x.length;
         
         for(String s : x)
         {
             col.add(s.toLowerCase());
         }
         
         taOutput.setText(String.format("Total words: %s\nTotal unique words: %s", length, col.size()));
    }

    @FXML
    private void sorteerAction(ActionEvent event) {
         Collection<String> col = new TreeSet<>();
         
         String text = taInput.getText();
         
         for(String s : splitter(text))
         {
             col.add(s.toLowerCase());
         }
         
         String out = "";
         for(String s : col)
         {
             out = String.format("%s\n%s", s, out);
         }
         
         taOutput.setText(out);
    }

    @FXML
    private void frequentieAction(ActionEvent event) {
        // String for word, int for usage amount
         Map<String, Integer> map = new TreeMap<>();
         
         String text = taInput.getText();
         
         for(String s : splitter(text))
         {
             String temp = s.toLowerCase();
             
             // check if word allready in map
             if(map.containsKey(temp)){
                 map.put(temp, map.get(temp) + 1);
             }
             else
             {
                 map.put(temp, 1);
             }
         }
         
         String out = "";
         for(Entry<String, Integer> e : map.entrySet())
         {
             out = out + String.format("%-20s\t%d\n", e.getKey() + ":", e.getValue());
         }
         
         taOutput.setText(out);
    }

    @FXML
    private void concordatieAction(ActionEvent event) {
        TreeMap<String, LinkedList<Integer>> concordance = new TreeMap<>();
        
        String[] splitLines = taInput.getText().toLowerCase().split("\n");
        
        for (int i = 0; i < splitLines.length; i++) {
            //for (String s : splitLines[i].split("[^a-zA-Z]+")) {
            for (String s : new HashSet<String>(Arrays.asList(splitLines[i].split("[^a-zA-Z]+")))) {
                if (!concordance.containsKey(s) || concordance.get(s) == null) {
                    LinkedList ll = new LinkedList<>();
                    ll.add(i + 1);
                    concordance.put(s, ll);
                } else {
                    LinkedList ll = concordance.get(s);
                    ll.add(i + 1);
                }
            }
        }
        
        String out = "";
        for (Entry<String, LinkedList<Integer>> e : concordance.entrySet()){
            out = out + String.format("%-20s\t%s\n", e.getKey() + ":", e.getValue().toString());
        }
        
        taOutput.setText(out);
    }
   
}
