package com.sls.slsjtemplate.thrift;

import com.sls.slsjtemplate.common.SettingFactory;

public class TServers {

    public boolean setupAndStart() {
        ThriftServer servers = new ThriftServer(SettingFactory.get("ThriftServer"));

//     Calc.Processor processor = new Calc.Processor(new TCalcHandler());
//     servers.setup(processor);
        return servers.start();
    }
}


/* Location:              /mnt/data/data/working/bitbucket/common/slsjtemplate/dist/jtemplate-1.0.jar!/com/sls/jtemplate/thrift/TServers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */
