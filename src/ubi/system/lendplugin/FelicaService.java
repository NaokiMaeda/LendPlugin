package ubi.system.lendplugin;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FelicaService extends Service{
	private	FelicaReader	felicaReader;
	
	public FelicaService(FelicaReader felicaReader) throws FelicaException {
		this.felicaReader = felicaReader;
	}
	
	@Override
	protected Task createTask() {
		return new Task(){
			@Override
			protected String call() throws Exception{
				return felicaReader.getID(FelicaReader.WILDCARD);
			}
		};
	}

}
