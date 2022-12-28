# EchoRelayJavaApp

```
alias actl=./antonctl.sh
```

## Create cluster
```
actl cluster create
```

## Install addons

```sh
# Promethus and grafana
actl cluster install prometheus-grafana

# nginx Ingress
actl cluster install ingress

# mysql
actl cluster install mysql

```

### Configure mysql

TBD

## Install service

### Build code

```sh
actl svc build
```

### Upload image of the code to artifact repo

```sh
actl svc upload
```

### Inatall the service on kubernetes cluster

```sh
actl svc install
```

## Set DNS

```
actl cluster update dns
```