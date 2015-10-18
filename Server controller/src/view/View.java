package view;

import java.util.concurrent.ConcurrentHashMap;

import presenter.RemoteControlCommand;

public interface View {
	
	
	public RemoteControlCommand getCommand();
	
	public void setCommands(ConcurrentHashMap<String, RemoteControlCommand> commandMap);
	
	public void Display(String msg);
	
	public void saveData(String data);
	
	public void addClient(String Client);
	
	public void removeClient(String Client);
	
	public void setUserCommand(RemoteControlCommand userCommand);

	void DisplayStatus(String msg);

}
