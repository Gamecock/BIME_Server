# BIME_Server
Server for BIME project


##  Build instructions


### Local Operation

Initial prototype runs locally at localhost:8080/greeting

To start type `gradle bootRun` in root directory  TODO: Add gradle wrapper

To test: point browser to `http://localhost:8080/greeting` or `http://localhost:8080/greeting?name=<name>`

### GCP Operation

Log into gcloud console

git clone https://github.com/ecu-seng-6230/BIME_Server.git

git checkout google-cloud-platform

./gradlew bootRun

Open webpreview 
There will be an error because the root is not mapped to anything.

edit url to ...devshell.apspot.com/greeting