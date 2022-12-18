# Sequential Async Process Flux

Replicando error para procesos asincronos dependientes que se deben ejecutar secuencialmente en Wappe TDP

```shell
# Crea imagen de la app
DOCKER_BUILDKIT=1 docker build -t sandbox/sequential-async-process-flux .

# Levanta los servicios con Docker Compose
docker compose up -d

# Ingresar al contenedor de MariaDB y crear la data (Solo la primera vez)
docker exec -it mariadb_sandbox bash
cd docker-entrypoint-initdb.d
mysql -u admin -p
source init-database.sql
exit # Para salir de la shell de mysql
exit # Para salir de la shell del contenedor

# Ejecucion de prueba
curl -X GET http://localhost:9090/solution?limit=5

# Detener la aplicacion y eliminar servicios
docker compose down
```