# spring-rest

This is a simple Spring application using Rest API.

[SpringRestApp](https://github.com/alyonazakharova/spring-rest/tree/master/SpringRestApp) is server side of the application and [JavaFXCLient](https://github.com/alyonazakharova/spring-rest/tree/master/JavaFXClient) is client side. Both are maven projects.

Database used for the project is MySQL. In [application.properties](https://github.com/alyonazakharova/spring-rest/blob/master/SpringRestApp/src/main/resources/application.properties) you should specify the name of the database and username and password to access the database.

You can perform the following steps in MySQL Command Line Client:
```
create database wholesale;
create user 'usr'@'%' identified by 'password';
grant all on wholesale.* to 'usr'@'%';
```
Client is a simple JavaFX project.
If you have Java 11 or later, make sure that you have JavaFX SDK installed.
You will need to add path to javafx-sdk\lib in File-Project Structure-Libraries and set VM options in Edit Configurations the following way:
```
--module-path D:/javafx-sdk-11.0.2/lib --add-modules=javafx.controls,javafx.fxml,javafx.graphics
```
