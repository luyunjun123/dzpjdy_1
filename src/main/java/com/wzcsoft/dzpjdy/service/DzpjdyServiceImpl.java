package com.wzcsoft.dzpjdy.service;

import com.wzcsoft.dzpjdy.dao.CurentbillnoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xingzhi on 2019/5/18.
 */
@Service
public class DzpjdyServiceImpl implements DzpjdyService{
    @Autowired
    private CurentbillnoMapper cbm;

    @Value("${bossinterf.placecode}")
    private String _placecode;

    @Value("${pageinit.title}")
    private String _pagetitle;

    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public Object pageInit(){
        Map<String,Object> retMap = new HashMap<>();

        return retMap;
    }
}
