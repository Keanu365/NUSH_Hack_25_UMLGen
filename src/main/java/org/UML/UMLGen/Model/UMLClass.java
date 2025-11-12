package org.UML.UMLGen.Model;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collections;

public class UMLClass {
    private Class<?> c;
    private boolean simpleNames;

    public UMLClass(Class<?> c) {
        this(c, false);
    }

    public UMLClass(Class<?> c, boolean sn) {
        this.c = c;
        this.simpleNames = sn;
    }

    public String getName(){
        return this.c.getCanonicalName() + getParameters(this.c.getTypeParameters()) + (this.c.isInterface() ? "\n<<interface>>" : "\n");
    }

    public String getFields(){
        String output = "";
        Field[] fields = this.c.getDeclaredFields();
        for (Field f : fields) {
            output += getLine(f, this.simpleNames) + "\n";
        }
        return output;
    }

    //Most likely unused for this project, but here in case required.
    public String getConstructors(){
        String output = "";
        Constructor<?>[] constructors = this.c.getDeclaredConstructors();
        for (Constructor<?> c : constructors) {
            output += getLine(c, this.simpleNames) + "\n";
        }
        return output;
    }

    //By default, getMethods() will return declared methods only, unless declaredOnly is set to false.
    public String getMethods(){
       return getMethods(true);
    }
    public String getMethods(boolean declaredOnly){
        String output = "";
        Method[] methods;
        if (declaredOnly) methods = this.c.getDeclaredMethods();
        else methods = this.c.getMethods();
        for (Method m : methods) {
            if (m.isSynthetic()) continue;
            output += getLine(m, this.simpleNames) + "\n";
        }
        return output;
    }

    public Class<?> getUMLClass(){
        return this.c;
    }

    public boolean isSimpleNames(){return this.simpleNames;}
    public void setSimpleNames(boolean sn){this.simpleNames = sn;}

    public Class<?> getSuperclass(){
        if (this.c.getSuperclass() != Object.class) {
            return this.c.getSuperclass();
        }
        return null;
    }

    public Class<?>[] getInterfaces(){return this.c.getInterfaces();}

    private static String getLine(AccessibleObject obj, boolean simpleNames) {
        String[] tokens = obj.toString().split(" ");
        String outputLine;
        boolean isStatic = false;
        switch(tokens[0]){
            case "public": outputLine = "+"; break;
            case "private": outputLine = "-"; break;
            case "protected": outputLine = "#"; break;
            case "static": isStatic = true;
            default: outputLine = "~"; break;
        }

        try {
            if (tokens[1].equals("static")) isStatic = true;
        }catch (ArrayIndexOutOfBoundsException _){}

        if (obj instanceof Field f) {
            outputLine += (simpleNames ? f.getName().substring(f.getName().indexOf(".")) : f.getName()) + ": " + tokens[tokens.length-2] + getParameters(f.getType(), f.getGenericType());
        }else if (obj instanceof Executable e) {
            //outputLine += tokens[tokens.length-1].substring(tokens[tokens.length-1].indexOf('.') + 1) + ": " + tokens[tokens.length-2];
            Parameter[] params = e.getParameters();
            outputLine += e.getName() + "(";
            for (Parameter p : params) {
                outputLine += p.getType().getTypeName() + getParameters(p.getType(), p.getParameterizedType()) + (p.equals(params[params.length-1]) ? "" : ", ");
            }
            outputLine += ")";
            if (obj instanceof Method m) outputLine += ": " + m.getReturnType().getTypeName() + getParameters(m.getReturnType(), m.getGenericReturnType());
        }else throw new IllegalArgumentException(); //Must be field or method
        if (isStatic) outputLine = "{s}" + outputLine + "{s}";
        return outputLine;
    }

    private static String getParameters(Class<?> c, Type pt){
        if (c.getTypeParameters().length == 0){return "";} //Method should only return a non-empty string if there are parameters
        if (!(pt instanceof ParameterizedType)) {
            return ""; // Not actually parameterized (raw type)
        }
        String result = "<";
        ParameterizedType fType = (ParameterizedType) pt;
        try {
            Class<?> fClass;
            for (Type t : fType.getActualTypeArguments()){
                fClass = (Class<?>) t;
                result += fClass.getSimpleName() + ", ";
            }
        }catch (ClassCastException e){
            for (Type t : fType.getActualTypeArguments()){
                result += t.getTypeName() + ", ";
            }
        }
        return result.substring(0, result.length()-2) + ">"; //Remove final ", " and add ">"
    }

    private static String getParameters(Type[] parameters){ //Note: Method overload implemented for standardisation purposes.
        if (parameters.length == 0){return "";}
        String result = "<";
        for (Type p : parameters){
            result += p.getTypeName() + ", ";
        }
        return result.substring(0, result.length()-2) + ">";
    }

    static Class<?> load(String relativePath, URLClassLoader classLoader) throws Exception {

        relativePath = relativePath
                .replace("/", ".")
                .replace("\\", ".")
                .replaceAll("\\.class$", "");

//        System.out.println(relativePath);
        return classLoader.loadClass(relativePath);
    }

    //Setting original folder
    private static int recursionCounter = 0;
    private static File rootFolder;
    public static Class<?>[] loadFolder(File folder) throws Exception{
        recursionCounter++;
        if (recursionCounter == 1) rootFolder = folder;
        File[] files = folder.listFiles();
        URLClassLoader classLoader = URLClassLoader.newInstance(
                new URL[]{ rootFolder.toURI().toURL() }
        );

        ArrayList<Class<?>> classes = new ArrayList<>();
        for (File file : files) {
            if (file.getName().equals("module-info.class")) continue;
            if (file.isDirectory()){
                Collections.addAll(classes, loadFolder(file));
            }else if (file.isFile() && file.getName().endsWith(".class")){
                String absPath = file.getAbsolutePath();
                String relPath = absPath.substring(rootFolder.getAbsolutePath().length()+1);
                classes.add(load(relPath, classLoader));
            }
        }
        recursionCounter--;
        return classes.toArray(new Class[0]);
    }

    public String toString(){
        return this.getName() + "\n\n" + this.getFields() + "\n" + this.getConstructors() + "\n" + this.getMethods();
    }

    @Override
    public boolean equals(Object obj){
        return obj instanceof UMLClass uc && this.toString().equals(uc.toString());
    }
}