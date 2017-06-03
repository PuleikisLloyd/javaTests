import java.util.*;

public class NFA<S,A> {

  Map<S, Map<A, Set <S>>> nfa = new HashMap<S, Map<A, Set<S>>>();

  //Konstruktor, welcher jedem Zustand in Q eine Abbildung zuordnet
  public NFA (Set<S> set) {
    Map<A, Set<S>> map = new HashMap<>(); //Transitionen sind Abbildungen von Symbolen auf Zustaende
    for (S zustand : set) {
      nfa.put(zustand, map);
    }
  }

  public void addTransition(S q, A a, S p) {
    Map<A, Set<S>> map = nfa.get(q);
    if (map == null) {                  //wenn Zustand q keine Abbildung hat, so wird eine erstellt
      map = new HashMap<A, Set<S>>();
      nfa.put(q, map);
      Set <S> temp = new HashSet<>();
      temp.add(p);
      map.put(a, temp);
    }
    if(!(map.isEmpty()) && (map.containsKey(a))) {
      Set<S> set = map.get(a);
      if (!set.contains(p)) {             //hat die Menge der erreichbaren Zustaende den uebergebenen
        set.add(p);                       //nicht dabei, so wird er hinzugefügt
      }
    }
    else {
      Set <S> temp = new HashSet<>();
      temp.add(p);
      map.put(a, temp);
    }
  }

  public Set<S> simulate(S q, List<A> w) {
    Set<S> retSet = new HashSet<>();
    for (A symbol : w) {                //wir iterieren über das Wort
      Map<A, Set<S>> map = nfa.get(q);
      if(map.containsKey(symbol)) {     //Ueberpruefung, ob der Buchstabe gelesen werden kann
        for(S zielzustand : map.get(symbol)) {
          if(w.size() == 1) {           //Ist das Wort nur ein Buchstabe fuegen
            retSet.add(zielzustand);    //wir alle erreichbaren Zustaende zu der Ergebnismenge
            return retSet;
          }
          w.remove(symbol);             //Wir "entnehmen" den gelesenen Buchstaben aus dem Wort
          for (S zusaetzliche : this.simulate(zielzustand, w)) {  //rekursiver Aufruf
            retSet.add(zusaetzliche);                             // um delta-Dach zu bestimmen
          }
        }
      }
    }
    return retSet;
  }
}
