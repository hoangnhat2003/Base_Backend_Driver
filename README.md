# Base_Backend_Drivor

Tech stack:

     - Spring boot
     - Authentication: JWT, OAuth2 | Return Access Token và Refresh Token
     - Message Queue: RabbitMQ
     - Cache: Redis, Jedis: Java Redis Client Library
     - Search Engine: ElasticSearch
     - Log Management: Elasticsearch, Logstash, Kibana
     - Docker
     - CI/CD: Travis CI, Heruko
     - Database: MySQL
     - Send message: Smack Client
     - Send SMS: Twilio
     - AWS S3 
     - Monitoring: Grafana & Prometheus 
     - VNPAY API Integration ( Payment Service )


Access Database: (Cài đặt DBeaver hoặc tạo connection mới trong MySQL WorkBench)
   - DB name: backend-drivor
   - Port: 3306
   - Host: localhost
   - Username: root
   - Password: hoangnhat


Reference document:
   
   - RabbitMQ: https://www.springcloud.io/post/2022-03/messaging-using-rabbitmq-in-spring-boot-application/#gsc.tab=0
   - Docker: https://www.docker.com/blog/kickstart-your-spring-boot-application-development/
   - Redis: https://viblo.asia/p/huong-dan-spring-boot-redis-aWj53NPGl6m
   - Elasticsearch: https://reflectoring.io/spring-boot-elasticsearch/
   - Source code example of RabbitMQ, Redis, ES: https://github.com/nhathoang07/Shop-App
   - CI/CI with Travis CI: https://dzone.com/articles/applying-cicd-to-java-apps-using-spring-boot
   - Exception Handling in Spring boot App: https://viblo.asia/p/spring-boot-15-exception-handling-exceptionhandler-restcontrolleradvice-controlleradvice-responsestatus-maGK7k2eKj2
   - Elasticsearch, Logstash, Kibana: https://www.youtube.com/watch?v=uSYExRWbC9Y  (Xây dựng hệ thống log tập trung)