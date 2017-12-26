package inter;

import lexer.Identifier;
import symbol.Env;

public class Statement {
    public static void statement(TypeS typeS, Identifier identifier, Env env){
        Id id = new Id(identifier, typeS.getType());
        env.put(id);
    }
}
