/**
 * Created by uidj6605 on 11/10/2015.
 */
public enum Player {
    AIPlayer,
    HUMANPlayer;
    private static final int BASE_ORDINAL = 1;

    public int getEnumValue(){
        return ordinal() + BASE_ORDINAL;
    }
}
