package tk.beason.common.env.model;

public class Item {
    public String name;
    public boolean isEditable;
    public String value;

    public Item(){
        this.name = "";
        this.isEditable = false;
    }
    public Item(String name, String value, boolean isEditable) {
        this.name = name;
        this.value = value;
        this.isEditable = isEditable;
    }

    public Item(String name, String value) {
        this.name = name;
        this.value = value;
        isEditable = false;
    }

    public void updateValue(String value) {
        this.value = value;
    }
}
