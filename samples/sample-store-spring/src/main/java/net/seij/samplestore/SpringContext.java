package net.seij.samplestore;

import org.springframework.context.ApplicationContext;

/**
 * Allow reaching Spring in non-spring environments (initialized bia {@link SpringContextProvider}
 */
public class SpringContext {
    private static ApplicationContext ctx;

    static void setApplicationContext(ApplicationContext applicationContext) {
        ctx = applicationContext;
    }

    public static <T> T getBean(Class<T> beanClass) {
        return ctx.getBean(beanClass);
    }
}

