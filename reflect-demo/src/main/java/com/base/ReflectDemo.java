package com.base;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

class Vehicle {
    public int speed;
}

interface Ride {
    void left();

    void right();

    void speedup();

    void speeddown();
}

class Car extends Vehicle implements Ride {
    private String Name;
    private String Color;

    protected int height;

    public double price;

    public Car() {
        System.out.println("Car()");
    }

    public Car(String color, String name) {
        this.Name = name;
        this.Color = color;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }

    @Override
    public String toString() {
        return "Car Color:" + this.Color + "Name:" + this.Name;
    }

	public void left() {
		// TODO Auto-generated method stub
		
	}

	public void right() {
		// TODO Auto-generated method stub
		
	}

	public void speedup() {
		// TODO Auto-generated method stub
		
	}

	public void speeddown() {
		// TODO Auto-generated method stub
		
	}

/*    @Override
    public void left() {

    }

    @Override
    public void right() {

    }

    @Override
    public void speedup() {

    }

    @Override
    public void speeddown() {

    }*/
}


public class ReflectDemo {
    public static void main(String[] args) {
        Class<?> clazz = null;
        Car car = null;
        try {
            clazz = Class.forName("com.ijtsai.Car");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //获取类的实例
        try {
            car = (Car) clazz.newInstance();
            car.setName("Benz");
            car.setColor("Pink");
            System.out.println(car);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        //获取类的属性

        //All the public fields up the entire class hierarchy.
        Field[] fields = clazz.getFields();
        //Returns an array of {@code Field} objects reflecting all the fields
        // declared by the class or interface represented by this
        //{@code Class} object. This includes public, protected, default
        //(package) access, and private fields, but excludes inherited fields.
        Field[] declareFields = clazz.getDeclaredFields();

        System.out.println("-------------getFields()-----------");
        printField(fields);
        System.out.println("\n-------------getDeclaredFields()-----------");
        printField(declareFields);

        //获取类的构造方法
        Constructor<?> constructors[] = clazz.getConstructors();

        Car car1 = null, car2 = null;
        try {
            car1 = (Car) constructors[0].newInstance();
            car2 = (Car) constructors[1].newInstance("White", "VW");
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println("\n" + car1);
        System.out.println(car2);

        System.out.println("\n-----getConstructors()------");
        printConstructors(constructors);

        //获取类的方法
        Method[] methods = clazz.getMethods();
        System.out.println("\n------getMethods()--------");
        printMethods(methods);

    }

    /**
     * 打印Field
     *
     * @param fields
     */
    static void printField(Field[] fields) {
        for (Field field : fields) {
            //权限修饰符
            int modifier = field.getModifiers();
            String modifierStr = Modifier.toString(modifier);

            //属性类型
            Class<?> type = field.getType();
            System.out.println(modifierStr + " " + type.getName() + " " + field.getName());
        }
    }

    /**
     * 打印Constructor
     *
     * @param constructors
     */
    static void printConstructors(Constructor[] constructors) {
        for (Constructor constructor : constructors) {
            Class<?> parameter[] = constructor.getParameterTypes();
            int modifier = constructor.getModifiers();
            System.out.print(Modifier.toString(modifier) + " " + constructor.getName());
            System.out.print("(");
            for (int i = 0; i < parameter.length; i++) {
                System.out.print("arg " + i + " " + parameter[i].getName());
                if (i < parameter.length - 1)
                    System.out.print(",");
            }
            System.out.println("){}");
        }
    }

    /**
     * 打印Method
     *
     * @param methods
     */
    static void printMethods(Method[] methods) {
        for (Method method : methods) {
            Class<?> returnType = method.getReturnType();
            Class<?> parameter[] = method.getParameterTypes();
            int mod = method.getModifiers();
            System.out.print(Modifier.toString(mod) + " ");
            System.out.print(returnType.getName() + " ");
            System.out.print(method.getName() + " ");
            System.out.print("(");
            for (int i = 0; i < parameter.length; i++) {
                System.out.println("arg" + i + " " + parameter[i].getName());
            }
            //方法抛出的exception
            Class<?> exceptions[] = method.getExceptionTypes();
            if (exceptions.length > 0) {
                System.out.print(")throws");
                for (int j = 0; j < exceptions.length; j++) {
                    System.out.print(exceptions[j].getName() + " ");
                    if (j < exceptions.length - 1) {
                        System.out.print(",");
                    }
                }
            } else {
                System.out.print(")");
            }
            System.out.println();
        }
    }
}

