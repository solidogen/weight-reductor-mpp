# Weight Reductor
## Multiplatform project
- Android app
- Frontend React app
- Backend using ktor

Open with Android Studio Canary to develop Android (change JDK from Bundled JDK to JDK 11)

## Issues
- "Android Studio version is too low, use 4.2/4.3/whatever" -> Set JDK to 11 in Project Structure
- Frontend project may not run using AS Gradle plugin. **Use ./gradlew in Studio's terminal if having problems**
- Backend project may look like it has errors in IDE - those are false positives from AS Gradle plugin. **Use ./gradlew in Studio's terminal if having problems**

## Running
- Backend  -->   `./gradlew backend:run` (**use terminal if backend code has errors in IDE, this is false positive**)
- Frontend -->   `./gradlew frontend:run --continuous` (**use terminal if task looks frozen with AS Gradle plugin**)
- Android: -->   AS will detect run configuration, if not, `./gradlew android:installDebug`

## Killing zombie BE/FE apps
- Linux - `fuser -n tcp -k 9090`
- macOS - `lsof -nti:9090 | xargs kill -9`

9090 is default backend port, 8080 is frontend one

## Credits
Project setup is based on great [PeopleInSpace](https://github.com/joreilly/PeopleInSpace) project by [John O'Reilly](https://github.com/joreilly)