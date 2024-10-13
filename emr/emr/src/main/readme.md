### Spin mysql db
- docker run -d -e MYSQL_ROOT_PASSWORD=secret -e MYSQL_DATABASE=taskdb02 --name mysqldb01 -p 3308:3306 mysql:8.0