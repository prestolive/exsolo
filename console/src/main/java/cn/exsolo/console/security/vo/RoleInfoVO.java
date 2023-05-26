package cn.exsolo.console.security.vo;

import cn.exsolo.console.security.po.RolePO;
import cn.exsolo.console.security.po.RolePowerPO;
import cn.exsolo.console.security.po.UserPO;

import java.util.List;

/**
 * @author prestolive
 * @date 2023/5/17
 **/
public class RoleInfoVO {

    private RolePO rolePO;

    private List<RolePowerPO> powers;

    public RolePO getRolePO() {
        return rolePO;
    }

    public void setRolePO(RolePO rolePO) {
        this.rolePO = rolePO;
    }

    public List<RolePowerPO> getPowers() {
        return powers;
    }

    public void setPowers(List<RolePowerPO> powers) {
        this.powers = powers;
    }
}
