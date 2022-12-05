echo "🔨  Building"
PROJECT_DIR=$(dirname "$PWD")
echo $PROJECT_DIR
echo
rm -rf $PROJECT_DIR/build/libs
$PROJECT_DIR/gradlew build -p $PROJECT_DIR -x test
java -jar $PROJECT_DIR/build/libs/echorelayapp-0.0.1-SNAPSHOT.jar