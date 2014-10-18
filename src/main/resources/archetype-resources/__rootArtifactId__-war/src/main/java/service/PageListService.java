#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )

package ${package}.service;

import ${package}.entity.PageListEntry;

import java.util.List;

/**
 * Created by marco on 13.10.14.
 *
 * Service is used to get a list of pages that are supposed to be searched by search engines. The sitemap-Servlet and the Snapshot-Refresh uses this service.
 */
public interface PageListService {

    List<PageListEntry> getPages();
}
