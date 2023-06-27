package me.plurg.plurg.dao;

import me.plurg.plurg.entity.Trend;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrendDao
        extends MongoRepository<Trend, String> {}