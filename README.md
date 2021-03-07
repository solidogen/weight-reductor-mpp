# Weight Reductor
## Multiplatform project
- Android app
- Frontend React app
- Backend using ktor

Open with Android Studio Canary (change JDK from Bundled JDK to JDK 11)

## Running apps
- see scripts folder in root for BE and FE local deploys
- AS should create run config for Android

## Killing zombie BE/FE apps (also see scripts folder in root)
- Linux - `fuser -n tcp -k 9090`
- macOS - `lsof -nti:9090 | xargs kill -9`

9090 is default backend port, 8080 is frontend one

## I can't run shell scripts
`chmod +x localDeployBackend.sh`

## How do I setup database for local BE deploy?
`sudo -u postgres -i` (I used `sudo su - postgres` but they say it's bad)
`psql -U postgres`
`create database weightreductor`

## Credits
Project setup is based on great [PeopleInSpace](https://github.com/joreilly/PeopleInSpace) project by [John O'Reilly](https://github.com/joreilly)