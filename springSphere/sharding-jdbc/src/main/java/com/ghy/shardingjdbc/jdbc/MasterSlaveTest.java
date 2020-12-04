package com.ghy.shardingjdbc.jdbc;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MasterSlaveTest {
    public static void main(String[] args) throws SQLException {
        // 配置真实数据源
        Map<String, DataSource> dataSourceMap = new HashMap<>();

        // 配置第一个数据源
        DruidDataSource dataSource1 = new DruidDataSource();
        dataSource1.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource1.setUrl("jdbc:mysql://192.168.2.105:3306/ljxmycat");
        dataSource1.setUsername("root");
        dataSource1.setPassword("root");
        dataSourceMap.put("master0", dataSource1);

        // 配置第二个数据源
        DruidDataSource dataSource2 = new DruidDataSource();
        dataSource2.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource2.setUrl("jdbc:mysql://192.168.2.106:3306/ljxmycat");
        dataSource2.setUsername("root");
        dataSource2.setPassword("root");
        dataSourceMap.put("slave0", dataSource2);

        // 配置读写分离规则
        MasterSlaveRuleConfiguration masterSlaveRuleConfig = new MasterSlaveRuleConfiguration("qs_master_slave", "master0", Arrays.asList("slave0"));

        // 获取数据源对象
        DataSource dataSource = MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, masterSlaveRuleConfig, new Properties());
        Connection conn = dataSource.getConnection();

        String selectSql = "SELECT * from student where qq=?";
        try {
            PreparedStatement preparedStatement = conn.prepareStatement(selectSql);
            // 直接在 slave 128 ds0 插入主节点没有的数据： insert into t_order(order_id, user_id) value(26732,26732)
            preparedStatement.setString(1, "466669999");
            System.out.println();
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    System.out.println("---------name：" + rs.getString(2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}