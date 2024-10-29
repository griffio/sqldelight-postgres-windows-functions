# SqlDelight 2.1.x Postgresql Window Functions 

https://github.com/cashapp/sqldelight

Initial Support for Window Function Calls in [SqlDelight 2.1.0-SNAPSHOT](https://github.com/sqldelight/sqldelight/pull/5163)

Not currently supported in SqlDelight grammar is the `WINDOW` clause

```sql
SELECT wf1() OVER w
FROM table_name
WINDOW w AS (PARTITION BY c1 ORDER BY c2);
```

Supported

```sql
CREATE TABLE scores (
  name TEXT NOT NULL,
  points INTEGER NOT NULL
);
```

```sql
select:
SELECT
  name,
  RANK() OVER (ORDER BY points DESC) rank,
  DENSE_RANK() OVER (ORDER BY points DESC) dense_rank,
  ROW_NUMBER() OVER (ORDER BY points DESC) row_num,
  LAG(points) OVER (ORDER BY points DESC) lag,
  LEAD(points) OVER (ORDER BY points DESC) lead,
  NTILE(6) OVER (ORDER BY points DESC) ntile,
  CUME_DIST() OVER (ORDER BY points DESC) cume_dist,
  PERCENT_RANK() OVER (ORDER BY points DESC) percent_rank
FROM scores;

selectAvgByPartition:
SELECT
  name,
  AVG(points) OVER (
    PARTITION BY name
    ORDER BY points
    ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
  ) AS moving_avg
FROM scores;

selectSumByPartition:
SELECT
  name,
  SUM(points) OVER (
    PARTITION BY name
    ORDER BY points
    RANGE BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW
    EXCLUDE CURRENT ROW 
  ) AS running_total
FROM scores;
```
----

```shell
createdb windowfuncs &&
./gradlew build &&
./gradlew flywayMigrate
```

Flyway db migrations
https://documentation.red-gate.com/fd/gradle-task-184127407.html
