package dqdatabase;

public class RegenRule{
	public static String OnMonday = new String("OnMonday");
	public static String OnTuesday = new String("OnTuesday");
	public static String OnWednesday = new String("OnWednesday");
	public static String OnThursday = new String("OnThursday");
	public static String OnFriday = new String("OnFriday");
	public static String OnSaturday = new String("OnSaturday");
	public static String OnSunday = new String("OnSunday");
	
	public String at(int i){
		if(0 < i & i < 32) {
			return String.format("at%d", i);
		}
		else {
			return null;
		}
	}
	
	// TODO regen_coolTime
	// TODO early remind
	
}