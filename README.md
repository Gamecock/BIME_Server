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

### Deploy App 
Used:
https://cloud.google.com/community/tutorials/kotlin-springboot-compute-engine  
./gradlew build  
gsutil cp build/libs/* gs://demo-01/bime.jar

####  modified instance creation

gcloud compute instances create bime-instance --image-family debian-9 --image-project debian-cloud --machine-type g1-small --scopes "userinfo-email,cloud-platform" --metadata-from-file startup-script=instance-startup.sh  --metadata BUCKET=bime-0419.appspot.com --zone us-east1-b --tags http-server  

should return:
Created [https://www.googleapis.com/compute/v1/projects/bime-0419/zones/us-east1-b/instances/bime-instance].
NAME           ZONE        MACHINE_TYPE  PREEMPTIBLE  INTERNAL_IP  EXTERNAL_IP    STATUS
bime-instance  us-east1-b  g1-small                   10.142.0.6   35.185.18.130  RUNNING

####Add firewall  
gcloud compute firewall-rules create default-allow-http-8080 \
     --allow tcp:8080 \
     --source-ranges 0.0.0.0/0 \
     --target-tags http-server \
     --description "Allow port 8080 access to http-server"  

### Appengine  Deployment
install gloud sdk, including app-engine-java

./gradlew build
./gradlew appengineDeploy

## Mobile App Configuration

To use go to MainActivity.java of BIME_ANDROID application.    
Ensure this line is uncommented.
    public static String service = "https://";  //Azure & GCP are secure
Ensure this line is commented out, it is normally active.  
    public static String server = "bimewebapi.azurewebsites.net";

Ensure this line is uncommented if using GCP. 
    //    public static String server = "bime-0419.appspot.com";

To test locally at uncomment these lines and comment the rest.  
    //  public static String service = "http://";
    //    public static String server = "10.0.2.2:8080";  //this is localhost on laptop when using emulator
