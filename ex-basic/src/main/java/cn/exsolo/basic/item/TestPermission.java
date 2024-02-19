package cn.exsolo.basic.item;

import cn.exsolo.batis.core.utils.GenerateID;

/**
 * @author prestolive
 * @date 2021/6/26
 **/
public class TestPermission {

     public static void main(String[] args) {
          for(int i=0;i<100;i++){
               System.out.println(GenerateID.generateShortUuid());
          }
     }
}
