*****************
## Вариант 1 Dockerfile
- Запускаем терминал и cобираем jar архив с нашим spring boot приложением: `mvn clean package`
- Создаем образ из нашего Dockerfile, мы должны запустить: `docker build -t moneytransfer .`
- Запускаем контейнер из нашего образа: `docker run --rm -p5500:5500 -it moneytransfer`

## Вариант 2 с помощью файла docker-compose.yml
- Запускаем терминал и cобираем jar архив с нашим spring boot приложением: `mvn clean package`
- в терминале и выполнить команду: `docker-compose up`

## Проверка
- Протестировать приложение в браузере: https://serp-ya.github.io/card-transfer/
- Протестировать приложение с помощью curl/postman

POST request --> http://localhost:5500/transfer
```
{
"cardFromNumber": "1115666600101892",
"cardFromValidTill": "05/23",
"cardFromCVV": "530",
"cardToNumber": "5555636200001111",
"amount": {
"value": 67899,
"currency": "RUB"
}
}
```

response --> 200 OK

```
{
"operationId": "533a4559-e55c-18b3-8456-555563322002"
}
```
response --> 400 and 500

```
{
"message": "string",
"id": 0
}
```
------------------------------------------------
POST request --> http://localhost:5500/confirmOperation

```
{
"operationId":  "1",
"code": "2304"
}
```

response --> 200 OK
```
{
"operationId": "1"
}
```
response --> 400 and 500
```
{
"message": "string",
"id": 0
}
```
