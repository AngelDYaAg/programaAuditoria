package vista;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import controlador.ConexionBD;
import controlador.GenerarReporte;

public class ResultadosAnalisis extends JFrame {
	ConexionBD conexion;

    /**
     * Creates new form ResultadosAnalisis
     */
    public ResultadosAnalisis(ConexionBD conexion) {
        this.conexion = conexion;
        initComponents();
        this.setSize(600, 400);
        this.setLocationRelativeTo(null);
        // Tablas
        resultadoTablasBD();
        // PK
        resultadoPKTablasBD();
        resultadoNoPKTablasBD();
        resultadosEvaluacionPKTablasBD();
        // FK
        resultadoFKTablasBD();
        resultadoNoFKTablasBD();
        // TRIGGERS
        resultadoTriggersBD();
        // CONSTRAIN
        resultadoCheckBD();
    }

    void resultadoTablasBD() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre Esquema");
        modelo.addColumn("Nombre Tabla");
        tbTablas.setModel(modelo);

        modelo = conexion.getTablas(conexion.getCnx(), modelo);

        tbTablas.setModel(modelo);
    }

    void resultadoPKTablasBD() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre Esquema");
        modelo.addColumn("Nombre Tabla");
        modelo.addColumn("Nombre PK");
        modelo.addColumn("Columna");
        tbPK.setModel(modelo);

        modelo = conexion.getPKs(conexion.getCnx(), modelo);

        tbPK.setModel(modelo);
    }

    void resultadoNoPKTablasBD() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre Esquema");
        modelo.addColumn("Nombre Tabla");
        tbNoPK.setModel(modelo);

        modelo = conexion.getNoPKs(conexion.getCnx(), modelo);

        tbNoPK.setModel(modelo);
    }

    void resultadosEvaluacionPKTablasBD() {
        String evaluacion[] = conexion.getPorcentajePKs(conexion.getCnx());
        txtNumTablas.setText(evaluacion[0]);
        txtNumTablasSinPK.setText(evaluacion[1]);
        txtPorcNoPK.setText(evaluacion[2]);
    }

    // FK
    void resultadoFKTablasBD() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre FK");
        modelo.addColumn("Nombre Tabla Origen");
        modelo.addColumn("Nombre Tabla Referenciada");
        tbFK.setModel(modelo);

        modelo = conexion.getFKs(conexion.getCnx(), modelo);

        tbFK.setModel(modelo);
    }

    void resultadoNoFKTablasBD() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre Esquema");
        modelo.addColumn("Nombre Tabla");
        modelo.addColumn("Clave Foranea");
        tbNoFK.setModel(modelo);

        modelo = conexion.getNoFKs(conexion.getCnx(), modelo);

        tbNoFK.setModel(modelo);
    }
    // TRIGGERS
    /*String table;
            String trigger_name;
            String activation;
            String event;
            String type;
            String status;
            String definition;*/
    void resultadoTriggersBD() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Nombre Tabla");
        modelo.addColumn("Nombre Trigger");
        modelo.addColumn("Activaci�n");
        modelo.addColumn("Evento");
        modelo.addColumn("Tipo");
        modelo.addColumn("Estado");
        modelo.addColumn("Definici�n");
        tbTriggers.setModel(modelo);

        modelo = conexion.getTriggers(conexion.getCnx(), modelo);

        tbTriggers.setModel(modelo);
    }
    // CHECK
    void resultadoCheckBD() {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("Table");
        modelo.addColumn("Constrain");
        modelo.addColumn("Where");
        tbRestricciones.setModel(modelo);
        modelo = conexion.getConstraints(conexion.getCnx(), modelo);
        tbRestricciones.setModel(modelo);
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        resultadosCheck = new javax.swing.JPanel();
        panelTablas = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbTablas = new javax.swing.JTable();
        evaluacionPK = new javax.swing.JPanel();
        panelPK = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbPK = new javax.swing.JTable();
        panelNoPK = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tbNoPK = new javax.swing.JTable();
        resultados = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtNumTablas = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtNumTablasSinPK = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtPorcNoPK = new javax.swing.JTextField();
        evaluacionFK = new javax.swing.JPanel();
        panelFK = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        tbFK = new javax.swing.JTable();
        panelNoFK = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tbNoFK = new javax.swing.JTable();
        evaluacionTriggers = new javax.swing.JPanel();
        panelTrigger = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane10 = new javax.swing.JScrollPane();
        tbTriggers = new javax.swing.JTable();
        evaluacionRestricciones = new javax.swing.JPanel();
        panelRestricciones = new javax.swing.JPanel();
        jPanel24 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tbRestricciones = new javax.swing.JTable();
        panelBotones = new javax.swing.JPanel();
        btnGenerarReporte5 = new javax.swing.JButton();
        btnSalir5 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.Y_AXIS));

        resultadosCheck.setLayout(new javax.swing.BoxLayout(resultadosCheck, javax.swing.BoxLayout.Y_AXIS));

        panelTablas.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tablas Encontradas", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        panelTablas.setLayout(new java.awt.CardLayout());

        jScrollPane4.setPreferredSize(new java.awt.Dimension(452, 200));

        tbTablas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "nombreEsquema", "nombreTabla"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tbTablas);

        panelTablas.add(jScrollPane4, "card2");

        resultadosCheck.add(panelTablas);

        evaluacionPK.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Evaluaci�n PK", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        evaluacionPK.setLayout(new javax.swing.BoxLayout(evaluacionPK, javax.swing.BoxLayout.Y_AXIS));

        panelPK.setLayout(new javax.swing.BoxLayout(panelPK, javax.swing.BoxLayout.Y_AXIS));

        jPanel14.setLayout(new javax.swing.BoxLayout(jPanel14, javax.swing.BoxLayout.LINE_AXIS));

        jLabel4.setText("Clave primaria de cada tabla");
        jPanel14.add(jLabel4);

        panelPK.add(jPanel14);

        jPanel15.setLayout(new java.awt.CardLayout());

        jScrollPane5.setPreferredSize(new java.awt.Dimension(452, 200));

        tbPK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "nombreEsquema", "nombreTabla"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tbPK);

        jPanel15.add(jScrollPane5, "card2");

        panelPK.add(jPanel15);

        evaluacionPK.add(panelPK);

        panelNoPK.setLayout(new javax.swing.BoxLayout(panelNoPK, javax.swing.BoxLayout.Y_AXIS));

        jPanel18.setLayout(new javax.swing.BoxLayout(jPanel18, javax.swing.BoxLayout.LINE_AXIS));

        jLabel5.setText("Tablas sin PK");
        jPanel18.add(jLabel5);

        panelNoPK.add(jPanel18);

        jPanel19.setLayout(new java.awt.CardLayout());

        jScrollPane6.setPreferredSize(new java.awt.Dimension(452, 200));

        tbNoPK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "nombreEsquema", "nombreTabla"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane6.setViewportView(tbNoPK);

        jPanel19.add(jScrollPane6, "card2");

        panelNoPK.add(jPanel19);

        evaluacionPK.add(panelNoPK);

        resultados.setBorder(javax.swing.BorderFactory.createTitledBorder("Resultado"));
        resultados.setLayout(new java.awt.GridLayout(3, 2));

        jLabel1.setText("# tablas");
        resultados.add(jLabel1);

        txtNumTablas.setEditable(false);
        resultados.add(txtNumTablas);

        jLabel2.setText("# tablas sin PK");
        resultados.add(jLabel2);

        txtNumTablasSinPK.setEditable(false);
        resultados.add(txtNumTablasSinPK);

        jLabel7.setText("% tablas sin PK");
        resultados.add(jLabel7);

        txtPorcNoPK.setEditable(false);
        txtPorcNoPK.setToolTipText("");
        resultados.add(txtPorcNoPK);

        evaluacionPK.add(resultados);

        resultadosCheck.add(evaluacionPK);

        evaluacionFK.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Evaluaci�n FK", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        evaluacionFK.setLayout(new javax.swing.BoxLayout(evaluacionFK, javax.swing.BoxLayout.Y_AXIS));

        panelFK.setLayout(new javax.swing.BoxLayout(panelFK, javax.swing.BoxLayout.Y_AXIS));

        jPanel16.setLayout(new javax.swing.BoxLayout(jPanel16, javax.swing.BoxLayout.LINE_AXIS));

        jLabel8.setText("Claves Foraneas");
        jPanel16.add(jLabel8);

        panelFK.add(jPanel16);

        jPanel17.setLayout(new java.awt.CardLayout());

        jScrollPane8.setPreferredSize(new java.awt.Dimension(452, 200));

        tbFK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "nombreEsquema", "nombreTabla"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(tbFK);

        jPanel17.add(jScrollPane8, "card2");

        panelFK.add(jPanel17);

        evaluacionFK.add(panelFK);

        panelNoFK.setLayout(new javax.swing.BoxLayout(panelNoFK, javax.swing.BoxLayout.Y_AXIS));

        jPanel20.setLayout(new javax.swing.BoxLayout(jPanel20, javax.swing.BoxLayout.LINE_AXIS));

        jLabel9.setText("Tablas sin referenciar en FK");
        jPanel20.add(jLabel9);

        panelNoFK.add(jPanel20);

        jPanel21.setLayout(new java.awt.CardLayout());

        jScrollPane9.setPreferredSize(new java.awt.Dimension(452, 200));

        tbNoFK.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "nombreEsquema", "nombreTabla"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane9.setViewportView(tbNoFK);

        jPanel21.add(jScrollPane9, "card2");

        panelNoFK.add(jPanel21);

        evaluacionFK.add(panelNoFK);

        resultadosCheck.add(evaluacionFK);

        evaluacionTriggers.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Evaluaci�n Triggers", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        evaluacionTriggers.setLayout(new javax.swing.BoxLayout(evaluacionTriggers, javax.swing.BoxLayout.Y_AXIS));

        panelTrigger.setLayout(new javax.swing.BoxLayout(panelTrigger, javax.swing.BoxLayout.Y_AXIS));

        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.LINE_AXIS));

        jLabel10.setText("Triggers");
        jPanel22.add(jLabel10);

        panelTrigger.add(jPanel22);

        jPanel23.setLayout(new java.awt.CardLayout());

        jScrollPane10.setPreferredSize(new java.awt.Dimension(452, 200));

        tbTriggers.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "nombreEsquema", "nombreTabla"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane10.setViewportView(tbTriggers);

        jPanel23.add(jScrollPane10, "card2");

        panelTrigger.add(jPanel23);

        evaluacionTriggers.add(panelTrigger);

        resultadosCheck.add(evaluacionTriggers);

        evaluacionRestricciones.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Evaluaci�n Restricciones", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 14))); // NOI18N
        evaluacionRestricciones.setLayout(new javax.swing.BoxLayout(evaluacionRestricciones, javax.swing.BoxLayout.Y_AXIS));

        panelRestricciones.setLayout(new javax.swing.BoxLayout(panelRestricciones, javax.swing.BoxLayout.Y_AXIS));

        jPanel24.setLayout(new javax.swing.BoxLayout(jPanel24, javax.swing.BoxLayout.LINE_AXIS));

        jLabel11.setText("Restricciones");
        jPanel24.add(jLabel11);

        panelRestricciones.add(jPanel24);

        jPanel25.setLayout(new java.awt.CardLayout());

        jScrollPane11.setPreferredSize(new java.awt.Dimension(452, 200));

        tbRestricciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "nombreEsquema", "nombreTabla"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane11.setViewportView(tbRestricciones);

        jPanel25.add(jScrollPane11, "card2");

        panelRestricciones.add(jPanel25);

        evaluacionRestricciones.add(panelRestricciones);

        resultadosCheck.add(evaluacionRestricciones);

        jScrollPane2.setViewportView(resultadosCheck);

        getContentPane().add(jScrollPane2);

        panelBotones.setLayout(new javax.swing.BoxLayout(panelBotones, javax.swing.BoxLayout.LINE_AXIS));

        btnGenerarReporte5.setText("Generar Reporte");
        btnGenerarReporte5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarReporte5ActionPerformed(evt);
            }
        });
        panelBotones.add(btnGenerarReporte5);

        btnSalir5.setText("Salir");
        btnSalir5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalir5ActionPerformed(evt);
            }
        });
        panelBotones.add(btnSalir5);

        getContentPane().add(panelBotones);

        pack();
    }// </editor-fold>                        

    private void btnSalir5ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    	System.exit(0);
    }                                         

    private void btnGenerarReporte5ActionPerformed(java.awt.event.ActionEvent evt) {                                                   
        try {
            GenerarReporte gr = new GenerarReporte(conexion.obtener());
            if(gr.generarReporteTxt()){
                JOptionPane.showMessageDialog(null,"Informe generado con �xito");

            }else {
                JOptionPane.showMessageDialog(null,"Informe no ha sido generado");

            }
        } catch (ClassNotFoundException ex) {
            System.err.println("Error class no found");
        }
    }                                                  

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify                     
    private javax.swing.JButton btnGenerarReporte5;
    private javax.swing.JButton btnSalir5;
    private javax.swing.JPanel evaluacionFK;
    private javax.swing.JPanel evaluacionPK;
    private javax.swing.JPanel evaluacionRestricciones;
    private javax.swing.JPanel evaluacionTriggers;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JPanel panelFK;
    private javax.swing.JPanel panelNoFK;
    private javax.swing.JPanel panelNoPK;
    private javax.swing.JPanel panelPK;
    private javax.swing.JPanel panelRestricciones;
    private javax.swing.JPanel panelTablas;
    private javax.swing.JPanel panelTrigger;
    private javax.swing.JPanel resultados;
    private javax.swing.JPanel resultadosCheck;
    private javax.swing.JTable tbFK;
    private javax.swing.JTable tbNoFK;
    private javax.swing.JTable tbNoPK;
    private javax.swing.JTable tbPK;
    private javax.swing.JTable tbRestricciones;
    private javax.swing.JTable tbTablas;
    private javax.swing.JTable tbTriggers;
    private javax.swing.JTextField txtNumTablas;
    private javax.swing.JTextField txtNumTablasSinPK;
    private javax.swing.JTextField txtPorcNoPK;
    // End of variables declaration   
}
