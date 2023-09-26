package cn.exsolo.kit.utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用分页工具
 * Created by prestolive on 2020/7/13.
 *
 * @author prestolive
 */
public class ExPageWork {

    public interface IPageExecute<T> {
        void execute(List<T> list);
    }

    public <T> void pageExecute(List<T> list, int batchSize, IPageExecute process) {

        Integer counts = list.size();
        Integer pages = counts / batchSize;
        if (counts % batchSize > 0) {
            pages += 1;
        }
        for (int page = 1; page <= pages; page++) {
            int startRow = (page - 1) * batchSize;
            int thisBatchSize = batchSize;
            if ((startRow + batchSize) > list.size()) {
                thisBatchSize = list.size() - startRow;
            }
            int endRow = startRow + thisBatchSize;
            List<T> target = new ArrayList<>();
            for (int i = startRow; i < endRow; i++) {
                target.add(list.get(i));
            }
            process.execute(target);
        }
    }
}
