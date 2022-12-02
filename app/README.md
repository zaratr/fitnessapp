
### SETUP Amazon Amplify, Google API

- Ensure your build/test environment meets the development requirements.
- Fork the repo and clone to your local.
- Load the project in AndroidStudio.
- In File > Settings > Build,Execution,Deployment > Build Tools > Gradle set Gradle JDK to JVM version 17+
- Click 'Sync Project with Gradle Files' to load all dependencies.
- Build the project. 
- Define an emulator in Device Manager with API 24 or higher (30 and up recommended for full functionality). 
- Optional: Connect a physical device via USB to debug on hardware.
- Run the project.
- Open emulators Google Maps and accept the location request when using app
- open BetterMe app
- Log in using Signup.
- Update Account using the Navbar.
- Use the Navbar to navigate App.
- Select a workout.
- Search the nearest Gym,Park or Supplement stores and get route to them. 
- Must use Google Api key (https://developers.google.com/maps/documentation/android-sdk/get-api-key) in res>google_map.xml><string name="google_map_key" templateMergeStrategy="preserver" translatable="false">google map key here</string>
- Make sure to initialize Amplify to your settings
