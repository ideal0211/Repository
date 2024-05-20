package com.example.mybatisplus;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MySQLGeneratorTest {

    private static final String URL = "jdbc:mysql://192.168.0.10:3306/fabric?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    /**
     * 数据源配置
     */
    private static final DataSourceConfig.Builder DATA_SOURCE_CONFIG = new DataSourceConfig.Builder(URL, USERNAME, PASSWORD);

    @BeforeAll
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
        String basePackage = "com.fabric.db";

        Map<OutputFile, String> pathInfo = new HashMap<>();
        pathInfo.put(OutputFile.controller, null);//禁用controller文件生成
        pathInfo.put(OutputFile.xml, xmlPath);//更改xml文件默认生成目录

        FastAutoGenerator.create(DATA_SOURCE_CONFIG)
                //全局配置
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
//                        .addInclude("t_commodity")
                        .addTablePrefix("t_")
                        .controllerBuilder().enableFileOverride().disable()
                        .entityBuilder().idType(IdType.ASSIGN_ID).enableFileOverride().enableLombok().enableTableFieldAnnotation()
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
