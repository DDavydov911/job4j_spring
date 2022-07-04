package ru.job4j_spring;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Context {
    private Map<String, Object> els = new HashMap<String, Object>();

    public void reg(Class cl) {
        printEls(1);
        Constructor[] constructors = cl.getDeclaredConstructors();
        System.out.println("Constr length " + cl.getCanonicalName() + " is " + constructors.length);
        if (constructors.length > 1) {
            throw new IllegalStateException("Class has multiple constructors : " + cl.getCanonicalName());
        }

        Constructor con = constructors[0];
        System.out.println("constructor.toString(): " + con.toString());
        System.out.println("constructor name: " + con.getName());

        List<Object> args = new ArrayList<Object>();
        for (Class arg : con.getParameterTypes()) {
            System.out.println("arg: " + arg);
            System.out.println("arg..getCanonicalName(): " + arg.getCanonicalName());
            System.out.println("els.containsKey(arg.getCanonicalName()): " + els.containsKey(arg.getCanonicalName()));
            if (!els.containsKey(arg.getCanonicalName())) {
                throw new IllegalStateException("Object doesn't found in context : " + arg.getCanonicalName());
            }
            printEls(2);
            System.out.println("els.get(arg.getCanonicalName()): " + els.get(arg.getCanonicalName()));
            args.add(els.get(arg.getCanonicalName()));
        }
        printEls(3);
        try {
            els.put(cl.getCanonicalName(), con.newInstance(args.toArray()));
        } catch (Exception e) {
            throw new IllegalStateException("Coun't create an instance of : " + cl.getCanonicalName(), e);
        }
        printEls(4);
    }

    public <T> T get(Class<T> inst) {
        return (T) els.get(inst.getCanonicalName());
    }
    public void printEls(int num) {
        System.out.println(num + " els.entrySet is: ");
        for (Map.Entry<String, Object> entry : els.entrySet()) {
            System.out.println("entryKey: " + entry.getKey());
            System.out.println("entryValue: " + entry.getValue().toString());
        }
        System.out.println("---------");
    }
}
