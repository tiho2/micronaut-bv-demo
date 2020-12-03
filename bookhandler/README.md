## Feature http-client documentation

- [Micronaut Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#httpClient)

## Feature rabbitmq documentation

- [Micronaut RabbitMQ Messaging documentation](https://micronaut-projects.github.io/micronaut-rabbitmq/latest/guide/index.html)

#BOOKHANDLER service

##CONFIGURATION

Batch processeing

Uploaded pdf is splitted to pages. Configure batch size (number of pages added to a batch)
`application.batch.size`  
`application.batch.size=-1` if batch  processing is not used, if all pages are added to one and only one batch.
