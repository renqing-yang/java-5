package week5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 *  SOLID
 *  Single Responsibility :
 *  Open Close : open to extend, close to modify
 *  Liskov Substitution : cannot use car class to extend pizza class
 *  Interface Segregation : refactor large interface to small interfaces
 *  Dependency Inversion : similar to IOC / DI
 *
 *  scaling strategies
 *  Vertical scaling: scale up hardware
 *  Horizontal scaling: deploy more servers / instances
 *
 *  cookie and session
 *  1. cookie is stored in browser side
 *  2. session : data stored in server side cache / database
 *              user
 *              |
 *          Load Balancer(public ip)
 *          /       \       \
 *       server1   server2  server3
 *
 *  Monolithic(most Legacy system / some on-premise system)
 *  1. scalability
 *  2. same language for your project
 *  3. deploy entire project
 *  4. hard to maintain
 *
 *  Microservices
 *              user
 *              |
 *          api gateway(public ip)     -   security service
 *              |
 *          order service
 *       spring boot project1
 *          tomcat1 (private ip1:port1)
 *          tomcat2 (private ip1:port2)
 *
 *          /                   \
 *
 *  payment service    inventory service
 *     project2            project3
 *     tomcat3(ip2:port1)   tomcat4(ip3: port1)
 *
 *
 *      1. communication between microservices
 *          a. tcp
 *                 websocket
 *                 http, rest api
 *                 grpc
 *          b. message queue
 *     2.  discovery service / service registration  / spring cloud eureka / aws cloud map
 *          order service -> inventory-service/uri
 *          discovery service: {inventory-service: [ip3:port1]}
 *
 *          step1: order service send http request to discovery service to query inventory service ip port address
 *                 all ip: port
 *          step2: order service pick one ip and port from returned list
 *          step3: send request to ipX:port/uri
 *    3.  Api Gateway
 *          1. log
 *          2. generate unique request id / co-relation id / transaction id
 *                  a. uuid
 *                  b. database primary key / key generator
 *                  c. snowflake
 *                     long 64 digit = timestamp + machine id + process id + serial number (0 ~ 11111)
 *          3. redirect your request to security service
 *    4. Circuit Breaker
 *          service A ->  service B / 3rd party api
 *          status
 *          1. open :
 *             service A send request to service B
 *             if xx requests have failed in last yy requests -> change status to close
 *          2. close :
 *             service A return fallback result to user
 *             in the mean time, background thread keeps checking on service B -> if service B is back -> change status to open
 *    5. Security Service(Thursday)
 *    6. Message queue(Wed)
 *    7. CI/CD (next week) + daily work
 *    8. Documentation
 *    9. Database Cluster(Tuesday)
 *    10. Cache
 *          1. local cache (in tomcat / using concurrent hashmap)
 *          2. global cache (redis / memcache / ..)
 *                  cache aside
 *                  service  -  cache
 *                      |
 *                    DB
 *                  a. read
 *                     if find in cache -> return data
 *                     if not -> read from DB -> save to cache(set TTL, 30s, 1min, 10min, 1h) -> return data
 *                  b. write
 *                      invalidate that id in cache
 *                      update database
 *
 *                  user1       invalidate that id1 in cache                                    update db
 *                                      |                                                          |
 *                  timeline ---------------------------------------------------------------------------------------------------------------------------------------------
 *                                                      |           |                 |
 *                  user2                          read id1  load old id1 from db   save to cache
 *
 *
 *
 *                  read through + write through
 *                  service - cache - database
 *
 *
 *
 *
 *
 */

class MultithreadingExampleInMicroservice {
    public static void main(String[] args) {
        CompletableFuture<String>[] completableFutures = new CompletableFuture[3];
        completableFutures[0] = CompletableFuture.supplyAsync(() -> service(2000, "service 1"));
        completableFutures[1] = CompletableFuture.supplyAsync(() -> service(5000, "service 2"));
        completableFutures[2] = CompletableFuture.supplyAsync(() -> service(3000, "service 3"));
        String res = CompletableFuture.allOf(completableFutures)
                .thenApply(VOID ->
                        Arrays.stream(completableFutures)
                                .map(CompletableFuture::join)
                                .collect(
                                        StringBuilder::new,
                                        StringBuilder::append,
                                        StringBuilder::append
                                )
                                .toString()
                )
                .orTimeout(6, TimeUnit.SECONDS)
                .join();
        System.out.println(res);
    }

    private static String service(int time, String name) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return name;
    }
}