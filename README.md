# Simple Users Server

This is a small and very simple backend service for practicing in writing integration unit tests.

All the functionality of this services:  
* Create and save new users - registration  
* Delete user  
* Fetch users session token - login  
* Easter egg - random joke from Chuck N.

## How to run:
1) Clone repository
2) Open project in IntelliJ IDEA 
3) Just run Application.class

or in command line:

1) Clone repository  
2) Navigate to project `cd /path/to/simple-user-server`
3) Run Maven package `mvn clean package`
4) Run app `java -jar target/simple-users-server-1.0.0-SNAPSHOT.jar`

## MVC  
The MVC architectural pattern has existed for a long time in software engineering. All most all the languages use MVC 
with slight variation, but conceptually it remains the same.
MVC stands for Model, View and Controller. MVC separates application into three components - Model, View and Controller

**Model**: Model represents shape of the data and business logic. It maintains the data of the application. 
Model objects retrieve and store model state in a database  

**View**: View is a user interface. View display data using model to the user and also enables them to modify the data  

**Controller**: Controller handles the user request. Typically, user interact with View, which in-turn raises 
appropriate URL request, this request will be handled by a controller. The controller renders the appropriate view with 
the model data as a response.

## Spring Boot Application concepts
Spring Boot offers a fast way to build applications. It looks at your classpath and at beans you have configured, makes 
reasonable assumptions about what you’re missing, and adds it. With Spring Boot you can focus more on business features 
and less on infrastructure.

`@SpringBootApplication` is a convenience annotation that adds all of the following:

* `@Configuration` tags the class as a source of bean definitions for the application context.
* `@EnableAutoConfiguration` tells Spring Boot to start adding beans based on classpath settings, other beans, and various property settings. For example, if spring-webmvc is on the classpath this flags the application as a web application and activates key behaviors such as setting up a DispatcherServlet.
* `@ComponentScan` tells Spring to look for other components, configurations, and services in the hello package, allowing it to find the controllers

The class which is flagged as a `@RestController`, meaning it’s ready for use by Spring MVC to handle web requests. 
`@RequestMapping` maps `/` to the `index()` method. When invoked from a browser or using curl on the command line, 
the method returns pure text. That’s because `@RestController` combines `@Controller` and `@ResponseBody`, two annotations 
that results in web requests returning data rather than a view.

#### JWT
JSON Web Token (JWT) is an open standard (RFC 7519) that defines a compact and self-contained way for securely 
transmitting information between parties as a JSON object. This information can be verified and trusted because it is 
digitally signed. JWTs can be signed using a secret (with the HMAC algorithm) or a public/private key pair using RSA or ECDSA.

#### MockMVC
The `MockMvc` comes from Spring Test and allows you, via a set of convenient builder classes, to send HTTP requests into 
the `DispatcherServlet` and make assertions about the result. Note the use of the `@AutoConfigureMockMvc` together with 
`@SpringBootTest` to inject a `MockMvc` instance. Having used `@SpringBootTest` we are asking for the whole application 
context to be created. An alternative would be to ask Spring Boot to create only the web layers of the context using 
the `@WebMvcTest` Spring Boot automatically tries to locate the main application class of your application in either 
case, but you can override it, or narrow it down, if you want to build something different.
