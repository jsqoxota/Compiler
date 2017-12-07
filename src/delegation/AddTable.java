package delegation;

import setsOfItems.NonTerminals;
import setsOfItems.Terminal;

import java.util.HashSet;

public interface AddTable {
    void ACTION(int setOfItemsNumber, HashSet<Terminal> terminals, int productionNumber);
    void ACTION(int setOfItemsNumber, Terminal terminal);
    void ACTION(int setOfItemsNumber, Terminal a, int nextSetOfItemsNumber);
    void GOTO(int setOfItemsNumber, NonTerminals X, int nextSetOfItemsNumber);
}
