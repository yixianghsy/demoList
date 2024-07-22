package cn.bugstack.springframework.transaction;

import cn.bugstack.springframework.tx.transaction.TransactionException;

/**
 *
 * @description Interface that specifies an API to programmatically manage transaction
 * savepoints in a generic fashion. Extended by TransactionStatus to
 * expose savepoint management functionality for a specific transaction.
 * @date 2022/3/16
 *  /CodeDesignTutorials
 *
 */
public interface SavepointManager {

    Object createSavepoint() throws cn.bugstack.springframework.tx.transaction.TransactionException;

    void rollbackToSavepoint(Object savepoint) throws cn.bugstack.springframework.tx.transaction.TransactionException;

    void releaseSavepoint(Object savepoint) throws TransactionException;

}
