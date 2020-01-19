package net.seij.samplestore;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Waits Spring is initialized and pushes ApplicationContext in a singleton accessible to non-spring areas
 */
@Component
public class SpringContextProvider implements ApplicationContextAware {

    private static ApplicationContext context;

    /**
     * Returns a bean
     */
    public static <T> T getBean(Class<T> beanClass) {
        return context.getBean(beanClass);
    }

    /**
     * Called by Spring when Application context is ready
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext ctx) throws BeansException {
        SpringContext.setApplicationContext(ctx);
        context = ctx;
    }
}
