/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package empiricalanalysis;


import java.io.FileNotFoundException;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
<<<<<<< HEAD
=======

>>>>>>> fd3ae4ecdbd90ca47300a570f6e8c5836511d50f

import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author shivani bhalchandra
 */
public class OutputChart extends javax.swing.JFrame {

    /**
     * Creates new form OutputChart
     */
    public OutputChart() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        getChart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        getChart.setText("GetChart");
        getChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getChartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addComponent(getChart)
                .addContainerGap(559, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(getChart)
                .addContainerGap(240, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void getChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getChartActionPerformed
        // TODO add your handling code here:

        EmpiricalAnalysis emp = new EmpiricalAnalysis();
        try {

            emp.start();

            Map<Integer, Long> tmap = emp.getTradmap();
            //System.out.println("tmap" + tmap.size());

            Map<Integer, Long> pureStrasmap = emp.getPureStrasmap();
            //System.out.println("pireStmap" + pureStrasmap.size());

            List<Map> outlist = emp.getMaplist();
            XYSeriesCollection dataset2 = createDataset(outlist);

            XYSeriesCollection dataset1 = new XYSeriesCollection();
            XYSeries series1 = new XYSeries("Traditional ");
            XYSeries series2 = new XYSeries("Pure Strassen");

            for (Map.Entry<Integer, Long> map : tmap.entrySet()) {
                series1.add(map.getKey(), map.getValue());
            }

            for (Map.Entry<Integer, Long> map : pureStrasmap.entrySet()) {
                series2.add(map.getKey(), map.getValue());

            }

            dataset1.addSeries(series1);
            dataset1.addSeries(series2);

            XYLineChartExample xs = new XYLineChartExample();

            xs.XYLineChartExample(dataset2);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(OutputChart.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_getChartActionPerformed

    private XYSeriesCollection createDataset(List<Map> maplist) {

        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries[] serieslist = new XYSeries[10];
        int breappoint = 16;
        for (int i = 0; i < maplist.size(); i++) {
            if (i == 0) {
                serieslist[i] = new XYSeries("Trad");

            } else {
                serieslist[i] = new XYSeries("BP " + breappoint);
                breappoint *= 2;
            }
            Map<Integer, Long> outmap = maplist.get(i);
            for (Map.Entry<Integer, Long> map : outmap.entrySet()) {
                serieslist[i].add(map.getKey(), map.getValue());
            }
            dataset.addSeries(serieslist[i]);

        }

        return dataset;

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(OutputChart.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(OutputChart.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(OutputChart.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(OutputChart.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new OutputChart().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton getChart;
    // End of variables declaration//GEN-END:variables
}
