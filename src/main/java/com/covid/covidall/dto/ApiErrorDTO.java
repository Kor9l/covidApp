package com.covid.covidall.dto;

import java.util.List;

public class ApiErrorDTO {

  private List<String> errors;

  public ApiErrorDTO(List<String> errors) {
    this.errors = errors;
  }

  public List<String> getErrors() {
    return errors;
  }

  public void setErrors(List<String> errors) {
    this.errors = errors;
  }
}
