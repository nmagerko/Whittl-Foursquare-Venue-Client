package data_models;

/**
 * Contains the "stats" about a business, data
 * that may be useful in evaluating popularity/quality
 * @author Nick Magerko
 *
 */
public class Stats {
	private int checkinsCount;
	private int usersCount;
	private int tipCount;
	
	public Stats() {
		
	}
	
	public int getCheckinsCount() {	return checkinsCount; }
	public int getUsersCount() { return usersCount; }
	public int getTipCount() { return tipCount; }
	
	public void setCheckinsCount(int checkinsCount) { this.checkinsCount = checkinsCount; }
	public void setUsersCount(int usersCount) { this.usersCount = usersCount; }
	public void setTipCount(int tipCount) { this.tipCount = tipCount; }
}
