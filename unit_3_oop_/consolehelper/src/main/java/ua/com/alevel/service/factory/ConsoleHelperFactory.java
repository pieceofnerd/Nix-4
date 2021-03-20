package ua.com.alevel.service.factory;

import org.reflections.Reflections;
import ua.com.alevel.service.ConsoleHelperService;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConsoleHelperFactory {
    private Map<Class<? extends ConsoleHelperService>, Object> consoleHelperImplementation = new ConcurrentHashMap<>();
    private static ConsoleHelperFactory instance;

    public ConsoleHelperFactory() {
        Reflections reflections = new Reflections("ua.com.alevel");
        Set<Class<? extends ConsoleHelperService>> consoleHelperSet = reflections.getSubTypesOf(ConsoleHelperService.class);
        for (Class<? extends ConsoleHelperService> c : consoleHelperSet) {
            if (!c.isAnnotationPresent(Deprecated.class)) {
                try {
                    consoleHelperImplementation.put(c, c.getDeclaredConstructor().newInstance());
                } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public ConsoleHelperService getConsoleHelpService() {
        for (Map.Entry<Class<? extends ConsoleHelperService>, Object> consoleService : consoleHelperImplementation.entrySet())
            if (!consoleService.getKey().isAnnotationPresent(Deprecated.class)) {
                return (ConsoleHelperService) consoleService.getValue();
            }
        throw new RuntimeException("There is no suitable classes");
    }


    public static ConsoleHelperFactory getInstance() {
        if (instance == null) {
            instance = new ConsoleHelperFactory();
        }
        return instance;
    }

}
