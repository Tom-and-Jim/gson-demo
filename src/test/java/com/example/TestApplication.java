package com.example;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.util.ArrayList;

public class TestApplication {

    @Test
    public void testBasic() {
        Gson gson = new Gson();
        assert gson.toJson(false).equals("false");
        assert gson.fromJson("false", Boolean.class).equals(false);
    }

    @Test
    public void testObject() {
        Gson gson = new Gson();
        User user = new User("qinchao", 1);
        assert gson.toJson(user).equals("{\"name\":\"qinchao\",\"age\":1}");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(new User("Tom", 1));
        userList.add(new User("Jerry", 2));

        String userListJson = userList.toString();
        assert gson.toJson(userList).equals(userListJson);
        assert gson.fromJson(userListJson, new TypeToken<ArrayList<User>>(){}.getType()).equals(userList);
    }
}
