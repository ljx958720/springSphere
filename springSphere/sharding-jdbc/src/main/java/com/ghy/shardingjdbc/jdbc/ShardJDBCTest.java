package com.ghy.shardingjdbc.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ShardJDBCTest {
    public static void main(String[] args) throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置第一个数据源
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://192.168.2.106:3306/ljxmycat0");
        dataSource1.setUsername("root");
        dataSource1.setPassword("root");
        dataSourceMap.put("ljxmycat0", dataSource1);

        // 配置第二个数据源
        DruidDataSource dataSource2 = new DruidDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://192.168.2.106:3306/ljxmycat1");
        dataSource2.setUsername("root");
        dataSource2.setPassword("root");
        dataSourceMap.put("ljxmycat1", dataSource2);

        // 配置Order表规则
        TableRuleConfiguration orderTableRuleConfig = new TableRuleConfiguration("student", "ljxmycat${0..1}.student");
        // 分库策略，使用inline实现
        InlineShardingStrategyConfiguration dataBaseInlineStrategy = new InlineShardingStrategyConfiguration("sid", "ljxmycat${sid % 2}");
        orderTableRuleConfig.setDatabaseShardingStrategyConfig(dataBaseInlineStrategy);

        // 分表策略，使用inline实现（没有分表，为什么不分表？）
        InlineShardingStrategyConfiguration tableInlineStrategy = new InlineShardingStrategyConfiguration("sid", "student");
        orderTableRuleConfig.setTableShardingStrategyConfig(tableInlineStrategy);

        // 添加表配置
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(orderTableRuleConfig);

        // 获取数据源对象
        DataSource dataSource = ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, new Properties());

        String sql = "SELECT * from student WHERE qq=?";
        try {
            Connection conn = dataSource.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "166669999");
            System.out.println();
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    // %2结果，路由到
                    System.out.println("---------name：" + rs.getString(2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}