/**
 * Read Uncommitted (dirty read, non-repeatable read, phantom read)
 * dirty read issue  :  a != b
 * user1
 *          begin tx     a=select *                 b=select *
 *             |            |                       |
 * -----------------------------------------------------------------------------> timeline
 *            |                         |                          |
 *         begin tx                update data                  commit x
 * user2
 *
 * Non-repeatable Read issue :  a != b
 * user1
 *          begin tx     a=select *                                 b=select *
 *             |            |                                           |
 * -----------------------------------------------------------------------------> timeline
 *            |                         |               |
 *         begin tx            update/delete data     commit x
 * user2
 *
 *
 * Phantom Read issue :  a != b
 * user1
 *          begin tx     a=select *                                 b=select *
 *             |            |                                           |
 * -----------------------------------------------------------------------------> timeline
 *            |                         |               |
 *         begin tx                 insert data     commit x
 * user2
 *
 *
 *
 *
 * Read Committed (non-repeatable read, phantom read)
 * no dirty read issue : a == b
 * user1
 *          begin tx     a=select *                 b=select *
 *             |            |                       |
 * -----------------------------------------------------------------------------> timeline
 *            |                         |                          |
 *         begin tx                update data                  commit x
 * user2

 * Repeatable Read  (phantom read)
 * Serializable
 *
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * MVCC - multi version concurrency control
 *
 * id, name,  row_id, tx_id, rollback pointer
 *  1  Tom ,   xxx      1          |
 *                                 |
 *                              id, name,  row_id, tx_id, rollback pointer
 *                              1  Jerry ,   xxx      5          null
 * tx_id : ascending order , create new tx_id for new tx
 *
 * read committed isolation level example
 *      begin tx : tx_id = 3
 *      select * = a : get read view(committed tx id : [1]) -> return 1, tom
 *      select * = b : get read view(committed tx id : [1, 5]) -> return 1, jerry
 *      commit tx
 *
 * repeatable read isolation level example
 *      begin tx : tx_id = 3
 *      select * = a : get read view(committed tx id : [1]) -> return 1, tom
 *      select * = b : use existing read view (tx id: [1]) -> return 1, tom
 *      commit tx
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Lock
 * 1. Write lock / exclusive lock
 *      update/insert/delete query
 *      select... for update
 * 2. Read lock / share lock
 *      select ... for share
 *
 * table
 * id
 * 1
 * 2
 * 4
 *
 * Read Committed/ Uncommitted
 *      select where id >= 1 and id <= 5 for update
 *      id = 1 (ex)
 *      id = 2 (ex)
 *      id = 4 (ex)
 * Repeatable Read : has another gap lock
 *      select where id >= 1 and id <= 5 for update
 *      id = 1 (ex)
 *      id = 2 (ex)
 *      (2, 4) (gap lock)
 *      id = 4 (ex)
 * Serializable
 *      all select statement will use share lock by default
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * Optimistic lock -> lost update
 *
 *      user1                   user2
 *     select a=1             select a=1
 *     update a=2             update a=2
 *     get lock
 *     commit (release lock)
 *                             get lock
 *                             commit
 *
 *      user1                   user2
 *     select a=1             select a=1
 *            v=1                    v=1
 *     update a=2,v=2         update a=2, v=2
 *     where v=1              where v=1
 *     get lock
 *     commit (release lock)
 *                             get lock
 *                             commit (fail, return error and ask backend to retry)
 *
 *                             select a=2
 *                                    v=2
 *                             update a=3, v=3 where v=2
 *                             get lock
 *                             commit
 *
 *
 *
 *
 *
 *
 *
 */