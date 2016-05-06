import java.io.*;
import java.util.*;
import java.lang.*;

public class Generator
{

    protected String realPath;

    protected String stubPath;

    protected String stubName;

    protected String targetPath;

    protected String targetName;

    protected String replaceContent;

    private String messages;

    //List available colors
    private static final String C_OFF = "\033[0m";
    private static final String BLACK = "\033[0;30m";
    private static final String RED = "\033[0;31m";
    private static final String GREEN = "\033[0;32m";
    private static final String YELLOW = "\033[0;33m";
    private static final String BLUE = "\033[0;34m";
    private static final String PURPLE = "\033[0;35m";
    private static final String CYAN = "\033[0;36m";
    private static final String WHITE = "\033[0;37m";

    public Generator() {
        realPath = "";
        stubPath = realPath + "pack/okplay/generator/stubs/";
        stubName = "";
        targetPath = realPath + "app/";
        targetName = "";
        replaceContent = "";
        messages = "";
    }

    protected void showHelp() {
        System.out.printf("%s%s%s %s%s %s\n\n", GREEN, "Play Framework", C_OFF, "version", CYAN, "2.5.3 (Streamy)");
        System.out.println("Usage :");
    }

    protected void showError(String err) {
        System.out.println(err);
    }

    public static void main(String[] args) {
        Generator g = new Generator();

        String _p1 = "",
               _p2 = "",
               _p3 = "",
               _p4 = "",
               _p5 = "",
               _p6 = "",
               _p7 = "";
        try {
            if (args.length >= 1) _p1 = args[0];
            if (args.length >= 2) _p2 = args[1];
            if (args.length >= 3) _p3 = args[2];
            if (args.length >= 4) _p4 = args[3];
            if (args.length >= 5) _p5 = args[4];
            if (args.length >= 6) _p6 = args[5];
            if (args.length >= 7) _p7 = args[6];

            if (! _p1.equals("-") && ! _p1.equals("")) g.realPath = _p1;
            if (! _p2.equals("-") && ! _p2.equals("")) {
                if (_p2.equals("make:controller")) {
                    g.targetPath += "controllers/";
                    g.stubName = "Controller.stub";
                    g.replaceContent = "Controller_Stub_Name|" + _p3;
                    g.messages = "New controller has been created.";

                    if (_p3.equals("")) {
                        g.showError("You must enter controller name!");
                        return;
                    }
                } else if (_p2.equals("make:model")) {
                    g.targetPath += "models/";
                    g.stubName = "Model.stub";
                    g.replaceContent = "Model_Stub_Name|" + _p3;
                    g.messages = "New model has been created.";

                    if (_p3.equals("")) {
                        g.showError("You must enter model name!");
                        return;
                    }
                } else if (_p2.equals("serve")) { 
                    Process proc = null;
                    try {
                        if (_p3.equals("--port")) {
                            proc = Runtime.getRuntime().exec(new String[]{
                                g.realPath + "/activator",
                                "~run " + _p4
                            });
                        } else if (_p3.equals("")) {
                            proc = Runtime.getRuntime().exec(new String[]{
                                g.realPath + "/activator",
                                "~run 8000"
                            });
                        }
                        BufferedReader br = new BufferedReader(
                            new InputStreamReader(proc.getInputStream())
                        );
                        String s;
                        while ((s = br.readLine()) != null)
                            System.out.println(s);
                        proc.waitFor();
                        proc.destroy();
                    } catch(Exception e) {
                        System.out.println(e.toString());
                    }
                    return;
                } else {
                    g.showError("Command not found.");
                    return;
                }
            }
            if (! _p3.equals("-") && ! _p3.equals("")) g.targetName = _p3 + ".java";
            if (! _p4.equals("-") && ! _p4.equals("")) g.targetPath = g.realPath + _p4;
            if (! _p5.equals("-") && ! _p5.equals("")) g.stubName = _p5;
            if (! _p6.equals("-") && ! _p6.equals("")) g.stubPath = g.realPath + _p6;
            if (! _p7.equals("-") && ! _p7.equals("")) g.replaceContent = _p7;

            if (_p2.equals("") || _p2.equals("-h") || _p2.equals("--help")) {
                g.showHelp();
            } else {
                String stubLoc = g.stubPath + g.stubName;
                String targetLoc = g.targetPath + g.targetName;
                File file = new File(stubLoc);
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line = "", stubContents = "";
                while((line = reader.readLine()) != null) {
                    stubContents += line + "\r\n";
                }
                reader.close();
                String[] replTxt = g.replaceContent.split("\\|");
                String targetContents = stubContents.replaceAll(replTxt[0], replTxt[1]);
                FileWriter writer = new FileWriter(targetLoc);
                writer.write(targetContents);
                writer.close();

                System.out.println(g.messages); 
            }
        } catch (IOException ie) {
            g.showError(ie.toString());
        }
    }
}
