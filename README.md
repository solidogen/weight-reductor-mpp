# Weight Reductor
## Multiplatform project
- Android app
- Frontend React app
- Backend using ktor

Open with Android Studio 4.2 latest Canary, or better if already available.

## Issues
Frontend project may not run using Android Studio Gradle plugin. **Use ./gradlew in Studio's terminal if having problems**

## Running
- Backend  -->   run main function in AS (preferred) or `./gradlew backend:run`
- Frontend -->   `./gradlew frontend:browserDevelopmentRun` (**use terminal if getting errors with AS Gradle plugin**)
- Android: -->   AS will detect run configuration, if not, `./gradlew android:installDebug`

## Killing zombie BE/FE apps
`fuser -n tcp -k 9090`

9090 is default backend port, 8080 is frontend one

## Credits
Project setup is based on great [PeopleInSpace](https://github.com/joreilly/PeopleInSpace) project by [John O'Reilly](https://github.com/joreilly)