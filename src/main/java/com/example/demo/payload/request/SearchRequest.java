package com.example.demo.payload.request;

public class SearchRequest {
    private String targetObjectCondition;
    private SearchCondition searchCondition;

    public String getTargetObjectCondition() {
        return targetObjectCondition;
    }

    public void setTargetObjectCondition(String targetObjectCondition) {
        this.targetObjectCondition = targetObjectCondition;
    }

    public SearchCondition getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(SearchCondition searchCondition) {
        this.searchCondition = searchCondition;
    }
}
