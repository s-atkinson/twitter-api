POM Dependencies 

GroupId (package name) - Uniquely identify the project in maven repository
ArtifactId (project name)- A file, usually a jar, that gets deployed to a maven repository

Advantages of pojo classes - verifies the structure of the body is intact whereas JsonPath will only verify the specified key/value pair

**Authorization Oath 1.0 in RestAssured**

Explain how these are used...
        
        <dependency>
        <groupId>com.github.scribejava</groupId>
            <artifactId>scribejava-core</artifactId>
            <version>2.5.3</version>
        </dependency>
        <dependency>
            <groupId>com.googlecode.json-simple</groupId>
            <artifactId>json-simple</artifactId>
            <version>1.1</version>
        </dependency>
        
Authorization oath 2.0 bearer token (access token)

Placed in the header of request
        
 Serialization / Deserialization
 
 At least one of these dependencies are required in order to deserialize response body to pojo class. When both are
 installed jackson is used by default. Use http://www.jsonschema2pojo.org/ to get your pojo from json
 
 <dependency>
     <groupId>com.fasterxml.jackson.core</groupId>
     <artifactId>jackson-databind</artifactId>
     <version>2.11.3</version>
 </dependency>
<dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.8.6</version>
        </dependency>