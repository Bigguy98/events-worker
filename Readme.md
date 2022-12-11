
## Overview

This worker listen to kafka events and do following tasks:

1. With this event, it will update medical record status to Approved

```
{
    "type": "Approval_event",
    "medicalRecordId": "<medicalrecord-id>"
}
```
For example
```
{ "type": "Approval_event", "medicalRecordId": "4" }
```


2. With this event, it will collect data and send to user email

```
{
    "type": "Bought_event",
    "categoryId": "<category-id>",
    "walletId": "<wallet-id>",
}
```

For example

```
{"type": "Bought_event", "categoryId": "2", "walletId": "walletID_1998"}
```

## Dependencies

This worker need a postgresDb and kafka cluster which are already run on machine.

## Package this app

you should have java JDK >= 18

Package .jar file

> mvn package -DskipTests=true

Build docker image

> docker build -t hayquen/solana-bootcamp-events-worker:1.0.0 .

To test docker image

> docker run hayquen/solana-bootcamp-events-worker:1.0.0 <command>

Run docker image:

> docker run --name worker hayquen/solana-bootcamp-events-worker:1.0.0
