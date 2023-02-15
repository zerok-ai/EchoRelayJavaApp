./gradlew build -x test
docker build -t echo-relay-java .
sh ./gcp-artifact-deploy-java.sh