package cn.exsolo.basic.tree;

import cn.exsolo.auth.shiro.ext.stereotype.AccessEdit;
import cn.exsolo.auth.shiro.ext.stereotype.AccessView;
import cn.exsolo.springmvcext.stereotype.RequestJSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author prestolive
 * @date 2024/11/15
 **/
public abstract class CommonTreeWebController<T extends CommonTreeNodePO> {

    protected abstract ITreeService<T> getTreeService();

    @AccessView
    @RequestMapping(path = "get", method = RequestMethod.POST)
    public T get(@RequestParam String id) {
        T nodeQueryTemplate= getTreeService().createTemplate();
        nodeQueryTemplate.setId(id);
        return getTreeService().getNode(nodeQueryTemplate);
    }

    @AccessView
    @RequestMapping(path = "nodes", method = RequestMethod.POST)
    public List<T> nodes(@RequestParam() String genus, @RequestParam(required = false) String parentId) {
        T nodeQueryTemplate= getTreeService().createTemplate();
        nodeQueryTemplate.setId(parentId);
        nodeQueryTemplate.setGenus(genus);
        return getTreeService().getNodeChildren(nodeQueryTemplate);
    }

    @AccessEdit
    @RequestMapping(path = "create-node", method = RequestMethod.POST)
    public T createNOde(@RequestJSON T node,@RequestJSON String parentId) {
        return getTreeService().createNode(node,parentId);
    }

    @AccessEdit
    @RequestMapping(path = "delete-node", method = RequestMethod.POST)
    public void deleteNode(@RequestParam() String id) {
        T nodeQueryTemplate=  getTreeService().createTemplate();
        nodeQueryTemplate.setId(id);
        getTreeService().deleteNode(nodeQueryTemplate);
    }

    @AccessEdit
    @RequestMapping(path = "modify-node", method = RequestMethod.POST)
    public T modifyNode(@RequestJSON T node) {
        return getTreeService().modifyNode(node);
    }

}
