import java.util.*;
import java.io.*;

public class myNFA {
  static public void main (String args[]) {

    Set<Integer> states = new HashSet<Integer>();   //Hinzufuegen der Zustaende
    for(int i = 1; i < 35; i++) {
      states.add(i);
    }

    NFA<Integer, String> M = new NFA<Integer, String>(states);

    try {                                           //br um Datei zu lesen
    BufferedReader br = null;
    FileReader fr = null;

    fr = new FileReader(args[0]);
    br = new BufferedReader(fr);

    String add;
    while((add = br.readLine()) != null) {          //jede Zeile die wir lesen
      String[] temp = add.split("\\s");             //wird in drei Strings unterteilt
      M .addTransition(Integer.parseInt(temp[0]), temp[1], Integer.parseInt(temp[2]));  //Interpretation der Strings in den Automaten
    }
    }
    catch(IOException e) {e.printStackTrace();}

    List<String> eingabe = new LinkedList<String>();
    for(int a = 1; a < 9; a++) {                    //Testwort muss mit Leerstellen eingegeben werden ...
      eingabe.add(args[a]);
    }
    Set<Integer> reachable = M.simulate(7, eingabe);
    System.out.println(reachable);
  }
}
