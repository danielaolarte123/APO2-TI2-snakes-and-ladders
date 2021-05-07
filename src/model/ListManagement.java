package model;

public class ListManagement {
	
	private List start;
	private List end;
	private User first;
	private User last;
	private String table;
	private String table2;
	
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RESET = "\u001B[0m";
	
	
	public ListManagement() {
		start=null;
		end=null;
		table="";
		table2="";
	}
	
	/* ------------------------------------------------------WORKING WITH THE CELLS----------------------------------------------------------*/
	
	public void addList(int rowXcolumns) {  
		if(start==null) {
			List newList =  new List(rowXcolumns);
			start = newList;
			end = newList;
		}else {
			List newList =  new List(rowXcolumns);
			end.setNextList(newList);
			end=newList;
		}
	}
	
	public List searchList(int pos) {  
		return searchList(pos, start);
	}
	
	private List searchList(int pos, List temp) {
		if(temp.getRowXcolumn()==pos) {
			return temp;
		}else {
			temp=temp.getNextList();
			return searchList(pos, temp);
		}
	}
	
	public void assignSpaces(int i) {
		assignSpaces(i,start);
	}
	
	private void assignSpaces(int i, List temporal) {
		if(i>0) {
		temporal.setPlayers("");
		temporal=temporal.getNextList();
		assignSpaces(i=i-1,temporal);
		}
	}
	
	public void assignPlayer(String players) {
		end.setPlayers(players);
	}
	
	/* ------------------------------------------------------BINARY TREE ABOUT THE POSITIONS----------------------------------------------------------*/
	
	public void addPosition(String nickname, int points) {
		
	}
	
	/* ------------------------------------------------------WORKING WITH SNAKES AND LADDERS----------------------------------------------------------*/
	
	public boolean checkLadder(List ladders) {
		boolean found = false;
		if(ladders.getLadders()==0) {
			found =true;
		}
		return found;
	}
	
	public void putLadder(char ladder, List pos) {
		pos.setLadders(ladder);
	}
	
	public boolean checkSnake(List snakes) {
		boolean found = false;
		if(snakes.getSnakes()==0) {
			found =true;
		}
		return found;
	}
	
	public void putSnake(char snake, List pos) {
		pos.setSnakes(snake);
	}
	
	public List searchSnake(char snake) {
		return searchSnake(snake, start);
	}
	
	private List searchSnake(char s, List temp) {
		if(temp.getSnakes()==s) {
			return temp;
		}else {
			temp=temp.getNextList();
			return searchSnake(s, temp);
		}
	}
	
	public List searchLadder(char ladder) {
		return searchLadder(ladder, start);
	}
	
	private List searchLadder(char l, List temp) {
		if(temp.getLadders()==l) {
			return temp;
		}else {
			temp=temp.getNextList();
			return searchLadder(l, temp);
		}
	}
	
	/* ------------------------------------------------------WORKING WITH THE PARTICIPANTS/PLAYERS----------------------------------------------------------*/
	
	public void addPlayer(char user, int order) {
		if(first==null) {
			User newUser =  new User(user, order);
			first = newUser;
			last = newUser;
		}else {
			User newUser =  new User(user, order);
			last.setNextUser(newUser);
			last=newUser;
		}
	}
	
	public User searchUser(int pos) {
		return searchUser(pos, first);
	}
	
	public User searchUser(int pos, User temp) {
		if(temp.getOrder()==pos) {
			return temp;
		}else {
			temp=temp.getNextUser();
			return searchUser(pos, temp);
		}
	}
	
	public boolean findUsers(List winner) {
		boolean win= true;
		if(winner.getPlayers()==start.getPlayers()) {
			win=false;
		}
		return win;
	}
	
	public boolean win(boolean f) {
		return f;
	}
	
	public boolean someWinner(User user) {
		boolean found=true;
		if(user!=null) {
			if(start.getPlayers()==String.valueOf(user.getPlayer())) {
				
				found=false;
			}else {
				user = user.getNextUser();
				someWinner(user);
			}
		}
		return found;
	}
	
	public String winner() {
		return start.getPlayers();
	}
	
	private boolean samePlayer(User p, List same) {
		boolean found = false;
		char users = 0;
		if(same.getPlayers().isEmpty()) {
		}else {
			users = same.getPlayers().charAt(0);
		}
	
		if(users==p.getPlayer()) {
				found=true;
				return found;
		}else {
			found=false;
			return found;
		}
	}
	
	public void countMovements(int turn) {
		User temp = searchUser(turn);
		int amountMove = temp.getMoves()+1;
		temp.setMoves(amountMove);
	}
	
	public int totalMovements(User p) {
		return p.getMoves();
	}
	
	/* ------------------------------------------------------MOVING THE PARTICIPANTS/PLAYERS----------------------------------------------------------*/
	
	public boolean movePlayers(char p, int pos, int moves) {
		return movePlayers(p, pos, moves, start);
	}
	
	public boolean movePlayers(char p, int pos, int moves,List temporal) {
		User player = new User(p, pos);
		
		if(temporal.getPlayers()!="") {
			if(samePlayer(player,temporal)) {
				temporal.setPlayers(temporal.getPlayers().replace(String.valueOf(player.getPlayer()), ""));
				int currentPos = temporal.getRowXcolumn();
				int newPos = currentPos+moves;
				if(searchList(maxCells(newPos))!=start) {
					if(checkLadder(searchList(newPos))) {  //**-------------------------------PLAYER IN THE NORMAL ROAD--------------------------------------**//
						if(checkSnake(searchList(newPos))) { 
							temporal= searchList(newPos);
							if(temporal.getPlayers()!="") {	
								temporal.setPlayers(temporal.getPlayers().replace(temporal.getPlayers(), temporal.getPlayers()+String.valueOf(player.getPlayer())));
								return false;
							}else {
								temporal.setPlayers(String.valueOf(player.getPlayer()));
								return false;
							}
						}else { //**-------------------------------------PLAYER FELL ON SNAKE--------------------------------------------**//
						temporal= searchList(newPos);
						moveAnotherSnake(temporal, player);
						return false;
						}
					}else {  //**-----------------------------------PLAYER FELL ON LADDER--------------------------------------------**//
						temporal=searchList(newPos);
						
						return moveAnotherLadder(temporal, player);
					}
				}else {
					temporal = start;
					temporal.setPlayers(String.valueOf(player.getPlayer()));
					return true;
				}	
			}else {
				temporal=temporal.getNextList();
				return movePlayers(p, pos, moves,temporal);
			}
		}else {
			temporal=temporal.getNextList();
			return movePlayers(p,pos, moves,temporal);
		}
	}
	
	private void moveAnotherSnake(List snake, User player) {
		char snake_1 = snake.getSnakes();
		List foundSnake = searchSnake(snake_1);
		if(foundSnake.getRowXcolumn()==snake.getRowXcolumn()) {
			foundSnake = searchSnake(snake_1, foundSnake.getNextList());
			if(foundSnake.getPlayers()!="") {
				foundSnake.setPlayers(foundSnake.getPlayers().replace(foundSnake.getPlayers(), foundSnake.getPlayers()+String.valueOf(player.getPlayer())));
			}else {
				foundSnake.setPlayers(String.valueOf(player.getPlayer()));
			}
		}else {
			if(snake.getPlayers()!="") {
				snake.setPlayers(snake.getPlayers().replace(snake.getPlayers(), snake.getPlayers()+String.valueOf(player.getPlayer())));
			}else {
				snake.setPlayers(String.valueOf(player.getPlayer()));
			}
		}
	}
	
	private boolean moveAnotherLadder(List ladder, User player) {
		char ladder_1 = ladder.getLadders();
		List foundLadder = searchLadder(ladder_1);
		if(foundLadder.getRowXcolumn()!=ladder.getRowXcolumn()) {
			if(foundLadder!=start) {
				if(foundLadder.getPlayers()!="") {
					foundLadder.setPlayers(foundLadder.getPlayers().replace(foundLadder.getPlayers(), foundLadder.getPlayers()+String.valueOf(player.getPlayer())));
					return false;
				}else {
					foundLadder.setPlayers(String.valueOf(player.getPlayer()));
					return false;
				}
			}else {
				foundLadder.setPlayers(String.valueOf(player.getPlayer()));
				return true;
			}
		}else {
			if(ladder.getPlayers()!="") {
				ladder.setPlayers(ladder.getPlayers().replace(ladder.getPlayers(), ladder.getPlayers()+String.valueOf(player.getPlayer())));
				return false;
			}else {
				ladder.setPlayers(String.valueOf(player.getPlayer()));
				return false;
			}
		}	
	}
	
	public int maxCells(int newPos) {
		int maxCell = start.getRowXcolumn();
		if(newPos>maxCell) {
			newPos=maxCell;
		}
		return newPos;
	}
	

	/* ------------------------------------------------------SHOW THE TABLES AND EACH POSITION----------------------------------------------------------*/
	
	private String showColumnsPrincipals(int column, int row,List temp, int i) {
		int oddOrEven=row%2;
		
		if(row!=0) {
			if(column>=1){
				if (oddOrEven==0) {
					i=i+1;
					table=table+"[ "+temp.getRowXcolumn()+/*ANSI_BLACK+*/temp.getLadders()+/*ANSI_RESET*/temp.getSnakes()+"]";
					return showColumnsPrincipals(column=column-1, row, temp.getNextList(), i);
				}else {
					i=i+1;
					int n=temp.getRowXcolumn()-column+i;
					table=table+"[ "+searchList(n).getRowXcolumn()+/*ANSI_BLACK+*/searchList(n).getLadders()+/*ANSI_RESET*/
							searchList(n).getSnakes()+"]";
					return showColumnsPrincipals(column=column-1, row, temp.getNextList(), i);
				}
			}else {
				table=table+"\n";
				return showColumnsPrincipals(i, row=row-1, temp,0);
			}
		}else {
			return table;
		}
	}
	
	private String showColumns(int column, int row,List temp, int i) {
		int oddOrEven=row%2;
		
		if(row!=0) {
			if(column>=1){
				if (oddOrEven==0) {
					i=i+1;
					table2=table2+"["+/*ANSI_BLACK+*/temp.getLadders()+/*ANSI_RESET*/temp.getSnakes()+temp.getPlayers()+"]";
					return showColumns(column=column-1, row, temp.getNextList(), i);
				}else {
					i=i+1;
					int n=temp.getRowXcolumn()-column+i;
					table2=table2+"["+/*ANSI_BLACK+*/searchList(n).getLadders()+/*ANSI_RESET*/
							searchList(n).getSnakes()+searchList(n).getPlayers()+"]";
					return showColumns(column=column-1, row, temp.getNextList(), i);
				}
			}else {
				table2=table2+"\n";
				return showColumns(i, row=row-1, temp,0);
			}
		}else {
			return table2;
		}
	}
	
	public String showContentPrincipal(int row, int column) {
		table="";
		return showColumnsPrincipals(column, row, start, 0);
	}
	
	public String showContent(int row, int column) {
		table2="";
		return showColumns(column, row, start, 0);
	}
	
}
