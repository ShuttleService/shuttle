package com.real.apps.shuttle.respository;

import com.real.apps.shuttle.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Created by zorodzayi on 14/10/26.
 */
public interface UserRepository extends PagingAndSortingRepository<User,ObjectId>{
}