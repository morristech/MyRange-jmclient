package ru.myrange.soap;

import org.ksoap2.serialization.PropertyInfo;

public class PropertyObject {
    PropertyInfo info = new PropertyInfo();
    Object obj = null;

    public PropertyObject() {

	}

    public PropertyObject(String name, java.lang.Class type, Object obj) {
		setInfo(name, type);
		setObj(obj);
	}

    public PropertyInfo getInfo() { return info; }

    public Object getObj() { return obj; }

	public void setInfo(String name, java.lang.Class type) {
        this.info.name = name;
		this.info.type = type;
    }

    public void setObj(Object obj) { this.obj = obj; }

}