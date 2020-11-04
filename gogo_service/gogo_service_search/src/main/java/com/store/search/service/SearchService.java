package com.store.search.service;

import java.util.Map;

public interface SearchService {

    Map<String, Object> getList(Map<String, String> searchMap);
}
