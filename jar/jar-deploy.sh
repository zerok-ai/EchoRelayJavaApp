echo "🔨  Building"
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
PROJECT_DIR=$(dirname "$SCRIPT_DIR")
echo $PROJECT_DIR
echo
rm -rf $PROJECT_DIR/build/libs
$PROJECT_DIR/gradlew build -p $PROJECT_DIR -x test
java -jar $PROJECT_DIR/build/libs/echorelayapp-0.0.1-SNAPSHOT.jar