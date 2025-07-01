package week5;


/**
 * CAP
 *  Consistency
 *  Availability
 *  Partition Tolerance
 *
 * BASE
 *  Basic Availability
 *  Soft Stage
 *  Eventually Consistency
 *   *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  Single Leader Cluster
 *
 *         user
 *          |
 *  Master/Leader/Primary/Write DB
 *      |
 *  Slave/Follower/Secondary/Read DB1, DB2, DB3
 *
 *  write : primary DB + 0 ~ N follower DB
 *  read  : read from primary or read from follower DB
 *
 *  Multi Leaders Cluster
 *
 *  ALL Leader / Leaderless Cluster: Cassandra
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  Random IO : B+ tree
 *  Sequential IO : Log append + immutable log file
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  sharding:
 *      db1:  id1 to id10k
 *      db2:  id10k+1 to id20k
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  MongoDB cluster
 *                      |
 *                  Mongos     -  config server
 *            /       \       \
 *        sharding1    2       3
 *        primary
 *        secondary
 *        secondary
 *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *  *
 *  Cassandra(LSM tree)
 *
 *  Cassandra Node(not required)
 *
 *    write  -> memtable (cache) -> dump data into SSTable(immutable file)
 *          |
 *       commit log(disk)
 *
 *                  SSTable4(doesn't contain id=1)
 *          /               |           \
 *      SSTable1        SSTable2        SSTable3            SStable5
 *      id=1                            id=1 (delete)       ..
 *
 *   read -> memtable (cache) -> blooming filter
 *
 *
 *   apple
 *   [true][][][][][][][][] hash algo1
 *   [][][][][][][][][true] hash algo2
 *   [][][][true][][][][][] hash algo3
 *
 *   read apple -> arr1[idx1] && arr2[idx2] && arr3[idx3] == true
 *   read banana -> arr1[idx1] && arr2[idx2] && arr3[idx5](false) == false
 *
 *
 * Cassandra Cluster (Consistent hashing algo)
 *       Node1(1 ~ 10k)
 *
 * Node4               Node2(10k ~ 15k)
 * (40k ~ max)
 *                    Node5(15k ~ 20k)
 *
 *       Node3(20k ~ 40k)
 *
 *  Replica Factor = 3
 *  Write Consistency level = 2
 *  Read Consistency level = 2
 *
 *  RC + WC > RF
 *
 *  write ->  node4 ->  node2 (success)
 *                  ->  node5 (success)
 *                  ->  node3
 *
 *  read -> node4 -> node2
 *                -> node3
 *
 *        1. read query result from node 5
 *           read hash value from node2
 *        2. compare result with hash value -> same -> return data
 *        3. if not same
 *              trigger "read repair"
 *        4. return the result
 * * * * * * * * * * * * * *
 *
 * DB1 -> 1, 2, 3, 4
 *   |
 * DB1 -> odd
 * DB2 -> even
 *  |
 * DB1 -> 0, 3, 6, 9..
 * DB2 -> 1, 4, 7, 10..
 * DB3 -> 2, 5, 8, 11..
 * idx % 3 = DB
 *   * * * * * * * * * * * * * *
 *   Redis cluster
 *   Backup
 *      1. save snapshot of current redis instance to disk every xx time
 *      2. AOF
 *
 *   Cluster (hash slot, ~21k)
 *
 *      Leader1(0 ~ 5k)     Leader2(5k ~ 10k)           Leader3(10k ~ max)
 *      /       \               /       \               /           \
 *    follower  follower    follower    follower
 *   * * * * * * * * * * * * * *
 *
 *   1. Nosql vs SQL
 *      SQL
 *      a. transaction acid
 *      b. stable / good for bank, large community support
 *      c. normalization -> less disk usage
 *      d. join
 *      e. fixed table structure , column number, name ..
 *      ..
 *      Nosql
 *      a. sharding, scalability
 *      b. json , key value pair ...
 *      c. more disk usage / keep duplicate data
 *      d. cluster
 *
 *   2. How to improve db performance
 *      a. execution plan + index + hint
 *      b. cache
 *      c. set up more read replica
 *
 *
 */