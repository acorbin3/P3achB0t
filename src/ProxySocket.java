///*
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.InetAddress;
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.net.SocketAddress;
//import java.nio.channels.SocketChannel;
//
//
//class ProxySocket extends Socket {
//
//    private InetAddress proxyInetAddress;
//    private InetAddress defaultAddress;
//    private int defaultPort;
//
//    private InetSocketAddress proxyAddress;
//
//    public ProxySocket(InetAddress address, int port) throws IOException {
//        super(address, port);
//    }
//
//    @Override
//    public void connect(SocketAddress address) throws IOException {
//
//        if (address instanceof InetSocketAddress) {
//            InetSocketAddress isa = (InetSocketAddress) address;
//            defaultAddress = InetAddress.getByName(isa.getHostString());
//            defaultPort = isa.getPort();
//        }
//        if (!client.proxy.type.equals("none")) {
//            proxyInetAddress = InetAddress.getByName(client.proxy.ip);
//            try {
//                proxyAddress = new InetSocketAddress(client.proxy.ip, client.proxy.port);
//                super.connect(proxyAddress);
//                initProxy();
//            } catch (Exception e) {
//                // Error
//            }
//        } else {
//            super.connect(address);
//        }
//    }
//
//    private void initProxy() throws IOException {
//        System.out.println("Proxying:" + defaultAddress + ":" + defaultPort + " Over:" + proxyInetAddress + ":" + client.proxy.port + " Type:" + client.proxy.type);
//        switch (client.proxy.type) {
//            case "HTTP":
//                //http_connect();
//                break;
//            case "SOCKS4":
//                //socks4_connect();
//                break;
//            case "SOCKS5":
//                socks5_connect();
//                break;
//            default:
//                throw new IOException("Unsupported proxy type:");
//        }
//    }
//
//    private void socks5_connect() throws IOException {
//        DataOutputStream out = new DataOutputStream(getOutputStream());
//        DataInputStream in  = new DataInputStream(getInputStream());
//
//
//
//
//        // write version
//        out.write(0x05); // field 1
//        out.write(0x01); // field 2
//        out.write(0x0);
//
//        out.flush();
//
//        byte version = in.readByte();
//        System.out.println("Version: " + Byte.toString(version));
//
//        if (version != 5) {
//            return;
//        }
//
//        byte authMethod = in.readByte();
//        System.out.println("Authentication method: " + Byte.toString(authMethod));
//
//        if (authMethod != 0) {
//            return;
//        }
//
//        out.write(0x05);
//        out.write(0x01);
//        out.write(0x00);
//
//        // Address of the host we want to connect to ONLY IPV4 support
//        byte[] b = defaultAddress.getAddress();
//        out.write(0x01);
//        out.write(b);
//        out.writeShort(defaultPort);
//        out.flush();
//
//        byte version2 = in.readByte();
//        System.out.println("Version2: " + Byte.toString(version2));
//
//        if (version2 != 5) {
//            return;
//        }
//
//        byte status = in.readByte();
//        System.out.println("status: " + Byte.toString(status));
//        in.readByte();
//        byte ipversion = in.readByte();
//        System.out.println("ip version: " + Byte.toString(status));
//        byte ip1 = in.readByte();
//        byte ip2 = in.readByte();
//        byte ip3 = in.readByte();
//        byte ip4 = in.readByte();
//        short portt = in.readShort();
//        System.out.println(Byte.toString(ip1) + "."+Byte.toString(ip2) + "."+Byte.toString(ip3) + "."+Byte.toString(ip4) + ":" +Short.toString(portt));
//    }
//
//    @Override
//    public int getPort() {
//        if (super.getInetAddress().equals(proxyInetAddress)) {
//            return defaultPort;
//        }
//        return super.getPort();
//    }
//
//    @Override
//    public InetAddress getInetAddress() {
//        if (super.getInetAddress().equals(proxyInetAddress)) {
//            return defaultAddress;
//        }
//        return super.getInetAddress();
//    }
//
//    @Override
//    public SocketAddress getRemoteSocketAddress() {
//        if (super.getInetAddress().equals(proxyInetAddress)) {
//            return proxyAddress;
//        }
//        return super.getRemoteSocketAddress();
//    }
//
//    @Override
//    public SocketChannel getChannel() {
//        if (super.getInetAddress().equals(proxyInetAddress)) {
//            return null;
//        }
//        return super.getChannel();
//    }
//
//    @Override
//    public void close() throws IOException {
//        super.close();
//    }
//}
//*/
//
//
///*  field 2 options
//    0x00: No authentication
//    0x01: GSSAPI[15]
//    0x02: Username/password[16]
//    0x03–0x7F: methods assigned by IANA[17]
//    0x80–0xFE: methods reserved for private use
//*/
//
//
//
///*
//    private void socks5_connect() throws IOException {
//        DataOutputStream out = new DataOutputStream(getOutputStream());
//        DataInputStream in  = new DataInputStream(getInputStream());
//        out.write(0x05); // the version
//        boolean auth = !client.proxy.username.equals("");
//        out.write(auth ? 2 : 1); // number of authentication methods (no auth
//        // for now)
//        out.write(0); // the authentication (none)
//        if (auth) {
//            out.write(2);
//        }
//        out.flush();
//        if (in.read() != 0x05) // remote proxy version
//        {
//            throw new IOException("Proxy server is not supported!");
//        }
//        switch (in.read()) { // auth method
//            case 0:
//                break; // no auth
//            case 2:
//                // username and pass stuff
//                out.write(0x01); // user/pass version #
//                out.write(client.proxy.username.length());
//                out.write(client.proxy.username.getBytes());
//                out.write(client.proxy.password.length());
//                out.write(client.proxy.password.getBytes());
//                out.flush();
//                in.read(); // skip the version
//                if (in.read() == 0) // Successful login, continue
//                {
//                    break;
//                }
//            default:
//                throw new IOException("Proxy server declined request!");
//        }
//
//        // now to write the actual request
//        out.write(0x05); // again the socks version
//        out.write(0x01); // the connection type (0x01 = TCP Connection)
//        out.write(0x00); // the reserve byte, un-used
//        byte[] b = defaultAddress.getAddress();
//        out.write(b.length == 4 ? 0x01 : 0x04); // if ipv4 or ipv6 (0x03 =
//        // domain name, but that's
//        // unsupported as of yet)
//        out.write(b);
//        out.writeShort(defaultPort);
//        out.flush();
//
//        // now to read the server's reply
//        if (in.read() != 0x05) // socks version (again)
//        {
//            throw new IOException("Proxy server dun goofed");
//        }
//        int reply = in.read();
//        if (reply == 0x08) {
//            throw new IOException("Bad address sent to proxy server");
//        }
//        if (reply != 0x00) {
//            throw new IOException("Unable to connect to server!");
//        }
//        in.read(); // reserve byte
//        int addrType = in.read();
//        b = new byte[4];
//        switch (addrType) {
//            case 0x01:
//                b = new byte[4];
//                break;
//            case 0x04:
//                b = new byte[16];
//                break;
//            default:
//                throw new IOException("Bad address type from proxy server!");
//        }
//        in.readFully(b);
//        in.readShort(); // the returned port #, ignored
//    }
//*/
