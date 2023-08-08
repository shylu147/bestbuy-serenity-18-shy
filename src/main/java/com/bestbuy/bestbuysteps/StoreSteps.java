package com.bestbuy.bestbuysteps;

import com.bestbuy.model.StorePojo;
import com.bestbuy.constants.EndPoints;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;



public class StoreSteps {

    @Step("Creating store with Name:{0},type : {1},address : {2},address2:{3}, city:{4}, state : {5},zip{6},lat{7},lng{8},hours{9}")
    public ValidatableResponse createStore(String name,String type,String address,String address2,String city,String state,String zip,int lat,int lng,String hours) {
         StorePojo storePojo =StorePojo.getStorePojo(name,type,address,address2,city,state,zip,lat,lng,hours);
        return SerenityRest.given()
                .contentType(ContentType.JSON)
                .body(storePojo)
                .when()
                .post()
                .then();
    }
    public HashMap<String, Object> getStoreInfoByName(String name) {

        String s1 = "findAll{it.name == '";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .when()
                .get(EndPoints.GET_ALL_STORES)
                .then().statusCode(200)
                .extract()
                .path(s1 + name + s2);
}

    @Step("Updating store with storeId {0},Name:{}1,type : {2},address : {3},address2:{4}, city:{5}, state : {6},zip{7},lat{8},lng{9},hours{10}")
    public ValidatableResponse updateStore(int storeId,String name,String type,String address,String address2,String city,String state,String zip,int lat,int lng,String hours){

        StorePojo storePojo= StorePojo.getStorePojo(name,type,address,address2,city,state,zip,lat,lng,hours);
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .pathParam("storeID", storeId)
                .body(storePojo)
                .when()
                .put(EndPoints.UPDATE_STORE_BY_ID)
                .then();
    }
    @Step("Deleting store information with storeId: {0}")
    public ValidatableResponse deleteStore(int storeId) {
        return SerenityRest.given().log().all()
                .pathParam("storeID", storeId)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then().statusCode(200);}
    @Step("Getting store information with storeId : {0}")
    public ValidatableResponse getStoreById(int storeId){
        return SerenityRest.given().log().all()
                .pathParam("storeID",storeId)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then();
    }




}
