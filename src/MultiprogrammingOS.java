import Machine.RealMachine;
import Processes.ProcessManager;
import Resources.Resource;
import Resources.ResourceManager;
import Resources.StaticResourceName;
import utils.Logger;

import java.io.IOException;

public class MultiprogrammingOS {
    public static void main(String[] args) throws IOException {
        RealMachine realMachine = new RealMachine();
        ProcessManager processManager = new ProcessManager(realMachine);

        StringBuilder buffer = new StringBuilder();

        while (true) {
            if (System.in.available() > 0) {
                int ch = System.in.read();
                if (ch == '\n' || ch == '\r') {
                    String input = buffer.toString();
                    buffer.setLength(0);
                    ResourceManager resourceManager = processManager.GetResourceManager();
                    // pass input string in IS_VARTOTOJO_SASAJOS resource
                    Resource resource = resourceManager.GetResource(StaticResourceName.IS_VARTOTOJO_SASAJOS.toString());
                    resource.SetElement(input);
                    resourceManager.FreeResource(StaticResourceName.IS_VARTOTOJO_SASAJOS.toString(), processManager.GetProcessByName("ReadFromInterface"));
                } else {
                    buffer.append((char) ch);
                }
            }

            int result = processManager.ExecutePlanner();

            if (result == -1) {
                Logger.debug("Terminating");
                break;
            }
        }
    }
}
