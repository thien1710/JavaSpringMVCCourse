package com.example.demo.payload.request;

public class SearchCondition {
    private UserSearchCondition userSearchCondition;
    private ProjectSearchCondition projectSearchCondition;
    private CustomerSearchCondition customerSearchCondition;

    public UserSearchCondition getUserSearchCondition() {
        return userSearchCondition;
    }

    public void setUserSearchCondition(UserSearchCondition userSearchCondition) {
        this.userSearchCondition = userSearchCondition;
    }

    public ProjectSearchCondition getProjectSearchCondition() {
        return projectSearchCondition;
    }

    public void setProjectSearchCondition(ProjectSearchCondition projectSearchCondition) {
        this.projectSearchCondition = projectSearchCondition;
    }

    public CustomerSearchCondition getCustomerSearchCondition() {
        return customerSearchCondition;
    }

    public void setCustomerSearchCondition(CustomerSearchCondition customerSearchCondition) {
        this.customerSearchCondition = customerSearchCondition;
    }
}
