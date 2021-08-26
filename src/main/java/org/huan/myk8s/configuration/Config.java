package org.huan.myk8s.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.convert.CustomConversions;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.Cluster;
import com.couchbase.client.java.Collection;
import com.couchbase.client.java.Scope;

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
    
    @Bean
    public CustomConversions customConversions() {
          return super.customConversions();
    }
    
    @Bean
    public Collection couchbaseCollection() {
    	Cluster cluster = Cluster.connect(getConnectionString(), getUserName(), getPassword());

        // get a bucket reference
        Bucket bucket = cluster.bucket(getBucketName());

        // get a user defined collection reference
        Scope scope = bucket.scope("inventory");
        return scope.collection("books");
    }
    
    @Bean
    public Collection couchbaseSearchCollection() {
    	Cluster cluster = Cluster.connect(getConnectionString(), getUserName(), getPassword());

        // get a bucket reference
        Bucket bucket = cluster.bucket(getBucketName());

        // get a user defined collection reference
        Scope scope = bucket.scope("search");
        return scope.collection("keywordsbooks");
    }
}