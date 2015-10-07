package ubi.system.lendplugin;

import java.io.IOException;
import java.util.Optional;

import ubi.system.plugin.Plugin;
import ubi.system.plugin.PluginFactory;

public class LendPluginFactory implements PluginFactory{
	LendPlugin	lendPlugin;
	
	@Override
	public String getName() {
		return "貸出";
	}
	
	@Override
	public Optional<Plugin> createPlugin() {
		try{
			lendPlugin = new LendPlugin();
			return	Optional.of(lendPlugin);
		}catch(IOException ex){
			return Optional.empty();
		}
	}
	
	@Override
	public void startPlugin(){
		try {
			lendPlugin.startPlugin();
		} catch (FelicaException e) {}
	}
	
}
