package cn.bugstack.springframework.transaction;

import cn.bugstack.springframework.tx.transaction.TransactionDefinition;
import cn.bugstack.springframework.tx.transaction.TransactionException;
import cn.bugstack.springframework.tx.transaction.TransactionStatus;

/**
 *
 * @description This is the central interface in Spring's imperative transaction infrastructure.
 * Applications can use this directly, but it is not primarily meant as an API:
 * Typically, applications will work with either TransactionTemplate or
 * declarative transaction demarcation through AOP.
 * @date 2022/3/16
 *  /CodeDesignTutorials
 *
 */
public interface PlatformTransactionManager {

    cn.bugstack.springframework.tx.transaction.TransactionStatus getTransaction(TransactionDefinition definition) throws cn.bugstack.springframework.tx.transaction.TransactionException;

    void commit(cn.bugstack.springframework.tx.transaction.TransactionStatus status) throws cn.bugstack.springframework.tx.transaction.TransactionException;

    void rollback(TransactionStatus status) throws TransactionException;

}
