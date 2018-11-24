package com.ltar.framework.base.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/17
 * @version: 1.0.0
 */
public class ReflectUtils {

    /**
     * Returns an array of Field objects reflecting all the fields
     * declared by the class or interface represented by this
     * {@code Class} object. This includes public, protected, default
     * (package) access, and private fields, but excludes inherited fields
     *
     * @param kclass
     * @param recursive 是否递归查找父类中的字段
     * @return
     */
    public static List<Field> getFields(Class<?> kclass, boolean recursive) {
        List<Class<?>> classList = new ArrayList<Class<?>>();
        classList.add(kclass);
        if (recursive) {
            for (Class current = kclass.getSuperclass(); null != current; current = current.getSuperclass()) {
                classList.add(current);
            }
        }

        List<Field> fieldList = new ArrayList<Field>();
        for (int i = classList.size() - 1; i >= 0; --i) {
            Class<?> currentClass = classList.get(i);
            fieldList.addAll(Arrays.asList(currentClass.getDeclaredFields()));
        }

        return fieldList;
    }

    /**
     * 检查kclass是否包含 annotationClass注解
     *
     * @param kclass          检查类
     * @param annotationClass 注解类
     * @return
     */
    public static boolean isAnnotationPresent(Class<?> kclass, Class<? extends Annotation> annotationClass) {
        for (Class current = kclass; null != current; current = current.getSuperclass()) {
            if (current.isAnnotationPresent(annotationClass)) {
                return true;
            }
        }
        return false;
    }

    public static <A extends Annotation> ReflectUtils.ClassAnnotation<A> getAnnotation(Class<?> kclass, Class<A> annotationClass) {
        for (Class current = kclass; null != current; current = current.getSuperclass()) {
            if (current.isAnnotationPresent(annotationClass)) {
                A annotation = (A) current.getAnnotation(annotationClass);
                return new ReflectUtils.ClassAnnotation<A>(current, annotation);
            }
        }
        return null;
    }

    public static class ClassAnnotation<A extends Annotation> {
        private final Class<?> kclass;
        private final A annotation;

        public ClassAnnotation(Class<?> kclass, A annotation) {
            this.kclass = kclass;
            this.annotation = annotation;
        }

        public Class<?> getKclass() {
            return kclass;
        }

        public A getAnnotation() {
            return annotation;
        }
    }
}
