package cn.bugstack.springframework.transaction;

import cn.bugstack.springframework.tx.transaction.SavepointManager;
import cn.bugstack.springframework.tx.transaction.TransactionExecution;

import java.io.Flushable;
import java.io.IOException;

/**
 *
 * @description Default implementation of the TransactionStatus
 * interface, used by AbstractPlatformTransactionManager Based on the concept
 * of an underlying "transaction object".
 * @date 2022/3/16
 *  /CodeDesignTutorials
 *
 */
public interface TransactionStatus extends TransactionExecution, SavepointManager, Flushable {

    boolean hasSavepoint();

    @Override
    void flush() throws IOException;

}
