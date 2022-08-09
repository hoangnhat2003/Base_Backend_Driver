# Base_Backend_Drivor

Project xây dựng Backend API cho hệ thống đặt xe

Tech stack:

Backend: 
   - Spring boot, JPA, Hibernate, MySQL
   - Authentication: JWT, OAuth2 (Connect with Facebook, Google)
   - Message Queue: RabbitMQ, Cache: Redis, Search Engine: ElasticSearch
   - Send real-time message: WebSocket
   - VNPAY API Integration ( Payment Service )
   - AWS services: EC2,S3,RDS
Deployment:
   - Docker, Heroku
   - CI/CD: Jenkins

Access Database Local: (Cài đặt DBeaver hoặc tạo connection mới trong MySQL WorkBench)
   - DB name: backend-drivor
   - Port: 3306
   - Host: localhost
   - Username: root
   - Password: hoangnhat


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
   - Source code example of RabbitMQ, Redis, ES: [Source code example of RabbitMQ, Redis, RS](https://github.com/hoangnhat2003/Shop-App)
   - CI/CD with Circle CI: [Implement CI/CD with Circle CI and Heroku](https://chatbotsmagazine.com/create-a-ci-cd-pipeline-with-circleci-to-deploy-your-bot-in-a-docker-image-to-heroku-32f5dfe887)
   - Exception Handling in Spring boot App: [Handle exception in Spring boot](https://viblo.asia/p/spring-boot-15-exception-handling-exceptionhandler-restcontrolleradvice-controlleradvice-responsestatus-maGK7k2eKj2)
   - Elasticsearch, Logstash, Kibana: [Example of ELK stack. Build a Log Management System](https://www.youtube.com/watch?v=uSYExRWbC9Y)
   - Smack Client, WebSocket: [Source code example of Smack Client and WebSocket](https://github.com/smartinrub/spring-xmpp-websocket-reactjs) || [Build Your Own Web Chat Application With Xmpp](https://sergiomartinrubio.com/projects/build-your-own-web-chat-application-with-xmpp/)
   - WebSocket: [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket/) || [Implement Websocket Server and Websocket client using Java](https://viblo.asia/p/su-dung-java-tao-websocket-de-tao-ung-dung-chat-gDVK2QAw5Lj) / 
   - [Integrating websocket with spring boot](https://programming.vip/docs/four-ways-of-integrating-websocket-with-spring-boot.html)