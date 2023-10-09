/**
 * 
 */
package com.polarbookshop.customerservice.auth.services;

import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.mongodb.BasicDBObject;
import com.polarbookshop.customerservice.auth.models.Role;
import com.polarbookshop.customerservice.auth.models.User;
import com.polarbookshop.customerservice.auth.repositories.CustomUserRepository;

import reactor.core.publisher.Mono;

/**
 * @author Gbenga
 *
 */
public class CustomUserRepositoryImpl implements CustomUserRepository {

	private final ReactiveMongoTemplate mongoTemplate;

	// @Autowired
	public CustomUserRepositoryImpl(ReactiveMongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Mono<User> changePassword(String userId, String newPassword) {
		// step 1
		Query query = new Query(Criteria.where("userId").is(userId));
		// step 2
		Update update = new Update().set("password", newPassword);
		// step 3
		return mongoTemplate.findAndModify(query, update, User.class);
	}

	@Override
	public Mono<User> addNewRole(String userId, Role role) {
		// step 1
		Query query = new Query(Criteria.where("userId").is(userId));
		// step 2
		Update update = new Update().addToSet("roles", role);
		// step 3
		return mongoTemplate.findAndModify(query, update, User.class);
	}

	@Override
	public Mono<User> removeRole(String userId, String permission) {
		// step 1
		Query query = new Query(Criteria.where("userId").is(userId));
		// step 2
		Update update = new Update().pull("roles", new BasicDBObject("permission", permission));
		// step 3
		return mongoTemplate.findAndModify(query, update, User.class);
	}

	@Override
	public Mono<Boolean> hasPermission(String userId, String permission) {
		// 2
		Query query = new Query(Criteria.where("userId").is(userId))
				.addCriteria(Criteria.where("roles.permission").is(permission)); // 3
		// 4
		return mongoTemplate.exists(query, User.class);
	}
}
