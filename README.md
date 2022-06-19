# hepster
# Coding challenge for hepster 
# REST API with microservices using Spring book application
# Posrgres sql
# liquibase for migration
# Junit with mockito Framework
# REST API


POST

http://localhost:8080/book

{
    "bookId": "{{$guid}}",
    "title":String,
    "author":String,
    "price":Big decimal,
    "active":boolean
}

GET  

Return all the active books

http://localhost:8080/book/

GET 

Return a book from uuid

http://localhost:8080/book/{UUID}


PATCH

Update bookby UUID 

http://localhost:8080/book/{UUID}

{
    "price": big decimal,
    "title":String
}




