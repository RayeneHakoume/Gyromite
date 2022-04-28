/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modele.deplacements;

/**
 *
 * @author Haowei WANG
 */
public class ColonneRed extends ControleColonne{
    private static ColonneRed colonneR;
    public static ColonneRed getInstance() {
        if (colonneR == null) {
            colonneR = new ColonneRed();
        }
        return colonneR;
    }
}
