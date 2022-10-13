# general-ledger
A basic bookkeeping app to track financial records.

## Features
- Create multiple ledgers.
- Enter transactions (debit/credit).
- Track the remaining balance via summarized view.
- Export the transactions of a specific ledger to CSV file.

## How to run
1. Clone project.
```
git clone https://github.com/karldaven/general-ledger.git
```

2. Go to `docker-mysql` directory.
```
cd general-ledger/docker-mysql/
```

3. Execute `docker-compose` to create a MySQL container.
```
docker-compose up -d
```

4. Go to `ledger` directory.
```
cd ../ledger/
```

5. Run the Springboot application.
```
./mvnw spring-boot:run
```

6. Go to `generic-ledger` directory.
```
cd ../generic-ledger/
```

7. Install dependencies.
```
npm i
```

8. Run the Vue application.
```
npm run serve
```

9. In the browser, go to http://localhost:8080.