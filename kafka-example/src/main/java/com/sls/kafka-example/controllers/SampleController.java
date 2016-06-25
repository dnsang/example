/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sls.slsjtemplate.controllers;

import com.sls.slsjtemplate.common.Settings;
import com.sls.slsjtemplate.core.FlyweightObject;

/**
 *
 * @author sangdn
 */
public class SampleController extends FlyweightObject{

    public SampleController(Settings settings) {
        super(settings);
    }

    public String sayHello(String name) {
        return Thread.currentThread().getName();
    }
    
}
