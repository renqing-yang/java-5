package week5;

/**
 * Why message queue
 *          user
 *           |
 *          service -> message queue -> consumer
 *       1. user send request to service
 *       2. service send msg to mq
 *       3. service send response to user
 *
 *   a. we can handle more request
 *   b. Asynchronous communication between microservices
 * Why kafka
 *   a. fast , 1 million msg / s
 * Why sqs
 *   a. task / message takes long time to finish
 *   b. aws
 * Kafka vs sqs
 * What is CDC outbox pattern
 * How do you handle global transaction
 * What is Saga pattern
 * How do you handle duplicate msg situation
 * What is idempotent service
 * How does kafka re balance work
 * * * * * * * * * * * * * * *
 * Active MQ, Rabbit MQ
 *
 * Kafka
 *
 * AWS SQS, SNS
 *
 *  * * * * * * * * * * * * * * * * * * * * * * *
 *  queue model
 *  pub-sub model
 *  * * * * * * * * * * * * * * * * * * * * * * *
 *  SQS(FIFO, standard)
 *  producer            message queue           consumer
 *  server1             queue1(ip1 port1)       server4[m1]
 *  server2             [m1][m2][m3]            server5[m2]
 *  server3
 *
 *  visibility timeout
 *
 * publisher -> sns -> sqs -> sub
 *                  -> sqs -> sub
 *  * * * * * * * * * * * * * * * * * * * * * * *
 *  Kafka
 *  producer              broker1                                consumer group
 *  server1             Topic1 Partition1(leader) [m1][m3]      consumer group1  consumer1 (pull msg from topic1, p2)
 *  server2             Topic1 Partition2 [m2][m5]              consumer group1  consumer2 (pull msg from topic1, p1)
 *  server3             Topic2 Partition1                       consumer group1  consumer3 (idle)
 *
 *                        broker2
 *                      Topic1 Partition1(follower)
 *
 *
 *  in each consumer group
 *  consumer can pull 1 to N partitions
 *  partition  m  -  1    consumer in consumer group
 *
 *  queue model = 1 consumer group
 *  pub - sub model = >1 consumer groups
 *  *  * * * * * * * * * * * * * * * * * * * * * * *
 *  Dead Letter Queue
 *  1. if message has failed xx times at consumer side
 *  2. push message to dead letter queue and remove it from your standard queue
 *  *  * * * * * * * * * * * * * * * * * * * * * * *
 *  Duplicate Message or Message executed only once
 *   1. make consumer idempotent
 *   2. global unique message id -> for duplicate msg they should have same id
 *   3. SNS -> deduplicate
 *   4. cache to save processed id
 *          a. consumer pull msg from mq
 *          b. handle msg if msg not in redis
 *          c. save id to redis cache
 *          d. commit to mq
 *
 *  *  * * * * * * * * * * * * * * * * * * * * * * *
 *  Global Transaction between message queue and db
 *
 *  user
 *   |
 *  service -> message queue
 *   |
 *  DB
 *
 *  1. insert data to db
 *  2. commit db tx
 *  3. send message to mq
 *  4. commit mq tx
 *
 *  CDC + outbox pattern
 *  user
 *   |
 *  service
 *   |
 *  DB -> change data capture service -> message queue
 *  service
 *  1. insert data to db
 *     insert msg  to outbox table
 *  2. commit db
 *
 *  CDC service
 *  1. keep pulling msg from outbox table
 *  2. send to msg
 *  3. remove it / mark sent status in outbox table
 *
 *  *  * * * * * * * * * * * * * * * * * * * * * * *
 *  Global Transaction among DBs
 *
 *  two phase
 *     1. phase1 : check db connection, send query check if query can be executed in both db
 *     2. phase2 : save commit msg to commit log
 *                 send commit msg to both db
 *
 *            coordinator
 *          /           \
 *         DB1          DB2
 *
 *
 *  SAGA
 *
 *      service1   - mq - service2 - mq - service3
 *        |                 |                   |
 *       db1               db2                  db3
 *
 *      1. service 1 -10 quantity commit tx in db1
 *      2. service 1 send msg to mq
 *      3. service 2 pull messages , commit tx in db2
 *          if failed
 *              send +10 quantity tx to a rollback queue / queue -> service1 -
 *
 */