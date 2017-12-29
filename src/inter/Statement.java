package inter;

import lexer.Identifier;
import symbol.Env;
import symbol.Type;

public class Statement {
    private static Env env;
    public static void statement(Type type, Identifier identifier){
        Id id = new Id(identifier, type);
        env.put(id);
    }

    public static void setEnv(Env env) {
        Statement.env = env;
    }
}
