package tk.beason.common.env.model;

import java.util.ArrayList;

public class Variable  {

    public String name;             // 名称
    public String desc;             // 说明
    public Item currentValue;       // 默认值/当前选项
    public ArrayList<Item> selections;   // 可选项

    public String getValue() {
        return currentValue.value;
    }

    public interface DefaultItemProvider {
        Class<? extends Item> provide();
    }
}
