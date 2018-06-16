package com.xn.demo.functions;


import com.alibaba.fastjson.JSONArray;
import com.xn.demo.functions.annos.AppointCancal;
import com.xn.demo.functions.annos.AppointFinish;
import com.xn.demo.functions.cancel.CancelBehavior;
import com.xn.demo.functions.finish.BehaviorAction;
import com.xn.demo.functions.finish.FinishBehavior;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 校验加载是否已经有配置
 * 构建生产策略的工厂
 *
 * @author lijizhen1@jd.com
 * @date 2018/6/13 8:27
 */
@Component
@Lazy(true)
@Slf4j
public class EndAppointFactoryContext<T, R>
        extends EndAppointFactory<T, R>
        implements ApplicationContextAware, BeanFactoryPostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EndAppointFactoryContext.class);
    //FIXME 可以使用注入
    private final static String SCANNER_PACKAGE = "com.jd.appoint";
    /**
     * 使用享元避免重复加载，可以优化一个value获取一个map中的数据
     */
    private final static Map<String, FinishBehavior> finishBehaviors = new HashMap<String, FinishBehavior>();
    private final static Map<String, CancelBehavior> cancelBehaviors = new HashMap<String, CancelBehavior>();

    private ApplicationContext applicationContext;

    /**
     * 创建取消的行为策略
     *
     * @param factoryBean
     * @return
     */
    @Override
    public CancelBehavior createCancelApi(FactoryBean factoryBean) {
        //通过参数获得需要的venderId下的取消执行
        CancelBehavior cancelBehavior =
                cancelBehaviors.get(String.valueOf(factoryBean.getVenderId()));
        if (null == cancelBehavior) {
            logger.error("没有获得对应的取消行为具体工厂生产factoryBean={}", JSONArray.toJSONString(factoryBean));
            throw new RuntimeException("没有获得对应的取消行为具体工厂生产");
        }
        return cancelBehavior;
    }

    /**
     * 创建预约完成的行为策略
     *
     * @param factoryBean
     * @return
     */
    @Override
    public FinishBehavior createFinishApi(FactoryBean factoryBean) {
        //做一个简单的责任链，没有匹配的命中通用的 通过参数获得需要的venderId下的取消执行
        FinishBehavior finishBehavior =
                finishBehaviors.get(String.valueOf(factoryBean.getVenderId()));
        if (null == finishBehavior) {
            logger.error("没有获得对应的完成行为具体工厂生产factoryBean={}", JSONArray.toJSONString(factoryBean));
            throw new RuntimeException("没有获得对应的完成行为具体工厂生产");
        }
        return finishBehavior;
    }


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        // 针对@AppointCancal进行扫描
        AnnotationScanner scannerCancel = bindScanner(beanFactory, AppointCancal.class);
        // 针对@AppointFinish进行扫描
        AnnotationScanner scannerFinish = bindScanner(beanFactory, AppointFinish.class);

        Map<String, Object> cancelMap = beanFactory.getBeansWithAnnotation(AppointCancal.class);
        cancelMap.forEach((k, v) -> {
            Annotation[] annotations = v.getClass().getAnnotations();
            //遍历注解获得值
            for (Annotation annotation : annotations) {
                //如果注解是否在Spring中有注册的服务
                if (annotation instanceof AppointCancal) {
                    AppointCancal appointCancal = (AppointCancal) annotation;
                    long[] venderIds = appointCancal.venderIds();
                    //为venderId绑定服务
                    for (Long venderId : venderIds) {
                        //留下动态扩展点
                        cancelBehaviors.put(String.valueOf(venderId), () -> (BehaviorAction) v);
                    }
                }
            }
        });

        Map<String, Object> finishMap = beanFactory.getBeansWithAnnotation(AppointFinish.class);
        finishMap.forEach((k, v) -> {
            Annotation[] annotations = v.getClass().getAnnotations();
            //遍历注解获得值
            for (Annotation annotation : annotations) {
                //如果注解是否在Spring中有注册的服务
                if (annotation instanceof AppointFinish) {
                    AppointFinish appointCancal = (AppointFinish) annotation;
                    long[] venderIds = appointCancal.venderIds();
                    //为venderId绑定服务
                    for (Long venderId : venderIds) {
                        finishBehaviors.put(String.valueOf(venderId), () -> (BehaviorAction) v);
                    }
                }
            }
        });
    }


    /**
     * 绑定扫描器
     *
     * @param beanFactory
     * @param annotation
     */
    private AnnotationScanner bindScanner(ConfigurableListableBeanFactory beanFactory,
                                          Class annotation) {
        AnnotationScanner scanner = AnnotationScanner.getScanner((BeanDefinitionRegistry) beanFactory, annotation);
        // 设置ApplicationContext
        scanner.setResourceLoader(this.applicationContext);
        // 执行扫描
        scanner.scan(SCANNER_PACKAGE);
        return scanner;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    /**
     * 注解扫描的类
     */
    static class AnnotationScanner extends ClassPathBeanDefinitionScanner {
        /**
         * 自定义AnnotationClazz
         */
        private Class<? extends Annotation> myAnnotationClazz;

        /**
         * 传值使用的临时静态变量
         */
        private static Class<? extends Annotation> staticAnnotationClazz = null;

        /**
         * 因构造函数无法传入指定的Annotation类，需使用静态方法来调用
         *
         * @param registry
         * @param clazz
         * @return
         */
        public static synchronized AnnotationScanner getScanner(BeanDefinitionRegistry registry,
                                                                Class<? extends Annotation> clazz) {
            staticAnnotationClazz = clazz;
            AnnotationScanner scanner = new AnnotationScanner(registry);
            scanner.setSelfAnnotationClazz(clazz);
            return scanner;
        }

        private AnnotationScanner(BeanDefinitionRegistry registry) {
            super(registry);
        }

        // 构造函数需调用函数，使用静态变量annotationClazz传值
        @Override
        public void registerDefaultFilters() {
            // 添加需扫描的Annotation Class
            this.addIncludeFilter(new AnnotationTypeFilter(staticAnnotationClazz));
        }

        /**
         * 以下为初始化后调用的方法
         *
         * @param basePackages
         * @return
         */
        @Override
        public Set<BeanDefinitionHolder> doScan(String... basePackages) {
            return super.doScan(basePackages);
        }

        @Override
        public boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
            return super.isCandidateComponent(beanDefinition)
                    && beanDefinition.getMetadata().hasAnnotation(this.myAnnotationClazz.getName());
        }

        public void setSelfAnnotationClazz(Class<? extends Annotation> selfAnnotationClazz) {
            this.myAnnotationClazz = selfAnnotationClazz;
        }
    }
}
