package ly.config;

import ly.AbstractConfigManger;
import ly.ConfigLoadException;
import ly.InterfaceConfigManagerProxy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;

/*
 * Author: liuYang
 * Date: 2025/3/31
 * File: ActivityInfoConfigManager
 */
public class ActivityInfoConfigManager implements InterfaceConfigManagerProxy {
    AtomicBoolean switched = new AtomicBoolean(false);
    private static ActivityInfoConfigManager instance = new ActivityInfoConfigManager();
    private static ActivityInfoConfigManagerImpl instanceImplA = new ActivityInfoConfigManagerImpl();
    private static ActivityInfoConfigManagerImpl instanceImplB = new ActivityInfoConfigManagerImpl();

    public boolean isSwitched() {
        return switched.getAndSet(!switched.get());
    }
    public static  ActivityInfoConfigManagerImpl getInstance(){
        if (instance.isSwitched()){
            return instanceImplA;
        } else {
            return instanceImplB;
        }
    }

    @Override
    public void loadConfig(String configDir) throws ConfigLoadException {
        getInstance().reload(configDir);
    }

    public static class ActivityInfoConfigManagerImpl extends AbstractConfigManger {

                List<ActivityInfoConfig> configList = new ArrayList<ActivityInfoConfig>();
        Map<Integer, ActivityInfoConfig> configMap = new HashMap<Integer, ActivityInfoConfig>();

        //@@@@@自定义属性开始区@@@@@
        List<ActivityInfoConfig> configList1 = new ArrayList<ActivityInfoConfig>();

        //@@@@@自定义属性结束区@@@@@


        @Override
      protected   void reload(String configDir) throws ConfigLoadException {
            String fileName = configDir + File.separator + getConfigFileName();
            File file = new File(fileName);
            clear();
            if (!file.exists()){ throw new ConfigLoadException("Config file does not exist :"+ fileName); }
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {  // 按行读取
                    String[] arr = line.split("\t");
                    ActivityInfoConfig config = new ActivityInfoConfig();
                    try {
                        config.activityId = Integer.parseInt(arr[0]);
                        config.activityName = arr[1];
                    }catch(Exception e){
                        logger.log(Level.WARNING,String.format("解析配置 %s 表, 字符串:%s 报错，请检查:%s",fileName,line,e.getMessage()));
                        e.printStackTrace();
                        throw new ConfigLoadException("Error parsing config file :"+ fileName);
                    }
                    config.afterLoad();
                    configList.add(config);
                    configMap.put(config.activityId, config);
                }
                afterLoad();
            } catch (IOException e) {
                e.printStackTrace();
                throw new ConfigLoadException("Config file could not be read :"+ fileName);
            }
        }

         @Override
         protected void clear() {

             configList.clear();
             configMap.clear();

             //@@@@@自定义clear方法开始区@@@@@
             configList1.clear();
             //@@@@@自定义clear方法结束区@@@@@
         }



         public List<ActivityInfoConfig> getConfigList() {
             return configList;
         }

         public Map<Integer, ActivityInfoConfig> getConfigMap() {
             return configMap;
         }

         @Override
        public String getConfigFileName() {
            return "activityInfo.txt";
        }


         //@@@@@自定义方法开始区@@@@@

         public List<ActivityInfoConfig> getConfigList1() {
             return configList1;
         }

         @Override
         protected  void afterLoad() {

         }

         //@@@@@自定义方法结束区@@@@@
    }
}
