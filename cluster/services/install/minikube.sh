echo "⎈ install on minikube"
SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

echo "🔨  Building Docker Image"
#docker build $PROJECT_DIR -t demoappjava
echo
echo "Recreating namespace demoappjava"
kubectl delete namespace demoappjava
kubectl create namespace demoappjava
echo
echo "🖼️  Pushing image to minikube, will take time..."
#minikube image rm demoappjava || echo "Image not present"
#minikube image load demoappjava
# minikube image build -t demoappjava .
echo
echo
echo "Creating pods and services"
kubectl apply -k $PROJECT_DIR/setup/minikube
#kubectl apply -f $PROJECT_DIR/minikube/demoappjava.yaml
echo "✅  Done"
echo
echo "Exposing demoappjava service on port 8080 locally"
#kubectl port-forward svc/svc -n demoappjava 8080:8080
#kubectl port-forward svc/svc -n demoappjava 80:8080
#sleep 10
kubectl port-forward svc/svc -n default 8080:80

