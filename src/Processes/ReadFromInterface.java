package Processes;

import Resources.Resource;
import Resources.ResourceManager;
import Resources.StaticResourceName;
import utils.Logger;

public class ReadFromInterface extends Process {
    private int currentStep = 1;

    public ReadFromInterface(ProcessManager processManager, ResourceManager resourceManager) {
        super(processManager, resourceManager);
    }

    @Override
    public void Step() {
        Resource isVartotojoSasajosResource = resourceManager.GetResource(StaticResourceName.IS_VARTOTOJO_SASAJOS.toString());
        String userInput = isVartotojoSasajosResource.GetElement();

        switch (currentStep) {
            case 1:
                resourceManager.RequestResource(StaticResourceName.IS_VARTOTOJO_SASAJOS.toString(), descriptor);
                ++currentStep;
                break;
            case 2:
                if (userInput.equals("exit")) {
                    currentStep = 3;
                } else {
                    currentStep = 4;
                }

                break;
            case 3:
                resourceManager.FreeResource(StaticResourceName.MOS_PABAIGA.toString(), descriptor.GetParent());
                currentStep = 1;
                break;
            case 4:
                if (userInput.equals("help")) {
                    currentStep = 5;
                } else {
                    currentStep = 6;
                }
                break;
            case 5:
                break;
            case 6:
                break;
        }
    }
}
