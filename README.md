# ms-dinnents

## Requisitos:
- Docker
- Consola bash
- curl


1. Abrir la consola bash y clonar este repositorio
2. Pararse en la carpeta root del proyecto
3. Ejecutar los contenedores con docker compose
```
docker-compose up
```
4. Importar la información
```
docker-compose exec dev-mariadb mysql -u root --password=0000 evalart_reto < bd.sql
```
5. Realizar la petición
```
curl --request POST 
--url http://localhost:8080/tables/distribute \
--header 'Content-Type: text/plain' \
--data '<General>
TC:1
<Mesa 1>
UG:2
RI:500000
<Mesa 2>
UG:1
RF:500000
<Mesa 3>
UG:3
TC:5
RF:10000
<Mesa 4>
UG:1
RF:100000
<Mesa 5>
UG:99
<Mesa 6>
TC:11
RI:10000'
```
6. Bajar los contenedores
```
docker-compose down
```