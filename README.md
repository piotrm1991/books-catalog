# Project Books Catalog
 
Application allows user to save books, with its author, publisher, custom status and its placement. 
The for this application is [here](https://github.com/piotrm1991/books-catalog-front-angular):

## Environment:

```
Language:   Java 17
DB:         MySQL 8.0
Deploy:     Docker 20.10.17
```

## Build application

1. Create `.env.local` based on `example.env.dist` file and fill it with variables' values (you can leave it as is for local development purposes as it already contains some example values).

2. Build the Docker image for your application by running the following command in project directory:
> docker-compose up -d --build

Note: after the first execution you can simply launch db with
> docker-compose up -d

This will start database in separate docker container.

Run Application with Intellij IDEA functionality, edit configuration - set active profile to local.

Add `VM option`:
> -Dspring.profiles.active=local

Project should start as expected

## StyleGuides

To run style check it is needed to execute `mvn checkstyle:checkstyle`

You can also install `checkstyle` plugin from Intellij IDEA marketplace. 