/*
 * Con questo programma voglio illustrare i seguenti concetti:
 * 1. MAIN e' un thread come gli altri e quindi puo' terminare prima che gli altri
 * 2. THREADs vengono eseguiti allo stesso tempo
 * 3. THREADs possono essere interrotti e hanno la possibilita' di interrompersi in modo pulito
 * 4. THREADs possono essere definiti mediante una CLASSE che implementa un INTERFACCIA Runnable
 * 5. THREADs possono essere avviati in modo indipendente da quando sono stati definiti
 * 6. posso passare parametri al THREADs tramite il costruttore della classe Runnable
 */

package multi;

import java.util.concurrent.TimeUnit;
import java.util.Random;

public class MultiThread {


    // "main" e' il THREAD principale da cui vengono creati e avviati tutti gli altri THREADs
    // i vari THREADs poi evolvono indipendentemente dal "main" che puo' eventualmente terminare prima degli altri
    public static void main(String[] args) {
        System.out.println("Main Thread iniziata...");
        long start = System.currentTimeMillis();
        Monitor m = new Monitor(); //monitor per controllare l'accesso alle risorse condivise
        
        // Creazione threads
        Thread tic = new Thread (new TicTacToe("TIC", m));
        Thread tac = new Thread(new TicTacToe("TAC", m));
        Thread toe = new Thread(new TicTacToe("TOE", m));
        
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
        System.out.println("Punteggio: " + m.getPunteggio()); // Stampa del punteggio
    }
}

// Ci sono vari (troppi) metodi per creare un THREAD in Java questo e' il mio preferito per i vantaggi che offre
// +1 si puo estendere da un altra classe
// +1 si possono passare parametri (usando il Costruttore)
// +1 si puo' controllare quando un THREAD inizia indipendentemente da quando e' stato creato
class TicTacToe implements Runnable {
    
     // per le variabili static c'è una variabile in comune a tutti i threads, mentre per le altre ce n'è una copia per ogni thread
    private String t;
    public String msg;
    private Monitor m;

    // Costruttore, possiamo usare il costruttore per passare dei parametri al THREAD
    public TicTacToe (String s, Monitor m) {
        this.m = m;
        this.t = s;
    }
    
    @Override // Annotazione per il compilatore
    // se facessimo un overloading invece di un override il copilatore ci segnalerebbe l'errore
    // per approfondimenti http://lancill.blogspot.it/2012/11/annotations-override.html
    public void run() {
        for (int i = 10; i > 0; i--) {
            msg = "<" + t + "> " + t + ": " + i;
            m.Scrivi(t, msg);
        }
    }
}

//classe monitor per la gestione di risorse condivise tra threads
class Monitor{
    private String precedente = ""; //variabile contenente il thread eseguito precedentemente
    private int punteggio; 
    private Random rand = new Random(); //oggetto Random per generare numeri casuali
    private int pickedNumber;
    
    //metodo synchronized. In questo modo non può essere eseguito più volte contemporaneamente
    public synchronized void Scrivi(String t, String msg){
            try {
                pickedNumber = rand.nextInt(300) + 100;
                TimeUnit.MILLISECONDS.sleep(pickedNumber);
            } catch (InterruptedException e) {
                System.out.println("THREAD " + t + " e' stata interrotta! bye bye...");
                return; //me ne vado = termino il THREAD
            }

            if(t.equals("TOE") && precedente.equals("TAC")) //confronto se il thread TOE capita dopo del thread TAC
            {
                punteggio ++;
                msg += "\t" + "<---------"; //aggiungo una freccia alla stringa per capire quando capita l'occorrenza
            }
            
            precedente = t;
            System.out.println(msg); 
    }
    
    public int getPunteggio()
    {
        return punteggio;
    }
}
