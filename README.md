# The BetterMe App <img src="Public/icon1.png" alt="login" width="250">
## Project Summary

BetterMe gives new and regular gym users a place to record their BMI and display a history log that shows a workout streak Mon-Fri.

It also provides its users with motivational quotes to keep them fired up and ready to train.

## Requirements

See [REQUIREMENTS](./REQUIREMENTS.md)

## Instructions

### SETUP

- Ensure your build/test environment meets the development requirements.
- Fork the repo and clone to your local.
- Load the project in AndroidStudio.
- In File > Settings > Build,Execution,Deployment > Build Tools > Gradle set Gradle JDK to JVM version 17+
- Click 'Sync Project with Gradle Files' to load all dependencies.
- Build the project. 
- Define an emulator in Device Manager with API 24 or higher (30 and up recommended for full functionality). 
- Optional: Connect a physical device via USB to debug on hardware.
- Run the project.
- Open emulators google maps and accept the location request when using app
- open BetterMe app
- Log in using Signup.
- Update Account using the Navbar.
- Use the Navbar to navigate App.
- Select a workout.
- Search nearest Gym,Park or Supplement stores and get route to them. 
- Must use Google Api key (https://developers.google.com/maps/documentation/android-sdk/get-api-key) in res>google_map.xml><string name="google_map_key" templateMergeStrategy="preserver" translatable="false">google map key here</string>
- Make sure to initialize Amplify

  <img src="Public/loginvideo.gif" alt="login" width="250">
  <img src="Public/signup.png" alt="Step1" width="250">
  <img src="Public/updateinfo.JPG" alt="Step2" width="250">
  <img src="Public/selectworkout.png" alt="Step3" width="250">
  <img src="Public/img.png" alt="step 4" width="250">
  <img src="Public/smartspotter.png" alt="MLVideo" width="250">
  <img src="Public/squatreps.gif" alt="MLVideo" width="250">
      

### Development Requirements

- AndroidStudio 2021.2.1 patch 1
- Gradle version 7.3.3
- Android gradle plugin version 4.1.2
- Android API 32 SDK (minimum v.24)
- Java Version 17
- Physical Android device (optional)
- gradle:4.1.2
- amplify-tools-gradle-plugin:1.0.2
- AWS Account
- AWS Cognito
- AWS S3 Storage
- Android APK v
- Java JDK 17
- Use Google API Key by placing it in: ()
- if using intellij make sure to update IDE and downgrade gradle version (current issue with gradle and Intellij)

## The Team

Roger Reyes [GitHub](https://github.com/RogerMReyes)

Raul Zarate [GitHub](https://github.com/zaratr)

Abdulahi Mohamud [GitHub](https://github.com/AbdulahiMohamud)

Jason Wilson [GitHub](https://github.com/WilsonJhub)

Mike Brunette [GitHub](https://github.com/mcbrunette33) 

Scott Evans [GitHub](https://github.com/mScottEvans)

Chuck Altopiedi [GitHub](https://github.com/ChuckAlto)

Jon Rumsey [GitHub](https://github.com/nojronatron)

## Resources
- <a target="_blank" href="https://icons8.com/icon/v551nqGeHhGn/github">GitHub</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
- <a target="_blank" href="https://icons8.com/icon/UyatB5WgOdeP/linkedin-circled">LinkedIn Circled</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
- <a target="_blank" href="https://icons8.com/icon/ZvjnlgX9t1tb/gym">Gym</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
- <a target="_blank" href="https://icons8.com/icon/i8S0UHJ4f47y/pill">Pill</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
- <a target="_blank" href="https://icons8.com/icon/j0vWxQ4slW7i/park">Park</a> icon by <a target="_blank" href="https://icons8.com">Icons8</a>
- https://www.geeksforgeeks.org/how-to-create-an-expandable-cardview-in-android/
- https://www.geeksforgeeks.org/cardview-using-recyclerview-in-android-with-example/