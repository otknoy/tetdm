
//
// Mining module for TETDM 
// MiningStyle.java Version 0.30 
// This is one of the sample modules.
// You should read the README file.
//

package module.MiningModules.MiningStyle;		// Replace ALL [MiningStyle] with your [module name]

import source.*;
import source.TextData.*;
//import source.Utility.*;

//import java.awt.event.*;

public class MiningStyle extends MiningModule //implements ActionListener
{
	public MiningStyle()
	{
		setModuleID(0);						// Set your module ID after you have got it
		pairingVisualizationID = new int[]{0,1005,1009,1013,1015,1016,1017};
		setToolType(1);
	}
	
	public void miningOperations(int optionNumber)
	{
		switch(optionNumber)
		{
			case 0:			
				break;
		}
	}
}
