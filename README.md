# EchoRelayJavaApp

```sh
alias ectl=./echoctl.sh
```

## Create cluster
```sh
ectl cluster create
```

## Install addons

```sh
# Prometheus and grafana
ectl cluster install prometheus-grafana

# nginx Ingress
ectl cluster install ingress

# mysql
ectl cluster install mysql

```

### Configure mysql

TBD

## Install service

### Build code

```sh
ectl svc build
```

### Upload image of the code to artifact repo

```sh
ectl svc upload
```

### Install the service on kubernetes cluster

```sh
ectl svc install
```

## Set DNS

```sh
ectl cluster update dns
```