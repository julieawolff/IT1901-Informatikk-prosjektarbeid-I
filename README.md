# Greenhouse🌱

We developed our app with the goal of providing structure and facilitating a more convenient way to manage plants in a greenhouse. The app helps users keep track of their plants' growth phases and watering schedules. This is achieved by enabling users to add plants, where they add information about the growth phase and select the watering frequency. To distinguish between different plants, each plant is assigned a unique user-created nickname. The user can at all times edit or delete the plant from the overview. 

When a user adds a plant to the system, the app automatically records the date of entry. As time progresses, the app calculates the next scheduled watering date based on the plant's initial entry date and the chosen watering frequency. Users will receive timely notifications when it's time to water their plants. When watering is registered in the app, the user will receive a subsequent notification when the next watering interval has elapsed. With this calender-based system, it is also possible for the user to see the how long the plants have lived.

<br>

[Open in Eclipse Che](https://che.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2023/gr2328/gr2328)


## Building and running the project

Follow the steps below, and then choose how you want to run the application, or what tests you would like to run.

1. For our project, we recommend java 17.0.8 and maven 3.9.4, make sure that you have a compatible version.
2. Open a new terminal, and make sure you don't already have springboot running in another terminal
3. Change directory to greenhouse
4. Write `mvn clean install -DskipTests`
5. After successfully building, change directory to springboot
6. Write `mvn spring-boot:run`
7. Open a new terminal (without closing the one just used)

### Run application manually
Continue here after step 7 under *How to run our application*:
<br>

8. Change directory to ui
9. Write `mvn javafx:run`
<br>

The application should now be visible.

### Run tests for core
Continue here after step 7 under *How to run our application*:
<br>

8. Change directory to core
9. Write `mvn test`

### Run tests for ui
Continue here after step 7 under *How to run our application*:
<br>

8. Change directory to ui
9. Write `mvn test`

### Run tests for springboot
Continue here after step 7 under *How to run our application*:
<br>

8. Change directory to springboot
9. Write `mvn test`
<br>

The tests should now run, and they are successfull if the terminal shows "BUILD SUCCESS".

### Use curl commands
Continue here after step 7 under *How to run our application*:
<br>

8. Change directory to springboot
9. Write the curl command you would like to use. There are pre-written examples below.

<br>

**Using POST**
<br>
If you would like to make a different plant, simply change "Test" to a valid name of your choosing, "Seed" to a valid phase, which is either Seed, Young%20plant, or Grown%20plant (%20 is how we write a space in the URL), and "2" to a valid water interval.

`curl -X POST "http://localhost:8080/greenhouse/post?name=TestPlant&phase=Seed&waterInterval=2"`

<br>

**Using GET**
<br>
The example shows how to get a plant called "TestPlant". If you would like to get a different plant, simply change "TestPlant" to the name of the plant you would like to get. Make sure that the plant exists in the plant overview.

`curl -X GET "http://localhost:8080/greenhouse/get?name=TestPlant"`

It is also possible to get the entire plant overview:

`curl -X GET "http://localhost:8080/greenhouse/getPlantOverview"`

<br>


**Using PUT**
<br>
It is necessary to enter the name of the plant you want to change, and the parameters you would like to change. That means you should be able to only write name and phase, or name and water interval, or name and if the plant will get watered, or any other combination of the parameters: To use PUT, you must enter the name of a plant you know exist. Here are two examples:

`curl -X PUT "http://localhost:8080/greenhouse/put?name=ExistingPlant&phase=Seed&waterInterval=2&water=True"`

`curl -X PUT "http://localhost:8080/greenhouse/put?name=ExistingPlant&waterInterval=8"`

<br>

**Using DELETE**
<br>
Write the name of the plant you want to delete.

`curl -X DELETE "http://localhost:8080/greenhouse/delete?name=TestPlant"`

<br>


### Run all tests at once
Continue here after step 7 under *How to run our application*:
<br>

8. Change directory to greenhouse
9. Write `mvn test`

## Folder structure overview
Our folder is called GR2328, and we have a folder within called greenhouse. The path to each file will be written like this: folder1>folder2>folder3 untill there is a relevant file, which will be written beneath as a bullet point.
<br><br>

**core**

Within the folder **greenhouse>core>src>main>java>core**:
- [Plant.java](greenhouse/core/src/main/java/core/Plant.java)
- [PlantOverview.java](greenhouse/core/src/main/java/core/PlantOverview.java)

Within the folder **greenhouse>core>src>main>java>json**:
- [GreenhouseModule.java](greenhouse/core/src/main/java/json/GreenhouseModule.java)
- [PlantDeserializer.java](greenhouse/core/src/main/java/json/PlantDeserializer.java)
- [PlantOverviewDeserializer.java](greenhouse/core/src/main/java/json/PlantOverviewDeserializer.java)
- [PlantOverviewSerializer.java](greenhouse/core/src/main/java/json/PlantOverviewSerializer.java)
- [PlantPersistence.java](greenhouse/core/src/main/java/json/PlantPersistence.java)
- [PlantSerializer.java](greenhouse/core/src/main/java/json/PlantSerializer.java)

Within the folder **greenhouse>core>src>test>java>core**:
- [PlantOverviewTest.java](greenhouse/core/src/main/test/java/core/PlantOverviewTest.java)
- [PlantTest.java](greenhouse/core/src/main/test/java/core/PlantTest.java)

Within the folder **greenhouse>core>src>test>java**:
- [module-info.java](greenhouse/core/src/main/java/module-info.java)

Within the folder **greenhouse>core>src>test>java>json**:
- [PlantDeserializerTest.java](greenhouse/core/src/main/test/java/json/PlantDeserializerTest.java)
- [PlantOverviewDeserializerTest.java](greenhouse/core/src/main/test/java/json/PlantOverviewDeserializerTest.java)
- [PlantOverviewSerializerTest.java](greenhouse/core/src/main/test/java/json/PlantOverviewSerializerTest.java)
- [PlantPersistenceTest.java](greenhouse/core/src/main/test/java/json/PlantPersistenceTest.java)
- [PlantSerializerTest.java](greenhouse/core/src/main/test/java/json/PlantSerializerTest.java)

Within the folder **greenhouse>core**:
- [pom.xml](greenhouse/core/pom.xml)

<br>

**documentation**

Within the folder **greenhouse>documentation>release1**:
- the folder **images**
- [README.md](greenhouse/documentation/release1/README.md)

Within the folder **greenhouse>documentation>release2**:
- the folder **images**
- [README.md](greenhouse/documentation/release2/README.md)
- [architecture.puml](greenhouse/documentation/release2/architecture.puml)

Within the folder **greenhouse>documentation>release3>diagrams**:
- [classDiagram.puml](greenhouse/documentation/release2/classDiagram.puml)
- [packageDiagram.puml](greenhouse/documentation/release2/packageDiagram.puml)
- [sequenceDiagram.puml](greenhouse/documentation/release2/sequenceDiagram.puml)

Within the folder **greenhouse>documentation>release3**:
- [README.md](greenhouse/documentation/release2/README.md)

<br>

**springboot**

Within the folder **greenhouse>springboot>src>main>java>greenhouse>springboot>restserver**:
- [PlantOverviewApplication.java](greenhouse/springboot/src/main/java/PlantOverviewApplication.java)
- [PlantOverviewController.java](greenhouse/springboot/src/main/java/PlantOverviewController.java)
- [PlantOverviewService.java](greenhouse/springboot/src/main/java/PlantOverviewService.java)

Within the folder **greenhouse>springboot>src>main>java**:
- [module-info.java](greenhouse/springboot/src/main/java/module-info.java)

Within the folder **greenhouse>springboot>src>test**:
- [PlantOverviewApplicationTest.java](greenhouse/springboot/src/test/java/greenhouse/springboot/restserver/PlantOverviewApplicationTest.java)
- [PlantOverviewControllerTest.java](greenhouse/springboot/src/test/java/greenhouse/springboot/restserver/PlantOverviewControllerTest.java)
- [PlantOverviewServiceTest.java](greenhouse/springboot/src/test/java/greenhouse/springboot/restserver/PlantOverviewServiceTest.java)

Within the folder **greenhouse>springboot**
- [pom.xml](greenhouse/springboot/pom.xml)
- [README.me](greenhouse/springboot/README.md)

<br>

**ui**


Within the folder **greenhouse>ui>src>main>java>ui>local**:
- [App.java](greenhouse/ui/src/main/java/ui/App.java)
- [AppController.java](greenhouse/ui/src/main/java/ui/AppController.java)
- [FrontPageController.java](greenhouse/ui/src/main/java/ui/FrontPageController.java)
- [NewPlantController.java](greenhouse/ui/src/main/java/ui/NewPlantController.java)
- [PopUpController.java](greenhouse/ui/src/main/java/ui/PopUpController.java)

Within the folder **greenhouse>ui>src>main>java>ui>remote**:
- [RemoteApp.java](greenhouse/ui/src/main/java/ui/RemoteApp.java)
- [RemoteAppController.java](greenhouse/ui/src/main/java/ui/RemoteAppController.java)
- [RemoteFrontPageController.java](greenhouse/ui/src/main/java/ui/RemoteFrontPageController.java)
- [RemoteNewPlantController.java](greenhouse/ui/src/main/java/ui/RemoteNewPlantController.java)
- [RemotePopUpController.java](greenhouse/ui/src/main/java/ui/RemotePopUpController.java)
- [RemotePlantOverviewAccess.java](greenhouse/ui/src/main/java/ui/RemotePlantOverviewAccess.java)

Within the folder **greenhouse>ui>src>main>java**:
- [module-info.java](greenhouse/ui/src/main/java/module-info.java)

Within the folder **greenhouse>ui>src>main>resources>ui**:
- [App.fxml](greenhouse/ui/src/main/resources/ui/App.fxml)
- [FrontPage.fxml](greenhouse/ui/src/main/resources/ui/FrontPage.fxml)
- [NewPlant.fxml](greenhouse/ui/src/main/resources/ui/NewPlant.fxml)
- [PlantPopUp.fxml](greenhouse/ui/src/main/resources/ui/PlantPopUp.fxml)
- [monsteraPlant.jpg](greenhouse/ui/src/main/resources/ui/monsteraPlant.jpg)
- [plants.json](greenhouse/ui/src/main/resources/ui/plants.json)
- [tallPlant.jpeg](greenhouse/ui/src/main/resources/ui/tallPlant.jpeg)
- [RemoteApp.fxml](greenhouse/ui/src/main/resources/ui/RemoteApp.fxml)

Within the folder **greenhouse>ui>src>test>java>ui**:
- [AppControllerTest.java](greenhouse/ui/src/test/java/ui/AppControllerTest.java)
- [FrontPageControllerTest.java](greenhouse/ui/src/test/java/ui/FrontPageControllerTest.java)
- [NewPlantControllerTest.java](greenhouse/ui/src/test/java/ui/NewPlantControllerTest.java)
- [PopUpControllerTest.java](greenhouse/ui/src/test/java/ui/PopUpControllerTest.java)
- [README.md](greenhouse/ui/src/test/java/ui/README.md)
- [RemoteAppControllerTest.java](greenhouse/ui/src/test/java/ui/RemoteAppControllerTest.java)
- [RemoteFrontPageControllerTest.java](greenhouse/ui/src/test/java/ui/RemoteFrontPageControllerTest.java)
- [RemoteNewPlantControllerTest.java](greenhouse/ui/src/test/java/ui/RemoteNewPlantControllerTest.java)
- [RemotePopUpControllerTest.java](greenhouse/ui/src/test/java/ui/RemotePopUpControllerTest.java)

Within the folder **greenhouse>ui>src>test>resources>ui**:
- [App_test.fxml](greenhouse/ui/src/test/resources/ui/App_test.fxml)
- [FrontPage_test.fxml](greenhouse/ui/src/test/resources/ui/FrontPage_test.fxml)
- [NewPlant_test.fxml](greenhouse/ui/src/test/resources/ui/NewPlant_test.fxml)
- [test-app.json](greenhouse/ui/src/test/resources/ui/test-app.json)
- [RemoteApp_test.fxml](greenhouse/ui/src/test/resources/ui/RemoteApp_test.fxml)

Within the folder **greenhouse>ui**:
- [pom.xml](greenhouse/ui/pom.xml)

<br>

**greenhouse**

Within the folder **greenhouse**:
- [.gitignore](greenhouse/.gitignore)
- [pom.xml](greenhouse/pom.xml)
- [README.md](greenhouse/README.md)
- [USERSCENARIOS.md](greenhouse/USERSCENARIOS.md)

On the same level as **greenhouse**
- [devfile.yaml](devfile.yaml)
- [README.md](README.md) which is this file
