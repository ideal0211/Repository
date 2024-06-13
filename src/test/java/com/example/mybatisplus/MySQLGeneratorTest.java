package com.example.mybatisplus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySQLGeneratorTest {

//    private static final String URL = "jdbc:mysql://192.168.0.10:3306/fabric?useUnicode=true&useSSL=false&characterEncoding=utf8";
//    private static final String USERNAME = "root";
//    private static final String PASSWORD = "123456";

    private static final String URL = "jdbc:mysql://devmysql.local.yjzhixue.com:3306/kp_promotion?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "kpkj@2021";
    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig.Builder(URL, USERNAME, PASSWORD);

//    @BeforeAll
    public static void before(){
        /**1.执行数据库脚本*/
        initDataSource(DATA_SOURCE_CONFIG.build());
        System.out.println("MySQLGeneratorTest.before");
    }
    @Test
    public void execute() {
        //先得到当前工程目录
        String projectDir = System.getProperty("user.dir");
        String outputPath = projectDir + "/src/main/java";
        String xmlPath = projectDir + "/src/main/resources/mapper";
        //父包路径
        String basePackage = "org.lazyjee.erbag";
        //指定表
        String[] tables = {"er_print_order"};
        //排除表前缀
        String tablePrefix = "er_";
        //指定实体后缀
        String entitySuffix = "Po";

        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.controller, null);//禁用controller文件生成
        pathInfo.put(OutputFile.xml, xmlPath);//更改xml文件默认生成目录

        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                //全局配置后缀
                .globalConfig(builder -> builder
                        .author("lzp")
                        .disableOpenDir()
                        .dateType(DateType.ONLY_DATE)
                        .outputDir(outputPath))
                //注入配置(自定义模板文件生成)
//                .injectionConfig(builder -> builder.customFile(Arrays.asList(new CustomFile.Builder().templatePath("/templates/entity.java.vm").build())))
                //包配置
                .packageConfig(builder -> builder
                        .parent(basePackage)
                        //路径配置信息
                        .pathInfo(pathInfo))
                //策略配置
                .strategyConfig(builder -> builder
                        //指定表（不设表示所有）
                        .addInclude(tables)
                        //过滤表前缀
                        .addTablePrefix(tablePrefix)
                        .controllerBuilder().enableFileOverride().disable()
                        .entityBuilder().disableSerialVersionUID().idType(IdType.ASSIGN_ID).formatFileName("%s" + entitySuffix).enableFileOverride().enableLombok().enableTableFieldAnnotation()
                        .mapperBuilder().enableFileOverride()
                        .serviceBuilder().enableFileOverride())
                //模板配置，默认：VelocityTemplateEngine
//                .templateEngine(new VelocityTemplateEngine())
                .execute();
    }

    /**
     * 执行数据库脚本
     */
    private static void initDataSource(DataSourceConfig dataSourceConfig) {
        try (Connection conn = dataSourceConfig.getConn();
             InputStream in = MySQLGeneratorTest.class.getResourceAsStream("/sql/init.sql")) {
            ScriptRunner scriptRunner = new ScriptRunner(conn);
            scriptRunner.setAutoCommit(true);
            scriptRunner.runScript(new InputStreamReader(in));
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

}
