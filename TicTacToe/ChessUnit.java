/**
 * Created by billin on 16-7-1.
 * Chess Unit class
 */
public class ChessUnit implements Cloneable{

    static final char O = 'o';
    static final char X = 'x';
    static final char NONE = '_';

    private char UNIT;

    private int XRowThreatWeight;
    private int XColumnThreatWeight;
    private int XRightCornerToLeftThreatWeight;
    private int XLeftCornerToRightThreatWeight;

    private int ORowThreatWeight;
    private int OColumnThreatWeight;
    private int ORightCornerToLeftThreatWeight;
    private int OLeftCornerToRightThreatWeight;

    private int XRowWeight;
    private int XColumnWeight;
    private int XRightCornerToLeftWeight;
    private int XLeftCornerToRightWeight;

    private int ORowWeight;
    private int OColumnWeight;
    private int ORightCornerToLeftWeight;
    private int OLeftCornerToRightWeight;

    @Override
    public String toString() {
        return UNIT + " ";
    }

    public ChessUnit(int weight) {
        setXColumnWeight(weight);
        setXRowWeight(weight);
        setXLeftCornerToRightWeight(weight);
        setXRightCornerToLeftWeight(weight);
        setOColumnWeight(weight);
        setORowWeight(weight);
        setOLeftCornerToRightWeight(weight);
        setORightCornerToLeftWeight(weight);

        setXRowThreatWeight(weight);
        setXColumnThreatWeight(weight);
        setXLeftCornerToRightThreatWeight(weight);
        setXRightCornerToLeftThreatWeight(weight);

        setUNIT(NONE);
    }

    public ChessUnit(char ox) {
        this(Integer.MAX_VALUE);
        setUNIT(ox);
    }

    public int getXColumnThreatWeight() {
        return XColumnThreatWeight;
    }

    public void setXColumnThreatWeight(int XColumnThreatWeight) {
        this.XColumnThreatWeight = XColumnThreatWeight;
    }

    public char getUNIT() {
        return UNIT;
    }

    public void setUNIT(char UNIT) {
        this.UNIT = UNIT;
    }

    public int getXRowThreatWeight() {
        return XRowThreatWeight;
    }

    public void setXRowThreatWeight(int XRowThreatWeight) {
        this.XRowThreatWeight = XRowThreatWeight;
    }

    public int getXRightCornerToLeftThreatWeight() {
        return XRightCornerToLeftThreatWeight;
    }

    public void setXRightCornerToLeftThreatWeight(int XRightCornerToLeftThreatWeight) {
        this.XRightCornerToLeftThreatWeight = XRightCornerToLeftThreatWeight;
    }

    public int getXLeftCornerToRightThreatWeight() {
        return XLeftCornerToRightThreatWeight;
    }

    public void setXLeftCornerToRightThreatWeight(int XLeftCornerToRightThreatWeight) {
        this.XLeftCornerToRightThreatWeight = XLeftCornerToRightThreatWeight;
    }

    public int getXRowWeight() {
        return XRowWeight;
    }

    public void setXRowWeight(int XRowWeight) {
        this.XRowWeight = XRowWeight;
    }

    public int getXColumnWeight() {
        return XColumnWeight;
    }

    public void setXColumnWeight(int XColumnWeight) {
        this.XColumnWeight = XColumnWeight;
    }

    public int getXRightCornerToLeftWeight() {
        return XRightCornerToLeftWeight;
    }

    public void setXRightCornerToLeftWeight(int XRightCornerToLeftWeight) {
        this.XRightCornerToLeftWeight = XRightCornerToLeftWeight;
    }

    public int getXLeftCornerToRightWeight() {
        return XLeftCornerToRightWeight;
    }

    public void setXLeftCornerToRightWeight(int XLeftCornerToRightWeight) {
        this.XLeftCornerToRightWeight = XLeftCornerToRightWeight;
    }

    public int getORowWeight() {
        return ORowWeight;
    }

    public void setORowWeight(int ORowWeight) {
        this.ORowWeight = ORowWeight;
    }

    public int getOColumnWeight() {
        return OColumnWeight;
    }

    public void setOColumnWeight(int OColumnWeight) {
        this.OColumnWeight = OColumnWeight;
    }

    public int getORightCornerToLeftWeight() {
        return ORightCornerToLeftWeight;
    }

    public void setORightCornerToLeftWeight(int ORightCornerToLeftWeight) {
        this.ORightCornerToLeftWeight = ORightCornerToLeftWeight;
    }

    public int getOLeftCornerToRightWeight() {
        return OLeftCornerToRightWeight;
    }

    public void setOLeftCornerToRightWeight(int OLeftCornerToRightWeight) {
        this.OLeftCornerToRightWeight = OLeftCornerToRightWeight;
    }

    public ChessUnit clone(){
        ChessUnit chess = null;
        try {
            chess = (ChessUnit) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return chess;
    }

}