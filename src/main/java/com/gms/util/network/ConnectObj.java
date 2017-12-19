package com.gms.util.network;

import android.content.Context;

import com.gms.util.interfaces.IConnectInterface;

/**
 * Created by gms on 2017/10/24.
 */

public abstract class ConnectObj implements IConnectInterface.ISubject {

    protected Context mContext;

    protected IConnectInterface.IManager mManager ;

    public ConnectObj(Context context , IConnectInterface.IManager manager) {
        this.mContext = context;
        registerManager(manager);
        initService();
    }

    @Override
    public void registerManager(IConnectInterface.IManager manager) {
        this.mManager = manager ;
    }

    @Override
    public void unRegisterObserver(IConnectInterface.IManager manager) {

    }

    protected abstract void initService() ;

}
