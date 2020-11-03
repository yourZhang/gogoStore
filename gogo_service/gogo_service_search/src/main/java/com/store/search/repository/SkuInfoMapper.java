
package com.store.search.repository;

import com.store.search.pojo.SkuInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;


public interface SkuInfoMapper extends ElasticsearchRepository<SkuInfo, Long> {
}
