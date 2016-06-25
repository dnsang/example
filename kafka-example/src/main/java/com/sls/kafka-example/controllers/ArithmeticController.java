package com.sls.slsjtemplate.controllers;

import com.sls.slsjtemplate.common.Settings;
import com.sls.slsjtemplate.core.FlyweightObject;

public class ArithmeticController
        extends FlyweightObject {

    public ArithmeticController(Settings settings) {
        super(settings);
    }

    public long sum(int x, int y) {
        return x + y * 2;
    }

    public long multiply(int a, int b) {
        return a * b;
    }
}


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/controllers/ArithmeticController.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
