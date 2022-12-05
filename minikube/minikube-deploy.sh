echo "🔨  Building"
PROJECT_DIR=$(dirname "$PWD")
echo $PROJECT_DIR
rm -rf $PROJECT_DIR/build/libs
$PROJECT_DIR/gradlew build -p $PROJECT_DIR -x test
echo
echo "🔨  Building Docker Image"
docker build $PROJECT_DIR -t demoappjava
echo
echo "Recreating namespace demoappjava"
kubectl delete namespace demoappjava
kubectl create namespace demoappjava
echo
echo "🖼️  Pushing image to minikube, will take time..."
minikube image rm demoappjava || echo "Image not present"
minikube image load demoappjava
echo
echo "Creating pods and services"
kubectl apply -f $PROJECT_DIR/minikube/demoappjava.yaml
echo "✅  Done"
echo
echo "Exposing demoappjava service on port 8080 locally"
kubectl port-forward svc/demoappjava -n demoappjava 8080:8080