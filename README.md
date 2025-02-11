# Pokemon Shakespeare Service

Questo servizio fornisce la descrizione di un Pokemon tradotta in stile shakespeariano utilizzando le API di PokeAPI e FunTranslations.

## Prerequisiti

Assicurati di avere installato:

- **Java 17**
- **Gradle**
- **Docker**
- **Kubernetes (ho usato minikube)**
- **kubectl**

## Configurazione

### 1. Clona il repository

```sh
git clone https://github.com/robertosolari/PokemonShakespeare.git
cd PokemonShakespeare
```

### 2. Compilazione e build del JAR

Esegui il comando:

```sh
gradle build
```

Il file JAR sarà generato in `build/libs/`

### 3. Eseguire il servizio localmente

Puoi eseguire il servizio direttamente con:

```sh
java -jar build/libs/PokemonShakespeare-*.jar
```

L'API sarà disponibile su `http://localhost:8000/pokemon/{pokemonName}`

## Esecuzione con Docker

### 4. Costruzione dell'immagine Docker

Assicurati che il file `Dockerfile` sia presente nella root del progetto, quindi esegui:

```sh
docker build -t pokemon-shakespeare .
```

### 5. Esecuzione con Docker

```sh
docker run -p 8000:8000 pokemon-shakespeare
```

L'API sarà disponibile su `http://localhost:8000/pokemon/{pokemonName}`

## Deploy su Kubernetes

### 6. Avvia il cluster Kubernetes

Se stai usando **minikube**, avvialo con:

```sh
minikube start
```

Se usi **kind**, crea un cluster con:

```sh
kind create cluster --name pokemon-cluster
```

### 7. Creazione del deployment e del service

Applica i manifest YAML:

```sh
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

### 8. Esporre il servizio

Con minikube, esponi la porta del servizio:

```sh
minikube service pokemon-shakespeare-service
```

Se usi kind, trovi l'IP del servizio con:

```sh
kubectl get svc pokemon-shakespeare-service
```

Ora puoi accedere all'API da Kubernetes!

## Test API

### 9. Verifica del funzionamento

Esegui una richiesta di test:

```sh
curl http://localhost:8000/pokemon/pikachu
```

Risultato atteso:

```json
{
    "name": "pikachu",
    "description": "Whenever pikachu cometh across something new, 't blasts 't with a jolt of electricity."
}
```

---


