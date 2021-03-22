package com.bt.test;

import com.google.cloud.bigtable.data.v2.BigtableDataClient;
import com.google.cloud.bigtable.data.v2.models.Row;
import com.google.cloud.bigtable.data.v2.models.RowCell;

import org.apache.commons.lang3.SerializationUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        if (args.length != 4) {
            System.out.println("Error - pass in 4 args: project-i, bt-instance-id, bt-table, rowkey");
            return;
        }
        String project = args[0];
        String instance = args[1];
        String table = args[2];
        String rowkey = args[3];

        BigtableDataClient client = null;
        try {
            System.out.printf("\nCreating client for bt instnace [%s] in project [%s]\n", instance, project);
            client = BigtableDataClient.create(project, instance);
            System.out.println("Created client");

            System.out.printf("Getting row with key [%s] on table [%s]\n", rowkey, table);
            Row row = client.readRow(table, rowkey);
            if (row == null) {
                System.out.println("No row with specified key found");
                return;
            }
            System.out.println("Successfully read row");
            System.out.println("key: " + row.getKey().toStringUtf8());
            for (RowCell cell : row.getCells()) {
                System.out.printf(
                    "Family: %s    Qualifier: %s    Value: %s%n",
                    cell.getFamily(), cell.getQualifier().toStringUtf8(), cell.getValue().toStringUtf8());
            }

            System.out.println("Serializing row");
            byte[] serializedRow = SerializationUtils.serialize(row);
            System.out.println("Serialization successful");

            System.out.println("Deserializing row");
            Row deserializedRow = SerializationUtils.deserialize(serializedRow);
            System.out.printf("Deserialization done: [%s]\n", deserializedRow.getKey().toStringUtf8());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            client.close();
        }
    }
}
