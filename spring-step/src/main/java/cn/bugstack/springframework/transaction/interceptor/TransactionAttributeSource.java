package cn.bugstack.springframework.transaction.interceptor;

import cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute;
import cn.bugstack.springframework.tx.transaction.interceptor.TransactionInterceptor;

import java.lang.reflect.Method;

/**
 *
 * @description Strategy interface used by {@link TransactionInterceptor} for metadata retrieval.
 * @date 2022/3/16
 *  /CodeDesignTutorials
 *
 */
public interface TransactionAttributeSource {

    TransactionAttribute getTransactionAttribute(Method method, Class<?> targetClass);

}
