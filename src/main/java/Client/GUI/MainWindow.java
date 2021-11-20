package Client.GUI;

import Utils.ConnectionUtils.Request;
import Utils.UserUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.PipedWriter;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainWindow extends JFrame {
    private JComboBox<Locale> localComboBox;
    private Request request;
    private CityTableModel cityTableModel;
    private ResourceBundle resourceBundle;
    private AddWindow addWindow;
    private UserInfoPanel userInfoPanel;
    private ButtonPanel buttonPanel;
    private JTextArea textArea;
    private JPanel cardLayout;
    private TablePanel tablePanel;
    private VisualObjectPanel visualPanel;
    private PipedWriter commandWriter;

    public MainWindow(Locale[] locales, PipedWriter commandWriter, Request request, ResourceBundle resourceBundle, UserUtils userUtils) {
        super("City_Builder_Visualizer");
        this.request = request;
        this.resourceBundle = resourceBundle;
        this.commandWriter = commandWriter;
        int localIndex = 0;
        for (int i = 0; i < locales.length; i++) {
            if (Locale.getDefault().equals(locales[i])) localIndex = i;
        }
        initComponents(locales, userUtils);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setCurrentLocale(locales[localIndex]);
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screen = kit.getScreenSize();
        setBounds(screen.width / 5, screen.height / 5, screen.width * 4 / 5, screen.height * 4 / 5);
        setLayout(new BorderLayout());
        JScrollPane scroll = new JScrollPane(textArea);
        add(scroll, BorderLayout.SOUTH);
        add(userInfoPanel, BorderLayout.NORTH);
        add(cardLayout, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.WEST);
        addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                visualPanel.updateVisual(request.getNewTable());
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    public void addAnswer(String message) {
        textArea.append(message + "\n");
    }

    public void setUserInfo(String user) {
        userInfoPanel.setUserLabel(user);
    }

    public VisualObjectPanel getVisualPanel() {
        return visualPanel;
    }

    public CityTableModel getCityTableModel() {
        return cityTableModel;
    }

    private void initComponents(Locale[] locales, UserUtils userUtils) {
        cityTableModel = new CityTableModel(resourceBundle, request);
        addWindow = new AddWindow(commandWriter, this, resourceBundle);
        tablePanel = new TablePanel(cityTableModel, commandWriter, resourceBundle, request, userUtils);
        visualPanel = new VisualObjectPanel(request, commandWriter, resourceBundle, userUtils);
        buttonPanel = new ButtonPanel(this, commandWriter, resourceBundle);
        localComboBox = new JComboBox<>(locales);
        userInfoPanel = new UserInfoPanel(commandWriter, resourceBundle, this, localComboBox, userUtils);
        textArea = new JTextArea(5, 50);
        cardLayout = new JPanel(new CardLayout());
        cardLayout.add(tablePanel, "Таблица");
        cardLayout.add(visualPanel, "Визуализация");
        cardLayout.getLayout();
        localComboBox.addActionListener(e -> {
            setCurrentLocale((Locale) localComboBox.getSelectedItem());
            validate();
        });
    }
    
    private void setCurrentLocale(Locale locale) {
        resourceBundle = ResourceBundle.getBundle("Client.Resources.ProgramResources", locale);
        tablePanel.setResourceBundle(resourceBundle);
        cityTableModel.changeColumns(resourceBundle);
        tablePanel.updateColumns();
        tablePanel.updateTime();
        tablePanel.updateSort();
        userInfoPanel.updateText(resourceBundle);
        localComboBox.setSelectedItem(locale);
        buttonPanel.updateText(resourceBundle);
        visualPanel.setResourceBundle(resourceBundle);
        addWindow.updateText(resourceBundle);
    }

    public JPanel getCardLayout() {
        return cardLayout;
    }
}
