/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yccheok.jstock.gui.portfolio;

import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.NumberFormatter;
import net.sf.nachocalendar.CalendarFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.yccheok.jstock.portfolio.DecimalPlaces;
import org.yccheok.jstock.portfolio.Dividend;

/**
 *
 * @author yccheok
 */
public class AutoDividendRowJPanel extends javax.swing.JPanel {

    /**
     * Creates new form AutoDividendRowJPanel
     */
    public AutoDividendRowJPanel(AutoDividendJPanel autoDividendJPanel, Dividend dividend) {
        initComponents();
        
        this.dividend = dividend;
        this.autoDividendJPanel = autoDividendJPanel;
        final net.sf.nachocalendar.components.DateField dateField = (net.sf.nachocalendar.components.DateField)jPanel1;
        dateField.setValue(dividend.date.getTime());
        jFormattedTextField2.setValue(dividend.amount);
    }

    private MouseListener getJFormattedTextFieldMouseListener() {
        MouseListener ml = new MouseAdapter()
        {
            @Override
            public void mousePressed(final MouseEvent e)
            {
                if (e.getClickCount() == 2) {
                    // Ignore double click.
                    return;
                }
                
                SwingUtilities.invokeLater(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        JTextField tf = (JTextField)e.getSource();
                        int offset = tf.viewToModel(e.getPoint());
                        tf.setCaretPosition(offset);
                    }
                });
            }
        };
        return ml;
    }
    
    private JFormattedTextField getCurrencyJFormattedTextField() {
        NumberFormat format= NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(3);
        NumberFormatter formatter= new NumberFormatter(format);
        formatter.setMinimum(0.0);
        formatter.setValueClass(Double.class);
        JFormattedTextField formattedTextField = new JFormattedTextField(formatter);
        formattedTextField.addMouseListener(getJFormattedTextFieldMouseListener());
        return formattedTextField;
    }
    
    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        jCheckBox1.setEnabled(enabled);
        jFormattedTextField2.setEnabled(enabled && jCheckBox1.isSelected());
        jPanel1.setEnabled(enabled && jCheckBox1.isSelected());
    }
    
    public boolean isSelected() {
        return jCheckBox1.isSelected();
    }
    
    public double getAmount() {
        Object value = jFormattedTextField2.getValue();
        if (value instanceof Double) {
            return (Double)value;
        }
        return 0.0;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0));
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel1 = CalendarFactory.createDateField();
        jFormattedTextField2 = getCurrencyJFormattedTextField();

        setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        add(filler1);

        jCheckBox1.setSelected(true);
        jCheckBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jCheckBox1ItemStateChanged(evt);
            }
        });
        add(jCheckBox1);

        jPanel1.setMinimumSize(new java.awt.Dimension(80, 20));
        jPanel1.setPreferredSize(new java.awt.Dimension(80, 20));
        add(jPanel1);

        jFormattedTextField2.setMinimumSize(new java.awt.Dimension(80, 20));
        jFormattedTextField2.setPreferredSize(new java.awt.Dimension(80, 20));
        jFormattedTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFormattedTextField2KeyTyped(evt);
            }
        });
        add(jFormattedTextField2);
    }// </editor-fold>//GEN-END:initComponents

    private void jCheckBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jCheckBox1ItemStateChanged
        final boolean enabled = evt.getStateChange() == ItemEvent.SELECTED;
        jFormattedTextField2.setEnabled(enabled);
        jPanel1.setEnabled(enabled);
        autoDividendJPanel.updateJCheckBoxColor();
        autoDividendJPanel.updateTotalLabel();
    }//GEN-LAST:event_jCheckBox1ItemStateChanged

    public void updateTaxInfo(double tax, double taxRate) {
        double value = this.dividend.amount - tax - (this.dividend.amount * taxRate / 100.0);
        value = Math.max(value, 0.0);
        final String text = org.yccheok.jstock.portfolio.Utils.toEditCurrency(DecimalPlaces.Three, value);
        value = Double.parseDouble(text);        
        jFormattedTextField2.setValue(value);
    }
    
    private void jFormattedTextField2KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField2KeyTyped
        SwingUtilities.invokeLater(new Runnable() {@Override public void run() {
            try {
                jFormattedTextField2.commitEdit();
            } catch (ParseException ex) {
                log.error(null, ex);
            }            
            autoDividendJPanel.updateTotalLabel();
        }});
    }//GEN-LAST:event_jFormattedTextField2KeyTyped

    private static final Log log = LogFactory.getLog(AutoDividendRowJPanel.class);
    
    private final Dividend dividend;
    private final AutoDividendJPanel autoDividendJPanel;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JFormattedTextField jFormattedTextField2;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}