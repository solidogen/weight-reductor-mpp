# Weight Reductor
## Multiplatform project
- Android app
- Frontend React app
- Backend using ktor

Open with Android Studio Canary (change JDK from Bundled JDK to JDK 11)

## Running apps
- see scripts folder in root for BE and FE local deploys
- AS should create run config for Android

## Killing zombie BE/FE apps (app already running on port XXXX etc)
Go to scripts/kill folder and pick script according to your OS

9090 is default backend port, 8080 is frontend one

## I can't run shell scripts
`chmod +x localDeployBackend.sh`

## How do I setup database for local BE deploy?
`sudo -u postgres -i` (I actually used `sudo su - postgres` but they say it's bad for some reason)
Then go to scripts/postgres and execute dropAndCreateLocalDatabase.sh (while having postgres installed on your system)

## Credits
Project setup is based on great [PeopleInSpace](https://github.com/joreilly/PeopleInSpace) project by [John O'Reilly](https://github.com/joreilly)