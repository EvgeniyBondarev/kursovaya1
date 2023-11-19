import java.util.LinkedList;
import java.util.Scanner;

public class MembershipManagement {
    private final Scanner reader = new Scanner(System.in);
    private int getIntInput(){
        while (!reader.hasNextInt()){
            reader.nextLine();
        }
        return reader.nextInt();
    }
    private void printClubOptions(){
        System.out.println("1) Club Mercury\n" +
                "2) Club Neptune\n" +
                "3) Club Jupiter\n" +
                "4) Multi Clubs\n");
    }
    public int getChoice(){
        System.out.println("\nWELCOME TO OZONE FITNESS CENTER\n" +
                "================================\n" +
                "1) Add Member\n" +
                "2) Remove Member\n" +
                "3) Display Member Information\n");
        System.out.print("Please select an option (or Enter -1 to quit): ");
        int choice = getIntInput();
        return choice;
    }
    public String addMembers(LinkedList<Member> list){
        String name;
        int club;
        String mem;
        double fees;
        int memberID;
        Member mbr;
        Calculator<Integer> cal = n -> {
            switch (n){
                case 1: return 900;
                case 2: return 950;
                case 3: return 1000;
                case 4: return 1200;
                default: return -1;
            }
        };
        System.out.print("Введите имя: ");
        name = reader.next();
        printClubOptions();
        System.out.print("Выберите тип абонемента: ");
        club = getIntInput();
        fees = cal.calculateFees(club);
        memberID = (list.size() == 0) ? 1 : list.getLast().getMemberID() + 1;//(list == null) ? 1 : list.size() + 1;
        mbr = (club == 4) ?
                new MultiClubMember('M', memberID, name, fees, 100) :
                new SingleClubMember('S', memberID, name, fees, club);
        list.addLast(mbr);
        mem = mbr.toString();
        System.out.println("Добавлен пользователь :\n" + mem);

        return mem;
    }
    public void removeMember(LinkedList<Member> members){
        int memberID;
        boolean isRemoved = false;
        do{
            System.out.print("Введите ID, удаляемого пользователя (-1 для выхода): ");
            memberID = getIntInput();
            for (Member m : members) {
                if(m.getMemberID() == memberID) {
                    isRemoved = members.remove(m);
                    break;
                }
            }
            if(!isRemoved)
                System.out.println("Пользователь с таким ID отсутствует.");
        } while (!isRemoved && memberID != -1 );
        System.out.println("Пользователь с ID " + memberID + " удалён.");
    }
    public void printMemberInfo(LinkedList<Member> members) {
        int memberID;
        boolean isFound = false;
        do{
            System.out.print("Введите ID пользователя для просмотра информации о нём (-1 для выхода): ");
            memberID = getIntInput();
            for (Member m : members) {
                if(m.getMemberID() == memberID) {
                    isFound = true;
                    String clubOrPoint = (m.getMemberType() =='S') ? "club" : "point";
                    int clubOrPointNum = (m.getMemberType() =='S') ? ((SingleClubMember)m).getClub() : ((MultiClubMember)m).getMembershipPoints();
                    System.out.printf("[memberType=%s memberID=%d name=%s fees=%.2f %s=%d]\n",
                            m.getMemberType(), m.getMemberID(), m.getName(), m.getFees(), clubOrPoint, clubOrPointNum);
                    break;
                }
            }
            if(!isFound)
                System.out.println("Пользователь с таким ID отсутствует.");
        } while (!isFound && memberID != -1 );
    }
}
