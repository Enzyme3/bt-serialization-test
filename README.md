# bt-serialization-test

## Pre-req
1 .Have existing Bigtable instance, table, and row to query
  * Can complete by following these [instructions](https://cloud.google.com/bigtable/docs/quickstart-cbt)
    * Create BT instsance
    * Install cbt
    * Setup cbtrc
    * Create table
    * Write to table
    * Confirm can read from table

## Run
1. mvn package
2. mvn exec:java -Dexec.mainClass="com.bt.test.App" -Dexec.args="{project-id} {bt-instance} {table-name} {row-key}"