package com.bestbuy.crudtest;

import com.bestbuy.bestbuysteps.ProductSteps;
import com.bestbuy.bestbuysteps.StoreSteps;
import com.bestbuy.testbase.StoreTestBase;
import com.bestbuy.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoreCRUDTest extends StoreTestBase {

    static String name = "New Store" + TestUtils.getRandomValue();
    static String type = "BigBox";
    static String address = "123 Fake St";
    static String address2 = "";
    static String city = "Springfield" + TestUtils.getRandomValue();
    static String state = "MN";
    static String zip="55123";
    static int  lat= (int) 44.969658;
    static int  lng= (int) -93.449539;
    static String hours=  "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";

    static int storetId;


    @Steps
    StoreSteps storeSteps;
    @Title("this will create a new store")
    @Test
    public void test001(){
        storeSteps.createStore(name,type,address,address2,city,state,zip,lat,lng,hours).statusCode(201);
    }
    @Title("verify if the store was added successfully")
    @Test
    public void test002(){
        HashMap<String, Object> storeMap = storeSteps.getStoreInfoByName(name);
        Assert.assertThat(storeMap, hasValue(name));
        storetId = (int) storeMap.get("id");
    }
    @Title("Update the store information and verify the updated information")
    @Test
    public void test003(){
        name = name + "_updated";
        storeSteps.updateStore(storetId,name,type,address,address2,city,state,zip,lat,lng,hours);

        HashMap<String,Object> storetMap = storeSteps.getStoreInfoByName(name);
        Assert.assertThat(storetMap, hasValue(name));
    }

    @Title("Delete the store and verify if the store is deleted")
    @Test
    public void test004(){
        storeSteps.deleteStore(storetId).statusCode(204);
        storeSteps.getStoreById(storetId).statusCode(404);
    }
}