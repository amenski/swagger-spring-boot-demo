# swagger-spring-boot-demo

**Why Swagger, You May Ask?**

Earlier, there were no industry standards for developing APIs or documenting them. Swagger emerged as an approach to building APIs and soon became the most popular framework for this purpose. Swagger is the largest framework for designing APIs using a common language and enabling the development across the whole API lifecycle, including documentation, design, testing, and deployment. The framework provides **a set of tools that help programmers generate client or server code and many more.**

# Code generation 
```xml

<plugin>
	<groupId>io.swagger</groupId>
	<artifactId>swagger-codegen-maven-plugin</artifactId>
	<version>${swagger-codegen-maven-plugin-version}</version>
	<executions>
		<execution>
			<phase>generate-sources</phase>
			<goals>
				<goal>generate</goal>
			</goals>
			<configuration>
				<inputSpec>${project.basedir}/swagger/rest-api-demo-spec-definition.yml</inputSpec>
				<language>spring</language>
				<generateApis>true</generateApis>
				<generateModels>true</generateModels>
				<generateModelDocumentation>false</generateModelDocumentation>
				<generateModelTests>false</generateModelTests>
				<generateSupportingFiles>true</generateSupportingFiles>
				<modelPackage>dzone.aman.swagger.model</modelPackage>
				<apiPackage>dzone.aman.swagger.api</apiPackage>
				<configOptions>
					<dateLibrary>java8</dateLibrary>
					<sourceFolder>swagger</sourceFolder>
					<interfaceOnly>true</interfaceOnly>
					<useTags>true</useTags>
				</configOptions>
				<output>${project.build.directory}/generated-sources/java</output>
			</configuration>
		</execution>
	</executions>
</plugin>

```
 You can check for more from [dzone.com](https://dzone.com/articles/openapi-swagger-and-spring-boot-integration)
