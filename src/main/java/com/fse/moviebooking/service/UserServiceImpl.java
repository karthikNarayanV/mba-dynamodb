package com.fse.moviebooking.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.GlobalSecondaryIndexDescription;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.LocalSecondaryIndex;
import com.amazonaws.services.dynamodbv2.model.Projection;
import com.amazonaws.services.dynamodbv2.model.ProjectionType;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.fse.moviebooking.config.DynamoDBConfig;
import com.fse.moviebooking.model.User;
import com.fse.moviebooking.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	private DynamoDBMapper dynamoDBMapper;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public String saveUser(User user) throws Exception {
		log.info("Start: Save User");
		try {
			/*dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
			
			CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(User.class);
			tableRequest.withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
			tableRequest.getGlobalSecondaryIndexes().get(0).setProvisionedThroughput(new ProvisionedThroughput(10l, 10l));
			TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);
			userRepository.save(user);*/
			if(userRepository.findByEmail(user.getEmail()) == null) {
				dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
				
				CreateTableRequest tableRequest = dynamoDBMapper.generateCreateTableRequest(User.class);
				tableRequest.withProvisionedThroughput(new ProvisionedThroughput(1L, 1L));
				tableRequest.getGlobalSecondaryIndexes().get(0).setProvisionedThroughput(new ProvisionedThroughput(10l, 10l));
				TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);
				userRepository.save(user);
			}
			else throw new Exception("email is already there");
			log.info("End: Save User");
			return "Saved";
		} catch (Exception e) {
			log.error("User Email Already exists or Re fetching login ID");
			log.error(e.getLocalizedMessage());
			log.error(e.getMessage());
			log.info("End: Save User");
			throw new Exception("email is already there");
		}
	}

	@Override
	public User getUser(String email) {
		log.info("Start: Get User");
		Optional<User> result = Optional.ofNullable(userRepository.findByEmail(email));
		if (result.isEmpty()) {
			log.error("No user");
			log.info("End: Get User");
			return null;
		}
		log.info("End: Get User");
		return result.get();
	}

}
