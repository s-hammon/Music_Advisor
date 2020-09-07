import java.lang.reflect.Method;

class MethodFinder {

    public static String findMethod(String methodName, String[] classNames) throws ClassNotFoundException {
        for (String s : classNames) {
            Method[] methods = Class.forName(s).getMethods();
            for (Method m : methods)
                if (m.getName().equals(methodName)) {
                    return s;
                }
        }

        return null;
    }
}