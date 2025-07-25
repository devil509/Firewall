
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class EthicalFirewall {
    static final String TOR_PROXY_HOST = "127.0.0.1";
    static final int TOR_PROXY_PORT = 9050;
    static final List<Integer> BLOCKED_PORTS = Arrays.asList(22, 445, 3389);
    static final List<String> BLOCKED_IPS = Arrays.asList("192.168.1.100");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> buildGUI());
    }

    private static void buildGUI() {
        JFrame frame = new JFrame("Ethical Firewall Client");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel ipLabel = new JLabel("Target IP:");
        JTextField ipField = new JTextField(15);

        JLabel portLabel = new JLabel("Target Port:");
        JTextField portField = new JTextField(5);

        JButton checkBtn = new JButton("Run Firewall Check");
        JTextArea outputArea = new JTextArea(10, 40);
        outputArea.setEditable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0; panel.add(ipLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 0; panel.add(ipField, gbc);
        gbc.gridx = 0; gbc.gridy = 1; panel.add(portLabel, gbc);
        gbc.gridx = 1; gbc.gridy = 1; panel.add(portField, gbc);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; panel.add(checkBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; panel.add(new JScrollPane(outputArea), gbc);

        checkBtn.addActionListener(e -> {
            String ip = ipField.getText().trim();
            int port;
            try {
                port = Integer.parseInt(portField.getText().trim());
            } catch (NumberFormatException ex) {
                outputArea.append("Invalid port.\n");
                return;
            }

            if (BLOCKED_IPS.contains(ip) || BLOCKED_PORTS.contains(port)) {
                outputArea.append("❌ Connection blocked by firewall rules!\n");
                logEvent("Blocked attempt to connect to " + ip + ":" + port);
                return;
            }

            System.setProperty("socksProxyHost", TOR_PROXY_HOST);
            System.setProperty("socksProxyPort", String.valueOf(TOR_PROXY_PORT));

            try {
                URL url = new URL("https://check.torproject.org/");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(7000);
                conn.setReadTimeout(7000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                boolean isAnon = false;
                while ((line = reader.readLine()) != null) {
                    outputArea.append(line + "\n");
                    if (line.contains("Congratulations. This browser is configured to use Tor")) {
                        isAnon = true;
                    }
                }
                reader.close();

                if (isAnon) {
                    outputArea.append("✅ Anonymity confirmed via Tor!\n");
                    logEvent("Tor anonymity validated.");
                } else {
                    outputArea.append("⚠️ Not anonymous — check Tor settings.\n");
                    logEvent("Tor anonymity failed.");
                }
            } catch (IOException ex) {
                outputArea.append("❌ Error: " + ex.getMessage() + "\n");
                logEvent("Tor HTTP request error: " + ex.getMessage());
            }
        });

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    private static void logEvent(String message) {
        try (FileWriter fw = new FileWriter("firewall_log.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println("[" + new Date() + "] " + message);
        } catch (IOException e) {
            System.err.println("Log error: " + e.getMessage());
        }
    }

    public static List getBLOCKED_IPS() {
        return BLOCKED_IPS;
    }
}
