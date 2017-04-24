package com.bitbosh.dropwizardheroku.webgateway.api;

import java.util.List;

public class ApiResponse {

  private List<?> list;

  public ApiResponse(List<?> items) {
    list = items;
  }

  public List<?> getList() {
    return list;
  }
}
