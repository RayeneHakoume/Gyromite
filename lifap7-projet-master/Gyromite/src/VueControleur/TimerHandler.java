/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package VueControleur;

/**
 *
 * @author fatma
 */
import java.util.Observable;



import modele.plateau.Jeu;


public class TimerHandler extends Observable implements Runnable {

	private int timer;
	private Jeu jeu;
	
	public TimerHandler(Jeu j, int t)
	{
		timer = t;
		jeu = j;
	}
	
	public void start() {

        new Thread(this).start();
    }
	
	@Override
	public void run() {
		while(timer >0)
		{
                    
			timer--;
			jeu.setTimer(timer);
                        jeu.refresh();
			try {
	            Thread.sleep(1000); 
	        } catch (InterruptedException ex) {
	        }
			
			
		}
		
	}
}
   
   