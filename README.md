## Automation Framework

#### About

This is an API automation test framework built using Java, Junit, Cucumber and RestAssured. It is designed for multiple use cases, presently being used for testing the twitter api.

#### Framework advantages
- Steps are generic and therefore are reusable to test many APIs
- Code is relatively simple and makes use of many libraries to do the hard work e.g. RestAssured, Jackson, JsonPath etc
- Can do many different request types with very little code
- Simple sharing of step defs using pico container

#### Framework disadvantages
- Generic steps are arguably less descriptive
- Setup with json payloads in mind (as opposed to xml/pojos)

#### Notes
- Schema validator requires '"additionalProperties": false' set in file in order to compare the schema like for like and not ignore keys
- Rest assured requires 'scribejava' for authorizing OAuth 1.0 and 2.0 - see pom.xml
- Newer versions of cucumber may not be compatible with the intellij cucumber feature-file plugin hence version 4.6.0 of cucumber is used
- CommonState class must be in step def package to make using of the pico container dependency injection

#### Potential expansions
1. Use TestNG to make use of the capability to execute scenarios from inside different feature files (including rows from scenario-outlines) in parallel - JUnit can only run feature files in parallel
2. Share state between step definitions using Spring dependency injection -(https://grasshopper.tech/1494/) -(https://natritmeyer.com/howto/cucumber-jvm-dependency-injection-with-spring/)
3. Use spring profiles for loading specific environment property file -(https://stackoverflow.com/questions/41263105/how-to-load-property-file-based-on-spring-profiles) -(https://www.baeldung.com/spring-profiles)
4. Build more payload request types e.g. xml/pojos