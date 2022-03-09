# kata-bank

Ce projet contient une solution pour un compte bancaire Kata qui gère les dépôts, les retraits et la gestion des relevés de compte. 

# User story
- Afin d'économiser de l'argent en tant que client bancaire, je veux faire un dépôt sur mon compte.
- Afin de récupérer tout ou partie de mes économies en tant que client bancaire, je veux faire un retrait sur mon compte.
- Pour consulter mes opérations En tant que client de la banque, je souhaite voir l'historique (opération, date, montant, solde) de mes opérations.  


# Approche et architecture 

Ce projet utilise le développement piloté par les tests (TDD) comme processus de développement logiciel. une architecture monolithiques  est utilisé

# Technologies

- Java 17
- Spring boot 2.6.4
- Maven
- Junit 5

# API Endpoints

```bash
POST /bank/deposit HTTP/1.1
```
```bash
{
     accountIdent": 1,
    "amount": 67.21
}
HTTP/1.1 201 OK
```


```bash
POST /bank/withdraw HTTP/1.1
```
```bash
{
     accountIdent": 1,
    "amount": 32.21
}
HTTP/1.1 201 OK
```
```bash
GET /history/{accountId}
```
```bash
HTTP/1.1 200 OK
{
    "balance": 14.35,
    "listTransaction": [
        {
            "operation": "WITHDRAWAL",
            "amount": 5.65,
            "dateOperation": "08/03/2022 13:42:49"
        },
        {
            "operation": "DEPOSIT",
            "amount": 20.0,
            "dateOperation": "08/03/2022 13:42:37"
        },
        {
            "operation": "WITHDRAWAL",
            "amount": 20.0,
            "dateOperation": "08/03/2022 13:41:10"
        },
        {
            "operation": "DEPOSIT",
            "amount": 20.0,
            "dateOperation": "08/03/2022 13:37:32"
        },
        {
            "operation": "WITHDRAWAL",
            "amount": 20.0,
            "dateOperation": "08/03/2022 13:42:29"
        },
        {
            "operation": "DEPOSIT",
            "amount": 20.0,
            "dateOperation": "08/03/2022 13:42:18"
        }
    ]
}


```






