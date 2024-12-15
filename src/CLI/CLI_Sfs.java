package CLI;

import sfs.SimpleFileServerClient;

import java.io.*;
import java.net.Socket;

public class CLI_Sfs {
    private static final String CONNECT = "connect";
    private static final String PUT = "put";
    private static final String EXIT = "exit";
    private final PrintStream outStream;
    private final BufferedReader inBufferedReader;
    private final String playerName;
    private Socket socket;
    private SimpleFileServerClient sfsClient;
    private InputStream is;
    private OutputStream os;

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to SFS version 0.1");

        CLI_Sfs userCmd = new CLI_Sfs("TestUser", System.out, System.in);

        userCmd.printUsage();
        userCmd.runCommandLoop();
    }

    public CLI_Sfs(String playerName, PrintStream os, InputStream is) throws IOException {
        this.playerName = playerName;
        this.outStream = os;
        this.inBufferedReader = new BufferedReader(new InputStreamReader(is));
    }

    public void printUsage() {
        StringBuilder b = new StringBuilder();

        b.append("\n");
        b.append("\n");
        b.append("valid commands:");
        b.append("\n");

        b.append(CONNECT);
        b.append(".. connect as tcp client to localhost. must provide port number");
        b.append("\n");

        b.append(PUT);
        b.append(".. once connected send file to server. must provide file name");
        b.append("\n");

        b.append(EXIT);
        b.append(".. exit");

        this.outStream.println(b.toString());
    }

    public void runCommandLoop() {
        boolean again = true;

        Socket socket = null;

        while (again) {
            boolean rememberCommand = true;
            String cmdLineString = null;

            try {
                // read user input
                cmdLineString = inBufferedReader.readLine();

                // finish that loop if less than nothing came in
                if (cmdLineString == null) break;

                // trim whitespaces on both sides
                cmdLineString = cmdLineString.trim();

                // extract command
                int spaceIndex = cmdLineString.indexOf(' ');
                spaceIndex = spaceIndex != -1 ? spaceIndex : cmdLineString.length();

                // got command string
                String commandString = cmdLineString.substring(0, spaceIndex);

                // extract parameters string - can be empty
                String parameterString = cmdLineString.substring(spaceIndex);
                parameterString = parameterString.trim();

                // start command loop
                switch (commandString) {

                    case CONNECT:
                        this.connect(parameterString);
                        break;
                    case PUT:
                        this.put(parameterString);
                        break;
                    case "q": // convenience
                    case EXIT:
                        again = false;
                        System.exit(1);
                        break; // end loop

                    default:
                        this.outStream.println("unknown command:" + cmdLineString);
                        this.printUsage();
                        rememberCommand = false;
                        break;
                }
            } catch (IOException ex) {
                this.outStream.println("cannot read from input stream - fatal, give up");
                again = false;
            }
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                           ui method implementations                                        //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private void doSkeleton(String cmd) {
        this.doSkeleton(cmd, null);
    }

    private void doSkeleton(String cmd, String parameterString) {
        System.out.print("command: " + cmd);
        if (parameterString == null) {
            System.out.print("\n");
        } else {
            System.out.println("(" + parameterString + ")");
        }
    }

    private void connect(String port) throws IOException {
        int portAsInt = Integer.parseInt(port);
        this.socket = new Socket("localhost", portAsInt);
        this.is = socket.getInputStream();
        this.os = socket.getOutputStream();
        this.sfsClient = new SimpleFileServerClient(this.is, this.os,"tests/test_files_client/");
    }

    private void put(String parameterString) throws IOException {
        this.sfsClient.putFile(parameterString);
    }
}