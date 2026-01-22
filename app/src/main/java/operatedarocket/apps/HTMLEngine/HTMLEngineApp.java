package operatedarocket.apps.HTMLEngine;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import net.miginfocom.swing.MigLayout;
import operatedarocket.Utilities;
import operatedarocket.ui.AppFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
public class HTMLEngineApp extends AppFrame {

    public final JTabbedPane tabbedPane = new JTabbedPane();
    private final JTextField portField = new JTextField("");
    public final JPanel topBar;

    public HTMLEngineApp(String title, String imgPath) {
        super(title, imgPath);
        setPreferredSize(new Dimension(1000, 700));

        // Ensure JavaFX platform is initialized
        initJavaFX();

        // Top bar with controls
        topBar = new JPanel(new MigLayout("insets 8, fill", "[][grow][][]", "[]"));
        topBar.add(new JLabel("Port:"), "gapright 4");
        topBar.add(portField, "growx, pushx");
        JButton addBtn = new JButton("Add Tab");
        topBar.add(addBtn, "gapleft 8");

        addBtn.addActionListener(this::onAddTab);

        // Layout
        content.setLayout(new MigLayout("fill, insets 0", "[grow]", "[][grow]"));
        addContent(topBar, "growx, wrap");
        addContent(tabbedPane, "grow");
        SwingUtilities.invokeLater(() -> createAndAddTab(0));

        Platform.runLater(() -> SwingUtilities.invokeLater(() -> {
            setVisible(true);
        }));

    }

    private void onAddTab(ActionEvent e) {
        String text = portField.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Enter a html file loc in resource.", "Input required", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (Character.isDigit(text.charAt(0))) {
            try {
                int port = Integer.parseInt(text);
                if (port <= 0 || port > 65535) throw new NumberFormatException();
                createAndAddTab(port);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid port number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            createAndAddTab(text);
        }

    }

    public void createAndAddTab(int port) {
        String title = "localhost:" + port;
        JFXPanel jfxPanel = new JFXPanel(); // Swing component that hosts JavaFX content

        // Add tab with placeholder content while JavaFX loads
        tabbedPane.addTab(title, jfxPanel);
        int index = tabbedPane.indexOfComponent(jfxPanel);
        tabbedPane.setTabComponentAt(index, createTabHeader(title, jfxPanel));

        // Load WebView on JavaFX thread
        Platform.runLater(() -> {
            try {
                WebView webView = new WebView();
                WebEngine engine = webView.getEngine();
                engine.load("http://localhost:" + port + "/");
                Scene scene = new Scene(webView);
                jfxPanel.setScene(scene);
            } catch (Exception ex) {
                // If JavaFX fails, show a simple error label on the Swing thread
                SwingUtilities.invokeLater(() -> {
                    jfxPanel.removeAll();
                    jfxPanel.add(new JLabel("Failed to initialize WebView: " + ex.getMessage()));
                    jfxPanel.revalidate();
                    jfxPanel.repaint();
                });
            }
        });
    }

    public void createAndAddTab(String path) {
        String title = "New Tab";
        JFXPanel jfxPanel = Utilities.getPort(
                path,
                false
        ); // Swing component that hosts JavaFX content

        // Add tab with placeholder content while JavaFX loads
        tabbedPane.addTab(title, jfxPanel);
        int index = tabbedPane.indexOfComponent(jfxPanel);
        tabbedPane.setTabComponentAt(index, createTabHeader(title, jfxPanel));
    }

    public void addTab(String title, JFXPanel panel) {
        tabbedPane.addTab(title, panel);
        int index = tabbedPane.indexOfComponent(panel);
        tabbedPane.setTabComponentAt(index, createTabHeader(title, panel));
    }

    private JPanel createTabHeader(String title, JComponent content) {
        JPanel tabHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 0));
        tabHeader.setOpaque(false);

        JLabel lbl = new JLabel(title);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 4));
        JButton closeBtn = new JButton("x");
        closeBtn.setMargin(new Insets(1, 6, 1, 6));
        closeBtn.setFocusable(false);

        closeBtn.addActionListener(e -> {
            int idx = tabbedPane.indexOfComponent(content);
            if (idx >= 0) {
                // Dispose JavaFX content if present
                Component comp = tabbedPane.getComponentAt(idx);
                if (comp instanceof JFXPanel) {
                    // Clear scene on JavaFX thread to release resources
                    Platform.runLater(() -> ((JFXPanel) comp).setScene(null));
                }
                tabbedPane.remove(idx);
            }
        });

        tabHeader.add(lbl);
        tabHeader.add(closeBtn);
        return tabHeader;
    }

    private void initJavaFX() {
        // Initialize JavaFX runtime. If already initialized, this call is safe.
        try {
            Platform.startup(() -> {
                // No-op: this initializes JavaFX toolkit
            });
        } catch (IllegalStateException ex) {
            // Platform already started; ignore
        }
    }
}
