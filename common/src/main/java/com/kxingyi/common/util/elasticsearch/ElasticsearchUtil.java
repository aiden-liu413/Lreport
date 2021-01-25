package com.kxingyi.common.util.elasticsearch;

import com.kxingyi.common.exception.BusinessException;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequestBuilder;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.collapse.CollapseBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author byliu
 */
public class ElasticsearchUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchUtil.class);
    private static final String GET_THE_ES_DATA_FAILED = "get the ES data failed";
    private static TransportClient client;
    private static boolean showDsl = false;

    public static TransportClient getClient() {
        return client;
    }

    public static void setClient(TransportClient client) {
        ElasticsearchUtil.client = client;
    }

    /**
     * 创建索引
     *
     * @param index
     * @return
     */
    public static boolean createIndex(String index) {
        if (!isIndexExist(index)) {
            LOGGER.info("Index is not exits!");
        }
        CreateIndexResponse indexresponse = client.admin().indices().prepareCreate(index).execute().actionGet();
        LOGGER.info("执行建立成功？" + indexresponse.isAcknowledged());
        return indexresponse.isAcknowledged();
    }

    /**
     * 删除索引
     *
     * @param index
     * @return
     */
    public static boolean deleteIndex(String index) {
        if (!isIndexExist(index)) {
            LOGGER.info("Index is not exits!");
        }
        DeleteIndexResponse dResponse = client.admin().indices().prepareDelete(index).execute().actionGet();
        if (dResponse.isAcknowledged()) {
            LOGGER.info("delete index " + index + "  successfully!");
        } else {
            LOGGER.info("Fail to delete index " + index);
        }
        return dResponse.isAcknowledged();
    }

    /**
     * 判断索引是否存在
     *
     * @param index
     * @return
     */
    public static boolean isIndexExist(String index) {
        IndicesExistsResponse inExistsResponse = client.admin().indices().exists(new IndicesExistsRequest(index)).actionGet();
        if (inExistsResponse.isExists()) {
            LOGGER.info("Index [" + index + "] is exist!");
        } else {
            LOGGER.info("Index [" + index + "] is not exist!");
        }
        return inExistsResponse.isExists();
    }

    /**
     * 数据添加，正定ID
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     * @return
     */
    public static String addData(JSONObject jsonObject, String index, String type, String id) {
        IndexResponse response = client.prepareIndex(index, type, id).setSource(jsonObject).get();
        LOGGER.info("addData response status:{},id:{}", response.status().getStatus(), response.getId());
        return response.getId();
    }

    /**
     * 数据添加
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @return
     */
    public static String addData(JSONObject jsonObject, String index, String type) {
        return addData(jsonObject, index, type, UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }

    /**
     * 通过ID删除数据
     *
     * @param index 索引，类似数据库
     * @param type  类型，类似表
     * @param id    数据ID
     */
    public static void deleteDataById(String index, String type, String id) {

        DeleteResponse response = client.prepareDelete(index, type, id).execute().actionGet();

        LOGGER.info("deleteDataById response status:{},id:{}", response.status().getStatus(), response.getId());
    }

    /**
     * 通过ID 更新数据
     *
     * @param jsonObject 要增加的数据
     * @param index      索引，类似数据库
     * @param type       类型，类似表
     * @param id         数据ID
     * @return
     */
    public static void updateDataById(JSONObject jsonObject, String index, String type, String id) {

        UpdateRequest updateRequest = new UpdateRequest();

        updateRequest.index(index).type(type).id(id).doc(jsonObject);

        client.update(updateRequest);

    }

    /**
     * 通过ID获取数据
     *
     * @param index  索引，类似数据库
     * @param type   类型，类似表
     * @param id     数据ID
     * @param fields 需要显示的字段，逗号分隔（缺省为全部字段）
     * @return
     */
    public static Map<String, Object> searchDataById(String index, String type, String id, String fields) {

        GetRequestBuilder getRequestBuilder = client.prepareGet(index, type, id);

        if (StringUtils.isNotEmpty(fields)) {
            getRequestBuilder.setFetchSource(fields.split(","), null);
        }

        GetResponse getResponse = getRequestBuilder.execute().actionGet();

        return getResponse.getSource();
    }

    /**
     * 使用分词查询,并分页
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param startPage      当前页
     * @param pageSize       每页显示条数
     * @param query          查询条件
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @param collapseField  折叠字段，用于字段去重
     * @param resultAdapter  实体适配器，当为null，对返回值不做处理
     * @return
     */
    public static EsPage searchDataPage(String index, String type, int startPage, int pageSize, QueryBuilder query, String fields, String sortField, String highlightField, String collapseField, EsResultAdapter resultAdapter) {
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(","));
        }
        searchRequestBuilder.setSearchType(SearchType.QUERY_THEN_FETCH);

        // 需要显示的字段，逗号分隔（缺省为全部字段）
        if (StringUtils.isNotEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(","), null);

        }

        //排序字段
        if (StringUtils.isNotEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }

        // 高亮（xxx=111,aaa=222）
        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();

            //highlightBuilder.preTags("<span style='color:red' >");//设置前缀
            //highlightBuilder.postTags("</span>");//设置后缀

            // 设置高亮字段
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }
        searchRequestBuilder.setFetchSource(true);
        //searchRequestBuilder.setQuery(QueryBuilders.matchAllQuery());
        searchRequestBuilder.setQuery(query);

        if (StringUtils.isNotEmpty(collapseField)) {
            searchRequestBuilder.setCollapse(new CollapseBuilder(collapseField));
        }
        // 分页应用
        searchRequestBuilder.setFrom(startPage * pageSize).setSize(pageSize);

        // 设置是否按查询匹配度排序
        searchRequestBuilder.setExplain(true);
        if (showDsl) {
            LOGGER.info("\n{}", searchRequestBuilder);
        }
        // 执行搜索,返回搜索响应信息
        SearchResponse searchResponse;
        try {
            searchResponse = searchRequestBuilder.execute().actionGet();
        } catch (Exception e) {
            throw new BusinessException(GET_THE_ES_DATA_FAILED);
        }

        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;
        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        if (showDsl) {
            LOGGER.info("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);
        }
        if (searchResponse.status().getStatus() == 200) {
            // 解析对象
            List<Map<String, Object>> sourceList = setSearchResponse(searchResponse, highlightField);
            if (resultAdapter == null) {
                return new EsPage(startPage, pageSize, (int) totalHits, sourceList);
            } else {
                return new EsPage(startPage, pageSize, (int) totalHits, sourceList, resultAdapter);
            }
        }
        return null;

    }

    /**
     * 使用分词查询 如果aggs不为空 返回的List的最后一个元素为聚合的结果，所以总结果数应该是list.size（）-1
     *
     * @param index          索引名称
     * @param type           类型名称,可传入多个type逗号分隔
     * @param query          查询条件
     * @param aggs           聚合条件
     * @param size           文档大小限制
     * @param fields         需要显示的字段，逗号分隔（缺省为全部字段）
     * @param sortField      排序字段
     * @param highlightField 高亮字段
     * @param collapseField  去重的字段
     * @return
     */
    public static List<Map<String, Object>> searchListData(
            String index, String type, QueryBuilder query, AggregationBuilder[] aggs, Integer size,
            String fields, String sortField, String highlightField, String collapseField) {

        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(","));
        }

        if (StringUtils.isNotEmpty(highlightField)) {
            HighlightBuilder highlightBuilder = new HighlightBuilder();
            // 设置高亮字段
            highlightBuilder.field(highlightField);
            searchRequestBuilder.highlighter(highlightBuilder);
        }

        searchRequestBuilder.setQuery(query);
        if (null != aggs && aggs.length > 0) {
            for (int i = 0; i < aggs.length; i++) {
                searchRequestBuilder.addAggregation(aggs[i]);
            }
        }
        if (StringUtils.isNotEmpty(fields)) {
            searchRequestBuilder.setFetchSource(fields.split(","), null);
        }
        if (StringUtils.isNotEmpty(collapseField)) {
            searchRequestBuilder.setCollapse(new CollapseBuilder(collapseField));
        }
        searchRequestBuilder.setFetchSource(true);

        if (StringUtils.isNotEmpty(sortField)) {
            searchRequestBuilder.addSort(sortField, SortOrder.DESC);
        }

        if (size != null && size >= 0) {
            searchRequestBuilder.setSize(size);
        }
        if (showDsl) {
            LOGGER.info("\n{}", searchRequestBuilder);
        }
        SearchResponse searchResponse;
        try {
            searchResponse = searchRequestBuilder.execute().actionGet();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(GET_THE_ES_DATA_FAILED);
        }

        long totalHits = searchResponse.getHits().totalHits;
        long length = searchResponse.getHits().getHits().length;
        //打印的内容 可以在 Elasticsearch head 和 Kibana  上执行查询
        if (showDsl) {
            LOGGER.info("共查询到[{}]条数据,处理数据条数[{}]", totalHits, length);
        }

        if (searchResponse.status().getStatus() == 200) {
            // 解析对象
            return setSearchResponse(searchResponse, highlightField);
        }
        return null;

    }

    /**
     * 高亮结果集 特殊处理
     *
     * @param searchResponse
     * @param highlightField
     */
    private static List<Map<String, Object>> setSearchResponse(SearchResponse searchResponse, String highlightField) {
        List<Map<String, Object>> sourceList = new ArrayList<Map<String, Object>>();
        StringBuffer stringBuffer = new StringBuffer();

        for (SearchHit searchHit : searchResponse.getHits().getHits()) {
            searchHit.getSourceAsMap().put("id", searchHit.getId());

            if (StringUtils.isNotEmpty(highlightField)) {

                System.out.println("遍历 高亮结果集，覆盖 正常结果集" + searchHit.getSourceAsMap());
                Text[] text = searchHit.getHighlightFields().get(highlightField).getFragments();

                if (text != null) {
                    for (Text str : text) {
                        stringBuffer.append(str.string());
                    }
                    //遍历 高亮结果集，覆盖 正常结果集
                    searchHit.getSourceAsMap().put(highlightField, stringBuffer.toString());
                }
            }
            sourceList.add(searchHit.getSourceAsMap());
        }
        Aggregations aggs = searchResponse.getAggregations();
        if (null != aggs) {
            Map<String, Object> aggsMap = new HashMap<>(aggs.getAsMap());
            sourceList.add(aggsMap);
        }
        return sourceList;
    }

    public static void setShowDsl(boolean showDsl) {
        ElasticsearchUtil.showDsl = showDsl;
    }

    /**
     * count总数
     *
     * @param index         索引
     * @param type          类型
     * @param query         查询条件
     * @param collapseField 去重的字段 不去重就置空
     * @return
     */
    public static long countOfQuery(String index, String type, QueryBuilder query, String collapseField) {
        Map<String, Object> result = new HashMap<>();
        SearchRequestBuilder searchRequestBuilder = client.prepareSearch(index);
        if (StringUtils.isNotEmpty(type)) {
            searchRequestBuilder.setTypes(type.split(","));
        }

        searchRequestBuilder.setQuery(query);

        if (StringUtils.isNotEmpty(collapseField)) {
            searchRequestBuilder.setCollapse(new CollapseBuilder(collapseField));
        }
        searchRequestBuilder.setFetchSource(true);

        SearchResponse searchResponse = searchRequestBuilder.execute().actionGet();

        return searchResponse.getHits().totalHits;

    }

    /**
     *
     */
    public boolean isTypeExist(String index, String type) {
        return isIndexExist(index) && client.admin().indices().prepareTypesExists(index).setTypes(type).execute().actionGet().isExists();
    }
}