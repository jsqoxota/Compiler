package setsOfItems;

import lexer.Tag;

/**
 * 终结符
 */
public class Terminal {
    private int tag;

    public Terminal(int tag){
        this.tag = tag;
    }

    @Override
    public String toString() {
        return Tag.tagToString(tag);
    }
}
