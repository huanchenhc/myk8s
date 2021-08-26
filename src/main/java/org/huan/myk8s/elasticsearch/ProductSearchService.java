package org.huan.myk8s.elasticsearch;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.couchbase.core.CouchbaseTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.IndexedObjectInformation;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import com.couchbase.client.java.Collection;
import com.couchbase.client.java.json.JsonObject;
import com.couchbase.client.java.kv.GetResult;

@Service
//@Slf4j
public class ProductSearchService {
	private static final String PRODUCT_INDEX = "book";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ElasticsearchOperations elasticsearchOperations;
	@Autowired
	private CouchbaseTemplate couchbaseTemplate;
	@Autowired
	private Collection couchbaseSearchCollection;
	
	public String createProductIndex(BookProduct product) {
	    IndexQuery indexQuery = new IndexQueryBuilder()
	         .withId(product.getId().toString())
	         .withObject(product).build();

	    String documentId = elasticsearchOperations
	     .index(indexQuery, IndexCoordinates.of(PRODUCT_INDEX));

	    return documentId;
	  }
	
	// TODO
	public List<IndexedObjectInformation> createProductIndexBulk(final List<BookProduct> products) {

		List<IndexQuery> queries = products.stream()
				.map(product -> new IndexQueryBuilder().withId(product.getId().toString()).withObject(product).build())
				.collect(Collectors.toList());

		return elasticsearchOperations.bulkIndex(queries, IndexCoordinates.of(PRODUCT_INDEX));
	}
	
	
	public List<BookProduct> processSearch(final String keywords) {
		
		logger.info("Search with query {}", keywords);
		
		if(couchbaseSearchCollection.exists(keywords).exists()) {
			List<BookProduct> books = new ArrayList();
				GetResult getResult = couchbaseSearchCollection.get(keywords);
				getResult.contentAsArray().forEach(bookJsonObject -> { 
					books.add(new BookProduct(((JsonObject)bookJsonObject).getString("id"),
							((JsonObject)bookJsonObject).getString("title"),
							((JsonObject)bookJsonObject).getString("description")));
				});
				if (!books.isEmpty()) {
					logger.info("Returned book " + keywords + " from couchbase cache.");
					return books;
				} 
		
		} 
		
		/*ExecutableFindById<BookQueryResult> findById = couchbaseTemplate.findById(BookQueryResult.class);
		BookQueryResult cachedResult = findById.one(keywords);
		if (cachedResult != null) {
			logger.info("Returned book search results with keywords '" + keywords + "' from couchbase cache.");
			return cachedResult.getBookProducts();
		} */
		
		// 1. Create query on multiple fields enabling fuzzy search
		QueryBuilder queryBuilder = 
				QueryBuilders
				.multiMatchQuery(keywords, "title", "description")
				.fuzziness(Fuzziness.AUTO);

		NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
				                .withFilter(queryBuilder)
				                .build();

		// 2. Execute search
		SearchHits<BookProduct> productHits = elasticsearchOperations.search(searchQuery, BookProduct.class,IndexCoordinates.of(PRODUCT_INDEX));
		
		// 3. Map searchHits to product list
		List<BookProduct> productMatches = new ArrayList<BookProduct>();
		productHits.forEach(srchHit->{
			productMatches.add(srchHit.getContent());
		});
		
		// insert into couchbase if not exist
		
		/*// using couchbaseTemplate 
		BookQueryResult result = new BookQueryResult();
		result.setQuery(keywords);
		result.setBookProducts(productMatches);
		ExecutableUpsertById<BookQueryResult> upsertById = couchbaseTemplate.upsertById(BookQueryResult.class);	
		upsertById.one(result);		
		*/
		couchbaseSearchCollection.insert(keywords, productMatches);
	
		logger.info("Returned book search results with keywords '" + keywords + "' from elastic.");

		return productMatches;
	}
	
	
	
}
