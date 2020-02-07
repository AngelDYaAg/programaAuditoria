/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author PAVILLION
 */
public class ConexionBD {

    private Connection cnx;
    private String server, database, user, password;

    public ConexionBD(String server, String database, String user, String password) {
        this.server = server;
        this.database = database;
        this.user = user;
        this.password = password;
    }

    public Connection getCnx() {
        return cnx;
    }

    public void setCnx(Connection cnx) {
        this.cnx = cnx;
    }

    public Connection obtener() {
    	String connectionUrl =
                "jdbc:sqlserver://"+server+";"
                + "database="+database+";"
                + "user="+user+";"
                + "password="+password+";";

        try {
        	Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        	cnx = DriverManager.getConnection(connectionUrl);
            // String dbURL = "jdbc:sqlserver://" + server + ";databaseName=" + database;
            // cnx = DriverManager.getConnection(dbURL, user, password);
            if (cnx != null) {
                DatabaseMetaData dm = (DatabaseMetaData) cnx.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return cnx;
    }

    public void cerrar() {
        if (cnx != null) {
            try {
                cnx.close();
            } catch (SQLException ex) {
                Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    // TABLAS
    public DefaultTableModel getTablas(Connection conn, DefaultTableModel modelo) {

        try {
            Statement statement = conn.createStatement();
            String selectTablas = "select schema_name(t.schema_id) as schema_name,\n"
                    + "       t.name as table_name\n"
                    + "from sys.tables t\n"
                    + "order by schema_name,\n"
                    + "         table_name;";
            ResultSet resultSet = statement.executeQuery(selectTablas);
            String row[] = new String[2];
            while (resultSet.next()) {
                for (int i = 0; i < 2; i++) {
                    row[i] = resultSet.getString(i + 1);
                }
                modelo.addRow(row);
            }
        } catch (SQLException ex) {
            System.err.println("Error al recuperar datos de tablas :(");
        }
        return modelo;
    }

    // PK
    public DefaultTableModel getPKs(Connection conn, DefaultTableModel modelo) {
        try {
            Statement statement = conn.createStatement();
            String selectTablas = "select schema_name(tab.schema_id) as [schema_name], \n"
                    + "    tab.[name] as table_name, \n"
                    + "    pk.[name] as pk_name,\n"
                    + "    substring(column_names, 1, len(column_names)-1) as [columns]\n"
                    + "from sys.tables tab\n"
                    + "    left outer join sys.indexes pk\n"
                    + "        on tab.object_id = pk.object_id \n"
                    + "        and pk.is_primary_key = 1\n"
                    + "   cross apply (select col.[name] + ', '\n"
                    + "                    from sys.index_columns ic\n"
                    + "                        inner join sys.columns col\n"
                    + "                            on ic.object_id = col.object_id\n"
                    + "                            and ic.column_id = col.column_id\n"
                    + "                    where ic.object_id = tab.object_id\n"
                    + "                        and ic.index_id = pk.index_id\n"
                    + "                            order by col.column_id\n"
                    + "                            for xml path ('') ) D (column_names)\n"
                    + "order by schema_name(tab.schema_id),\n"
                    + "    tab.[name]";
            ResultSet resultSet = statement.executeQuery(selectTablas);
            String row[] = new String[4];
            while (resultSet.next()) {
                for (int i = 0; i < 4; i++) {
                    row[i] = resultSet.getString(i + 1);
                }
                modelo.addRow(row);
            }
        } catch (SQLException ex) {
            System.err.println("Ocurrio un error al traer info de las PK");
        }
        return modelo;
    }

    public DefaultTableModel getNoPKs(Connection conn, DefaultTableModel modelo) {

        try {
            Statement statement = conn.createStatement();
            String selectTablas = "select schema_name(tab.schema_id) as [schema_name], \n"
                    + "    tab.[name] as table_name\n"
                    + "from sys.tables tab\n"
                    + "    left outer join sys.indexes pk\n"
                    + "        on tab.object_id = pk.object_id \n"
                    + "        and pk.is_primary_key = 1\n"
                    + "where pk.object_id is null\n"
                    + "order by schema_name(tab.schema_id),\n"
                    + "    tab.[name]";
            ResultSet resultSet = statement.executeQuery(selectTablas);
            String row[] = new String[2];
            while (resultSet.next()) {
                for (int i = 0; i < 2; i++) {
                    row[i] = resultSet.getString(i + 1);
                }
                modelo.addRow(row);
            }
        } catch (SQLException ex) {
            System.err.println("Error al traer datos de FKs");
        }
        return modelo;
    }

    public String[] getPorcentajePKs(Connection conn){
        String resultado[] = new String[3];
        try {    
            Statement statement = conn.createStatement();
            String selectTablas = "select \n"
                    + "    all_tabs.[tables] as all_tables,\n"
                    + "    no_pk.[tables] as no_pk_tables,\n"
                    + "    cast(cast(100.0 * no_pk.[tables] / \n"
                    + "    all_tabs.[tables] as decimal(36, 1)) as varchar) + '%' as no_pk_percent\n"
                    + "from\n"
                    + "    (select count(*) as [tables]\n"
                    + "    from sys.tables tab\n"
                    + "        left outer join sys.indexes pk\n"
                    + "            on tab.object_id = pk.object_id \n"
                    + "            and pk.is_primary_key = 1\n"
                    + "    where pk.object_id is null) as no_pk\n"
                    + "inner join \n"
                    + "    (select count(*) as [tables]\n"
                    + "    from sys.tables) as all_tabs\n"
                    + "on 1 = 1";
            ResultSet resultSet = statement.executeQuery(selectTablas);
            String all_tables;
            String no_pk_tables;
            String no_pk_percent;
            
            while (resultSet.next()) {
                for (int i = 0; i < 3; i++) {
                    resultado[i] = resultSet.getString(i + 1);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultado;
    }

    // FK
    public DefaultTableModel getFKs(Connection conn, DefaultTableModel modelo) {
        try {
            Statement statement = conn.createStatement();
            String selectTablas = "SELECT FK.name, PFK.name AS parentTable, RFK.name AS referencedTable\n"
                    + "FROM sys.foreign_keys FK\n"
                    + "INNER JOIN sys.objects PFK ON PFK.object_id = FK.parent_object_id\n"
                    + "INNER JOIN sys.objects RFK ON RFK.object_id = FK.referenced_object_id";
            ResultSet resultSet = statement.executeQuery(selectTablas);
            String row[] = new String[3];
            while (resultSet.next()) {
                for (int i = 0; i < 3; i++) {
                    row[i] = resultSet.getString(i + 1);
                }
                modelo.addRow(row);
            }
        } catch (SQLException ex) {
            System.err.println("Error al traer las FK");
        }
        return modelo;

    }

    public DefaultTableModel getConstraints(Connection conn, DefaultTableModel modelo) {
        try {
            Statement statement = conn.createStatement();
            String selectTablas = "DBCC CHECKCONSTRAINTS WITH ALL_CONSTRAINTS";
            ResultSet resultSet = statement.executeQuery(selectTablas);
            
            String row[] = new String[3];
            while (resultSet.next()) {
                for (int i = 0; i < 3; i++) {
                    row[i] = resultSet.getString(i + 1);
                }
                modelo.addRow(row);
            }
        } catch (SQLException ex) {
            System.err.println("Error al traer datos de constrains");
            return modelo;
        }
        return modelo;
    }

    public DefaultTableModel getNoFKs(Connection conn, DefaultTableModel modelo) {
        try {
            Statement statement = conn.createStatement();
            String selectTablas = "select schema_name(fk_tab.schema_id) as schema_name,\n"
                    + "    fk_tab.name as table_name,\n"
                    + "    '>- no FKs' foreign_keys\n"
                    + "from sys.tables fk_tab\n"
                    + "    left outer join sys.foreign_keys fk\n"
                    + "        on fk_tab.object_id = fk.parent_object_id\n"
                    + "where fk.object_id is null\n"
                    + "order by schema_name(fk_tab.schema_id),\n"
                    + "    fk_tab.name";
            ResultSet resultSet = statement.executeQuery(selectTablas);
            String row[] = new String[3];
            while (resultSet.next()) {
                for (int i = 0; i < 3; i++) {
                    row[i] = resultSet.getString(i + 1);
                }
                modelo.addRow(row);
            }
        } catch (SQLException ex) {
            System.err.println("Error al recibir datos");
        }
        return modelo;
    }

    public DefaultTableModel getTriggers(Connection conn, DefaultTableModel modelo) {

        try {
            Statement statement = conn.createStatement();
            String selectTablas = "select schema_name(tab.schema_id) + '.' + tab.name as [table],\n"
                    + "    trig.name as trigger_name,\n"
                    + "    case when is_instead_of_trigger = 1 then 'Instead of'\n"
                    + "        else 'After' end as [activation],\n"
                    + "    (case when objectproperty(trig.object_id, 'ExecIsUpdateTrigger') = 1 \n"
                    + "            then 'Update ' else '' end\n"
                    + "    + case when objectproperty(trig.object_id, 'ExecIsDeleteTrigger') = 1 \n"
                    + "            then 'Delete ' else '' end\n"
                    + "    + case when objectproperty(trig.object_id, 'ExecIsInsertTrigger') = 1 \n"
                    + "            then 'Insert ' else '' end\n"
                    + "    ) as [event],\n"
                    + "    case when trig.[type] = 'TA' then 'Assembly (CLR) trigger'\n"
                    + "        when trig.[type] = 'TR' then 'SQL trigger' \n"
                    + "        else '' end as [type],\n"
                    + "    case when is_disabled = 1 then 'Disabled'\n"
                    + "        else 'Active' end as [status],\n"
                    + "    object_definition(trig.object_id) as [definition]\n"
                    + "from sys.triggers trig\n"
                    + "    inner join sys.objects tab\n"
                    + "        on trig.parent_id = tab.object_id\n"
                    + "order by schema_name(tab.schema_id) + '.' + tab.name, trig.name;";
            ResultSet resultSet = statement.executeQuery(selectTablas);
            String row[] = new String[7];
            while (resultSet.next()) {
                for (int i = 0; i < 7; i++) {
                    row[i] = resultSet.getString(i + 1);
                }
                modelo.addRow(row);
            }
        } catch (SQLException ex) {
            System.err.println("Error al traer datos de Triggers");
        }
    return modelo;
    }
    
}
