package VueControleur;
//

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Classe d'outils Swing.
 *
 *
 *
 */
public class SwingUtil {

    /**
     * Créer un objet ImageIcon qui peut s'adapter à la taille du composant
     *
     * @param image crée ImageIcon à partir de l'objet <code> Image </code>
     * @param contraint S'il faut zoomer dans des proportions égales.
     * Lorsqu'elle est <code> true </code>, vous pouvez passer
     * {@link javax.swing.JComponent # setAlignmentX (float)} et
     * {@link javax.swing.JComponent # setAlignmentY (float)} pour définir
     * l'alignement des composants.
     */
    public static ImageIcon createAutoAdjustIcon(Image image, boolean constrained) {
        ImageIcon icon = new ImageIcon(image) {
            @Override
            public synchronized void paintIcon(java.awt.Component cmp, Graphics g, int x, int y) {
                //Paramètres d'initialisation
                Point startPoint = new Point(0, 0);//Point de départ du dessin par défaut
                Dimension cmpSize = cmp.getSize();//Obtenir la taille du composant
                Dimension imgSize = new Dimension(getIconWidth(), getIconHeight());//Obtenir la taille de l'image

                //Calculer le point de départ et la surface du dessin
                if (constrained) {//Zoom proportionnel
                    //Calculer le rapport hauteur / largeur de l'image
                    double ratio = 1.0 * imgSize.width / imgSize.height;
                    //Calculez la taille de la zone après la mise à l'échelle
                    imgSize.width = (int) Math.min(cmpSize.width, ratio * cmpSize.height);
                    imgSize.height = (int) (imgSize.width / ratio);
                    //Calculer le point de départ du dessin
                    startPoint.x = (int) (cmp.getAlignmentX() * (cmpSize.width - imgSize.width));
                    startPoint.y = (int) (cmp.getAlignmentY() * (cmpSize.height - imgSize.height));
                } else {//Complètement rempli
                    imgSize = cmpSize;
                }

                //Dessinez en fonction du point de départ et de la taille de la zone
                if (getImageObserver() == null) {
                    g.drawImage(getImage(), startPoint.x, startPoint.y,
                            imgSize.width, imgSize.height, cmp);
                } else {
                    g.drawImage(getImage(), startPoint.x, startPoint.y,
                            imgSize.width, imgSize.height, getImageObserver());
                }
            }
        ;
        };
        return icon;
    }

    /**
     * Créez un objet Icon qui peut s'adapter à la taille du composant
     *
     * @param filename spécifie le nom du fichier ou la chaîne de chemin
     * @param contraint S'il faut mettre à l'échelle dans des proportions
     * égales. Lorsqu'elle est <code> true </code>, vous pouvez passer
     * {@link javax.swing.JComponent # setAlignmentX (float)} et
     * {@link javax.swing.JComponent # setAlignmentY (float)} pour définir
     * l'alignement des composants.
     *
     */
    public static ImageIcon createAutoAdjustIcon(String filename, boolean constrained) {
        return createAutoAdjustIcon(new ImageIcon(filename).getImage(), constrained);
    }

    /**
      * Créez un objet ImageIcon qui peut s'adapter à la taille du composant
      *
      * @param url crée ImageIcon à partir de l'objet <code> URL </code> spécifié
      * @param contraint S'il faut zoomer dans des proportions égales. Lorsqu'elle est <code> true </code>, vous pouvez passer
      * {@link javax.swing.JComponent # setAlignmentX (float)} et
      * {@link javax.swing.JComponent # setAlignmentY (float)} pour définir l'alignement des composants.
      *
      */
    public static ImageIcon createAutoAdjustIcon(URL url, boolean constrained) {
        return createAutoAdjustIcon(new ImageIcon(url).getImage(), constrained);
    }

}
