package PocketMans;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PokeRepository {

    private final DynamoDBMapper dbReader;

    public PokeRepository() {
        dbReader = new DynamoDBMapper(AmazonDynamoDBClientBuilder.defaultClient());
    }

    public List<Poke> getAllPoke() {
        return dbReader.scan(Poke.class, new DynamoDBScanExpression());
    }

    public Optional<Poke> getPokeByName(String poke) {

        Map<String, AttributeValue> queryInputs = new HashMap<>();
        queryInputs.put(":poke", new AttributeValue().withS(poke));

        DynamoDBScanExpression query = new DynamoDBScanExpression()
                .withFilterExpression("poke = :poke")
                .withExpressionAttributeValues(queryInputs);

        List<Poke> queryResult = dbReader.scan(Poke.class, query);

        return queryResult.stream().findFirst();

    }

}
