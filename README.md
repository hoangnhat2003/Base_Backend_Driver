# Base_Backend_Drivor

Project xây dựng Backend API cho hệ thống đặt xe 

Tech stack:

     - Spring boot, JPA, Hibernate
     - Authentication: JWT, OAuth2 (Connect with Facebook, Google)
     - Message Queue: RabbitMQ
     - Cache: Redis
     - Search Engine: ElasticSearch
     - Log Management: Elasticsearch, Logstash, Kibana
     - Docker
     - CI/CD: Circle CI, Heruko
     - Database: MySQL
     - Send message: Smack Client
     - Send SMS: Twilio
     - AWS: S3, RDS
     - Monitoring: Grafana & Prometheus 
     - VNPAY API Integration ( Payment Service )


Access Database Local: (Cài đặt DBeaver hoặc tạo connection mới trong MySQL WorkBench)
   - DB name: backend-drivor
   - Port: 3306
   - Host: localhost
   - Username: root
   - Password: hoangnhat

Reference document:
   
   - RabbitMQ: https://www.springcloud.io/post/2022-03/messaging-using-rabbitmq-in-spring-boot-application/#gsc.tab=0
               https://gpcoder.com/6925-su-dung-direct-exchange-trong-rabbitmq/
     (Để truy cập vào RabbitMQ Web Admin, type command: `docker exec [CONTAINER_NAME] rabbitmq-plugins enable rabbitmq_management`, sau đó vào host: `http://localhost:15672`
       Default account : Username: guest/Password: guest
     )
   - Docker: https://www.docker.com/blog/kickstart-your-spring-boot-application-development/
   - Redis: https://viblo.asia/p/huong-dan-spring-boot-redis-aWj53NPGl6m || https://viblo.asia/p/redis-spring-boot-cache-aside-design-pattern-1Je5E6LLKnL
   - (Sử dụng Redis GUI để manage keys
       . Cách dowload Another Redis Desktop Manager - A GUI client for Redis:
       Right click Command Prompt -> Run as administrator -> Type command : winget install qishibo.AnotherRedisDesktopManager
     )
   - Elasticsearch: https://reflectoring.io/spring-boot-elasticsearch/
   - Source code example of RabbitMQ, Redis, ES: https://github.com/hoangnhat2003/Shop-App
   - CI/CD with Circle CI: https://chatbotsmagazine.com/create-a-ci-cd-pipeline-with-circleci-to-deploy-your-bot-in-a-docker-image-to-heroku-32f5dfe887
   - Exception Handling in Spring boot App: https://viblo.asia/p/spring-boot-15-exception-handling-exceptionhandler-restcontrolleradvice-controlleradvice-responsestatus-maGK7k2eKj2
   - Elasticsearch, Logstash, Kibana: https://www.youtube.com/watch?v=uSYExRWbC9Y  (Xây dựng hệ thống log tập trung)
