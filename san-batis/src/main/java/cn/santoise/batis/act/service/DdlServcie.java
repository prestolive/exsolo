package cn.santoise.batis.act.service;

import cn.santoise.batis.act.bo.ActTableBO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * @author wbingy
 * @date 2023/3/9
 **/
@Service
public class DdlServcie {

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public void createTable(ActTableBO table) {

    }

    @Transactional(value = Transactional.TxType.REQUIRES_NEW, rollbackOn = Exception.class)
    public void modifyTable() {

    }
}
