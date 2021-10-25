# ms-dinnents

## Requisitos:
- Docker
- Consola (en windows debe ser cmd)
- Bash (para la petición)
- curl


1. Abrir la consola bash y clonar este repositorio
2. Pararse en la carpeta root del proyecto
3. Ejecutar los contenedores con docker compose
```sh
docker-compose up -d
```
4. Importar la información (a veces es necesario esperar un poco más de 5 a 10 segundos a que esté listo por completo el contenedor de mariadb)
```sh
docker-compose exec dev-mariadb mysql -u root --password=0000 evalart_reto < bd.sql
```
5. Realizar la petición
```sh
curl --request POST \
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
```sh
docker-compose down
```
