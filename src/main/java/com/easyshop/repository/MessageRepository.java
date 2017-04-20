package com.easyshop.repository;

import com.easyshop.model.MessageModel;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by pavan on 2/21/17.
 */
public interface MessageRepository extends MongoRepository<MessageModel, Long> {
    MessageModel findByCustId(long custId);
}
