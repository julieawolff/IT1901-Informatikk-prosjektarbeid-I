# Springboot

## RestServer
Spring Boot offers a development model that streamlines Java development, eliminating tedious steps and reducing boilerplate code. Using Spring Boot as our REST API for the application provided advantages for our group. We gained the possibility of making an application that communicate through a remote access class in our ui module, and we were able to make the data saving process to happen remotely. It simplifies the development and unit testing, reducing time and effort by offering opinionated defaults configuration. Additionally, Spring Boot provides embedded HTTP servers and plugins for easier development and testing using build tools like Maven. This not only enhances productivity but also ensures a smooth and efficient development experience, especially for remote saving through our UI module's remote access class.

*More information about how to run our application below.*

## Our experience of Springboot:
At the beginning of release 3, we found it challengeing to learn and understand the use of Spring Boot, and how it would make it "easier" for us to develop an app. A lot of time was used to understand how Spring Boot would process information, and how it would interact with our application.

When we had implemented our Spring Boot, we had to understand how our controllers would gain access to the methods - hence our RemotePlantOverviewAccess class. From our class RemotePlantOverviewAccess in the ui-module, we have methods that sends HttpRequests to the PlantOverviewController in our module springboot, which then uses these HttpRequests to carry out the actions we want, in collaboration with our other Spring Boot classes.

That being said, we now understand its purpose and how helpful it has been. Information floats between our application and server in a sturdy matter. After acquiring proficiency in its implementation, we now recognize the strategic advantage of implementing and using Spring Boot in future projects.


## Further information

### Building and running the project

To run the application or curl commands, read the steps in this [README.md](../../README.md)

### Code quality
To ensure that our code has a certain quality, we have made tests to make sure it can proceed with the wanted methods, and can handle exceptions with the classes
- [PlantOverviewApplicationTest](src/test/PlantOverviewApplicationTest.java)
- [PlantOverviewControllerTest](src/test/PlantOverviewControllerTest.java)
- [PlantOverviewServiceTest](src/test/PlantOverviewServiceTest.java).

<br>

You can read more about the test coverage in this [README.md](../documentation/release3/README.md)

