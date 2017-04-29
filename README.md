# TicTacToe

Il programma è basato sull'uso dei threads. Ci sono 3 threads che partono QUASI contemporaneamente, mentre la loro esecuzione avviene contemporaneamente. Quando TOE capita subito dopo TAC c'è un punteggio che viene aumentato di uno (il quale parte da 0 ovviamente).
Nel codice d'esecuzione dei threads, ogni volta che devono stampare un numero, viene generato un tempo casuale per cui con differenti esecuzioni dello stesso codice ci possono essere differenti output.

# Istruzioni per l'uso

- Scaricare il file MultiThreading.java
- Importarlo in NetBeans o in qualsiasi altro IDE
- Andare a modificare il nome del package (se necessario)
- Premere "Esegui" (la freccia verde in alto)
- L'output verrà mostrato in una finestra in basso

# Istruzioni per modificare il tempo random

Il tempo random del programma viene generato tra i 100 e 300 millisecondi. Per modificare questa caratteristica, bisogna:
- Scaricare il file MultiThreading.java
- Importarlo in NetBeans o in qualsiasi altro IDE
- Andare a modificare il nome del package (se necessario)
- Andare a modificare il codice alla linea 75, in cui il numero tra parentesi riguarda il numero massimo che può essere generato casualmente, mentre quello dopo va ad indicare il numero minimo. In questo modo si imposta un range di numeri che verrano generati casualmente in cui ci il primo indica il massimo mentre il secondo indica il minimo.

ATTENZIONE: i numeri inseriti sono intesi come millisecondi (quindi 300 saranno 0,3 secondi)

Esempio: se vogliamo creare un range di numeri casuali che parte da 1 secondo e arriva a 3,5 secondi, dovremo scrivere:
pickedNumber = rand.nextInt(3500) + 1000;
