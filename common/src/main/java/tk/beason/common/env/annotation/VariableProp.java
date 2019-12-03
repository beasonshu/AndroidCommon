package tk.beason.common.env.annotation;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import tk.beason.common.env.model.Item;
import tk.beason.common.env.model.Variable;

@Retention(RetentionPolicy.RUNTIME)
public @interface VariableProp {

    String name();

    String desc();

    Class<? extends Variable.DefaultItemProvider> defaultValue();

    Class<? extends Item>[] selections();
}
