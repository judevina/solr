package com.smn.solr.operations;

import static org.junit.Assert.*;

import org.apache.solr.client.solrj.SolrServerException;
import org.junit.Test;

public class BulkRestructureTest {

	@Test
	public void testRestructure()  {
		try {
			String solrUrl="http://localhost:8983/solr";
			BulkRestructure bs = new BulkRestructure(solrUrl);
			bs.restructure();
		} catch (Exception e) {
			fail("exception "+e.getMessage());
		}
		
	}

}
