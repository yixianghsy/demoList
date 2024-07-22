package cn.bugstack.springframework.transaction.interceptor;

import cn.bugstack.springframework.tx.transaction.PlatformTransactionManager;
import cn.bugstack.springframework.tx.transaction.interceptor.TransactionAspectSupport;
import cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttributeSource;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;

/**
 *
 * @description AOP Alliance MethodInterceptor for declarative transaction
 * @date 2022/3/16
 *  /CodeDesignTutorials
 *
 */
public class TransactionInterceptor extends TransactionAspectSupport implements MethodInterceptor, Serializable {

    public TransactionInterceptor(PlatformTransactionManager ptm, TransactionAttributeSource transactionAttributeSource) {
        setTransactionManager(ptm);
        setTransactionAttributeSource(transactionAttributeSource);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return invokeWithinTransaction(invocation.getMethod(), invocation.getThis().getClass(), invocation::proceed);
    }

}
