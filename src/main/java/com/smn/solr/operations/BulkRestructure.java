package com.smn.solr.operations;

import java.util.Arrays;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

public class BulkRestructure {

	private String solrUrl = null;
	private SolrServer server = null;

	public BulkRestructure(String solrUrl) {
		this.solrUrl = solrUrl;
		server = new HttpSolrServer(solrUrl);
	}

	public long getDocumentCount() throws SolrServerException {
		SolrQuery q = new SolrQuery("*:*");
		q.setRows(0);
		return server.query(q).getResults().getNumFound();
	}

	public void restructure() throws SolrServerException {

		SolrQuery sQueryParams = new SolrQuery();

		long numresults = getDocumentCount();
		int rows = 14;
		int start = 0;
		while (start <= numresults) {
			SolrQuery query = new SolrQuery("*:*");
			query.setRows(rows);
			query.setStart(start);
			QueryResponse resp = server.query(query, METHOD.POST);
			start += rows;
			process(resp);

		}

	}

	private void process(QueryResponse resp) throws SolrServerException {
		SolrDocumentList sDocList = resp.getResults();

		for (SolrDocument doc : sDocList) {
			String microResume = (String) doc.get("microresume");
			System.out.println(microResume);
			microResume = microResume.replace("<br/>", ",");
			microResume = microResume.replace("</ br>", ",");
			String nonHtml = Jsoup.clean(microResume, Whitelist.none());
			System.out.println(nonHtml);
			String[] elementsInMicroresume = nonHtml.split(",");
			
			
			System.out.println(Arrays.asList(elementsInMicroresume));

			System.out.println("-----------------");
			// System.out.println(doc.toString());

		}

	}
}
