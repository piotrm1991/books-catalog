# Project

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

Project should start as expected

## StyleGuides

To run style check it is needed to execute `mvn checkstyle:checkstyle`

You can also install `checkstyle` plugin from Intellij IDEA marketplace. It will refactor your code on the fly to comply with configuration for this project
 Setup:
   * Go to Settings
   * Editor -> Codestyle
   * Click on gear icon and choose `Import scheme`
   * Choose Checkstyle configuration and navigate to `projectRoot`/checkstyle/google_checks_custom.xml

## Tests

To run Unit Tests:

* Compile project with maven compiler plugin
* Compile testClasses
* Navigate to surefire maven plugin. Launch tests with `surefire:test`

To run Integration Tests:

* Compile project with maven compiler plugin
* Compile testClasses
* Navigate to failsafe maven plugin. Launch tests with `failsafe:integration-test`

## Swagger

Swagger is configured for the current project. It allows you to efficiently test API you developed
Read more: https://swagger.io/tools/swagger-ui/

Swagger is up on spring boot project run under the endpoint: `localhost:8080/api/swagger-ui.html`

## Possibles issues and solutions

#### Resource folder cant be recognized as spring boot resources

Mark directory as resources with clicking on directory with mouse right button -> Mark as -> Resource Root


## Test feature

We included test feature implementation, which includes controller with core requests, service, repository and some tests.
We assume that feature will be deleted with implementation of CRUD feature that will correspond to this feature.

Our recommendation for you is to use package structure based on business entities (recipe, user, etc.) and within these packages
keep structure based on components (repository, controller, service, dto, etc.).
It will allow you to find necessary classes quicker.

## SecurityContextConfig

We disabled security context configuration because we have no security logic yet.

You have to enable autoconfiguration in `application.yml` (autoconfiguration section)