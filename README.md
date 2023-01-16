# EchoRelayJavaApp

```sh
alias ectl=./echoctl.sh
```

## Code

### Build 

```sh
ectl svc build
```

### Upload image of the code to artifact repo

```sh
ectl svc upload
```

## Create cluster

```sh
ectl cluster create
```

## Install addons and service

```sh
# Prometheus and grafana
ectl cluster install prometheus-grafana

# nginx Ingress
ectl cluster install ingress

# mysql
ectl cluster install mysql

# Install the service components on kubernetes cluster
ectl svc install

```

## Set DNS

```sh
ectl cluster update dns
```
