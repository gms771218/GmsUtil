package com.gms.util.interfaces;

/**
 * Created by gms on 2017/7/24.
 */

public class IConnectInterface {


    public interface IManager {
        /**
         * 加入監聽
         * @param observer
         */
        void registerObserver(IConnectInterface.IObserver observer);

        /**
         * 移除監聽
         * @param observer
         */
        void unRegisterObserver(IConnectInterface.IObserver observer);

        void notifyObservers(boolean isConnect, boolean isInternet);

        /**
         * 是否有連線
         * @return
         */
        int getConnectStatus();

        void onStart();

        void onStop();

    }

    public  interface ISubject {

        void registerManager(IConnectInterface.IManager manager) ;

        void unRegisterObserver(IConnectInterface.IManager manager);

    }


    /**
     * 觀察者
     */
    public interface IObserver {

        /**
         * 接受網路變更通知
         * @param connectStatus
         */
        void update(int connectStatus);
    }

}
