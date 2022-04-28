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
public class ColonneBlue extends ControleColonne{
    private static ColonneBlue colonneB;
    public static ColonneBlue getInstance() {
        if (colonneB == null) {
            colonneB = new ColonneBlue();
        }
        return colonneB;
    }
}
