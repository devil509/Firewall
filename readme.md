# Ethical Firewall Client (Java-Based)



A Java Swing-based ethical hacking firewall tool that:

- Routes all HTTP requests through the Tor SOCKS5 proxy
- Simulates port and IP-based outbound traffic blocking
- Checks for anonymity using the Tor Project's service
- Logs all firewall events to a `firewall_log.txt` file

---



## 🔧 Features

- ✅ GUI for entering target IP and port
- 🔐 SOCKS5 proxy routing via Tor (default: 127.0.0.1:9050)
- 🚫 IP/port blacklist filtering
- 🌐 Anonymity verification using [check.torproject.org](https://check.torproject.org/)
- 📄 Activity log saved to `firewall_log.txt`

## 🚀 Requirements

- Java 8 or higher
- Tor service running locally on port 9050 (`sudo service tor start`)

## 🧪 How to Run

```bash
# 1. Compile the program
javac EthicalFirewallClientGUI.java

# 2. Run the GUI app
java EthicalFirewallClientGUI
```

## 📁 File Structure

```
ethical-firewall-client/
│
├── EthicalFirewallClientGUI.java      # Main GUI source code
├── firewall_log.txt                   # Generated firewall log file
├── README.md                          # Project readme
└── screenshots/                       # Folder containing GUI screenshots
    ├── firewall_gui.png
    └── tor_check.png
```

## 📓 Log File: `firewall_log.txt`

Each event is logged with a timestamp. Example:

```
[Thu Jul 25 18:00:22 IST 2025] Blocked attempt to connect to 192.168.1.100:22
[Thu Jul 25 18:01:10 IST 2025] Tor anonymity validated.
```

## ⚠️ Legal Notice

This tool is intended for educational purposes **only**. Do not use it to scan or connect to unauthorized systems. Always get permission before conducting any ethical hacking.

## 📌 Future Enhancements

- GeoIP tagging of target IPs
- Real-time network traffic graphs
- Jar bundler + shell installer
- Integration with system-level firewalls (`iptables`, `ufw`)

## 👨‍💻 Author

**Ramanand Patharwalkar**

## 📦 GitHub Setup

To publish this on GitHub:

```bash
git init
git add .
git commit -m "Initial commit of Ethical Firewall Client"
git remote add origin https://github.com/your-username/ethical-firewall-client.git
git push -u origin main
```

---

Feel free to fork, enhance, and share!

