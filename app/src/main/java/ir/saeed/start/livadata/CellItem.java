package ir.saeed.start.livadata;

public class CellItem {
    private String Name;
    private long UnitPrice,Count;
    private int ID;
    public CellItem(String firstName, long unitPrice, long count){
        Name = firstName;
        UnitPrice = unitPrice;
        Count = count;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getID(){
        return ID;
    }

    public CellItem setID(int id){
        ID = id;
        return this;
    }

    public long getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(long unitPrice) {
        UnitPrice = unitPrice;
    }

    public long getCount() {
        return Count;
    }

    public void setCount(long count) {
        Count = count;
    }
}
