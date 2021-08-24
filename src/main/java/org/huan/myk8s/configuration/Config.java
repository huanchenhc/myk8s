package org.huan.myk8s.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

@Configuration
public class Config extends AbstractCouchbaseConfiguration {

    @Override
    public String getConnectionString() {
        return "couchbase://127.0.0.1";
    }

    @Override
    public String getUserName() {
        return "myk8s";
    }

    @Override
    public String getPassword() {
        return "123456";
    }

    @Override
    public String getBucketName() {
        return "myk8s";
    }
}