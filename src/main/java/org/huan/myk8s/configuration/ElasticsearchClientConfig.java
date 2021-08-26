package org.huan.myk8s.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

@Configuration
//@EnableElasticsearchRepositories(basePackages 
//        = "io.pratik.elasticsearch.repositories")
@ComponentScan(basePackages = { "org.huan.myk8s.elasticsearch" })
public class ElasticsearchClientConfig extends AbstractElasticsearchConfiguration {
	@Override
	@Bean
	public RestHighLevelClient elasticsearchClient() {

		final ClientConfiguration clientConfiguration = ClientConfiguration.builder().connectedTo("localhost:9200")
				.build();

		return RestClients.create(clientConfiguration).rest();
	}

}
