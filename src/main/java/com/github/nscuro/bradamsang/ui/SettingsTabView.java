package com.github.nscuro.bradamsang.ui;

import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import lombok.AccessLevel;
import lombok.Getter;

import javax.annotation.Nullable;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.File;
import java.nio.file.Path;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

@Getter(AccessLevel.PACKAGE)
public class SettingsTabView implements Observer {

    private JPanel mainPanel;

    private JTextField radamsaCommandTextField;

    private JButton radamsaCommandButton;

    private JTextField radamsaOutputDirTextField;

    private JButton radamsaOutputDirButton;

    private JSpinner payloadCountSpinner;

    private JTextField intruderInputDirTextField;

    private JButton intruderInputDirButton;

    private JCheckBox enableWslModeCheckBox;

    private JTextField customSeedTextField;

    private JCheckBox customSeedCheckBox;

    private JComboBox wslDistroComboBox;

    @Override
    public void update(final Observable observable, @Nullable final Object argument) {
        if (!(observable instanceof SettingsTabModel)) {
            return;
        }

        final SettingsTabModel model = (SettingsTabModel) observable;

        // Update components related to Radamsa command
        if (!model.isWslAvailableAndEnabled()) {
            // When in WSL mode, the user manually enters a command. There is no
            // need to refresh the text fields content. When not in WSL mode however,
            // an executable is selected via file dialog, so we need to reflect this in the UI.
            model.getRadamsaCommand().ifPresent(radamsaCommandTextField::setText);
        }
        radamsaCommandTextField.setEditable(model.isWslAvailableAndEnabled());
        radamsaCommandButton.setEnabled(!model.isWslAvailableAndEnabled());

        // Update components related to Radamsa output directory
        Optional
                .ofNullable(model.getRadamsaOutputDir())
                .map(Path::toString)
                .ifPresent(radamsaOutputDirTextField::setText);
        radamsaOutputDirTextField.setEnabled(!model.isWslAvailableAndEnabled());
        radamsaOutputDirButton.setEnabled(!model.isWslAvailableAndEnabled());

        // Update components related to intruder input directory
        Optional
                .ofNullable(model.getIntruderInputDir())
                .map(Path::toString)
                .ifPresent(intruderInputDirTextField::setText);
        intruderInputDirTextField.setEnabled(model.isWslAvailableAndEnabled());
        intruderInputDirButton.setEnabled(model.isWslAvailableAndEnabled());

        // Update components related to custom seed
        customSeedTextField.setEnabled(model.isUseCustomSeed());

        // Update WSL related components
        getEnableWslModeCheckBox().setEnabled(model.isWslAvailable());
        wslDistroComboBox.setEnabled(model.isWslAvailableAndEnabled());
        if (wslDistroComboBox.getItemCount() == 0
                && model.getAvailableWslDistros() != null
                && !model.getAvailableWslDistros().isEmpty()) {
            // TODO: Make a smarter decision here
            model.getAvailableWslDistros().forEach(wslDistroComboBox::addItem);
        }
    }

    Optional<String> getPathFromFileChooser(final int fileSelectionMode) {
        final JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(fileSelectionMode);
        fc.setFileHidingEnabled(false);

        if (fc.showOpenDialog(mainPanel) == JFileChooser.APPROVE_OPTION) {
            return Optional
                    .of(fc.getSelectedFile())
                    .map(File::toString);
        }

        return Optional.empty();
    }

    void showWarningDialog(final String message) {
        JOptionPane.showMessageDialog(mainPanel, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    void showErrorDialog(final String message) {
        JOptionPane.showMessageDialog(mainPanel, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(5, 1, new Insets(10, 10, 10, 10), -1, -1));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 3, new Insets(0, 5, 0, 5), -1, -1));
        mainPanel.add(panel1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel1.setBorder(BorderFactory.createTitledBorder("General"));
        final JLabel label1 = new JLabel();
        label1.setText("Radamsa Command:");
        panel1.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radamsaCommandTextField = new JTextField();
        panel1.add(radamsaCommandTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        radamsaCommandButton = new JButton();
        radamsaCommandButton.setText("..");
        panel1.add(radamsaCommandButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("Radamsa Output Dir:");
        panel1.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        radamsaOutputDirTextField = new JTextField();
        radamsaOutputDirTextField.setEditable(false);
        panel1.add(radamsaOutputDirTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        radamsaOutputDirButton = new JButton();
        radamsaOutputDirButton.setText("...");
        panel1.add(radamsaOutputDirButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        mainPanel.add(spacer1, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(2, 3, new Insets(0, 5, 0, 5), -1, -1));
        mainPanel.add(panel2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel2.setBorder(BorderFactory.createTitledBorder("Payload Generator"));
        final JLabel label3 = new JLabel();
        label3.setText("Payload Count:");
        panel2.add(label3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        payloadCountSpinner = new JSpinner();
        panel2.add(payloadCountSpinner, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label4 = new JLabel();
        label4.setText("Intruder Input Dir:");
        panel2.add(label4, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        intruderInputDirTextField = new JTextField();
        intruderInputDirTextField.setEditable(false);
        intruderInputDirTextField.setEnabled(true);
        panel2.add(intruderInputDirTextField, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        intruderInputDirButton = new JButton();
        intruderInputDirButton.setEnabled(false);
        intruderInputDirButton.setText("...");
        panel2.add(intruderInputDirButton, new GridConstraints(1, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(1, 3, new Insets(0, 5, 0, 5), -1, -1));
        mainPanel.add(panel3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel3.setBorder(BorderFactory.createTitledBorder("Tweaking"));
        final JLabel label5 = new JLabel();
        label5.setText("Seed:");
        panel3.add(label5, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        customSeedTextField = new JTextField();
        customSeedTextField.setEnabled(true);
        panel3.add(customSeedTextField, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        customSeedCheckBox = new JCheckBox();
        customSeedCheckBox.setText("Custom");
        panel3.add(customSeedCheckBox, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel4 = new JPanel();
        panel4.setLayout(new GridLayoutManager(2, 2, new Insets(0, 0, 0, 0), -1, -1));
        mainPanel.add(panel4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        panel4.setBorder(BorderFactory.createTitledBorder("WSL"));
        final JLabel label6 = new JLabel();
        label6.setText("Distribution:");
        panel4.add(label6, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        wslDistroComboBox = new JComboBox();
        wslDistroComboBox.setEnabled(true);
        panel4.add(wslDistroComboBox, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        enableWslModeCheckBox = new JCheckBox();
        enableWslModeCheckBox.setEnabled(true);
        enableWslModeCheckBox.setText("Enable WSL mode");
        panel4.add(enableWslModeCheckBox, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }

}