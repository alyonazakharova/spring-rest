# spring-rest

This is a simple Spring CRUD application using Rest API.

[SpringRestApp](https://github.com/alyonazakharova/spring-rest/tree/master/SpringRestApp) is server side of the application and [JavaFXCLient](https://github.com/alyonazakharova/spring-rest/tree/master/JavaFXClient) is client side.

Database used for the project is MySQL. In [application.properties](https://github.com/alyonazakharova/spring-rest/blob/master/SpringRestApp/src/main/resources/application.properties) you should specify the name of the database and username and password to access the database.

You can perform the following steps in MySQL Command Line Client:
```
create database wholesale;
create user 'usr'@'%' identified by 'password';
grant all on wholesale.* to 'usr'@'%';
```
More information can be found [here](https://spring.io/guides/gs/accessing-data-mysql/).

To create UI JavaFX library was used. If you use Java 11 or later, make sure that you have JavaFX SDK installed.
More detailed information you can get in the [JetBrains guide](https://www.jetbrains.com/help/idea/javafx.html?gclid=CjwKCAiA4rGCBhAQEiwAelVti3vD6AhncQvjPmD3Qs8e6fpu1IAipPj8nzHoRuy3qJ-ACYfqENhsKxoCVPUQAvD_BwE).
