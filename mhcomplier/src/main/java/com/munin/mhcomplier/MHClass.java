package com.munin.mhcomplier;

import javax.lang.model.element.Element;

public class MHClass {

    private Element cls;

    public Element getCls() {
        return cls;
    }

    public void setCls(Element cls) {
        this.cls = cls;
    }

    @Override
    public String toString() {
        if(cls == null)
        {
            return "";
        }
        return cls.getSimpleName().toString();
    }
}
