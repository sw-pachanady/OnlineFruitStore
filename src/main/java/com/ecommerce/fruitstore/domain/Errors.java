package com.ecommerce.fruitstore.domain;

import java.util.ArrayList;
import java.util.List;

public class Errors {

    List<Error> errorList;

    public Errors() {
        this.errorList = new ArrayList<>();
    }
    public Errors(List<Error> errorList) {
        this.errorList = errorList;
    }

    public void addError(Error error) {
        errorList.add(error);
    }

    public List<Error> getErrorList() {
        return errorList;
    }

    public void setErrorList(List<Error> errorList) {
        this.errorList = errorList;
    }

}
