package cn.santoise.comm.utils;

import org.reflections.Reflections;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author wbingy
 * @date 2023/3/8
 **/
public class SanAnnotationUtil {

    public static List<Class<?>> getAnnotationFromContext(ApplicationContext applicationContext, final Class<? extends Annotation> annotation){
        List<Class<?>> list = new ArrayList<>();
        String[] packages = getBasePackages(applicationContext);
        for(String path:packages){
            Reflections ref = new Reflections(path);
            Set<Class<?>> set = ref.getTypesAnnotatedWith(annotation);
            list.addAll(set);
        }
        return list;
    }

    public static String[] getBasePackages(ApplicationContext context){
        Map<String, Object> annotatedBeans = context.getBeansWithAnnotation(SpringBootApplication.class);
        Set<String> paths = new HashSet<>();
        if(!annotatedBeans.isEmpty()){
            for(Object obj:annotatedBeans.values()){

                Class clz = obj.getClass();
                SpringBootApplication sba = (SpringBootApplication) clz.getAnnotation(SpringBootApplication.class);
                if(sba.scanBasePackages()!=null){
                    paths.addAll(Arrays.asList(sba.scanBasePackages()));
                }
                String clzName = clz.getName().split("[$]")[0];
                paths.add(clz.getPackage().getName());
                ComponentScan annoScan = null;
                try {
                    annoScan = (ComponentScan) Class.forName(clzName).getAnnotation(ComponentScan.class);
                    if(annoScan!=null){
                        String[] packages = annoScan.basePackages();
                        paths.addAll(Arrays.asList(packages));
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e.getMessage(),e);
                }
            }

        }
        return paths.toArray(new String[paths.size()]);
    }
}
