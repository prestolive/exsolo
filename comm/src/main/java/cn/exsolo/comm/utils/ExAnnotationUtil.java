package cn.exsolo.comm.utils;

import org.reflections.Reflections;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * @author prestolive
 * @date 2023/3/8
 **/
public class ExAnnotationUtil {

    interface ClassFilter {
        public boolean check(Class<?> cls);
    }

    public static List<Class<?>> getAnnotationFromContext(ApplicationContext applicationContext, final Class<? extends Annotation> annotation) {
        List<Class<?>> list = new ArrayList<>();
        String[] packages = getBasePackages(applicationContext);
        for (String path : packages) {
            Reflections ref = new Reflections(path);
            Set<Class<?>> set = ref.getTypesAnnotatedWith(annotation);
            list.addAll(set);
        }
        return list;
    }

    public static String[] getBasePackages(ApplicationContext context) {
        Map<String, Object> annotatedBeans = context.getBeansWithAnnotation(SpringBootApplication.class);
        Set<String> paths = new HashSet<>();
        if (!annotatedBeans.isEmpty()) {
            for (Object obj : annotatedBeans.values()) {

                Class clz = obj.getClass();
                SpringBootApplication sba = (SpringBootApplication) clz.getAnnotation(SpringBootApplication.class);
                if (sba.scanBasePackages() != null) {
                    paths.addAll(Arrays.asList(sba.scanBasePackages()));
                }
                String clzName = clz.getName().split("[$]")[0];
                paths.add(clz.getPackage().getName());
                ComponentScan annoScan = null;
                try {
                    annoScan = (ComponentScan) Class.forName(clzName).getAnnotation(ComponentScan.class);
                    if (annoScan != null) {
                        String[] packages = annoScan.basePackages();
                        paths.addAll(Arrays.asList(packages));
                    }
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }

        }
        return paths.toArray(new String[paths.size()]);
    }


    /**
     * 通过接口名取得某个接口下所有实现这个接口的类
     */
    public static List<Class<?>> getAllClassByInterface(ApplicationContext applicationContext, final Class<?> itf) {
        String[] packages = getBasePackages(applicationContext);
        ClassFilter filter = new ClassFilter() {
            @Override
            public boolean check(Class<?> cls) {
                //1、继承接口 2、非抽象类 3、本身不加入进去
                if (itf.isAssignableFrom(cls)) {
                    if (!Modifier.isAbstract(cls.getModifiers()) && !itf.equals(cls)) {
                        return true;
                    }
                }
                return false;
            }

        };
        Set<Class<?>>  all = new HashSet<>();
        for(String pkg:packages){
            List<Class<?>>  list = finalClassByFilter(pkg, filter);
            if(list!=null){
                all.addAll(list);
            }
        }
        return new ArrayList<>(all);
    }


    public static List<Class<?>> finalClassByFilter(String packageName, ClassFilter filter) {

        // 第一个class类的集合
        List<Class<?>> results = new ArrayList<Class<?>>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath, recursive, results, filter);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    findAndAddClassesInPackageByJar(url, packageName, packageDirName, recursive, results, filter);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return results;
    }


    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName
     * @param packagePath
     * @param recursive
     * @param result
     */
    private static void findAndAddClassesInPackageByFile(String packageName, String packagePath, final boolean recursive, List<Class<?>> result, ClassFilter filter) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirfiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            public boolean accept(File file) {
                return (recursive && file.isDirectory()) || (file.getName().endsWith(".class"));
            }
        });
        // 循环所有文件
        for (File file : dirfiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, result, filter);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    // 添加到集合中去
                    Class clz = Class.forName(packageName + '.' + className);
                    if (filter.check(clz)) {
                        result.add(clz);
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void findAndAddClassesInPackageByJar(URL url, String packageName, String packageDirName, final boolean recursive, List<Class<?>> result, ClassFilter filter) {
        // 定义一个JarFile
        JarFile jar;
        try {
            // 获取jar
            jar = ((JarURLConnection) url.openConnection()).getJarFile();
            // 从此jar包 得到一个枚举类
            Enumeration<JarEntry> entries = jar.entries();
            // 同样的进行循环迭代
            while (entries.hasMoreElements()) {
                // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                JarEntry entry = entries.nextElement();
                String name = entry.getName();
                // 如果是以/开头的
                if (name.charAt(0) == '/') {
                    // 获取后面的字符串
                    name = name.substring(1);
                }
                // 如果前半部分和定义的包名相同
                if (name.startsWith(packageDirName)) {
                    int idx = name.lastIndexOf('/');
                    // 如果以"/"结尾 是一个包
                    if (idx != -1) {
                        // 获取包名 把"/"替换成"."
                        packageName = name.substring(0, idx).replace('/', '.');
                    }
                    // 如果可以迭代下去 并且是一个包
                    if ((idx != -1) || recursive) {
                        // 如果是一个.class文件 而且不是目录
                        if (name.endsWith(".class") && !entry.isDirectory()) {
                            // 去掉后面的".class" 获取真正的类名
                            String className = name.substring(packageName.length() + 1, name.length() - 6);
                            try {
                                // 添加到classes
                                Class clz = Class.forName(packageName + '.' + className);
                                if (filter.check(clz)) {
                                    result.add(clz);
                                }

                            } catch (ClassNotFoundException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
