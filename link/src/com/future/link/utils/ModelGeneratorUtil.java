package com.future.link.utils;

import com.jfinal.kit.PathKit;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.generator.Generator;
import com.jfinal.plugin.activerecord.generator.MetaBuilder;
import com.jfinal.plugin.druid.DruidPlugin;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelGeneratorUtil {
	
	public static DataSource getDataSource() {
        PropKit.use("DataBase.properties");
        DruidPlugin druidPlugin = new DruidPlugin(PropKit.get("jdbc.mysqlUrl"), PropKit.get("jdbc.mysqlUser"), PropKit.get("jdbc.mysqlPassword").trim());
        druidPlugin.start();
        return druidPlugin.getDataSource();
    }

    /**
     * @param tableHead  ��ǰ׺
     * @param moduleName ģ����
     */
    public static void start(String tableHead, String moduleName) {
        Connection conn = null;
        //base model ��ʹ�õİ���
        String baseModelPackageName = "com.future.link." + tableHead + ".model.base";
        // base model �ļ�����·��
        String baseModelOutputDir = PathKit.getWebRootPath() + "/../" + moduleName + "/src/com/future/link/" + tableHead + "/model/base";
        // model ��ʹ�õİ��� (MappingKit Ĭ��ʹ�õİ���)
        String modelPackageName = "com.future.link." + tableHead + ".model";
        // model �ļ�����·�� (MappingKit �� DataDictionary �ļ�Ĭ�ϱ���·��)
        String modelOutputDir = baseModelOutputDir + "/..";
        try {
            //��ȡ��ݿ������
            conn = getDataSource().getConnection();
            //��ȡ���ڵı�
            DatabaseMetaData dbMeta = conn.getMetaData();
            ResultSet rs = dbMeta.getTables(conn.getCatalog(), null, null, new String[]{"TABLE", "VIEW"});
            //�����Զ��������
            MetaBuilder metaBuilder = new MetaBuilder(getDataSource());
            // ���������
            Generator gernerator = new Generator(getDataSource(), baseModelPackageName, baseModelOutputDir, modelPackageName, modelOutputDir);
            // �����Ƿ��� Model ����� dao ����
            gernerator.setGenerateDaoInModel(true);
            // �����Ƿ�����ֵ��ļ�
            gernerator.setGenerateDataDictionary(true);
            // ������Ҫ���Ƴ�ı���ǰ׺�������modelName��������� "osc_user"���Ƴ�ǰ׺ "osc_"����ɵ�model��Ϊ "User"��� OscUser
            metaBuilder.setRemovedTableNamePrefixes(tableHead + "_");
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                if (!tableName.startsWith(tableHead + "_")) {
                    // ��Ӳ���Ҫ��ɵı���
                    metaBuilder.addExcludedTable(tableName);
                }
                System.out.println(tableName);
            }
            gernerator.setMetaBuilder(metaBuilder);
            // ���
            gernerator.generate();
            rs.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
