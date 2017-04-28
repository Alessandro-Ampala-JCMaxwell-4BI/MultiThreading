/*
 * Con questo programma voglio illustrare i seguenti concetti:
 * 1. MAIN e' un thread come gli altri e quindi puo' terminare prima che gli altri
 * 2. THREADs vengono eseguiti allo stesso tempo
 * 3. THREADs possono essere interrotti e hanno la possibilita' di interrompersi in modo pulito
 * 4. THREADs possono essere definiti mediante una CLASSE che implementa un INTERFACCIA Runnable
 * 5. THREADs possono essere avviati in modo indipendente da quando sono stati definiti
 * 6. posso passare parametri al THREADs tramite il costruttore della classe Runnable
 */
package multithread;

import java.util.concurrent.TimeUnit;
import java.util.Random; //classe per la generazione di numeri random

public class MultiThread {

    // "main" e' il THREAD principale da cui vengono creati e avviati tutti gli altri THREADs
    // i vari THREADs poi evolvono indipendentemente dal "main" che puo' eventualmente terminare prima degli altri
    public static void main(String[] args) {
        System.out.println("Main Thread iniziata...");
        long start = System.currentTimeMillis();
        
        // Creazione threads
        Thread tic = new Thread (new TicTacToe("TIC"));
        Thread tac = new Thread(new TicTacToe("TAC"));
        Thread toe = new Thread(new TicTacToe("TOE"));
        
	//Avvio threads
        toe.start();
        tac.start();
        tic.start();
	
	// Attendo che l'esecuzione di ogni thread finisca per poter andare avanti con il codice
        try{
            tic.join();
            tac.join();
            toe.join();
        }catch (InterruptedException e) {}
        
        long end = System.currentTimeMillis();
        System.out.println("Main Thread completata! tempo di esecuzione: " + (end - start) + "ms");
	System.out.println("Punteggio: " + TicTacToe.punteggio);	// Stampa del punteggio
    }
    
}

// Ci sono vari (troppi) metodi per creare un THREAD in Java questo e' il mio preferito per i vantaggi che offre
// +1 si puo estendere da un altra classe
// +1 si possono passare parametri (usando il Costruttore)
// +1 si puo' controllare quando un THREAD inizia indipendentemente da quando e' stato creato
class TicTacToe implements Runnable {
    
    // per le variabili static c'è una variabile in comune a tutti i threads, mentre per le altre ce n'è una copia per ogni thread
    private String t;
    private String msg;
    private static String precedente;
    public static int punteggio = 0;
    private Random rand = new Random(); //oggetto Random per generazione di numeri random
    private int pickedNumber;

    // Costruttore, possiamo usare il costruttore per passare dei parametri al THREAD
    public TicTacToe (String s) {
        this.t = s;
    }
    
    @Override // Annotazione per il compilatore
    // se facessimo un overloading invece di un override il copilatore ci segnalerebbe l'errore
    // per approfondimenti http://lancill.blogspot.it/2012/11/annotations-override.html
    public void run() {
        for (int i = 10; i > 0; i--) {
            msg = "<" + t + "> ";
            
            try {
                pickedNumber = rand.nextInt(300) + 100;
                TimeUnit.MILLISECONDS.sleep(pickedNumber);
            } catch (InterruptedException e) {
                System.out.println("THREAD " + t + " e' stata interrotta! bye bye...");
                return; //me ne vado = termino il THREAD
            }
            msg += t + ": " + i;
            System.out.println(msg);
	    precedente = t;
            if(t.equals("TOE") && precedente.equals("TAC"))
                punteggio ++;
            System.out.println(msg);
         
        }
    }
    
}
