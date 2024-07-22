package cn.bugstack.springframework.transaction.interceptor;


import cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute;
import cn.bugstack.springframework.tx.transaction.support.DelegatingTransactionDefinition;

import java.io.Serializable;

/**
 *
 * @description {@link cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute} implementation that delegates all calls to a given target
 * {@link cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute} instance. Abstract because it is meant to be subclassed,
 * with subclasses overriding specific methods that are not supposed to simply delegate
 * to the target instance.
 * @date 2022/3/16
 *  /CodeDesignTutorials
 *
 */
public abstract class DelegatingTransactionAttribute extends DelegatingTransactionDefinition implements cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute, Serializable {

    private final cn.bugstack.springframework.tx.transaction.interceptor.TransactionAttribute targetAttribute;

    public DelegatingTransactionAttribute(TransactionAttribute targetAttribute) {
        super(targetAttribute);
        this.targetAttribute = targetAttribute;
    }

    @Override
    public boolean rollbackOn(Throwable ex) {
        return this.targetAttribute.rollbackOn(ex);
    }
}
