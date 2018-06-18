package example;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.junit.Test;

/**
 * 
 * 测试类
 *
 * @author wangzejun
 */
public class QSortTest
{
    
    @Test
    public void QuickSortTest()
        throws InterruptedException, ExecutionException
    {
        // 从文档获取的所有的字符串集合
        List<String> allList = new ArrayList<String>();
        allList = Utils.readOutFile("in.txt");
        // 将总的字符串集合分割成n个字符串集合，这里n=3
        List<List<String>> lists = Utils.averageAssign(allList, 3);
        List<List<String>> newLists = new ArrayList<List<String>>();
        // 经过排序后的字符串集合
        List<String> newList = new ArrayList<String>();
        
        // 多线程分别快速排序
        for (List<String> list : lists)
        {
            QSortService de1 = new QSortService(list, list.size());
            // 起异步线程
            FutureTask<List<String>> result = new FutureTask<List<String>>(de1);
            new Thread(result).start();
            newLists.add(result.get());
        }
        System.out.println(newLists.toString());
        // 将分别排序后的list归并排序
        if (newLists != null && newLists.size() > 0)
        {
            for (int i = 0; i < newLists.size(); i++)
            {
                // 归并排序，合并已排好序的集合
                newList = Utils.merge(newList, newLists.get(i));
            }
        }
        // 打印排序完成后的集合
        System.out.println(newList.toString());
        // 将已排序的字符串集合按行写入out.txt文件
        Utils.writeFile("out.txt", newList);
        
    }
    
}