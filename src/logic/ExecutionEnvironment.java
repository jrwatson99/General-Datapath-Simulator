package logic;

/*
 * ExecutionEnvironment is the class which contains references to all components within the datapath. There can
 * only be one ExecutionEnvironment, and thus it is represented by a singleton. This class simulates the clock
 * signal in the datapath and initiates data flow. It also transmits all data to and recieves user input from the LogicInterface
 * 
 * 
 */

public class ExecutionEnvironment {
	private static ExecutionEnvironment EESingleton;
	
	public static ExecutionEnvironment getExecutionEnvironment() { return EESingleton;}
}
