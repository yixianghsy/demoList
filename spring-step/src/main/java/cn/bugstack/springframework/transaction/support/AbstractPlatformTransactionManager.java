package cn.bugstack.springframework.transaction.support;

import cn.bugstack.springframework.tx.transaction.PlatformTransactionManager;
import cn.bugstack.springframework.tx.transaction.TransactionDefinition;
import cn.bugstack.springframework.tx.transaction.TransactionException;
import cn.bugstack.springframework.tx.transaction.TransactionStatus;
import cn.bugstack.springframework.tx.transaction.support.DefaultTransactionDefinition;
import cn.bugstack.springframework.tx.transaction.support.DefaultTransactionStatus;

import java.io.Serializable;

/**
 *
 * @description 抽象事务管理器平台
 * @date 2022/3/16
 *  /CodeDesignTutorials
 *
 */
public abstract class AbstractPlatformTransactionManager implements PlatformTransactionManager, Serializable {

    @Override
    public final TransactionStatus getTransaction(TransactionDefinition definition) throws TransactionException {
        Object transaction = doGetTransaction();
        if (null == definition) {
            definition = new DefaultTransactionDefinition();
        }
        if (definition.getTimeout() < TransactionDefinition.TIMEOUT_DEFAULT) {
            throw new TransactionException("Invalid transaction timeout " + definition.getTimeout());
        }
        // 暂定事务传播为默认的行为
        cn.bugstack.springframework.tx.transaction.support.DefaultTransactionStatus status = newTransactionStatus(definition, transaction, true);
        // 开始事务
        doBegin(transaction, definition);
        return status;
    }

    protected cn.bugstack.springframework.tx.transaction.support.DefaultTransactionStatus newTransactionStatus(TransactionDefinition definition, Object transaction, boolean newTransaction) {
        return new cn.bugstack.springframework.tx.transaction.support.DefaultTransactionStatus(transaction, newTransaction);
    }

    @Override
    public void commit(TransactionStatus status) throws TransactionException {
        if (status.isCompleted()) {
            throw new IllegalArgumentException(
                    "Transaction is already completed - do not call or rollback more than once per transaction");
        }
        cn.bugstack.springframework.tx.transaction.support.DefaultTransactionStatus defStatus = (cn.bugstack.springframework.tx.transaction.support.DefaultTransactionStatus) status;
        processCommit(defStatus);
    }

    private void processCommit(cn.bugstack.springframework.tx.transaction.support.DefaultTransactionStatus status) throws TransactionException {
        doCommit(status);
    }

    @Override
    public void rollback(TransactionStatus status) throws TransactionException {
        if (status.isCompleted()) {
            throw new IllegalArgumentException(
                    "Transaction is already completed - do not call commit or rollback more than once per transaction");
        }
        cn.bugstack.springframework.tx.transaction.support.DefaultTransactionStatus defStatus = (cn.bugstack.springframework.tx.transaction.support.DefaultTransactionStatus) status;
        processRollback(defStatus, false);
    }

    private void processRollback(cn.bugstack.springframework.tx.transaction.support.DefaultTransactionStatus status, boolean unexpected) {
        doRollback(status);
    }

    /**
     * 获取事务
     */
    protected abstract Object doGetTransaction() throws TransactionException;

    /**
     * 提交事务
     */
    protected abstract void doCommit(cn.bugstack.springframework.tx.transaction.support.DefaultTransactionStatus status) throws TransactionException;

    /**
     * 事务回滚
     */
    protected abstract void doRollback(DefaultTransactionStatus status) throws TransactionException;

    /**
     * 开始事务
     */
    protected abstract void doBegin(Object transaction, TransactionDefinition definition) throws TransactionException;

}
