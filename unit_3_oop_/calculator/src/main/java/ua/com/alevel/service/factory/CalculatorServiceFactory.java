package ua.com.alevel.service.factory;

import org.reflections.Reflections;
import ua.com.alevel.service.CalculatorService;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class CalculatorServiceFactory {

    private Map<Class<? extends CalculatorService>, Object> calculatorServiceImplementations = new ConcurrentHashMap<>();
    private static CalculatorServiceFactory instance;


    public CalculatorServiceFactory() {
        Reflections reflections = new Reflections("ua.com.alevel");
        Set<Class<? extends CalculatorService>> calculatorServiceSet = reflections.getSubTypesOf(CalculatorService.class);
        for (Class<? extends CalculatorService> c : calculatorServiceSet) {
            if (!c.isAnnotationPresent(Deprecated.class)) {
                try {
                    calculatorServiceImplementations.put(c, c.getDeclaredConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public CalculatorService getCalculatorService() {
        for (Map.Entry<Class<? extends CalculatorService>, Object> calcService : calculatorServiceImplementations.entrySet())
            if (!calcService.getKey().isAnnotationPresent(Deprecated.class)) {
                return (CalculatorService) calcService.getValue();
            }
        throw new RuntimeException("There is no suitable classes");
    }


    public static CalculatorServiceFactory getInstance() {
        if (instance == null) {
            instance = new CalculatorServiceFactory();
        }
        return instance;
    }
}
