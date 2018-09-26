package com.ltar.redis.core.ops;

/**
 * @desc:
 * @author: changzhigao
 * @date: 2018/9/26
 * @version: 1.0.0
 */
public interface ConnectionOperations {
    /**
     * 为redis服务请求设置一个密码。
     * redis可以设置在客户端执行commands请求前需要通过密码验证。
     * 通过修改配置文件的requirepass就可以设置密码。
     * 如果密码与配置文件里面设置的密码一致，服务端就会发会一个OK的状态码，
     * 接受客户端发送其他的请求命令，否则服务端会返回一个错误码，客户端需要尝试使用新的密码来进行连接。
     *
     * @param password
     * @return
     */
    String auth(String password);

    /**
     * 返回消息
     *
     * @param string
     * @return
     */
    String echo(String string);

    /**
     * 如果后面没有参数时返回PONG，否则会返回后面带的参数。
     *
     * @return
     */
    String ping();

    /**
     * 请求服务器关闭连接。连接将会尽可能快的将未完成的客户端请求完成处理。
     *
     * @return
     */
    String quit();

    /**
     * 选择一个数据库，下标值从0开始，一个新连接默认连接的数据库是DB0。
     *
     * @param index
     * @return
     */
    String select(int index);
}
