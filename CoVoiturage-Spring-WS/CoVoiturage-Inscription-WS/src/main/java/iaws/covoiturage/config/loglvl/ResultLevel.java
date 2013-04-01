package iaws.covoiturage.config.loglvl;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ResultLevel extends Level {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int RESULT_LEVEL_INT = Level.INFO_INT + 1;
	
	/**
     * {@link Level} representing my log level
     */
    public static final Level RESULT = new ResultLevel(RESULT_LEVEL_INT,"RESULT",7);

    private static final String RESULT_MSG = "RESULT";
    
    /**
     * Default constructor.
     */
    protected ResultLevel(int arg0, String arg1, int arg2) {
        super(arg0, arg1, arg2);

    }
    
    public static Level toLevel(String sArg) {
        if (sArg != null && sArg.toUpperCase().equals(RESULT_MSG)) {
            return RESULT;
        }
        return (Level) toLevel(sArg, Level.INFO);
    }
    
    public static Level toLevel(int val) {
        if (val == RESULT_LEVEL_INT) {
            return RESULT;
        }
        return (Level) toLevel(val, Level.INFO);
    }
    
    public static Level toLevel(int val, Level defaultLevel) {
        if (val == RESULT_LEVEL_INT) {
            return RESULT;
        }
        return Level.toLevel(val,defaultLevel);
    }
    
    public static Level toLevel(String sArg, Level defaultLevel) {                 
	    if(sArg != null && sArg.toUpperCase().equals(RESULT_MSG)) {
	        return RESULT;
	    }
	    return Level.toLevel(sArg,defaultLevel);
	 }
    
    public static void main(String[] args) {
		 
		 Logger logger = Logger.getLogger(ResultLevel.class.getName());
		 
		 logger.log(ResultLevel.RESULT,"This is a RESULT level message");
	 }
}
