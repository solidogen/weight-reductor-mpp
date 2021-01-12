# Weight Reductor
## Multiplatform project
- Android app
- Frontend React app
- Backend using ktor

Open with Android Studio Canary (change JDK from Bundled JDK to JDK 11)

## Running (also see scripts folder in root)
- Backend  -->   `./gradlew backend:run` (**use terminal if backend code has errors in IDE, this is false positive**)
- Frontend -->   `./gradlew frontend:run --continuous` (**use terminal if task looks frozen with AS Gradle plugin**)
- Android: -->   AS will detect run configuration, if not, `./gradlew android:installDebug`

## Killing zombie BE/FE apps (also see scripts folder in root)
- Linux - `fuser -n tcp -k 9090`
- macOS - `lsof -nti:9090 | xargs kill -9`

9090 is default backend port, 8080 is frontend one

## I can't run shell scripts
`chmod +x testDeployFrontend.sh`

## Credits
Project setup is based on great [PeopleInSpace](https://github.com/joreilly/PeopleInSpace) project by [John O'Reilly](https://github.com/joreilly)