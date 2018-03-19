package com.pollen.aem.core.models;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory ;
import org.apache.sling.models.annotations.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

/**
 * The RelatedLinks Resource object represents the list of child pages 
 * under specified content path. The content path is hardcoded in a constant 
 * and can be made dynamic based on per request call.
 * 
 * @author pollen.ace@gmail.com
 *
 */
@Model(adaptables=Resource.class)
public class RelatedLinks {
	
	/** Initializing logger	 */
	private final Logger logger = LoggerFactory.getLogger(getClass());

	/** CONTENT_PATH constant	 */
	private final String CONTENT_PATH = "/content/wb-features/en";

	/** CHILD_PAGES_QUERY constant */
	private final String CHILD_PAGES_QUERY = "SELECT * FROM [cq:Page] AS s "
			+ "WHERE ISCHILDNODE(s,''{0}'') "
			+ "order by [jcr:content/cq:lastModified]";

	/** 	THe ResourceResolverFactory object */
	@Inject
	private ResourceResolverFactory resolverFactory;

	/** The Session object initialise	 */
	private Session session;
	
	/** 	List to store list of child pages */
	final List<String> childPages = new ArrayList<>();
	
	/** 	Child page nodes */
	javax.jcr.NodeIterator nodeIter;

	/** 	This method initializes the RelatedLinks model resource. */
	@PostConstruct
	protected void init() {
		try {
			final ResourceResolver resourceResolver = resolverFactory.getAdministrativeResourceResolver(null);
			session = resourceResolver.adaptTo(Session.class);

			// Obtain the query manager for the session ...
			final javax.jcr.query.QueryManager queryManager = session.getWorkspace().getQueryManager();
			final javax.jcr.query.Query query = queryManager
					.createQuery(MessageFormat.format(CHILD_PAGES_QUERY, CONTENT_PATH), "JCR-SQL2");

			// Execute the query and get the results ...
			final javax.jcr.query.QueryResult result = query.execute();

			// Iterate over the nodes in the results ...
			nodeIter = result.getNodes();
		} catch (final Exception e) {
			logger.error(e.getMessage());
		}
	}

	/**
	 * The method returns the list of child pages under the content path based
	 * on the initialized NodeIterator object.
	 * 
	 * The logic is to gets the pages from NodeIterator if it's size is more
	 * than 0
	 * 
	 * @return
	 */
	public List<String> getChildPages() {
		if (nodeIter != null && nodeIter.getSize() > 0) {
			while (nodeIter.hasNext()) {
				final javax.jcr.Node node = nodeIter.nextNode();
				try {
					childPages.add(node.getPath());
				} catch (final RepositoryException e) {
					logger.error(e.getMessage());
				}
			}
		}
		return childPages;
	}
}
