# WebFlux R2DBC Sample 

### Database 

**Start PostgreSQL**
```
 docker run --name mac-postgres -e POSTGRES_PASSWORD=password -p 5432:5432 -d postgres
```

**Create DB and Table**
```sql
CREATE DATABASE mac_shopping;

CREATE TABLE product (
    id SERIAL PRIMARY KEY,
    uuid UUID,
    name VARCHAR(128),
    price DECIMAL , 
    created_time TIMESTAMP
);
```

### APIs 

```shell script
curl --location --request POST 'http://localhost:8080/products' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "Product3",
    "price": 125
}'

curl --location --request GET 'http://localhost:8080/products'

curl --location --request GET 'http://localhost:8080/products/1fa6fa43-1d18-4340-8811-85aa8ec20831'
```
