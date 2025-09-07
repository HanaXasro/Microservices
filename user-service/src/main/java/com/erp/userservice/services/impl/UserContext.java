package com.erp.userservice.services.impl;

import com.erp.userservice.messaging.producer.RabbitProducer;
import com.erp.userservice.services.IUserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserContext implements IUserContext {

    @Autowired
    private RabbitProducer rabbitProducer;


    @Override
    public Long getUserId() {
        return 12l;
    }

    @Override
    public String getUsername() {
        return "";
    }


    @Override
    public List<String> getPermissions() {
        var permissions = rabbitProducer.sendAndReceive("permission.user","def" , List.class);
        return permissions;
    }

}