package symbol;

import inter.Id;
import lexer.Identifier;
import lexer.Token;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Env {
    private HashMap<Identifier, Id> symbolTable;
    private Env prev;

    public Env(Env p){
        symbolTable = new HashMap<>();
        prev = p;
    }

    public void put(Id id){
        symbolTable.put((Identifier) id.getOp(), id);
    }

    public Id get(Token w){
        for (Env e = this; e != null; e = e.prev){
            Id found = (e.symbolTable.get(w));
            if(found != null )return symbolTable.get(w);
        }
        return null;
    }

    public Env getPrev() {
        return prev;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        if(prev != null)stringBuilder.append(prev.toString());
        Iterator iterator= symbolTable.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Id val = (Id)entry.getValue();
            stringBuilder.append(val.getOp().toString() + "\t" + val.getType().toString() + "\t" + val.offset);
            stringBuilder.append("\r\n");
        }
        stringBuilder.append("\n\n");
        return stringBuilder.toString();
    }
}
