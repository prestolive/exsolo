package cn.exsolo.basic.discovery;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author prestolive
 * @date 2021/3/1
 **/
@Component
public class NacosAliveContextAware implements ApplicationContextAware {
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

//    /**
//     * 当前应用的服务端口
//     */
//    @Value("${server.port}")
//    private String serverPort;
//
//    @Value("${spring.application.name}")
//    private String applicationName;
//
//    /**
//     * nacos注册中心地址
//     */
//    @Value("${spring.cloud.nacos.discovery.server-addr}")
//    private String serverAddress;
//
//    /**
//     * Nacos命名服务，此处为静态对象
//     */
//    public static NamingService naming;
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        try {
//            if (naming==null) {
//                //初始化Nacos命名服务
//                naming = NamingFactory.createNamingService(serverAddress);
////                //手工注册服务实例
////                Instance instance=new Instance();
////                instance.setIp(NetUtils.localIP());
////                instance.setPort(Integer.valueOf(serverPort));
////                //注册实例
////                naming.registerInstance(applicationName,instance);
//            }
//        } catch (NacosException e) {
//            e.printStackTrace();
//            System.exit(-1);
//        }
//    }
//
//    public List<Instance> findAllInstance(){
//        try {
//            return  naming.getAllInstances(applicationName);
//        } catch (NacosException e) {
//            throw new RuntimeException(e.getMessage(),e);
//        }
//    }
}
