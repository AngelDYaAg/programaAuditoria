package controlador;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GenerarReporte {

    Connection cnx;
    LeerEscribirArchivos lea = new LeerEscribirArchivos();

    public GenerarReporte(Connection cnx) {
        this.cnx = cnx;
    }

    public Boolean generarReporteTxt() throws ClassNotFoundException {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd-HHmmss").format(Calendar.getInstance().getTime());
            String nombreArchivo = "logAuditoria" + timeStamp;
            lea.crearArchivo(nombreArchivo);
            String informe = "";
            Statement statement = cnx.createStatement();
            informe = informe + "\n***************************Tablas:*************************************************\n";
            informe = getTablas(cnx, informe);
            informe = informe + "\n***************************Primary Key:*************************************************\n";
            informe = getPKs(cnx, informe);
            informe = informe + "\n***************************No Primary Key:*************************************************\n";
            informe = getNoPKs(cnx, informe);
            informe = informe + "\n***************************Porcentaje Primary Key:*************************************************\n";
            informe = getPorcentajePKs(cnx, informe);
            informe = informe + "\n***************************Foreign Key:*************************************************\n";
            informe = getFKs(cnx, informe);
            informe = informe + "\n***************************No Foreign Key:*************************************************\n";
            informe = getNoFKs(cnx, informe);
            informe = informe + "\n***************************Triggers:*************************************************\n";
            informe = getTriggers(cnx, informe);
            informe = informe + "\n***************************Constraints:*************************************************";
            informe = getConstraints(cnx, informe);
            lea.escribirArchivo(informe, nombreArchivo);
           return true;
        } catch (Error | SQLException ex) {
            System.err.println("Error al generar reporte");
            return false;
        }
    }

    public String getTablas(Connection conn, String informe){
        Statement statement;
		try {
			statement = conn.createStatement();
        String selectTablas = "select schema_name(t.schema_id) as schema_name,\n"
                + "       t.name as table_name\n"
                + "from sys.tables t\n"
                + "order by schema_name,\n"
                + "         table_name;";
        ResultSet resultSet = statement.executeQuery(selectTablas);
        String schema_name;
        String table_name;

        while (resultSet.next()) {
            schema_name = resultSet.getString("schema_name");
            table_name = resultSet.getString("table_name");
            informe = informe + "\nschema_name" + ": " + schema_name + " | " + "table_name" + ": " + table_name + "\n";

        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			informe = informe + "/nNo existen resultados";
		}
        return informe;
    }

    public String getPKs(Connection conn, String informe) {

        Statement statement;
		try {
			statement = conn.createStatement();
		
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
        String schema_name;
        String table_name;
        String pk_name;
        String columns;

        while (resultSet.next()) {
            schema_name = resultSet.getString("schema_name");
            table_name = resultSet.getString("table_name");
            pk_name = resultSet.getString("pk_name");
            columns = resultSet.getString("columns");
            informe = informe + "\nschema_name" + ": " + schema_name + " | " + "table_name" + ": " + table_name + "\t | " + "pk_name" + ": " + pk_name + "\t | " + "columns" + ": " + columns + "\n";

        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			informe = informe + "/nNo existen resultados";
		}
        return informe;
    }

    public String getNoPKs(Connection conn, String informe){

        Statement statement;
		try {
			statement = conn.createStatement();
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
        String schema_name;
        String table_name;

        while (resultSet.next()) {
            schema_name = resultSet.getString("schema_name");
            table_name = resultSet.getString("table_name");
            informe = informe + "\nschema_name" + ": " + schema_name + " | " + "table_name" + ": " + table_name + "\n";

        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			informe = informe + "/nNo existen resultados";
		}
        return informe;
    }

    public String getPorcentajePKs(Connection conn, String informe) {

        Statement statement;
		try {
			statement = conn.createStatement();
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
            all_tables = resultSet.getString("all_tables");
            no_pk_tables = resultSet.getString("no_pk_tables");
            no_pk_percent = resultSet.getString("no_pk_percent");
            informe = informe + "\nall_tables" + ": " + all_tables + " | " + "no_pk_tables" + ": " + no_pk_tables + " | " + "no_pk_percent" + ": " + no_pk_percent + "\n";

        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			informe = informe + "/nNo existen resultados";
		}
       return informe;
    }

    public String getFKs(Connection conn, String informe) {

        Statement statement;
		try {
			statement = conn.createStatement();
        String selectTablas = "SELECT FK.name, PFK.name AS parentTable, RFK.name AS referencedTable\n"
                + "FROM sys.foreign_keys FK\n"
                + "INNER JOIN sys.objects PFK ON PFK.object_id = FK.parent_object_id\n"
                + "INNER JOIN sys.objects RFK ON RFK.object_id = FK.referenced_object_id";
        ResultSet resultSet = statement.executeQuery(selectTablas);
        String name;
        String parentTable;
        String referencedTable;

        while (resultSet.next()) {
            name = resultSet.getString("name");
            parentTable = resultSet.getString("parentTable");
            referencedTable = resultSet.getString("referencedTable");
            informe = informe + "\nname" + ": " + name + " | " + "parentTable" + ": " + parentTable + "\t | " + "referencedTable" + ": " + referencedTable + "\n";

        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			informe = informe + "/nNo existen resultados";
		}
        return informe;
    }

    public String getConstraints(Connection conn, String informe) {

        Statement statement;
		try {
			statement = conn.createStatement();
		
        String selectTablas = "DBCC CHECKCONSTRAINTS WITH ALL_CONSTRAINTS";
        ResultSet resultSet = statement.executeQuery(selectTablas);
        String Table;
        String Constraint;
        String Where;

        	while (resultSet.next()) {
                Table = resultSet.getString("Table");
                Constraint = resultSet.getString("Constraint");
                Where = resultSet.getString("Where");
                informe = informe + "\nTable" + ": " + Table + " | " + "Constraint" + ": " + Constraint + "\t | " + "Where" + ": " + Where + "\n";

            }
      
        
		} catch (SQLException e) {
			informe = informe + "\nNo existen resulados";
		}
        
        return informe;
    }

    public String getNoFKs(Connection conn, String informe) {

        Statement statement;
		try {
			statement = conn.createStatement();
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
        String schema_name;
        String table_name;
        String foreign_keys;

        while (resultSet.next()) {
            schema_name = resultSet.getString("schema_name");
            table_name = resultSet.getString("table_name");
            foreign_keys = resultSet.getString("foreign_keys");
            informe = informe + "\nschema_name" + ": " + schema_name + " | " + "table_name" + ": " + table_name + "\t | " + "foreign_keys" + ": " + foreign_keys + "\n";

        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			informe = informe + "\nNo existen resultados";
		}
        return informe;
    }

    public String getTriggers(Connection conn, String informe) {

        Statement statement;
		try {
			statement = conn.createStatement();
	
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
        String table;
        String trigger_name;
        String activation;
        String event;
        String type;
        String status;
        String definition;

        while (resultSet.next()) {
            table = resultSet.getString("table");
            trigger_name = resultSet.getString("trigger_name");
            activation = resultSet.getString("activation");
            event = resultSet.getString("event");
            type = resultSet.getString("type");
            status = resultSet.getString("status");
            definition = resultSet.getString("definition");
            informe = informe + "table" + ": " + table + " | " + "trigger_name" + ": " + trigger_name + "\t | " + "activation" + ": " + activation + "\t | " + "event" + ": " + event + "\t | " + "type" + ": " + type + "\t | " + "status" + ": " + status + "\t | " + "definition" + ": " + definition + "\n";

        }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			informe = informe + "\nNo existen resultados";
		}
        return informe;
    }

}
