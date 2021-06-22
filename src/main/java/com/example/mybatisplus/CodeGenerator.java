package com.example.mybatisplus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;

import java.util.Collections;

public class CodeGenerator {

    private final static String author = "lzp";
    //项目根目录
    private final static String projectPath = System.getProperty("user.dir");

    private final static String url = "jdbc:mysql://192.168.0.84:3306/fyt_exam_result?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private final static String driverName = "com.mysql.cj.jdbc.Driver";
    private final static String userName = "root";
    private final static String pwd = "kpkj@2021";

    //去掉表前缀再生成实体类
    private final static String prefix = "t_";
    //设置entity、mapper、service生成的package
    private final static String basePackage = "com.test.tools";
    //设置mapper.xml生成的目录
    private final static String mapperDir = "/src/main/resources/mapper/";
    //需要包含的表名，允许正则表达式,不设置表示当前DB下所有表
    private final static String[] tables = {};



    public static void main(String[] args) {
        //执行生成
        getGenerator().execute();
    }


    public static AutoGenerator getGenerator() {
        // 全局配置
        GlobalConfig gcConfig = new GlobalConfig();
        //代码生成目录
        gcConfig.setOutputDir(projectPath + "/src/main/java")
                .setAuthor(author)  //开发人员
                .setOpen(false) //是否打开输出目录(默认值：null)
//                .setSwagger2(true)  //实体属性 Swagger2 注解
                .setFileOverride(true)  // 是否覆盖已有文件(默认值：false)
                .setIdType(IdType.INPUT)//设置id生成策略
                .setServiceImplName("%sServiceImpl") //设置实现类名称
                .setServiceName("%sService")//设置接口名称
                .setDateType(DateType.ONLY_DATE);// 配置时间类型策略（date类型），如果不配置会生成LocalDate类型

        // 数据库表配置
        StrategyConfig strategy = new StrategyConfig()
                .setNaming(NamingStrategy.underline_to_camel)   // 数据库表映射到实体的命名策略:下划线转驼峰
                .setColumnNaming(NamingStrategy.underline_to_camel) // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
                .setEntityLombokModel(true) // 实体是否为lombok模型（默认 false）
                .setRestControllerStyle(true)   // 生成 @RestController 控制器
                .setEntityTableFieldAnnotationEnable(true)  // 是否生成实体时，生成字段注解 默认false;
                .setControllerMappingHyphenStyle(true)  // 驼峰转连字符
                .setInclude(tables) // 需要包含的表名，允许正则表达式,不设置表示当前db下所有表
                .setTablePrefix(prefix); // 去掉表前缀再生成实体类

        // 配置代码生成器
        return new AutoGenerator()
                .setGlobalConfig(gcConfig)
                .setDataSource(new DataSourceConfig()
                        .setUrl(url)
                        .setDriverName(driverName)
                        .setUsername(userName)
                        .setPassword(pwd))
                .setPackageInfo(new PackageConfig().setParent(basePackage))
                //关闭默认生成xml,关闭Controller生成,关闭service生成 .setService(null).setServiceImpl(null)
                .setTemplate(new TemplateConfig().setXml(null).setController(null))
                //指定生成的模板引擎
                .setTemplateEngine(new VelocityTemplateEngine())
                //自定义xml生成路径
                .setCfg(new MyInjectionConfig().setFileOutConfigList(Collections.singletonList(new MyFileOutConfig())))
                .setStrategy(strategy);
    }


    public static class MyInjectionConfig extends InjectionConfig {
        @Override
        public void initMap() {
            //TODO 注入自定义 Map 对象(注意需要setMap放进去)
        }
    }

    /**
     * 自定义xml生成
     * 1.指定模板引擎
     * 2.指定生成xml的目录+命名规则
     */
    public static class MyFileOutConfig extends FileOutConfig {

        public MyFileOutConfig(){
            // 如果模板引擎是 freemarker
            // String templatePath = "/templates/mapper.xml.ftl";
            // 如果模板引擎是 velocity
            // String templatePath = "/templates/mapper.xml.vm";
            super("/templates/mapper.xml.vm");
        }

        @Override
        public String outputFile(TableInfo tableInfo) {
            // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
            return projectPath + mapperDir + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
        }
    }
}