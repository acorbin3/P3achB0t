
public class ProxyConnection {

    public String ip;
    public int port;
    public String username;
    public String password;
    public String type;

    public ProxyConnection(String input) {
        if (!input.equals("none")) {

            String[] params = input.split(";");
            if (params.length == 2) {
                type = params[0];
                String[] connection = params[1].split(":");
                ip = connection[0];
                port = Integer.parseInt(connection[1]);
            } else {
                type = params[0];
                String[] connection = params[1].split(":");
                ip = connection[0];
                port = Integer.getInteger(connection[1]);

                String[] credentials = params[2].split(":");
                username = credentials[0];
                password = credentials[1];
            }
        } else  {
            type = "none";
        }
    }
}
