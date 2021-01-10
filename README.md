## Automation Framework

#### About

This is an API automation test framework built using Java, Junit, Cucumber and RestAssured. It is designed for multiple use cases, presently being used for testing the twitter api.

#### Further Requirements Needed

The following features are not currently present should be added to the framework:

1. Run tests in parallel -(https://cucumber.io/docs/guides/parallel-execution/)
2. Share state between step definitions using Spring dependency injection -(https://grasshopper.tech/1494/) -(https://natritmeyer.com/howto/cucumber-jvm-dependency-injection-with-spring/)
3. Use spring profiles for loading specific environment property file -(https://stackoverflow.com/questions/41263105/how-to-load-property-file-based-on-spring-profiles) -(https://www.baeldung.com/spring-profiles)

#### Framework Advantages

- Steps are generic and therefore are reusable to test many APIs
- Code is relatively simple and makes use of many libraries e.g. RestAssured, Jackson etc
- Can do many different request types with very little code

#### Framework Disadvantages

- Generic steps are arguably less descriptive in terms of BDD understanding
- Designed with mostly json payloads in mind (as opposed to xml/pojos)
- Not feature complete (see 'Further Requirements Needed' section)


#### Notes

- Schema validator requires '"additionalProperties": false' set in file in order to compare the schema like for like
- Rest assured requires 'scribejava' for authorizing OAuth 1.0 and 2.0
- Newer versions of cucumber may not be compatible with the intellij cucumber feature file plugin hence version 4.6.0 is used
- Creating pojos for payloads appears to take a lot more effort than writing and saving your payloads to a .json file

