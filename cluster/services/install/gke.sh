SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )
PROJECT_DIR=$(dirname "$SCRIPT_DIR")

echo "⎈ install on gke"

echo
echo "Recreating namespace demoappjava"
kubectl delete namespace demoappjava
kubectl create namespace demoappjava

exit 1;
echo
echo "🖼️  Pushing image to GKE, will take time..."
minikube image rm demoappjava || echo "Image not present"
minikube image load demoappjava

echo
echo "waiting for 20 seconds"
sleep 20

echo
echo "Creating pods and services"
kubectl apply -k $PROJECT_DIR/setup/gke
echo "✅  Done"

echo
echo "Exposing echo-relay-java-app service on port 8080 locally"
#kubectl port-forward svc/echo-relay-java-app -n demoappjava 8080:8080