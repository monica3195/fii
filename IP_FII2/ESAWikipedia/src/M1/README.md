## Usage

Create a VSM instance with input and output paths.
		
	vsm.Vsm vsm = new vsm.Vsm("input", "output");
	
Optional, set the event dispatcher.

	vsm.events.Dispatcher events = new vsm.events.ProgressDispatcher();
	vsm.setDispatcher(events);
		
Start processing.
	
	vsm.start();

## Output

[Link to the final output distances archive.](https://www.dropbox.com/s/6vztunig1morr2q/M1_Output.zip?dl=0)
