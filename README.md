# Base_Backend_Drivor

Project xây dựng Backend API cho hệ thống đặt xe

Tech stack:
   - Spring boot, JPA, Hibernate, Postgres
   - Authentication: JWT, OAuth2 (Connect with Facebook, Google)
   - Message Queue: RabbitMQ
   - Cache: Redis
   - Search Engine: ElasticSearch
   - Send real-time message: WebSocket
   - VNPAY API Integration ( Payment Service )
   - AWS services: EC2,S3
   - Docker
   - CI/CD: Jenkins

Access Database Local: (Cài đặt DBeaver)
   - DB name: booking_db
   - Port: 5432
   - Host: localhost
   - Username: postgres
   - Password: password


Reference document:

   - RabbitMQ:  [Config RabbitMQ and and start with basic example](https://www.springcloud.io/post/2022-03/messaging-using-rabbitmq-in-spring-boot-application/#gsc.tab=0) ||
                [Example of direct exchange in RabbitMQ](https://gpcoder.com/6925-su-dung-direct-exchange-trong-rabbitmq/)
   - (Để truy cập vào RabbitMQ Web Admin, type command: `docker exec [CONTAINER_NAME] rabbitmq-plugins enable rabbitmq_management`, sau đó vào host: `http://localhost:15672`
       Default account : Username: guest/Password: guest
     )
   - Docker: [Basic example of Docker and How to dockerize spring boot app](https://www.docker.com/blog/kickstart-your-spring-boot-application-development/)
   - Redis: [Redis example with Spring boot](https://viblo.asia/p/huong-dan-spring-boot-redis-aWj53NPGl6m) || [Basic about Caching strategies](https://viblo.asia/p/redis-spring-boot-cache-aside-design-pattern-1Je5E6LLKnL)
   - (Sử dụng Redis GUI để manage keys
       . Cách dowload Another Redis Desktop Manager - A GUI client for Redis:
       Right click Command Prompt -> Run as administrator -> Type command : winget install qishibo.AnotherRedisDesktopManager
     )
   - Elasticsearch: [Example of Elasticsearch with Spring boot](https://reflectoring.io/spring-boot-elasticsearch/)
   - View data from Elasticsearch: `http://localhost:9200/{indexName}/_search?pretty`
   - Exception Handling in Spring boot App: [Handle exception in Spring boot](https://viblo.asia/p/spring-boot-15-exception-handling-exceptionhandler-restcontrolleradvice-controlleradvice-responsestatus-maGK7k2eKj2)
   - WebSocket: [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket/) || [Implement Websocket Server and Websocket client using Java](https://viblo.asia/p/su-dung-java-tao-websocket-de-tao-ung-dung-chat-gDVK2QAw5Lj) / 
   - WebSocket with Spring boot: [Integrating websocket with spring boot](https://programming.vip/docs/four-ways-of-integrating-websocket-with-spring-boot.html)
