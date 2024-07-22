package cn.bugstack.springframework.transaction.interceptor;

import cn.bugstack.springframework.core.MethodClassKey;
import cn.bugstack.springframework.tx.transaction.interceptor.DefaultTransactionAttribute;
import cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute;
import cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttributeSource;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @description Abstract implementation of {@link cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttributeSource} that caches
 * attributes for methods and implements a fallback policy: 1. specific target
 * method; 2. target class; 3. declaring method; 4. declaring class/interface.
 * @date 2022/3/16
 *  /CodeDesignTutorials
 *
 */
public abstract class AbstractFallbackTransactionAttributeSource implements TransactionAttributeSource {

    private final Map<Object, cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute> attributeCache = new ConcurrentHashMap<>(1024);

    private static final cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute NULL_TRANSACTION_ATTRIBUTE = new DefaultTransactionAttribute() {
        @Override
        public String toString() {
            return "null";
        }
    };

    @Override
    public cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute getTransactionAttribute(Method method, Class<?> targetClass) {
        if (method.getDeclaringClass() == Object.class) {
            return null;
        }
        Object cacheKey = getCacheKey(method, targetClass);
        cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute cached = this.attributeCache.get(cacheKey);
        if (null != cached) {
            if (cached == NULL_TRANSACTION_ATTRIBUTE) {
                return null;
            } else {
                return cached;
            }
        } else {
            cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute txAttr = computeTransactionAttribute(method, targetClass);
            if (null == txAttr) {
                this.attributeCache.put(cacheKey, NULL_TRANSACTION_ATTRIBUTE);
            } else {
                this.attributeCache.put(cacheKey, txAttr);
            }
            return txAttr;
        }
    }

    protected Object getCacheKey(Method method, Class<?> targetClass) {
        return new MethodClassKey(method, targetClass);
    }

    protected cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute computeTransactionAttribute(Method method, Class<?> targetClass) {
        if (!Modifier.isPublic(method.getModifiers())) {
            return null;
        }
        cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute txAttr = findTransactionAttribute(method);
        if (null != txAttr) {
            return txAttr;
        }
        return findTransactionAttribute(method.getDeclaringClass());
    }

    /**
     * 在方法上查找事务的相关属性
     */
    protected abstract cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute findTransactionAttribute(Method method);

    protected abstract TransactionAttribute findTransactionAttribute(Class<?> clazz);

}
