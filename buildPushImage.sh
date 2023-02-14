./gradlew build -x test
docker build -t echo-relay-java-1 .
sh ./gcp-artifact-deploy-java.sh