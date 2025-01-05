# Min ways for coin project
The aim of this project is to build a REST API that allows for the input of coins from a selected range of coins and an amount between 0 and 10000.
The backend is built with Java and Dropwizard and containerized with Docker.

## Hosting
- Backend is hosted on http://13.215.160.209:8080/coin-change
- Frontend is hosted on http://54.179.228.236:3000/
- Both are hosted using AWS EC2 instance

## Docker
Docker image for this project can be found on `gsgiansen/dropwizard-app:latest` on dockerhub

## How to use 
1. Head to http://54.179.228.236:3000/
2. The default amount is 0, which will always give you back an empty list
3. Change to which coins are to your liking and the amount! (Amount must be within 0 and 10000 inclusive)
4. ALtenratively, if you are a POSTMAN or CURL person, you can send a POST request/ Curl to http://13.215.160.209:8080/coin-change with the following JSON in the body
```agsl
{
  "coins": [0.01, 0.02],
  "amount": 100
}
```


## How to run (locally)
1. `docker pull gsgiansen/dropwizard-app:latest` to get latest image available (works for linux/amd)
2. `docker run -d -p 8080:8080 gsgiansen/dropwizard-app:latest` to start the container
3. You can now access the POST endpoint at `<your_ip>:8080/coin-change`
4. Use postman/curl to input the relevant input with the correct JSON input (see below)
```agsl
{
  "coins": [0.01, 0.05],
  "amount": 1000
}
```


## Further connections
1. If you wish to connect your own frontend to the hosted backend, do reach out to me so that I can add your credentials